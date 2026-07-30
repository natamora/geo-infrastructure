import {Button, Group, Select, TextInput} from '@mantine/core';
import {DatePickerInput} from '@mantine/dates';
import {useForm} from '@mantine/form';

import '@mantine/dates/styles.css';
import {type JSX} from "react";
import {lifecycleStatusOptions} from "../../common/options/lifecycleStatusOptions.ts";
import {useCreateCable, useUpdateCable} from "../../hooks/useCables.ts";
import type {CableFormValues} from "../../models/cables.ts";
import {mapCableFormToPayload} from "../../utils/mappers/cableMapper.ts";
import {cableTypeOptions} from "../../common/options/cableTypeOptions.ts";


interface CableFormProps {
    initialValues: CableFormValues;
    geometry: any;
    onClose?: () => void;
}

export function CableForm({initialValues, geometry, onClose}: CableFormProps): JSX.Element {

    const form = useForm<CableFormValues>({
        initialValues
    });

    const createCable = useCreateCable();
    const updateCable = useUpdateCable();

    const handleSubmit = form.onSubmit((values) => {
        const payload = mapCableFormToPayload(values, geometry);
        if (initialValues.id) {
            updateCable.mutate(
                {id: initialValues.id, payload: payload},
                {
                    onSuccess: () => {
                        onClose?.();
                    }
                }
            );
        } else {
            createCable.mutate(
                payload,
                {
                    onSuccess: () => {
                        onClose?.();
                    }
                }
            );
        }
    });

    const isPending = createCable.isPending || updateCable.isPending;

    const startNodeValue = initialValues.startNodeId ? String(initialValues.startNodeId) : '';
    const endNodeValue = initialValues.endNodeId ? String(initialValues.endNodeId) : '';

    const startNodeOptions = startNodeValue ? [{ value: startNodeValue, label: `Node #${startNodeValue}` }] : [];
    const endNodeOptions = endNodeValue ? [{ value: endNodeValue, label: `Node #${endNodeValue}` }] : [];

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
                label="Type"
                data={cableTypeOptions}
                comboboxProps={{
                    zIndex: 100001,
                    withinPortal: true
                }}
                mb="md"
                {...form.getInputProps('type')}
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

            {/* Start Node jako Select z nazwami, ale zablokowany do edycji */}
            <Select
                label="Start Node"
                data={startNodeOptions}
                disabled
                mb="md"
                comboboxProps={{ zIndex: 100001, withinPortal: true }}
                {...form.getInputProps('startNodeId')}
            />

            {/* End Node jako Select z nazwami, ale zablokowany do edycji */}
            <Select
                label="End Node"
                data={endNodeOptions}
                disabled
                mb="md"
                comboboxProps={{ zIndex: 100001, withinPortal: true }}
                {...form.getInputProps('endNodeId')}
            />

            <DatePickerInput
                label="Installation Date"
                placeholder="Choose date..."
                valueFormat="YYYY-MM-DD"
                clearable
                mb="md"
                popoverProps={{zIndex: 100001}}
                {...form.getInputProps('installationDate')}
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