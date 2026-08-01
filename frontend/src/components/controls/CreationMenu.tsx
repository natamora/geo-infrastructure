import {ActionIcon, Menu, Tooltip} from "@mantine/core";
import {IconPlus, IconPoint, IconPolygon, IconRoute} from "@tabler/icons-react";
import {useMapStore} from "../../stores/useMapStore.ts";

export const CreationMenu = () => {
    const {setMode} = useMapStore();

    return (
            <Menu shadow="md" width={200} position="left-start">
                <Menu.Target>
                    <Tooltip label="Add new object" position="left" >
                        <ActionIcon size="lg" radius="xl" variant="filled" color="blue">
                            <IconPlus size={24}/>
                        </ActionIcon>
                    </Tooltip>
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
    );

}
