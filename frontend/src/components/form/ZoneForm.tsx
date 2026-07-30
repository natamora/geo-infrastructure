import {Button, Group, Select, TextInput} from '@mantine/core';
import {useForm} from '@mantine/form';

import '@mantine/dates/styles.css';
import {type JSX} from "react";
import {lifecycleStatusOptions} from "../../common/options/lifecycleStatusOptions.ts";
import type {ZoneFormValues} from "../../models/zones.ts";
import {mapZoneFormToPayload} from "../../utils/mappers/zoneMapper.ts";
import {useCreateZone, useUpdateZone} from "../../hooks/useZones.ts";
import {zoneClassOptions} from "../../common/options/zoneClassOptions.ts";

interface ZoneFormProps {
    initialValues: ZoneFormValues;
    geometry: any;
    onClose?: () => void;
}

export function ZoneForm({initialValues, geometry, onClose}: ZoneFormProps): JSX.Element {

    const form = useForm<ZoneFormValues>({
        initialValues
    });

    const createZone = useCreateZone();
    const updateZone = useUpdateZone();

    const handleSubmit = form.onSubmit((values) => {
        const payload = mapZoneFormToPayload(values, geometry);
        if (initialValues.id) {
            updateZone.mutate(
                {id: initialValues.id, payload: payload},
                {
                    onSuccess: () => {
                        onClose?.();
                    }
                }
            );
        } else {
            createZone.mutate(
                payload,
                {
                    onSuccess: () => {
                        onClose?.();
                    }
                }
            );
        }
    });

    const isPending = createZone.isPending || updateZone.isPending;

    return (
        <form onSubmit={handleSubmit}>
            <TextInput
                label="Name"
                placeholder=""
                required
                mb="sm"
                {...form.getInputProps('name')}
            />

            <Select
                label="Zone Class"
                data={zoneClassOptions}
                comboboxProps={{
                    zIndex: 100001,
                    withinPortal: true
                }}
                mb="md"
                {...form.getInputProps('zoneClass')}
            />

            <Select
                label="Lifecycle state"
                data={lifecycleStatusOptions}
                mb="md"
                comboboxProps={{
                    zIndex: 100001,
                    withinPortal: true
                }}
                {...form.getInputProps('status')}
            />

            <Group justify="flex-end">
                <Button variant="default" onClick={onClose}>
                    Cancel
                </Button>
                <Button type="submit" loading={isPending}>
                    Save
                </Button>
            </Group>
        </form>
    );
}