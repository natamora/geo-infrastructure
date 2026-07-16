import {MapContainer, Pane, TileLayer} from "react-leaflet";
import 'leaflet/dist/leaflet.css';
import './App.css'
import {LayerTree} from "./components/LayerTree.tsx";
import {MapController} from "./components/MapController.tsx";
import {CreationMenu} from "./components/CreationMenu.tsx";
import {MapLayer} from "./components/MapLayer.tsx";
import {useMapStore} from "./store/useMapStore.ts";
import {LAYER_CONFIGS} from "./models/layers.ts";

function App() {
    const {mode, setMode} = useMapStore();

    return (
        <div style={{height: '100vh', width: '100vw'}}>
            <MapContainer
                center={[50.0509, 20.0500]}
                zoom={12}
                style={{height: '100%', width: '100%'}}
            >
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    opacity={0.6}
                />

                {LAYER_CONFIGS.map((layer) => (
                    <Pane
                        key={layer.id}
                        name={`${layer.id}-pane`}
                        style={{zIndex: layer.zIndex}}
                    />
                ))}
                {LAYER_CONFIGS.map((layer) => (
                    <MapLayer
                        key={layer.id}
                        pane={`${layer.id}-pane`}
                        layerConfig={layer}
                    />
                ))}
                <MapController
                    mode={mode}
                />
            </MapContainer>
            <CreationMenu setMode={setMode}/>
            <LayerTree/>
        </div>
    );
}

export default App;
