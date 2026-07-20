import {ActionIcon, Menu, Tooltip as MantineTooltip} from "@mantine/core";
import {IconPlus, IconPoint, IconPolygon, IconRoute} from "@tabler/icons-react";
import {useMapStore} from "../store/useMapStore.ts";

export const CreationMenu = () => {
    const {setMode} = useMapStore();

    return (
        <div style={{position: 'absolute', top: 20, right: 20, zIndex: 1000}}>
            <Menu shadow="md" width={200} position="left-start">
                <Menu.Target>
                    <MantineTooltip label="Add new object">
                        <ActionIcon size="lg" radius="xl" variant="filled" color="blue">
                            <IconPlus size={24}/>
                        </ActionIcon>
                    </MantineTooltip>
                </Menu.Target>

                <Menu.Dropdown style={{zIndex: 9999}}>
                    <Menu.Label>Choose what to create: </Menu.Label>
                    <Menu.Item leftSection={<IconPoint size={16}/>} onClick={() => setMode('DRAW_POINT')}>
                        Point
                    </Menu.Item>
                    <Menu.Item leftSection={<IconRoute size={16}/>} onClick={() => setMode('DRAW_CABLE')}>
                        Cable
                    </Menu.Item>
                    <Menu.Item leftSection={<IconPolygon size={16}/>} onClick={() => setMode('DRAW_ZONE')}>
                        Zone
                    </Menu.Item>
                </Menu.Dropdown>
            </Menu>
        </div>
    );

}
