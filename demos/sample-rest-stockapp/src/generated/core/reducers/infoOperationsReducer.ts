/*
 * WARNING: DO NOT EDIT THIS FILE. This file is generated by yarn gen. Any changes will be overwritten.
 */

import * as I from 'immutable';
import {
    InfoOperationsAction,
    INFO_ANY_SUCCESSFUL,
    INFO_ANY_FAILED,
    INFO_ANY_IN_PROGRESS,
    INFO_NODE_SUCCESSFUL,
    INFO_NODE_FAILED,
    INFO_NODE_IN_PROGRESS,
} from '../actions/infoOperationsActions';
import { ApiState } from '../state/ApiState';
import { createTypedMap } from '../../../core/types/TypedMap';

export default function infoOperationsReducer(
    state: ApiState,
    action: InfoOperationsAction
): ApiState {
    if (state == null) {
        // No state yet. create initial
        state = createTypedMap();
    } else if (!I.Iterable.isIterable(state)) {
        // State plain object. Convert to immutable.
        state = I.fromJS(state);
    }

    switch (action.type) {
        case INFO_ANY_IN_PROGRESS:
            state = state.setIn(['infoOperations', 'infoAny', 'inProgress'], true);
            break;
        case INFO_ANY_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['infoOperations', 'infoAny', 'inProgress'], false)
                        .removeIn(['infoOperations', 'infoAny', 'errorValue'])
                        .setIn(['infoOperations', 'infoAny', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case INFO_ANY_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['infoOperations', 'infoAny', 'inProgress'], false)
                        .removeIn(['infoOperations', 'infoAny', 'successValue'])
                        .setIn(
                            ['infoOperations', 'infoAny', 'errorValue'],
                            I.fromJS(action.payload)
                        )
                        .removeIn(['last', 'successValue'])
                        .setIn(['last', 'errorValue'], payload)
                );
            }
            break;

        case INFO_NODE_IN_PROGRESS:
            state = state.setIn(['infoOperations', 'infoNode', 'inProgress'], true);
            break;
        case INFO_NODE_SUCCESSFUL:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['infoOperations', 'infoNode', 'inProgress'], false)
                        .removeIn(['infoOperations', 'infoNode', 'errorValue'])
                        .setIn(['infoOperations', 'infoNode', 'successValue'], payload)
                        .setIn(['last', 'successValue'], payload)
                        .removeIn(['last', 'errorValue'])
                );
            }
            break;
        case INFO_NODE_FAILED:
            {
                const payload = I.fromJS(action.payload);
                state = state.withMutations(map =>
                    map
                        .setIn(['infoOperations', 'infoNode', 'inProgress'], false)
                        .removeIn(['infoOperations', 'infoNode', 'successValue'])
                        .setIn(
                            ['infoOperations', 'infoNode', 'errorValue'],
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
