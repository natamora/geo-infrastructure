import {useMap, useMapEvents} from "react-leaflet";
import {useEffect} from "react";
import {useMapStore} from "../stores/useMapStore.ts";
import {notifications} from '@mantine/notifications';

import '@geoman-io/leaflet-geoman-free';
import '@geoman-io/leaflet-geoman-free/dist/leaflet-geoman.css';
import {getOrCreateIcon} from "../utils/mapIcons.tsx";
import {useModalStore} from "../stores/useModalStore.ts";
import {FeatureForm} from "./FeatureForm.tsx";


export const MapController = () => {
    const map = useMap();
    const {setDrawingStartedFromNode, mode, setBBox, selectFeature, setMode} = useMapStore();


    useEffect(() => {
        const debugListener = (e: any) => {
            console.log(`🗺️ [Leaflet Event]: ${e.type}`, e);
        };

        map.on('click pm:click pm:vertexadded pm:snap pm:drawstart', debugListener);

        return () => {
            map.off('click pm:click pm:vertexadded pm:snap pm:drawstart', debugListener);
        };
    }, [map]);

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
        if (map.doubleClickZoom) {
            map.doubleClickZoom.disable();
        }
        updateBounds();
    }, [map]);

    useMapEvents({
        moveend: updateBounds,
        click: (e) => {
            const {mode, isDrawingStartedFromNode, setMode} = useMapStore.getState();
            if (mode === 'DRAW_CABLE' && !isDrawingStartedFromNode) {
                console.warn("Cable have to start from node");
                notifications.show({
                    message: 'To create cable you need to choose start node!',
                    color: 'orange',
                    position: 'bottom-center',
                });
                map.pm.disableDraw();
                setMode('IDLE');
                selectFeature(null);
            }
            console.log('clicked mapcontroller');
            if (mode === 'DRAW_POINT') {
                console.log("Add point:", e.latlng);
            }
            if (mode === 'IDLE') {
                selectFeature(null);
            }
        }
    });

    useEffect(() => {
        const handleDrawCreate = (e: any) => {
            const layer = e.layer;

            setDrawingStartedFromNode(false);
            const geoJson = layer.toGeoJSON();

            console.log("Zakończono rysowanie. GeoJSON:");
            console.log(JSON.stringify(geoJson, null, 2));
            notifications.show({
                message: 'Created shape: ' + JSON.stringify(geoJson, null, 2),
                color: 'green',
                position: 'bottom-center',
            });
            selectFeature(null);
            setMode('IDLE');
            layer.remove();

            const rawGeometry = geoJson?.geometry;
            if (!rawGeometry) {
                console.error("Error: Could not retrieve geometry!", geoJson);
                notifications.show({
                    title: 'Error',
                    message: 'Failed to retrieve shape geometry. Please try again.',
                    color: 'red',
                    position: 'bottom-center',
                });
                layer.remove();
                return;
            }
            useModalStore.getState().openModal(<FeatureForm geometry={rawGeometry}/>, "Add");
        };

        map.on('pm:create', handleDrawCreate);

        return () => {
            map.off('pm:create', handleDrawCreate);
        };
    }, [map, setMode]);

    useEffect(() => {
        map.pm.disableDraw();

        if (mode === 'DRAW_POINT') {
            const icon = getOrCreateIcon('blue', 'CIRCLE');
            map.pm.enableDraw('Marker', {
                markerStyle: {icon: icon}
            });
        } else if (mode === 'DRAW_CABLE') {
            map.pm.enableDraw('Line', {
                templineStyle: {
                    color: 'red',
                    weight: 2,
                    opacity: 0.8
                },
                hintlineStyle: {
                    color: 'red',
                    weight: 2,
                    opacity: 0.6,
                    dashArray: '5, 5'
                },
                pathOptions: {
                    color: 'red',
                    weight: 2,
                    opacity: 0.8
                },
                snappable: true,
                snapDistance: 40,
                snapSegment: false,
                snapMiddle: false,
                requireSnapToFinish: true,

                continueDrawing: false,
            });
        } else if (mode === 'DRAW_ZONE') {
            map.pm.enableDraw('Polygon', {
                templineStyle: {
                    color: 'green',
                    weight: 2,
                },
                hintlineStyle: {
                    color: 'green',
                    weight: 2,
                    dashArray: '5, 5'
                },
                pathOptions: {
                    color: 'green',
                    weight: 2,
                    fillColor: 'green',
                    fillOpacity: 0.3
                },
                continueDrawing: false
            });
        }
    }, [mode, map]);

    return null;
}