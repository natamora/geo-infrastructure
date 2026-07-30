import type {CableFormValues, CableRequest} from "../../models/cables.ts";
import type {LineString} from "geojson";
import dayjs from "dayjs";

export const mapCableFormToPayload = (values: CableFormValues, geometry: LineString): CableRequest => {
    return {
        name: values.name,
        type: values.type as any,
        status: values.status as any,
        installationDate: values.installationDate
            ? dayjs(values.installationDate).format('YYYY-MM-DD')
            : null,
        startNodeId: values.startNodeId,
        endNodeId: values.endNodeId,
        shape: geometry
    };
}