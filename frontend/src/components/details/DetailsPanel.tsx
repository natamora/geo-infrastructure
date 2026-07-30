import {CloseButton, Divider, Group, Paper, Title} from '@mantine/core';
import {useMapStore} from "../../stores/useMapStore.ts";
import type {LineString, Point, Polygon} from "geojson";
import {NodeDetails} from "./NodeDetails.tsx";
import {CableDetails} from "./CableDetails.tsx";
import {ZoneDetails} from "./ZoneDetails.tsx";

export const DetailsPanel = () => {
    const {mode, selectedFeature, selectFeature} = useMapStore();

    if (!selectedFeature || mode !== 'IDLE') return null;

    const featureId = selectedFeature?.properties?.id ?? null;
    const entityType = selectedFeature.geometry.type;

    if (!featureId) return null;

    return (
        <Paper
            shadow="md"
            p="md"
            withBorder
            style={{
                position: 'absolute',
                bottom: 20,
                right: 20,
                width: 320,
                zIndex: 1000,
            }}
        >
            <Group justify="space-between" mb="xs">
                <Title order={4}>Details</Title>
                <CloseButton onClick={() => selectFeature(null)}/>
            </Group>

            <Divider mb="sm"/>

            {entityType === 'Point' && (
                <NodeDetails
                    nodeId={featureId}
                    geometry={selectedFeature.geometry as Point}
                />
            )}
            {entityType === 'LineString' && (
                <CableDetails
                    cableId={featureId}
                    geometry={selectedFeature.geometry as LineString}
                />
            )}
            {entityType === 'Polygon' && (
                <ZoneDetails
                    zoneId={featureId}
                    geometry={selectedFeature.geometry as Polygon}
                />
            )}

        </Paper>
    );
};