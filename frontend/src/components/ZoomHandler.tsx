import {useMapEvents} from "react-leaflet";

interface ZoomHandlerProps {
    onZoomChange: (zoom: number) => void;
}

export const ZoomHandler = ({onZoomChange}: ZoomHandlerProps) => {
    useMapEvents({
        zoomend: (e) => {
            console.log("zoom: ", e.target.getZoom());
            onZoomChange(e.target.getZoom());
        },
    });
    return null;
};