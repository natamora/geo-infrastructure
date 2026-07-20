// src/components/FeatureDetails.tsx
import {CloseButton, Divider, Group, Paper, Stack, Text, Title} from '@mantine/core';
import {useMapStore} from "../store/useMapStore";

export const FeatureDetails = () => {
    const {mode, selectedFeature, selectFeature} = useMapStore();
    console.log("Dane w panelu: ", selectedFeature);
    if (!selectedFeature || mode !== 'IDLE') return null;
    console.log("Dane w panelu2: ", selectedFeature);

    const properties = selectedFeature.properties || {};

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
            {/*<pre>{JSON.stringify(selectedFeature, null, 2)}</pre>*/}
            <Group justify="space-between" mb="xs">
                <Title order={4}>Details</Title>
                <CloseButton onClick={() => selectFeature(null)}/>
            </Group>

            <Divider mb="sm"/>

            <Stack gap="xs">
                {Object.entries(properties)
                    .filter(([key]) => !['id'].includes(key))
                    .map(([key, value]) => (
                        <Text key={key} size="sm">
                            <strong>
                                {key.charAt(0).toUpperCase() + key.slice(1)}:
                            </strong>
                            {' '}
                            {value !== null ? String(value) : '---'}
                        </Text>
                    ))}
            </Stack>
        </Paper>
    );
};