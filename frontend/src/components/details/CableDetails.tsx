import type {LineString} from "geojson";
import {useModalStore} from "../../stores/useModalStore.ts";
import {Button, Group, Loader, Stack, Text} from "@mantine/core";
import {useCableDetail} from "../../hooks/useCables.ts";
import type {CableFormValues} from "../../models/cables.ts";
import {CableForm} from "../form/CableForm.tsx";

interface CableDetailProps {
    cableId: number;
    geometry: LineString;
}

export const CableDetails = ({cableId, geometry}: CableDetailProps) => {
    const {data: cable, isLoading, isError} = useCableDetail(cableId);

    if (isLoading)
        return (
            <Group justify="center" p="xl">
                <Loader size="sm"/>
            </Group>
        )
    if (isError || !cable)
        return <Text c="red" size="sm">Unable to get cable details</Text>;

    const handleEdit = () => {
        const initialValues: CableFormValues = {
            id: cable?.id ?? cableId,
            name: cable?.name ?? '',
            type: cable?.type ?? '',
            status: cable?.status ?? 'ACTIVE',
            startNodeId: cable?.startNodeId ?? null,
            endNodeId:cable?.endNodeId ?? null,
            installationDate: cable?.installationDate ? new Date(cable.installationDate) : null,
        };

        useModalStore.getState().openModal(
            <CableForm
                initialValues={initialValues}
                geometry={geometry}
                onClose={() => useModalStore.getState().closeModal()}
            />,
            "Edit Cable"
        );
    };

    return (
        <Stack gap="xs">
            {Object.entries(cable)
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