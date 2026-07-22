import {Button, Group, Select, TextInput} from '@mantine/core';
import {DatePickerInput} from '@mantine/dates';
import {useForm} from '@mantine/form';
import {useMutation, useQueryClient} from '@tanstack/react-query';
import {notifications} from '@mantine/notifications';
import {createNode} from '../services/mapService';

import '@mantine/dates/styles.css';
import {useModalStore} from "../stores/useModalStore.ts";
import {type JSX} from "react"; // Wymagane style dla komponentów daty

interface FeatureFormProps {
    geometry: any;
}

export function FeatureForm({geometry}: FeatureFormProps): JSX.Element {
    const {closeModal} = useModalStore();
    const queryClient = useQueryClient();

    const form = useForm({
        initialValues: {
            name: '',
            type: 'BUILDING',
            status: 'PLANNED',
            installationDate: null as Date | null, // Przechowujemy obiekt Date lub null
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

            return await createNode(payload);
        },
        onSuccess: () => {
            notifications.show({
                title: 'Success',
                message: 'Node was created succesfully!',
                color: 'green',
                position: 'bottom-center',
            });

            void queryClient.invalidateQueries({queryKey: ['layer']});
            closeModal();
            form.reset();
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
                data={[
                    {value: 'POLE', label: 'Pole'},
                    {value: 'MANHOLE', label: 'Manhole'},
                    {value: 'CABINET', label: 'Cabinet'},
                    {value: 'BUILDING', label: 'Building'},
                ]}
                comboboxProps={{
                    zIndex: 100001,
                    withinPortal: true
                }}
                mb="md"
                {...form.getInputProps('type')}
            />

            <Select
                label="Lifecycle state"
                data={[
                    {value: 'PLANNED', label: 'Planned'},
                    {value: 'ACTIVE', label: 'Active'},
                    {value: 'MAINTENANCE', label: 'Maintenance'},
                    {value: 'DECOMMISSIONED', label: 'Decommissioned'},
                ]}
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
                <Button variant="default" onClick={closeModal}>
                    Cancel
                </Button>
                <Button type="submit" loading={mutation.isPending}>
                    Save
                </Button>
            </Group>
        </form>
    );
}