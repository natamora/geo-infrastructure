import type {Polygon} from "geojson";
import type {ZoneFormValues, ZoneRequest} from "../../models/zones.ts";

export const mapZoneFormToPayload = (values: ZoneFormValues, geometry: Polygon): ZoneRequest => {
    return {
        name: values.name,
        zoneClass: values.zoneClass,
        status: values.status,
        shape: geometry
    }
}