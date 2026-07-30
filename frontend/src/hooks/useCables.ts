import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {Cables} from "../api/apiClient.ts";
import type {CableRequest, CableResponse} from "../models/cables.ts";
import {notifications} from "@mantine/notifications";

export const useCableDetail = (id: number | null) => {
    return useQuery<CableResponse, Error>({
        queryKey: ['cables', id],
        queryFn: () => Cables.getById(id!),
        enabled: Boolean(id),
    });
};

export const useCreateCable = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async (payload: CableRequest) => {
            console.log(JSON.stringify(payload));
            return Cables.create(payload);
        },
        onSuccess: () => {
            notifications.show({
                title: 'Success',
                message: 'Cable was created successfully!',
                color: 'green',
                position: 'bottom-center'
            });
            void queryClient.invalidateQueries({queryKey: ['layer'], exact: false});
        },
        onError: (error: any) => {
            notifications.show({
                title: 'Error',
                message: error.response?.data?.message || 'Cable create failed.',
                color: 'red',
                position: 'bottom-center'
            });
        }
    })
}

export const useUpdateCable = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({id, payload}: { id: number; payload: CableRequest }) => {
            console.log(JSON.stringify(payload));
            return Cables.update(id, payload);
        },
        onSuccess: (updatedCable) => {
            notifications.show({
                title: 'Success',
                message: 'Cable was updated successfully!',
                color: 'green',
                position: 'bottom-center'
            });
            void queryClient.invalidateQueries({queryKey: ['layer'], exact: false});
            void queryClient.invalidateQueries({queryKey: ['cables', updatedCable.id]})
        },
        onError: (error: any) => {
            notifications.show({
                title: 'Error',
                message: error.response?.data?.message || 'Cable update failed.',
                color: 'red',
                position: 'bottom-center'
            });
        }
    })
}