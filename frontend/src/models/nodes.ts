import type {Point} from 'geojson';
import type {LifeCycleStatus} from "../common/options/lifecycleStatusOptions.ts";

export interface NodeRequest {
    name: string;
    type: string;
    status?: LifeCycleStatus;
    installationDate?: string | null; // 'YYYY-MM-DD'
    shape: Point;
}

export interface NodeResponse {
    id: number;
    name: string;
    type: string;
    status?: LifeCycleStatus;
    installationDate?: string | null; // 'YYYY-MM-DD'
    shape: Point;
}

export interface NodeFormValues {
    id?: number; // Optional: present when edit
    name: string;
    type: string;
    status: LifeCycleStatus;
    installationDate: Date | null;
}

export interface NodeResponseDetails {
    id: number;
    name: string;
    type: string;
    status?: LifeCycleStatus;
    installationDate?: string | null; // 'YYYY-MM-DD'
    shape: Point;
    isDeletable: boolean;
    connectedCablesCount: number;
}