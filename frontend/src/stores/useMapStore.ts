import {create} from 'zustand';
import {FLAT_LAYERS} from "../models/layers.ts";

interface MapState {
    visibleLayers: Record<string, boolean>;
    selectedFeature: any | null;
    selectFeature: (feature: any | null) => void;
    toggleLayer: (id: string) => void;
    mode: 'IDLE' | 'DRAW_POINT' | 'DRAW_CABLE' | 'DRAW_ZONE';
    setMode: (mode: MapState['mode']) => void;
    bbox: { minX: number, minY: number, maxX: number, maxY: number } | null;
    setBBox: (bbox: any) => void;

    startNodeId: number | null;
    endNodeId: number | null;
    setStartNodeId: (id: number) => void;
    setEndNodeId: (id: number) => void;

    isDrawingStartedFromNode: boolean;
    setDrawingStartedFromNode: (val: boolean) => void;
}

export const useMapStore = create<MapState>((set, get) => ({
    mode: 'IDLE',
    visibleLayers: FLAT_LAYERS.reduce((acc, l) => ({...acc, [l.id]: true}), {}),
    selectedFeature: null,
    selectFeature: (feature) => {
        if (get().mode !== 'IDLE') {
            return;
        }
        set({selectedFeature: feature});
        console.log("Selecting feature: " + feature);
    },
    setMode: (mode) => {
        set({mode})
        console.log("Seting mode: " + mode);
    },
    toggleLayer: (id) => set((state) => ({
        visibleLayers: {
            ...state.visibleLayers,
            [id]: !state.visibleLayers[id]
        }
    })),
    bbox: null,
    setBBox: (bbox) => set({bbox}),

    startNodeId: null,
    endNodeId: null,
    setStartNodeId: (id: number) => set({startNodeId: id}),
    setEndNodeId: (id: number) => set({endNodeId: id}),

    isDrawingStartedFromNode: false,
    setDrawingStartedFromNode: (val: boolean) => set({isDrawingStartedFromNode: val}),
}));