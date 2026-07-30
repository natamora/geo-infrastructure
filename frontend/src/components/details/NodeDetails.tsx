import {useNodeDetail} from "../../hooks/useNodes.ts";
import type {Point} from "geojson";
import type {NodeFormValues} from "../../models/nodes.ts";
import {useModalStore} from "../../stores/useModalStore.ts";
import {NodeForm} from "../form/NodeForm.tsx";
import {Button, Group, Loader, Stack, Text} from "@mantine/core";

interface NodeDetailProps {
    nodeId: number;
    geometry: Point;
}

export const NodeDetails = ({nodeId, geometry}: NodeDetailProps) => {
    const {data: node, isLoading, isError} = useNodeDetail(nodeId);
    console.log ("choosing node details panel");
    if (isLoading)
        return (
            <Group justify="center" p="xl">
                <Loader size="sm"/>
            </Group>
        )
    if (isError || !node)
        return <Text c="red" size="sm">Unable to get node details</Text>;

    const handleEdit = () => {
        const initialValues: NodeFormValues = {
            id: node?.id ?? nodeId,
            name: node?.name ?? '',
            type: node?.type ?? '',
            status: node?.status ?? 'ACTIVE',
            installationDate: node?.installationDate ? new Date(node.installationDate) : null,
        };

        useModalStore.getState().openModal(
            <NodeForm
                initialValues={initialValues}
                geometry={geometry}
                onClose={() => useModalStore.getState().closeModal()}
            />,
            "Edit node"
        );
    };

    return (
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
            <Button variant="light" size="xs" mt="sm" onClick={handleEdit}>
                Edit
            </Button>
        </Stack>
    );
}