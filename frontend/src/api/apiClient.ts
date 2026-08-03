import axios, {type AxiosResponse} from 'axios';
import type {BoundingBoxParams} from "../models/boundingBoxParams.ts";
import {notifications} from "@mantine/notifications";
import type {NodeRequest, NodeResponse, NodeResponseDetails} from "../models/nodes.ts";
import type {FeatureCollection} from "geojson";
import type {CableRequest, CableResponse} from "../models/cables.ts";
import type {ZoneRequest, ZoneResponse} from "../models/zones.ts";

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.response.use(
    (response) => response,
    (error) => {
        const { status, data } = error.response || {};

        switch (status) {
            case 400:
                notifications.show({
                    title: 'Bad Request',
                    message: data?.message || 'Invalid input data.',
                    color: 'red',
                });
                break;
            case 404:
                notifications.show({
                    title: 'Not Found',
                    message: 'The requested resource could not be found.',
                    color: 'red',
                });
                break;
            case 500:
                notifications.show({
                    title: 'Server Error',
                    message: 'An unexpected error occurred on the server.',
                    color: 'red',
                });
                break;
        }
        return Promise.reject(error);
    }
);

const responseBody = <T>(response: AxiosResponse<T>) => response.data;

const requests = {
    get: <T>(url: string, params?: object | URLSearchParams) => api.get<T>(url, {params}).then(responseBody),
    post: <T>(url: string, body: {}) => api.post<T>(url, body).then(responseBody),
    put: <T>(url: string, body: {}) => api.put<T>(url, body).then(responseBody),
    delete: <T>(url: string) => api.delete<T>(url).then(responseBody),
};


export const Layers = {
    // dynamic endpoint from layer configuration
    fetchLayerData: (endpoint: string, bbox: BoundingBoxParams, defaultParams?: Record<string, string | number> ) =>
        requests.get<FeatureCollection>(endpoint, {...defaultParams, ...bbox}),
}

export const Nodes = {
    getById: (id: number) => requests.get<NodeResponseDetails>(`/nodes/${id}`),
    create: (payload: NodeRequest) => requests.post<NodeResponse>('/nodes', payload),
    update: (id: number, payload: NodeRequest) => requests.put<NodeResponse>(`/nodes/${id}`, payload),
    delete: (id: number) => requests.delete<void>(`/nodes/${id}`),
}

export const Cables = {
    getById: (id: number) => requests.get<CableResponse>(`/cables/${id}`),
    create: (payload: CableRequest) => requests.post<CableResponse>('/cables', payload),
    update: (id: number, payload: CableRequest) => requests.put<CableResponse>(`/cables/${id}`, payload),
    delete: (id: number) => requests.delete<void>(`/cables/${id}`),
}

export const Zones = {
    getById: (id: number) => requests.get<ZoneResponse>(`/zones/${id}`),
    create: (payload: ZoneRequest) => requests.post<ZoneResponse>('/zones', payload),
    update: (id: number, payload: ZoneRequest) => requests.put<ZoneResponse>(`/zones/${id}`, payload),
    delete: (id: number) => requests.delete<void>(`/zones/${id}`),
}

export default api;