import type {PathOptions} from "leaflet";

export type IconType = 'HOME' | 'CIRCLE';

export interface LayerConfig {
    id: string;
    label: string;
    endpoint: string;
    minZoom: number;
    zIndex: number;
    style?: PathOptions;
    iconType?: IconType;
    iconColor?: string;
}

export const LAYER_CONFIGS: LayerConfig[] = [
    {
        id: 'nodes',
        label: 'Nodes',
        endpoint: '/nodes',
        minZoom: 11,
        zIndex: 600,
        iconType: 'HOME',
        iconColor: 'blue'
    },
    {
        id: 'cables',
        label: 'Cables',
        endpoint: '/cables',
        minZoom: 10,
        zIndex: 500,
        style: { color: 'red', weight: 3, dashArray: '5, 5' }
    },
    {
        id: 'zones',
        label: 'Zones',
        endpoint: '/zones',
        minZoom: 12,
        zIndex: 400,
        style: { color: 'green', fillColor: 'lightgreen',fillOpacity: 0.1, weight: 1 }
    }
] as const satisfies LayerConfig[];