/*
 * WARNING: DO NOT EDIT THIS FILE. This file is generated by yarn gen. Any changes will be overwritten.
 */

import { RestClientError } from '../api';
import SimpleResponse from '../types/SimpleResponse';

export interface IndexInformationStateFields {
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

export interface CreateIndexStateFields {
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

export interface GetIndexStatsStateFields {
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

export interface DropIndexStateFields {
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

export interface SecondaryIndexMethodsStateFields {
    readonly indexInformation: IndexInformationStateFields;
    readonly createIndex: CreateIndexStateFields;
    readonly getIndexStats: GetIndexStatsStateFields;
    readonly dropIndex: DropIndexStateFields;
}

export type SecondaryIndexMethodsState = SecondaryIndexMethodsStateFields;
