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
package com.aerospike.restclient.controller;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.restclient.ASTestUtils;
import com.aerospike.restclient.ASTestUtils.WritePolicyMatcher;
import com.aerospike.restclient.ASTestUtils.WritePolicyMatcher.WritePolicyComparator;
import com.aerospike.restclient.controllers.KeyValueController;
import com.aerospike.restclient.service.AerospikeRecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
/*
 * These methods are almost identical except they end up using a different recordExists action for the actual
 * storage
 *
 */
public class KVControllerV1PutPostPatchTests {

	private String ns = "test";
	private String set = "set";
	private String key = "key";

	private Map<String, Object> dummyBins;
	private Map<String, String> queryParams;
	private WritePolicy expectedPolicy = new WritePolicy();

	private byte[] msgpackBins;
	private ObjectMapper mpMapper = new ObjectMapper(new MessagePackFactory());

	private WritePolicyComparator existsActionComparator = new WritePolicyComparator() {
		@Override
		public boolean comparePolicy(WritePolicy p1, WritePolicy p2) {
			return p1.recordExistsAction == p2.recordExistsAction;
		}
	};

	@Before
	public void setup() throws JsonProcessingException {
		dummyBins = new HashMap<String, Object>();
		dummyBins.put("bin", "a");
		msgpackBins = mpMapper.writeValueAsBytes(dummyBins);
		queryParams = new HashMap<String, String>();
	}

	@Autowired KeyValueController controller;
	@MockBean AerospikeRecordService recordService;

	/* Create/Post */
	@Test
	public void testCreateNSSetKey() {
		expectedPolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.createRecordNamespaceSetKey(ns, set, key, dummyBins, queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), eq(set), eq(key), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	@Test
	public void testCreateNSKey() {
		expectedPolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.createRecordNamespaceKey(ns, key, dummyBins, queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), isNull(), eq((key)), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	@Test
	public void testCreateNSSetKeyMP() {
		expectedPolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.createRecordNamespaceSetKeyMP(ns, set, key, new ByteArrayInputStream(msgpackBins), queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), eq(set), eq(key), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	@Test
	public void testCreateNSKeyMP() {

		expectedPolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.createRecordNamespaceKeyMP(ns, key, new ByteArrayInputStream(msgpackBins), queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), isNull(), eq((key)), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	/* Update/Patch */
	@Test
	public void testUpdateNSSetKey() {
		expectedPolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.updateRecordNamespaceSetKey(ns, set, key, dummyBins, queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), eq(set), eq(key), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	@Test
	public void testUpdateNSKey() {
		expectedPolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.updateRecordNamespaceKey(ns, key, dummyBins, queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), isNull(), eq((key)), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	@Test
	public void testUpdateNSSetKeyMP() {
		expectedPolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.updateRecordNamespaceSetKeyMP(ns, set, key, new ByteArrayInputStream(msgpackBins), queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), eq(set), eq(key), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	@Test
	public void testUpdateNSKeyMP() {

		expectedPolicy.recordExistsAction = RecordExistsAction.UPDATE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.updateRecordNamespaceKeyMP(ns, key, new ByteArrayInputStream(msgpackBins), queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), isNull(), eq((key)), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	/* Replace/Put */
	@Test
	public void testReplaceNSSetKey() {
		expectedPolicy.recordExistsAction = RecordExistsAction.REPLACE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.replaceRecordNamespaceSetKey(ns, set, key, dummyBins, queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), eq(set), eq(key), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	@Test
	public void testReplaceNSKey() {
		expectedPolicy.recordExistsAction = RecordExistsAction.REPLACE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.replaceRecordNamespaceKey(ns, key, dummyBins, queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), isNull(), eq((key)), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	@Test
	public void testReplaceNSSetKeyMP() {
		expectedPolicy.recordExistsAction = RecordExistsAction.REPLACE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.replaceRecordNamespaceSetKeyMP(ns, set, key, new ByteArrayInputStream(msgpackBins), queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), eq(set), eq(key), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

	@Test
	public void testReplaceNSKeyMP() {

		expectedPolicy.recordExistsAction = RecordExistsAction.REPLACE_ONLY;
		WritePolicyMatcher createActionMatcher = new ASTestUtils.WritePolicyMatcher(expectedPolicy, existsActionComparator);
		controller.replaceRecordNamespaceKeyMP(ns, key, new ByteArrayInputStream(msgpackBins), queryParams);

		verify(recordService, Mockito.only()).storeRecord(
				eq(ns), isNull(), eq((key)), eq(dummyBins), isNull(),
				argThat(createActionMatcher));
	}

}
