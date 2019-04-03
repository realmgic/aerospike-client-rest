/*
 * WARNING: DO NOT EDIT THIS FILE. This file is generated by yarn gen. Any changes will be overwritten.
 */

import * as I from 'immutable';
import {
    AdminOperationsAction,
    GET_ROLES_SUCCESSFUL,
    GET_ROLES_FAILED,
    GET_ROLES_IN_PROGRESS,
    CREATE_ROLE_SUCCESSFUL,
    CREATE_ROLE_FAILED,
    CREATE_ROLE_IN_PROGRESS,
    GET_ROLE_SUCCESSFUL,
    GET_ROLE_FAILED,
    GET_ROLE_IN_PROGRESS,
    DROP_ROLE_SUCCESSFUL,
    DROP_ROLE_FAILED,
    DROP_ROLE_IN_PROGRESS,
    GRANT_PRIVILEGES_SUCCESSFUL,
    GRANT_PRIVILEGES_FAILED,
    GRANT_PRIVILEGES_IN_PROGRESS,
    REVOKE_PRIVILEGES_SUCCESSFUL,
    REVOKE_PRIVILEGES_FAILED,
    REVOKE_PRIVILEGES_IN_PROGRESS,
    GET_USERS_SUCCESSFUL,
    GET_USERS_FAILED,
    GET_USERS_IN_PROGRESS,
    CREATE_USER_SUCCESSFUL,
    CREATE_USER_FAILED,
    CREATE_USER_IN_PROGRESS,
    GET_USER_SUCCESSFUL,
    GET_USER_FAILED,
    GET_USER_IN_PROGRESS,
    DROP_USER_SUCCESSFUL,
    DROP_USER_FAILED,
    DROP_USER_IN_PROGRESS,
    CHANGE_PASSWORD_SUCCESSFUL,
    CHANGE_PASSWORD_FAILED,
    CHANGE_PASSWORD_IN_PROGRESS,
    GRANT_ROLES_SUCCESSFUL,
    GRANT_ROLES_FAILED,
    GRANT_ROLES_IN_PROGRESS,
    REVOKE_ROLES_SUCCESSFUL,
    REVOKE_ROLES_FAILED,
    REVOKE_ROLES_IN_PROGRESS,
} from '../actions/adminOperationsActions';
import { ApiState } from '../state/ApiState';
import { createTypedMap } from '../../../core/types/TypedMap';

export default function adminOperationsReducer(
    state: ApiState,
    action: AdminOperationsAction
): ApiState {
    if (state == null) {
        // No state yet. create initial
        state = createTypedMap();
    } else if (!I.Iterable.isIterable(state)) {
        // State plain object. Convert to immutable.
        state = I.fromJS(state);
    }

    switch (action.type) {
        case GET_ROLES_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'getRoles', 'inProgress'], true);
            break;
        case GET_ROLES_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'getRoles', 'inProgress'], false)
                        .removeIn(['adminOperations', 'getRoles', 'errorValue'])
                        .setIn(['adminOperations', 'getRoles', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case GET_ROLES_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'getRoles', 'inProgress'], false)
                        .removeIn(['adminOperations', 'getRoles', 'successValue'])
                        .setIn(
                            ['adminOperations', 'getRoles', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case CREATE_ROLE_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'createRole', 'inProgress'], true);
            break;
        case CREATE_ROLE_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'createRole', 'inProgress'], false)
                        .removeIn(['adminOperations', 'createRole', 'errorValue'])
                        .setIn(['adminOperations', 'createRole', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case CREATE_ROLE_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'createRole', 'inProgress'], false)
                        .removeIn(['adminOperations', 'createRole', 'successValue'])
                        .setIn(
                            ['adminOperations', 'createRole', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case GET_ROLE_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'getRole', 'inProgress'], true);
            break;
        case GET_ROLE_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'getRole', 'inProgress'], false)
                        .removeIn(['adminOperations', 'getRole', 'errorValue'])
                        .setIn(['adminOperations', 'getRole', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case GET_ROLE_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'getRole', 'inProgress'], false)
                        .removeIn(['adminOperations', 'getRole', 'successValue'])
                        .setIn(
                            ['adminOperations', 'getRole', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case DROP_ROLE_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'dropRole', 'inProgress'], true);
            break;
        case DROP_ROLE_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'dropRole', 'inProgress'], false)
                        .removeIn(['adminOperations', 'dropRole', 'errorValue'])
                        .setIn(['adminOperations', 'dropRole', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case DROP_ROLE_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'dropRole', 'inProgress'], false)
                        .removeIn(['adminOperations', 'dropRole', 'successValue'])
                        .setIn(
                            ['adminOperations', 'dropRole', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case GRANT_PRIVILEGES_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'grantPrivileges', 'inProgress'], true);
            break;
        case GRANT_PRIVILEGES_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'grantPrivileges', 'inProgress'], false)
                        .removeIn(['adminOperations', 'grantPrivileges', 'errorValue'])
                        .setIn(['adminOperations', 'grantPrivileges', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case GRANT_PRIVILEGES_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'grantPrivileges', 'inProgress'], false)
                        .removeIn(['adminOperations', 'grantPrivileges', 'successValue'])
                        .setIn(
                            ['adminOperations', 'grantPrivileges', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case REVOKE_PRIVILEGES_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'revokePrivileges', 'inProgress'], true);
            break;
        case REVOKE_PRIVILEGES_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'revokePrivileges', 'inProgress'], false)
                        .removeIn(['adminOperations', 'revokePrivileges', 'errorValue'])
                        .setIn(['adminOperations', 'revokePrivileges', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case REVOKE_PRIVILEGES_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'revokePrivileges', 'inProgress'], false)
                        .removeIn(['adminOperations', 'revokePrivileges', 'successValue'])
                        .setIn(
                            ['adminOperations', 'revokePrivileges', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case GET_USERS_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'getUsers', 'inProgress'], true);
            break;
        case GET_USERS_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'getUsers', 'inProgress'], false)
                        .removeIn(['adminOperations', 'getUsers', 'errorValue'])
                        .setIn(['adminOperations', 'getUsers', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case GET_USERS_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'getUsers', 'inProgress'], false)
                        .removeIn(['adminOperations', 'getUsers', 'successValue'])
                        .setIn(
                            ['adminOperations', 'getUsers', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case CREATE_USER_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'createUser', 'inProgress'], true);
            break;
        case CREATE_USER_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'createUser', 'inProgress'], false)
                        .removeIn(['adminOperations', 'createUser', 'errorValue'])
                        .setIn(['adminOperations', 'createUser', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case CREATE_USER_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'createUser', 'inProgress'], false)
                        .removeIn(['adminOperations', 'createUser', 'successValue'])
                        .setIn(
                            ['adminOperations', 'createUser', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case GET_USER_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'getUser', 'inProgress'], true);
            break;
        case GET_USER_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'getUser', 'inProgress'], false)
                        .removeIn(['adminOperations', 'getUser', 'errorValue'])
                        .setIn(['adminOperations', 'getUser', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case GET_USER_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'getUser', 'inProgress'], false)
                        .removeIn(['adminOperations', 'getUser', 'successValue'])
                        .setIn(
                            ['adminOperations', 'getUser', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case DROP_USER_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'dropUser', 'inProgress'], true);
            break;
        case DROP_USER_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'dropUser', 'inProgress'], false)
                        .removeIn(['adminOperations', 'dropUser', 'errorValue'])
                        .setIn(['adminOperations', 'dropUser', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case DROP_USER_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'dropUser', 'inProgress'], false)
                        .removeIn(['adminOperations', 'dropUser', 'successValue'])
                        .setIn(
                            ['adminOperations', 'dropUser', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case CHANGE_PASSWORD_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'changePassword', 'inProgress'], true);
            break;
        case CHANGE_PASSWORD_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'changePassword', 'inProgress'], false)
                        .removeIn(['adminOperations', 'changePassword', 'errorValue'])
                        .setIn(['adminOperations', 'changePassword', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case CHANGE_PASSWORD_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'changePassword', 'inProgress'], false)
                        .removeIn(['adminOperations', 'changePassword', 'successValue'])
                        .setIn(
                            ['adminOperations', 'changePassword', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case GRANT_ROLES_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'grantRoles', 'inProgress'], true);
            break;
        case GRANT_ROLES_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'grantRoles', 'inProgress'], false)
                        .removeIn(['adminOperations', 'grantRoles', 'errorValue'])
                        .setIn(['adminOperations', 'grantRoles', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case GRANT_ROLES_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'grantRoles', 'inProgress'], false)
                        .removeIn(['adminOperations', 'grantRoles', 'successValue'])
                        .setIn(
                            ['adminOperations', 'grantRoles', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case REVOKE_ROLES_IN_PROGRESS:
            state = state.setIn(['adminOperations', 'revokeRoles', 'inProgress'], true);
            break;
        case REVOKE_ROLES_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'revokeRoles', 'inProgress'], false)
                        .removeIn(['adminOperations', 'revokeRoles', 'errorValue'])
                        .setIn(['adminOperations', 'revokeRoles', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case REVOKE_ROLES_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['adminOperations', 'revokeRoles', 'inProgress'], false)
                        .removeIn(['adminOperations', 'revokeRoles', 'successValue'])
                        .setIn(
                            ['adminOperations', 'revokeRoles', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;
    }

    return state;
}
