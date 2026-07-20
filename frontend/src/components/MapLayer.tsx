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
    const {mode, visibleLayers, bbox, selectFeature} = useMapStore();

    const isVisible = visibleLayers[layerConfig.id];
    const {data, isLoading} = useLayerData(layerConfig, bbox, isVisible);
    const isZoomValid = map.getZoom() >= layerConfig.minZoom;

    if (!isVisible || isLoading || !data || !isZoomValid) {
        return null;
    }

    const onEachFeature = (feature: any, layer: L.Layer) => {
        if (feature.properties?.name) {
            layer.bindTooltip(feature.properties.name, {
                permanent: true,
                direction: 'top',
                // offset: [0, 0],
                className: 'my-custom-label'
            });
        }
        layer.on({
            click: (e) => {L.DomEvent.stopPropagation(e);
            console.log('clicked');

                if (useMapStore.getState().mode === 'DRAW_CABLE') {
                    const isPointLayer = feature.geometry?.type === 'Point';
                    if (isPointLayer) {
                        useMapStore.getState().setDrawingStartedFromNode(true);
                        console.log("layer-click: Kliknięto w węzeł startowy");
                    }
                    map.fire('click',e);
                    return;

                }
                if (useMapStore.getState().mode === 'DRAW_ZONE') {
                    map.fire('click',e);
                }

            console.log("Kliknieto w warstwe: ", layerConfig.id);
            console.log(mode + " Selected feature: " + feature.properties.name + " feature: " + feature);
            selectFeature(feature)
        },
            mouseover: (e) => {
                const targetLayer = e.target;
                const geomType = feature.geometry?.type;

                if (geomType === 'Polygon' || geomType === 'MultiPolygon') {
                    if (targetLayer.setStyle) {
                        targetLayer.setStyle({ fillOpacity: (targetLayer.options.fillOpacity || 0.1) + 0.1 });
                    }
                } else if (geomType === 'LineString' || geomType === 'MultiLineString') {
                    if (targetLayer.setStyle) {
                        targetLayer.setStyle({ weight: (targetLayer.options.weight || 3) + 1, opacity: (targetLayer.options.opacity || 1) - 0.1 });
                    }
                }
            },

            mouseout: (e) => {
                const targetLayer = e.target;
                const geomType = feature.geometry?.type;

                if (geomType === 'Polygon' || geomType === 'MultiPolygon') {
                    if (targetLayer.setStyle) {
                        targetLayer.setStyle({ fillOpacity: (targetLayer.options.fillOpacity || 0.1) - 0.1 });
                    }
                } else if (geomType === 'LineString' || geomType === 'MultiLineString') {
                    if (targetLayer.setStyle) {
                        targetLayer.setStyle({ weight: (targetLayer.options.weight || 4) - 1, opacity: (targetLayer.options.opacity || 1) + 0.1 });
                    }
                }
            }
        });
    };

    const pointToLayer = layerConfig.iconType
        ? (_f: any, latlng: L.LatLng) => L.marker(latlng, {
            icon: getOrCreateIcon(layerConfig.iconColor || 'black', layerConfig.iconType!)
        })
        : undefined;

    const features = data?.features || [];
    const dataHash = `${features.length}-${features[features.length - 1]?.id || ''}`;

    return (
        <GeoJSON
            key={`${layerConfig.id}-${dataHash}`}
            pane={pane}
            data={data}
            style={layerConfig.style}
            pointToLayer={pointToLayer}
            onEachFeature={onEachFeature}
        />
    );
};