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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(Parameterized.class)
@SpringBootTest
public class RecordGetErrorTests {

	@ClassRule
	public static final SpringClassRule springClassRule = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Parameters
	public static Object[] getParams() {
		return new Object[] {true, false};
	}


	String nonExistentNSendpoint = null;
	String nonExistentRecordEndpoint = null;
	String invalidKeytypeEndpoint = null;
	String invalidIntegerEndpoint = null;
	String invalidBytesEndpoint = null;
	String invalidDigestEndpoint = null;

	public  RecordGetErrorTests(boolean useSet) {
		if (useSet) {
			nonExistentNSendpoint = ASTestUtils.buildEndpoint("kvs", "fakeNS", "demo", "1");
			nonExistentRecordEndpoint = ASTestUtils.buildEndpoint("kvs", "test", "demo", "thisisnotarealkeyforarecord");
			invalidKeytypeEndpoint = ASTestUtils.buildEndpoint("kvs", "test", "demo", "1") + "?keytype=skeleton";
			invalidIntegerEndpoint = ASTestUtils.buildEndpoint("kvs", "test", "demo", "key") + "?keytype=INTEGER";
			invalidBytesEndpoint = ASTestUtils.buildEndpoint("kvs", "test", "demo", "/=") + "?keytype=BYTES"; /*Invalid urlsafe bae64*/
			invalidDigestEndpoint = ASTestUtils.buildEndpoint("kvs", "test", "demo", "key") + "?keytype=DIGEST";
		} else {
			nonExistentNSendpoint = ASTestUtils.buildEndpoint("kvs", "fakeNS",  "1");
			nonExistentRecordEndpoint = ASTestUtils.buildEndpoint("kvs", "test", "thisisnotarealkeyforarecord");
			invalidKeytypeEndpoint = ASTestUtils.buildEndpoint("kvs", "test", "1") + "?keytype=skeleton";
			invalidIntegerEndpoint = ASTestUtils.buildEndpoint("kvs", "test", "key") + "?keytype=INTEGER";
			invalidBytesEndpoint = ASTestUtils.buildEndpoint("kvs", "test", "/=") + "?keytype=BYTES"; /*Invalid urlsafe bae64*/
			invalidDigestEndpoint = ASTestUtils.buildEndpoint("kvs", "test", "key") + "?keytype=DIGEST";
		}
	}


	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMVC;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMVC = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void GetFromNonExistentNS() throws Exception {

		MvcResult result = mockMVC.perform(
				get(nonExistentNSendpoint)
				).andExpect(status().isInternalServerError()).andReturn();

		MockHttpServletResponse res = result.getResponse();
		String resJson = res.getContentAsString();
		TypeReference<Map<String, Object>> sOMapType= new TypeReference<Map<String, Object>>() {};
		Map<String, Object>resObject = objectMapper.readValue(resJson, sOMapType);

		Assert.assertFalse((boolean) resObject.get("inDoubt"));
	}

	@Test
	public void GetNonExistentRecord() throws Exception {

		MvcResult result = mockMVC.perform(
				get(nonExistentRecordEndpoint)
				).andExpect(status().isNotFound()).andReturn();

		MockHttpServletResponse res = result.getResponse();
		String resJson = res.getContentAsString();
		TypeReference<Map<String, Object>> sOMapType= new TypeReference<Map<String, Object>>() {};
		Map<String, Object>resObject = objectMapper.readValue(resJson, sOMapType);

		Assert.assertFalse((boolean) resObject.get("inDoubt"));
	}

	@Test
	public void GetWithInvalidKeyType() throws Exception {
		mockMVC.perform(
				get(invalidKeytypeEndpoint)
				).andExpect(status().isBadRequest());

	}

	@Test
	public void GetWithInvalidIntegerKey() throws Exception {
		mockMVC.perform(
				get(invalidIntegerEndpoint)
				).andExpect(status().isBadRequest());

	}

	@Test
	public void GetWithInvalidBytesKey() throws Exception {
		mockMVC.perform(
				get(invalidBytesEndpoint) /*This has an illegally encoded urlsafebase64 */
				).andExpect(status().isBadRequest());

	}

	@Test
	public void GetWithInvalidDigestKey() throws Exception {
		mockMVC.perform(
				get(invalidDigestEndpoint) /* This is not 20 bytes long */
				).andExpect(status().isBadRequest());

	}
}