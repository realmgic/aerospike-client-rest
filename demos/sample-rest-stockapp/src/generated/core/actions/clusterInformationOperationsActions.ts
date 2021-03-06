/*
 * WARNING: DO NOT EDIT THIS FILE. This file is generated by yarn gen. Any changes will be overwritten.
 */

import { ActionWithPayload } from './Action';
import { Action } from 'redux';
import { ClusterInformation, RestClientError } from '../api';

export const GET_CLUSTER_INFO_SUCCESSFUL = 'GET_CLUSTER_INFO_SUCCESSFUL';
export type GetClusterInfoSuccessful = ActionWithPayload<
    typeof GET_CLUSTER_INFO_SUCCESSFUL,
    ClusterInformation
>;

export const getClusterInfoSuccessful = (res: ClusterInformation): GetClusterInfoSuccessful => ({
    type: GET_CLUSTER_INFO_SUCCESSFUL,
    payload: res,
});

export const GET_CLUSTER_INFO_FAILED = 'GET_CLUSTER_INFO_FAILED';
export type GetClusterInfoFailed = ActionWithPayload<
    typeof GET_CLUSTER_INFO_FAILED,
    RestClientError
>;

export const getClusterInfoFailed = (res: RestClientError): GetClusterInfoFailed => ({
    type: GET_CLUSTER_INFO_FAILED,
    payload: res,
});

export const GET_CLUSTER_INFO_IN_PROGRESS = 'GET_CLUSTER_INFO_IN_PROGRESS';
export type GetClusterInfoInProgress = Action<typeof GET_CLUSTER_INFO_IN_PROGRESS>;

export const getClusterInfoInProgress = (): GetClusterInfoInProgress => ({
    type: GET_CLUSTER_INFO_IN_PROGRESS,
});

export type GetClusterInfoAction =
    | GetClusterInfoSuccessful
    | GetClusterInfoFailed
    | GetClusterInfoInProgress;

export type ClusterInformationOperationsAction = GetClusterInfoAction;
