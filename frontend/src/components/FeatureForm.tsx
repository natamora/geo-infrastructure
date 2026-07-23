import {Button, Group, Select, TextInput} from '@mantine/core';
import {DatePickerInput} from '@mantine/dates';
import {useForm} from '@mantine/form';
import {useMutation, useQueryClient} from '@tanstack/react-query';
import {notifications} from '@mantine/notifications';

import '@mantine/dates/styles.css';
import {type JSX} from "react";
import {nodeTypeOptions} from "../common/options/nodeTypeOptions.ts";
import {lifecycleStatusOptions} from "../common/options/lifecycleStatusOptions.ts";
import {Nodes} from "../api/apiClient.ts";

interface FeatureFormProps {
    geometry: any;
    onClose?: () => void;
}

export function FeatureForm({geometry, onClose}: FeatureFormProps): JSX.Element {
    const queryClient = useQueryClient();

    const form = useForm({
        initialValues: {
            name: '',
            type: 'BUILDING',
            status: 'ACTIVE',
            installationDate: null as Date | null,
        },
    });

    const mutation = useMutation({
        mutationFn: async (formValues: any) => {

            // Konwertujemy obiekt Date z Mantine na format 'YYYY-MM-DD' oczekiwany przez LocalDate w Javie
            let formattedDate = null;
            if (formValues.installationDate) {
                const d = new Date(formValues.installationDate);
                const year = d.getFullYear();
                const month = String(d.getMonth() + 1).padStart(2, '0');
                const day = String(d.getDate()).padStart(2, '0');
                formattedDate = `${year}-${month}-${day}`;
            }

            const payload = {
                name: formValues.name,
                type: formValues.type,
                status: formValues.status,
                installationDate: formattedDate,
                shape: geometry,
            };

            return Nodes.create(payload);
        },
        onSuccess: () => {
            notifications.show({
                title: 'Success',
                message: 'Node was created succesfully!',
                color: 'green',
                position: 'bottom-center',
            });

            void queryClient.invalidateQueries({queryKey: ['layer']});
            onClose?.();
        },
        onError: (error: any) => {
            notifications.show({
                title: 'Error',
                message: error.response?.data?.message || 'Node save failed.',
                color: 'red',
                position: 'bottom-center',
            });
        },
    });

    const handleSubmit = form.onSubmit((values) => {
        mutation.mutate(values);
    });

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
                <Button type="submit" loading={mutation.isPending}>
                    Save
                </Button>
            </Group>
        </form>
    );
}