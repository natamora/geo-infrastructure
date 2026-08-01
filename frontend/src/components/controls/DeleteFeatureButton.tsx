import { ActionIcon, Tooltip } from "@mantine/core";
import { IconTrash } from "@tabler/icons-react";
import { useMapStore } from "../../stores/useMapStore.ts";
import { useDeleteNode, useNodeDetail } from "../../hooks/useNodes.ts";
import { useDeleteCable } from "../../hooks/useCables.ts";
import { useDeleteZone } from "../../hooks/useZones.ts";

export const DeleteFeatureButton = () => {
    const { selectedFeature, selectFeature } = useMapStore();

    const deleteNode = useDeleteNode();
    const deleteCable = useDeleteCable();
    const deleteZone = useDeleteZone();

    const featureId = selectedFeature?.properties?.id ?? null;
    const entityType = selectedFeature?.geometry?.type;

    // 1. Wszystkie hooki muszą być wywołane na samej górze, ZANIM pojawią się jakiekolwiek warunki (if)
    const { data: nodeDetails, isLoading: isDetailsLoading } = useNodeDetail(
        entityType === 'Point' ? featureId : null
    );

    // 2. Dopiero tutaj sprawdzamy, czy w ogóle jest co renderować
    if (!selectedFeature) return null;

    // Sprawdzamy czy można usunąć
    const isDeletable = entityType === 'Point' ? (nodeDetails?.isDeletable ?? true) : true;
    const connectedCablesCount = nodeDetails?.connectedCablesCount ?? 0;

    const handleDelete = async () => {
        if (!featureId) return;

        const confirmMsg = `Are you sure you want to delete this ${entityType === 'Point' ? 'node' : 'cable'}?`;
        if (window.confirm(confirmMsg)) {
            try {
                if (entityType === 'Point') {
                    await deleteNode.mutateAsync(featureId);
                } else if (entityType === 'LineString') {
                    await deleteCable.mutateAsync(featureId);
                } else if (entityType === 'Polygon') {
                    await deleteZone.mutateAsync(featureId);
                }
                selectFeature(null);
            } catch (error) {
                console.error("Failed to delete feature", error);
            }
        }
    };

    const isPending = deleteNode.isPending || deleteCable.isPending || deleteZone.isPending;
    const isDisabled = !isDeletable || isDetailsLoading;

    const tooltipLabel = !isDeletable
        ? `Cannot delete node: connected to ${connectedCablesCount} cable(s)`
        : "Delete selected object";

    return (
        <Tooltip label={tooltipLabel} position="left">
            <ActionIcon
                size="lg"
                radius="xl"
                variant="filled"
                color="red"
                onClick={handleDelete}
                loading={isPending || isDetailsLoading}
                disabled={isDisabled}
            >
                <IconTrash size={20} />
            </ActionIcon>
        </Tooltip>
    );
};