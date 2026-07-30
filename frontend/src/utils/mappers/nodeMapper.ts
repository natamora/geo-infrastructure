import type {NodeFormValues, NodeRequest} from "../../models/nodes.ts";
import dayjs from "dayjs";
import type {Point} from "geojson";

export const mapNodeFormToPayload = (values: NodeFormValues, geometry: Point): NodeRequest => {
    return {
        name: values.name,
        type: values.type,
        status: values.status,
        installationDate: values.installationDate
            ? dayjs(values.installationDate).format('YYYY-MM-DD')
            : null,
        shape: geometry
    }
}

