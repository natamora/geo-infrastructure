import type {LifeCycleStatus} from "../common/options/lifecycleStatusOptions.ts";
import type {Polygon} from "geojson";

export interface ZoneRequest {
    name: string;
    zoneClass: string;
    status?: LifeCycleStatus;
    shape: Polygon;
}

export interface ZoneResponse {
    id: number;
    name: string;
    zoneClass: string;
    status?: LifeCycleStatus;
    shape: Polygon;
}

export interface ZoneFormValues {
    id?: number; // Optional: present when edit
    name: string;
    zoneClass: string;
    status: LifeCycleStatus;
}