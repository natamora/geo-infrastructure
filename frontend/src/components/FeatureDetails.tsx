import {Button, CloseButton, Divider, Group, Loader, Paper, Stack, Text, Title} from '@mantine/core';
import {useMapStore} from "../stores/useMapStore";
import {useModalStore} from "../stores/useModalStore.ts";
import {FeatureForm} from "./FeatureForm.tsx";
import type {Point} from "geojson";
import {useNodeDetail} from "../hooks/useNodes.ts";
import type {NodeFormValues} from "../models/nodes.ts";

export const FeatureDetails = () => {
    const {mode, selectedFeature, selectFeature} = useMapStore();

    const nodeId = selectedFeature?.properties?.id ?? null;

    const {data: node, isLoading, isError} = useNodeDetail(nodeId);

    console.log("Dane w panelu: ", selectedFeature);
    if (!selectedFeature || mode !== 'IDLE') return null;

    const entityType = selectedFeature.geometry.type;

    const handleEditClick = () => {
        if (entityType === 'Point') {
            const initialValues: NodeFormValues = {
                id: node?.id ?? nodeId,
                name: node?.name ?? '',
                type: node?.type ?? '',
                status: node?.status ?? 'ACTIVE',
                installationDate: node?.installationDate ? new Date(node.installationDate) : null,
            };

            useModalStore.getState().openModal(
                <FeatureForm
                    initialValues={initialValues}
                    geometry={selectedFeature.geometry as Point}
                    onClose={() => useModalStore.getState().closeModal()}
                />,
                "Edit node"
            );
            console.log("point ", entityType);
        } else if (entityType === 'LineString') {
            console.log("line ", entityType);
        } else if (entityType === 'Polygon') {
            console.log("polygon ", entityType);
        } else {
            console.log("nothing: ", entityType);
        }
    };

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

            {isLoading ? (
                <Group justify="center" p="xl">
                    <Loader size="sm"/>
                </Group>
            ) : isError || !node ? (
                <Text c="red" size="sm">Unable to get node details</Text>
            ) : (
                <Stack gap="xs">
                    {Object.entries(node)
                        .filter(([key]) => !['shape', 'id'].includes(key))
                        .map(([key, value]) => (
                            <Text key={key} size="sm">
                                <strong>
                                    {key.charAt(0).toUpperCase() + key.slice(1)}:
                                </strong>
                                {' '}
                                {value !== null && value !== undefined ? String(value) : '---'}
                            </Text>
                        ))}

                    {entityType === 'Point' && (
                        <Button
                            variant="light"
                            size="xs"
                            mt="sm"
                            onClick={handleEditClick}
                        >
                            Edit
                        </Button>
                    )}
                </Stack>
            )}
        </Paper>
    );
};