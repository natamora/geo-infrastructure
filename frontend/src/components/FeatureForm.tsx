import {Button, Group, Select, TextInput} from '@mantine/core';
import {DatePickerInput} from '@mantine/dates';
import {useForm} from '@mantine/form';

import '@mantine/dates/styles.css';
import {type JSX} from "react";
import {nodeTypeOptions} from "../common/options/nodeTypeOptions.ts";
import {lifecycleStatusOptions} from "../common/options/lifecycleStatusOptions.ts";
import type {NodeFormValues} from "../models/nodes.ts";
import {mapNodeFormToPayload} from "../utils/mappers/nodeMapper.ts";
import {useCreateNode, useUpdateNode} from "../hooks/useNodes.ts";

interface FeatureFormProps {
    initialValues: NodeFormValues;
    geometry: any;
    onClose?: () => void;
}

export function FeatureForm({initialValues, geometry, onClose}: FeatureFormProps): JSX.Element {

    const form = useForm<NodeFormValues>({
        initialValues
    });

    const createNode = useCreateNode();
    const updateNode = useUpdateNode();

    const handleSubmit = form.onSubmit((values) => {
        const payload = mapNodeFormToPayload(values, geometry);
        if (initialValues.id) {
            updateNode.mutate(
                {id: initialValues.id, payload: payload},
                {
                    onSuccess: () => {
                        onClose?.();
                    }
                }
            );
        } else {
            createNode.mutate(
                payload,
                {
                    onSuccess: () => {
                        onClose?.();
                    }
                }
            );
        }
    });

    const isPending = createNode.isPending || updateNode.isPending;

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
                data={nodeTypeOptions}
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