export const lifecycleStatusOptions = [
    {value: 'PLANNED', label: 'Planned'},
    {value: 'ACTIVE', label: 'Active'},
    {value: 'MAINTENANCE', label: 'Maintenance'},
    {value: 'DECOMMISSIONED', label: 'Decommissioned'}
] as const;

export type LifeCycleStatus = typeof lifecycleStatusOptions[number]['value'];