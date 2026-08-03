import type {PathOptions} from "leaflet";

export type IconType = 'HOME' | 'CIRCLE';

export interface LayerConfig {
    id: string;
    label: string;
    endpoint: string;
    defaultParams?: Record<string, string | number>;
    minZoom: number;
    zIndex: number;
    style?: PathOptions;
    iconType?: IconType;
    iconColor?: string;
}

export interface LayerGroupConfig {
    id: string;
    label: string;
    type: 'group';
    minZoom: number;
    zIndex: number;
    children: LayerConfig[];
}

export type LayerNodeConfig = LayerConfig | LayerGroupConfig;

export const LAYER_CONFIGS: LayerNodeConfig[] = [
    {
        id: 'nodes-group',
        label: 'Nodes',
        type: 'group',
        minZoom: 11,
        zIndex: 600,
        children: [
            {
                id: 'nodes-pole',
                label: 'Pole',
                endpoint: '/nodes',
                defaultParams: { type: 'POLE' },
                minZoom: 11,
                zIndex: 600,
                iconType: 'CIRCLE',
                iconColor: 'steelblue'
            },
            {
                id: 'nodes-manhole',
                label: 'Manhole',
                endpoint: '/nodes',
                defaultParams: { type: 'MANHOLE' },
                minZoom: 11,
                zIndex: 600,
                iconType: 'CIRCLE',
                iconColor: 'navy'
            },
            {
                id: 'nodes-cabinet',
                label: 'Cabinet',
                endpoint: '/nodes',
                defaultParams: { type: 'CABINET' },
                minZoom: 11,
                zIndex: 600,
                iconType: 'CIRCLE',
                iconColor: 'mediumblue'
            },
            {
                id: 'nodes-building',
                label: 'Building',
                endpoint: '/nodes',
                defaultParams: { type: 'BUILDING' },
                minZoom: 11,
                zIndex: 600,
                iconType: 'HOME',
                iconColor: 'blue'
            }
        ]
    },
    {
        id: 'cables-group',
        label: 'Cables',
        type: 'group',
        minZoom: 11,
        zIndex: 600,
        children: [
            {
                id: 'cables-fiber',
                label: 'Fiber',
                endpoint: '/cables',
                defaultParams: { type: 'FIBER' },
                minZoom: 10,
                zIndex: 500,
                style: { color: 'red', weight: 3 }
            },
            {
                id: 'cables-copper',
                label: 'Copper',
                endpoint: '/cables',
                defaultParams: { type: 'COPPER' },
                minZoom: 10,
                zIndex: 500,
                style: { color: 'red', weight: 3 }
            },
            {
                id: 'cables-coaxial',
                label: 'Coaxial',
                endpoint: '/cables',
                defaultParams: { type: 'COAXIAL' },
                minZoom: 10,
                zIndex: 500,
                style: { color: 'red', weight: 3 }
            }
        ]
    },
    {
        id: 'zones-group',
        label: 'Zones',
        type: 'group',
        minZoom: 11,
        zIndex: 600,
        children: [
            {
                id: 'zones-residential',
                label: 'Residential',
                endpoint: '/zones',
                defaultParams: { zoneClass: 'RESIDENTIAL' },
                minZoom: 12,
                zIndex: 400,
                style: { color: 'darkorange', fillColor: 'peachpuff',fillOpacity: 0.1, weight: 1 }
            },
            {
                id: 'zones-industrial',
                label: 'Industrial',
                endpoint: '/zones',
                defaultParams: { zoneClass: 'INDUSTRIAL' },
                minZoom: 12,
                zIndex: 400,
                style: { color: 'slategray', fillColor: 'thistle',fillOpacity: 0.1, weight: 1 }
            },
            {
                id: 'zones-protected',
                label: 'Protected',
                endpoint: '/zones',
                defaultParams: { zoneClass: 'PROTECTED' },
                minZoom: 12,
                zIndex: 400,
                style: { color: 'forestgreen', fillColor: 'lightgreen',fillOpacity: 0.1, weight: 1 }
            }
        ]
    }
] as const satisfies LayerNodeConfig[];

export const getLeafLayers = (configs: readonly LayerNodeConfig[]): LayerConfig[] => {
    const leaves: LayerConfig[] = [];
    const traverse = (items: readonly LayerNodeConfig[]) => {
        for (const item of items) {
            const isGroup = 'type' in item && item.type === 'group';
            if (isGroup && item.children) {
                traverse(item.children);
            } else {
                leaves.push(item as LayerConfig);
            }
        }
    };
    traverse(configs);
    return leaves;
};

export const FLAT_LAYERS = getLeafLayers(LAYER_CONFIGS);