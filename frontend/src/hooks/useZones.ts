import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {Zones} from "../api/apiClient.ts";
import type {ZoneRequest, ZoneResponse} from "../models/zones.ts";
import {notifications} from "@mantine/notifications";

export const useZoneDetail = (id: number | null) => {
    return useQuery<ZoneResponse, Error>({
        queryKey: ['zones', id],
        queryFn: () => Zones.getById(id!),
        enabled: Boolean(id),
    });
};

export const useCreateZone = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async (payload: ZoneRequest) => {
            console.log(JSON.stringify(payload));
            return Zones.create(payload);
        },
        onSuccess: () => {
            notifications.show({
                title: 'Success',
                message: 'Zone was created successfully!',
                color: 'green',
                position: 'bottom-center'
            });
            void queryClient.invalidateQueries({queryKey: ['layer'], exact: false});
        },
        onError: (error: any) => {
            notifications.show({
                title: 'Error',
                message: error.response?.data?.message || 'Zone create failed.',
                color: 'red',
                position: 'bottom-center'
            });
        }
    })
}

export const useUpdateZone = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async ({id, payload}: { id: number; payload: ZoneRequest }) => {
            console.log(JSON.stringify(payload));
            return Zones.update(id, payload);
        },
        onSuccess: (updatedZone) => {
            notifications.show({
                title: 'Success',
                message: 'Zone was updated successfully!',
                color: 'green',
                position: 'bottom-center'
            });
            void queryClient.invalidateQueries({queryKey: ['layer'], exact: false});
            void queryClient.invalidateQueries({queryKey: ['zones', updatedZone.id]})
        },
        onError: (error: any) => {
            notifications.show({
                title: 'Error',
                message: error.response?.data?.message || 'Zone update failed.',
                color: 'red',
                position: 'bottom-center'
            });
        }
    })
}

export const useDeleteZone = () => {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: async (id: number) =>  {
            return Zones.delete(id);
        },
        onSuccess: () => {
            notifications.show({
                title: 'Success',
                message: 'Zone was deleted successfully!',
                color: 'green',
                position: 'bottom-center'
            });
            void queryClient.invalidateQueries({queryKey: ['layer'], exact: false});
        },
        onError: (error: any) => {
            notifications.show({
                title: 'Error',
                message: error.response?.data?.message || 'Zone delete failed.',
                color: 'red',
                position: 'bottom-center'
            });
        }
    })
}