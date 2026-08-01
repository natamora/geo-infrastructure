import { Stack } from "@mantine/core";
import { CreationMenu } from "./CreationMenu";
import { DeleteFeatureButton } from "./DeleteFeatureButton";

export const MapControls = () => {
    return (
            <Stack
                pos="absolute"
                top={20}
                right={20}
                style={{zIndex: 1000}}
                gap="sm"
            >
                <CreationMenu />
                <DeleteFeatureButton />
            </Stack>
    );
};