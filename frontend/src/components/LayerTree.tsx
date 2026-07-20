import {Checkbox, Paper, Stack} from '@mantine/core';
import {useMapStore} from "../store/useMapStore.ts";
import {LAYER_CONFIGS} from "../models/layers.ts";
import '@mantine/notifications/styles.css';

export const LayerTree = () => {
    const {visibleLayers, toggleLayer} = useMapStore();

    return (
        <div style={{position: 'absolute', bottom: 20, left: 20, zIndex: 1000}}>
            <Paper shadow="md" p="md" withBorder>
                <Stack gap="xs">
                    {LAYER_CONFIGS.map((layer) => (
                        <Checkbox
                            key={layer.id}
                            label={layer.label}
                            checked={visibleLayers[layer.id]}
                            onChange={() => toggleLayer(layer.id)}
                        />
                    ))}
                </Stack>
            </Paper>
        </div>
    )
};