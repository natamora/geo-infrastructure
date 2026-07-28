import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import type {NodeRequest, NodeResponse} from "../models/nodes.ts";
import {Nodes} from "../api/apiClient.ts";
import {notifications} from "@mantine/notifications";

export const useNodeDetail = (id: number | null) => {
    return useQuery<NodeResponse, Error>({
        queryKey: ['nodes', id],
        queryFn: () => Nodes.getById(id!),
        enabled: Boolean(id),
    });
};

export const useCreateNode = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async (payload: NodeRequest) => {
            console.log(JSON.stringify(payload));
            return Nodes.create(payload);
        },
        onSuccess: () => {
            notifications.show({
                title: 'Success',
                message: 'Node was created successfully!',
                color: 'green',
                position: 'bottom-center'
            });
            void queryClient.invalidateQueries({queryKey: ['layer'], exact: false});
        },
        onError: (error: any) => {
            notifications.show({
                title: 'Error',
                message: error.response?.data?.message || 'Node create failed.',
                color: 'red',
                position: 'bottom-center'
            });
        }
    })
}

export const useUpdateNode = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({id, payload}: { id: number; payload: NodeRequest }) => {
            console.log(JSON.stringify(payload));
            return Nodes.update(id, payload);
        },
        onSuccess: (updatedNode) => {
            notifications.show({
                title: 'Success',
                message: 'Node was updated successfully!',
                color: 'green',
                position: 'bottom-center'
            });
            void queryClient.invalidateQueries({queryKey: ['layer'], exact: false});
            void queryClient.invalidateQueries({ queryKey: ['nodes', updatedNode.id] })
        },
        onError: (error: any) => {
            notifications.show({
                title: 'Error',
                message: error.response?.data?.message || 'Node update failed.',
                color: 'red',
                position: 'bottom-center'
            });
        }
    })
}
