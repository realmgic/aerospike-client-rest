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
package com.aerospike.restclient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aerospike.restclient.domain.RestClientError;
import com.aerospike.restclient.service.AerospikeTruncateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@Api(tags="Truncate Operations", description="Remove multiple records from the server.")
@RestController
@RequestMapping("/v1/truncate")
public class TruncateController {

	@Autowired private AerospikeTruncateService truncateService;

	@RequestMapping(method=RequestMethod.DELETE, value="/{namespace}", produces={"application/json", "application/msgpack"})
	@ApiOperation(value="Truncate records in a specified namespace.", nickname="truncateNamespace")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to access the resource",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=400, response=RestClientError.class, message = "Invalid parameters or request",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))

	})
	public void truncateNamespace(
			@ApiParam(value="The namespace whose records will be truncated.", required=true) @PathVariable(value="namespace")String namespace,
			@ApiParam(value="${RestClient.Truncate.dateQueryParam.notes}", required=false, example="2019-12-03T10:15:30+01:00") @RequestParam(value="date", required=false) String dateString) {
		truncateService.truncate(namespace, null, dateString);
	}


	@RequestMapping(method=RequestMethod.DELETE, value="/{namespace}/{set}", produces={"application/json", "application/msgpack"})
	@ApiOperation(value="Truncate records in a specified namespace and set.", nickname="truncateSet")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to access the resource",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=400, response=RestClientError.class, message = "Invalid parameters or request",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))

	})
	public void truncateSet(
			@ApiParam(value="The namespace whose records will be truncated", required=true) @PathVariable(value="namespace")String namespace,
			@ApiParam(value="The set, in the specified namespace, whose records will be truncated", required=true)@PathVariable(value="set")String set,
			@ApiParam(value="${RestClient.Truncate.dateQueryParam.notes}", required=false, example="2019-12-03T10:15:30+01:00") @RequestParam(value="date", required=false) String dateString) {
		truncateService.truncate(namespace, set, dateString);
	}

}