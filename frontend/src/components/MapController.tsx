import {useMap, useMapEvents} from "react-leaflet";
import {useEffect} from "react";
import {useMapStore} from "../store/useMapStore.ts";

interface MapEventsProps {
    mode: string;
}

export const MapController = ({mode}: MapEventsProps) => {
    const map = useMap();
    const {setBBox, selectFeature} = useMapStore();

    const updateBounds = () => {
        const bounds = map.getBounds();
        setBBox({
            minX: bounds.getWest(),
            minY: bounds.getSouth(),
            maxX: bounds.getEast(),
            maxY: bounds.getNorth()
        });
    }

    //initial setup
    useEffect(() => {
        updateBounds();
    }, [map]);

    useMapEvents({
        moveend: updateBounds,
        click: (e) => {
            if (mode === 'DRAW_POINT') {
                console.log("Add point:", e.latlng);
            }
            selectFeature(null);
        }
    });
    return null;
}