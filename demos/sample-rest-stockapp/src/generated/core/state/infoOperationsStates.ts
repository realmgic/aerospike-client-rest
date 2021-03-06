/*
 * WARNING: DO NOT EDIT THIS FILE. This file is generated by yarn gen. Any changes will be overwritten.
 */

import { RestClientError } from '../api';
import SimpleResponse from '../types/SimpleResponse';

export interface InfoAnyStateFields {
    /**
     * RestClientError
     */
    readonly errorValue: RestClientError;
    /**
     * SimpleResponse
     */
    readonly successValue: SimpleResponse;
    readonly inProgress: boolean;
}

export interface InfoNodeStateFields {
    /**
     * RestClientError
     */
    readonly errorValue: RestClientError;
    /**
     * SimpleResponse
     */
    readonly successValue: SimpleResponse;
    readonly inProgress: boolean;
}

export interface InfoOperationsStateFields {
    readonly infoAny: InfoAnyStateFields;
    readonly infoNode: InfoNodeStateFields;
}

export type InfoOperationsState = InfoOperationsStateFields;
