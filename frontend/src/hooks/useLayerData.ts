import {useQuery} from '@tanstack/react-query';
import type {LayerConfig} from "../models/layers.ts";
import type {BoundingBoxParams} from "../models/boundingBoxParams.ts";
import {Layers} from "../api/apiClient.ts";

export const useLayerData = (config: LayerConfig,
                             bbox: BoundingBoxParams | null,
                             isVisible: boolean) => {
    return useQuery({
        queryKey: ['layer', config.id, bbox],
        queryFn: () => Layers.fetchLayerData(config.endpoint, bbox!),
        enabled: isVisible && !!bbox, // only if visible and bbox available
        staleTime: 60000, // Dane ważne przez 60s
    });
};