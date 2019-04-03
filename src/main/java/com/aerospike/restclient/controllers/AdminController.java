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

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aerospike.client.admin.User;
import com.aerospike.restclient.domain.RestClientError;
import com.aerospike.restclient.domain.RestClientPrivilege;
import com.aerospike.restclient.domain.RestClientRole;
import com.aerospike.restclient.domain.RestClientUserModel;
import com.aerospike.restclient.service.AerospikeAdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@Api(tags="Admin Operations", description="Manage users and privileges.")
@RestController
@RequestMapping("/v1/admin")
public class AdminController {

	@Autowired private AerospikeAdminService adminService;

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to read user information",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))})
	@ApiOperation(value="Return a list of information about users.", nickname="getUsers")
	@RequestMapping(method=RequestMethod.GET, value="/user", produces= {"application/json", "application/msgpack"})
	public User[] getUsers() {
		return adminService.getUsers();
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to read user information",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=404, response=RestClientError.class, message = "Specified user not found",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Return information about a specific user.", nickname="getUser")
	@RequestMapping(method=RequestMethod.GET, value="/user/{user}", produces={"application/json", "application/msgpack"})
	public User getUser(@ApiParam(required=true) @PathVariable(value="user") String user) {
		return adminService.getUser(user);
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to delete users.",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=404, response=RestClientError.class, message = "Specified user not found",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Remove a user.", nickname="dropUser")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@RequestMapping(method=RequestMethod.DELETE, value="/user/{user}", produces= {"application/json", "application/msgpack"})
	public void dropUser(@ApiParam(required=true) @PathVariable(value="user")String user) {
		adminService.dropUser(user);
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to modify users.",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=404, response=RestClientError.class, message = "Specified user not found",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Change the password of the specified user.", nickname="changePassword")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@RequestMapping(method=RequestMethod.PATCH, value="/user/{user}", consumes={"application/json", "application/msgpack"}, produces= {"application/json", "application/msgpack"})
	public void changePassword(@ApiParam(required=true) @PathVariable(value="user")String user,
			@ApiParam(required=true) @RequestBody String password) {
		adminService.changePassword(user, password);
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to create users.",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=400, response=RestClientError.class, message = "Invalid user creation parameters",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=409, response=RestClientError.class, message = "User already exists.",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Create a new user.", nickname="createUser")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@RequestMapping(method=RequestMethod.POST, value="/user",
	consumes={"application/json", "application/msgpack"}, produces= {"application/json", "application/msgpack"})
	public void createUser(@ApiParam(required=true) @Valid @RequestBody RestClientUserModel userInfo) {
		adminService.createUser(userInfo);
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to modify users.",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=404, response=RestClientError.class, message = "Specified user not found",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=400, response=RestClientError.class, message = "Invalid role parameters",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Grant a set of roles to the specified user.", nickname="grantRoles")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@RequestMapping(method=RequestMethod.POST, value="/user/{user}/role",
	consumes={"application/json", "application/msgpack"}, produces= {"application/json", "application/msgpack"})
	public void grantRoles(@ApiParam(required=true) @PathVariable(value="user")String user,
			@ApiParam(required=true) @RequestBody List<String>roles) {
		adminService.grantRoles(user, roles);
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to modify users.",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=404, response=RestClientError.class, message = "Specified user not found",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=400, response=RestClientError.class, message = "Invalid role parameters",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Revoke a set of roles from the specified user.", nickname="revokeRoles")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@RequestMapping(method=RequestMethod.PATCH, value="/user/{user}/role/delete",
	consumes={"application/json", "application/msgpack"}, produces= {"application/json", "application/msgpack"})
	public void revokeRoles(
			@PathVariable(value="user") @ApiParam(value="The user from which to revoke roles", required=true) String user,
			@RequestBody @ApiParam(value="A list of names to revoke from the user", required=true)List<String>roles) {
		adminService.revokeRoles(user, roles);
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to read role information",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))})
	@ApiOperation(value="Return a list of all roles registered with the Aerospike cluster.", nickname="getRoles")
	@RequestMapping(method=RequestMethod.GET, value="/role", produces= {"application/json", "application/msgpack"})
	public List<RestClientRole> getRoles() {

		return adminService.getRoles();
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to create roles.",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=400, response=RestClientError.class, message = "Invalid role creation parameters",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=409, response=RestClientError.class, message = "Role already exists.",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Create a role on the Aerospike cluster.", nickname="createRole")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@RequestMapping(method=RequestMethod.POST, value="/role",
	consumes={"application/json", "application/msgpack"}, produces= {"application/json", "application/msgpack"})
	public void createRole(@ApiParam(required=true) @RequestBody RestClientRole rcRole) {

		adminService.createRole(rcRole);
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to read role information",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=404, response=RestClientError.class, message = "Specified role not found",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Get information about a specific role.", nickname="getRole")
	@RequestMapping(method=RequestMethod.GET, value="/role/{name}", produces= {"application/json", "application/msgpack"})
	public RestClientRole getRole(
			@ApiParam(value="The name of the role whose information should be retrieved.", required=true) @PathVariable(value="name")String role) {

		return adminService.getRole(role);
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to remove roles",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=404, response=RestClientError.class, message = "Specified role not found",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Remove a specific role from the Aerospike cluster.", nickname="dropRole")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@RequestMapping(method=RequestMethod.DELETE, value="/role/{name}", produces= {"application/json", "application/msgpack"})
	public void dropRole(
			@ApiParam(value="The name of the role to remove.", required=true) @PathVariable(value="name")String role) {

		adminService.dropRole(role);
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to modfiy roles.",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=400, response=RestClientError.class, message = "Invalid privilege parameters",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=404, response=RestClientError.class, message = "Specified role not found",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Grant a list of privileges to a specific role.", nickname="grantPrivileges")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@RequestMapping(method=RequestMethod.POST, value="/role/{name}/privilege",
	consumes={"application/json", "application/msgpack"}, produces= {"application/json", "application/msgpack"})
	public void grantPrivileges(
			@ApiParam(value="The name of the role to which privileges will be granted.", required=true) @PathVariable(value="name") String name,
			@ApiParam(required=true) @RequestBody List<RestClientPrivilege> privileges) {

		adminService.grantPrivileges(name, privileges);
	}

	@ApiResponses(value= {
			@ApiResponse(code=403, response=RestClientError.class, message = "Not authorized to modfiy roles.",
					examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=400, response=RestClientError.class, message = "Invalid privilege parameters",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")})),
			@ApiResponse(code=404, response=RestClientError.class, message = "Specified role not found",
			examples= @Example(value = {@ExampleProperty(mediaType="Example json", value = "{'inDoubt': false, 'message': 'A message' ")}))
	})
	@ApiOperation(value="Remove a list of privileges from a specific role.", nickname="revokePrivileges")
	@ResponseStatus(value=HttpStatus.ACCEPTED)
	@RequestMapping(method=RequestMethod.PATCH, value="/role/{name}/privilege/delete",
	consumes={"application/json", "application/msgpack"}, produces= {"application/json", "application/msgpack"})
	public void revokePrivileges(
			@ApiParam(value="The name of the role from which privileges will be removed.", required=true) @PathVariable(value="name") String name,
			@ApiParam(required=true) @RequestBody List<RestClientPrivilege> privileges) {
		adminService.revokePrivileges(name, privileges);
	}
}