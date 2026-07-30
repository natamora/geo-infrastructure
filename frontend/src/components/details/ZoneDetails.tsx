import type {Polygon} from "geojson";
import {Button, Group, Loader, Stack, Text} from "@mantine/core";
import {useZoneDetail} from "../../hooks/useZones.ts";
import type {ZoneFormValues} from "../../models/zones.ts";
import {ZoneForm} from "../form/ZoneForm.tsx";
import {useModalStore} from "../../stores/useModalStore.ts";

interface ZoneDetailProps {
    zoneId: number;
    geometry: Polygon;
}

export const ZoneDetails = ({zoneId, geometry}: ZoneDetailProps) => {
    const {data: zone, isLoading, isError} = useZoneDetail(zoneId);
    console.log("choosing zone details panel");
    if (isLoading)
        return (
            <Group justify="center" p="xl">
                <Loader size="sm"/>
            </Group>
        )
    if (isError || !zone)
        return <Text c="red" size="sm">Unable to get zone details</Text>;

    const handleEdit = () => {
        const initialValues: ZoneFormValues = {
            id: zone?.id ?? zoneId,
            name: zone?.name ?? '',
            zoneClass: zone?.zoneClass ?? '',
            status: zone?.status ?? 'ACTIVE',
        };

        useModalStore.getState().openModal(
            <ZoneForm
                initialValues={initialValues}
                geometry={geometry}
                onClose={() => useModalStore.getState().closeModal()}
            />,
            "Edit zone"
        );
    };

    return (
        <Stack gap="xs">
            {Object.entries(zone)
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