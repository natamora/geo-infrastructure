import '@mantine/core/styles.css';
import '@mantine/notifications/styles.css';
import '@mantine/core/styles.css';
import '@mantine/notifications/styles.css';
import {MantineProvider} from '@mantine/core';
import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {Notifications} from "@mantine/notifications";

const queryClient = new QueryClient();
createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <QueryClientProvider client={queryClient}>
            <MantineProvider>
                <Notifications/>
                <App/>
            </MantineProvider>
        </QueryClientProvider>
    </StrictMode>,
)
