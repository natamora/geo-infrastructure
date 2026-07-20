import api from '../api/axiosConfig';
import type {BoundingBoxParams} from "../models/boundingBoxParams.ts";

export const fetchLayerData = async (endpoint: string, bbox: BoundingBoxParams) => {
    const response = await api.get(endpoint, {params: bbox});
    console.log("N:" + bbox.minX + " " + bbox.minY+ " " + bbox.maxX + " " + bbox.maxY);
    console.log("Resp: " + response.data);
    return response.data;
};

export const createNode = async ( payload: any) => {
    const response = await api.post('/nodes', payload);
    return response.data;
}