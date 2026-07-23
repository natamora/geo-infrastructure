import axios, {type AxiosResponse} from 'axios';
import type {BoundingBoxParams} from "../models/boundingBoxParams.ts";

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

const responseBody = <T>(response: AxiosResponse<T>) => response.data;

const requests = {
    get: <T>(url: string, params?: object | URLSearchParams) => api.get<T>(url, {params}).then(responseBody),
    post: <T>(url: string, body: {}) => api.post<T>(url, body).then(responseBody),
    put: <T>(url: string, body: {}) => api.put<T>(url, body).then(responseBody),
    delete: <T>(url: string) => api.delete<T>(url).then(responseBody),
};

export const Layers = {
    // dynamic endpoint from layer configuration
    fetchLayerData: (endpoint: string, bbox: BoundingBoxParams) => requests.get<any>(endpoint, bbox),
}

export const Nodes = {
    create: (payload: any) => requests.post<void>('/nodes', payload),
}

export default api;