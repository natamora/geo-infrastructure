import type {LifeCycleStatus} from "../common/options/lifecycleStatusOptions.ts";
import type {LineString} from "geojson";

export interface CableRequest {
    name: string;
    type: string;
    status?: LifeCycleStatus;
    installationDate: string | null; // "YYYY-MM-DD"
    startNodeId: number;
    endNodeId: number;
    shape: LineString;
}

export interface CableResponse {
    id: number;
    name: string;
    type: string;
    status?: LifeCycleStatus;
    installationDate: string | null;
    startNodeId: number;
    endNodeId: number;
    shape: LineString;
}

export interface CableFormValues {
    id?: number;
    name: string;
    type: string;
    status: LifeCycleStatus;
    startNodeId: number | null;
    endNodeId: number | null;
    installationDate: Date | null;
}