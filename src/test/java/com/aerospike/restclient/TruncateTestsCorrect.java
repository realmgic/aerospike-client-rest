/*
 * Copyright 2019 Aerospike, Inc.
 *
 * Portions may be licensed to Aerospike, Inc. under one or more contributor
 * license agreements WHICH ARE COMPATIBLE WITH THE APACHE LICENSE, VERSION 2.0.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.aerospike.restclient;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TruncateTestsCorrect {

	private MockMvc mockMVC;
	private String truncateSplitPoint;
	DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
	private String testEndpoint = "/v1/truncate/";
	private Key otherKey = new Key("test", "otherset", 0);
	private List<Key>preCutoffKeys;
	private List<Key>postCutoffKeys;
	@Autowired
	private AerospikeClient client;


	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() throws InterruptedException {
		mockMVC = MockMvcBuilders.webAppContextSetup(wac).build();
		preCutoffKeys = new ArrayList<Key>();
		postCutoffKeys = new ArrayList<Key>();
		for (int i = 0; i < 10; i++) {
			Key key = new Key("test", "truncate", i);
			preCutoffKeys.add(key);
			Bin bin = new Bin("before", "the cutoff");
			client.put(null, key, bin);
		}

		Thread.sleep(2000); //Allow some time to pass
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
		truncateSplitPoint = formatter.format(cal.toZonedDateTime());
		/* Truncate is asynchronous, so wait for it to propgate to other servers */
		Thread.sleep(2000);
		for (int i = 10; i < 20; i++) {
			Key key = new Key("test", "truncate", i);
			Bin bin = new Bin("after", "the cutoff");
			client.put(null, key, bin);
			postCutoffKeys.add(key);
		}

		// Store a record in a different set
		client.put(null, otherKey, new Bin("a", "b"));
	}

	@After
	public void clean() {
		for (Key key: preCutoffKeys) {
			try {
				client.delete(null, key);
			} catch (AerospikeException e) {
				;
			}
		}
		for (Key key: postCutoffKeys) {
			try {
				client.delete(null, key);
			} catch (AerospikeException e) {
				;
			}
		}
		client.delete(null, otherKey);
	}

	@Test
	public void TruncateWithNoCutoff() throws Exception {

		mockMVC.perform(delete(testEndpoint + "test/truncate")).andExpect(status().isAccepted());

		Thread.sleep(5000);
		boolean stillExists = false;
		for (Key key : preCutoffKeys) {
			Record record = client.get(null, key);
			if (record != null) {
				stillExists = true;
				break;
			}
		}

		for (Key key : postCutoffKeys) {
			Record record = client.get(null, key);
			if (record != null) {
				stillExists = true;
				break;
			}
		}

		Assert.assertFalse(stillExists);
		Record otherRecord = client.get(null, otherKey);
		Assert.assertNotNull(otherRecord);
	}

	@Test
	public void TruncateEntireNS() throws Exception {

		mockMVC.perform(delete(testEndpoint + "test")).andExpect(status().isAccepted());

		Thread.sleep(5000);
		boolean stillExists = false;
		for (Key key : preCutoffKeys) {
			Record record = client.get(null, key);
			if (record != null) {
				stillExists = true;
				break;
			}
		}

		for (Key key : postCutoffKeys) {
			Record record = client.get(null, key);
			if (record != null) {
				stillExists = true;
				break;
			}
		}

		Assert.assertFalse(stillExists);
		Record otherRecord = client.get(null, otherKey);
		Assert.assertNull(otherRecord);
	}

	@Test
	public void TruncateWithCutoff() throws Exception {

		mockMVC.perform(delete(testEndpoint + "test/truncate?date=" + truncateSplitPoint))
		.andExpect(status().isAccepted());

		Thread.sleep(5000);
		boolean preStillExists = false;
		boolean postStillExists = true;
		for (Key key : preCutoffKeys) {
			Record record = client.get(null, key);
			if (record != null) {
				preStillExists = true;
				break;
			}
		}
		Assert.assertFalse(preStillExists);

		for (Key key : postCutoffKeys) {
			Record record = client.get(null, key);
			if (record == null) {
				postStillExists = true;
				break;
			}
		}

		Assert.assertTrue(postStillExists);
		Record otherRecord = client.get(null, otherKey);
		Assert.assertNotNull(otherRecord);
	}

}
