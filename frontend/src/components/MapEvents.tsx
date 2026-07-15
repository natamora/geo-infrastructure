import {useMapEvents} from "react-leaflet";

interface MapEventsProps {
    mode: string;
}

export const MapEvents = ({mode}: MapEventsProps) => {
    useMapEvents({
        click(e) {
            if (mode === 'DRAW_POINT') {
                // POST to java
                console.log("Add point:", e.latlng);
            }
        }
    });
    return null;
}