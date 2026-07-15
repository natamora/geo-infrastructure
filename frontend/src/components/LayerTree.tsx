import {Paper, Stack, Checkbox} from '@mantine/core';

export const LayerTree = ({visibleLayers, setVisibleLayers}: any) => (
    <div style={{position: 'absolute', bottom: 20, left: 20, zIndex: 1000}}>
        <Paper shadow="md" p="md" withBorder>
            <Stack gap="xs">
                {Object.entries(visibleLayers).map(([key, value]) => (
                    <Checkbox
                        key={key}
                        label={key.charAt(0).toUpperCase() + key.slice(1)}
                        checked={value as boolean}
                        onChange={(e) => {
                            const isChecked = e.currentTarget.checked;
                            setVisibleLayers((prev: any) => ({...prev, [key]: isChecked}))
                        }

                        }
                    />
                ))}
            </Stack>
        </Paper>
    </div>
);