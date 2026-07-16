import {IconCircleFilled, IconHome2Filled, type IconProps} from "@tabler/icons-react";
import L from "leaflet";
import {renderToStaticMarkup} from "react-dom/server";
import type {IconType} from "../models/layers.ts";

const iconCache: Record<string, L.DivIcon> = {};

const ICON_MAP: Record<IconType, React.FC<IconProps>> = {
    HOME: IconHome2Filled,
    CIRCLE: IconCircleFilled,
};

export const getOrCreateIcon = (color: string, type: IconType) => {
    const key = `${type}-${color}`;

    if(iconCache[key]) {
        return iconCache[key];
    }

    const Component = ICON_MAP[type];
    const icon = L.divIcon({
        className: 'custom-icon',
        html: renderToStaticMarkup(
            <div style={{ color }}>
                <Component size={24}/>
            </div>
        ),
        iconSize: [24, 24],
        iconAnchor: [12, 12],
    });

    iconCache[key] = icon;
    return icon;
}