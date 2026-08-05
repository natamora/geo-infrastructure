import { Checkbox, Paper, Stack, Text, Box } from '@mantine/core';
import { useMapStore } from "../stores/useMapStore.ts";
import { LAYER_CONFIGS, type LayerNodeConfig } from "../models/layers.ts";
import '@mantine/notifications/styles.css';

export const LayerTree = () => {
    const { visibleLayers, toggleLayer } = useMapStore();

    const renderNode = (node: LayerNodeConfig) => {
        const isGroup = 'type' in node && node.type === 'group';

        if (isGroup) {
            return (
                <Stack key={node.id} gap={6}>
                    <Text size="sm" fw={600} pt={2}>
                        {node.label}
                    </Text>

                    <Box
                        pl="md"
                        ml="sm"
                        style={{ borderLeft: '2px solid var(--mantine-color-default-border)' }}
                    >
                        <Stack gap={6}>
                            {node.children.map((child) => renderNode(child))}
                        </Stack>
                    </Box>
                </Stack>
            );
        }

        return (
            <Checkbox
                key={node.id}
                label={node.label}
                checked={visibleLayers[node.id]}
                onChange={() => toggleLayer(node.id)}
            />
        );
    };

    return (
        <div style={{ position: 'absolute', bottom: 20, left: 20, zIndex: 1000 }}>
            <Paper p="md" style={{ minWidth: 200 }}>
                <Stack gap="sm">
                    <Text size="sm" fw={700}>Layers</Text>
                    <Stack gap={8}>
                        {LAYER_CONFIGS.map((layer) => renderNode(layer))}
                    </Stack>
                </Stack>
            </Paper>
        </div>
    );
};