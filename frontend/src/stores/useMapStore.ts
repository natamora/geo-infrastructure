import {create} from 'zustand';
import {LAYER_CONFIGS} from "../models/layers.ts";

interface MapState {
    visibleLayers: Record<string, boolean>;
    selectedFeature: any | null;
    selectFeature: (feature: any | null) => void;
    toggleLayer: (id: string) => void;
    mode: 'IDLE' | 'DRAW_POINT' | 'DRAW_CABLE' | 'DRAW_ZONE';
    setMode: (mode: MapState['mode']) => void;
    bbox: { minX: number, minY: number, maxX: number, maxY: number } | null;
    setBBox: (bbox: any) => void;

    isDrawingStartedFromNode: boolean;
    setDrawingStartedFromNode: (val: boolean) => void;
}

export const useMapStore = create<MapState>((set, get) => ({
    mode: 'IDLE',
    visibleLayers: LAYER_CONFIGS.reduce((acc, l) => ({...acc, [l.id]: true}), {}),
    selectedFeature: null,
    selectFeature: (feature) => {
        if(get().mode !== 'IDLE') {
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

    isDrawingStartedFromNode: false,
    setDrawingStartedFromNode: (val: boolean) => set({ isDrawingStartedFromNode: val }),
}));