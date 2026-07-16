import {GeoJSON, useMap} from "react-leaflet"
import type {LayerConfig} from "../models/layers.ts";
import {useLayerData} from "../hooks/useLayerData.ts";
import {useMapStore} from "../store/useMapStore.ts";
import L from "leaflet";
import {getOrCreateIcon} from "../utils/mapIcons.tsx";

interface MapLayerProps {
    layerConfig: LayerConfig;
    pane: any;
}

export const MapLayer = ({layerConfig, pane}: MapLayerProps) => {
    const map = useMap();
    const {visibleLayers, bbox, selectFeature} = useMapStore();

    const isVisible = visibleLayers[layerConfig.id];
    const {data, isLoading} = useLayerData(layerConfig, bbox, isVisible);
    const isZoomValid = map.getZoom() >= layerConfig.minZoom;

    if (!isVisible || isLoading || !data || !isZoomValid) {
        return null;
    }

    // Logika wewnątrz komponentu
    const onEachFeature = (feature: any, layer: L.Layer) => {
        if (feature.properties?.name) {
            layer.bindTooltip(feature.properties.name, {
                permanent: true,
                direction: 'top',
                // offset: [0, 0],
                className: 'my-custom-label'
            });
        }
        layer.on('click', () => selectFeature(feature));
    };

    const pointToLayer = layerConfig.iconType
        ? (_f: any, latlng: L.LatLng) => L.marker(latlng, {
            icon: getOrCreateIcon(layerConfig.iconColor || 'black', layerConfig.iconType!)
        })
        : undefined;

    return (
        <GeoJSON
            key={layerConfig.id}
            pane={pane}
            data={data}
            style={layerConfig.style}
            pointToLayer={pointToLayer}
            onEachFeature={onEachFeature}
        />
    );
};