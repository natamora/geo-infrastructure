import {MapContainer, Pane, TileLayer} from "react-leaflet";
import 'leaflet/dist/leaflet.css';
import './App.css'
import {LayerTree} from "./components/LayerTree.tsx";
import {MapController} from "./components/MapController.tsx";
import {MapLayer} from "./components/MapLayer.tsx";
import {FLAT_LAYERS} from "./models/layers.ts";
import {DetailsPanel} from "./components/details/DetailsPanel.tsx";
import ModalContainer from "./common/modals/ModalContainer.tsx";
import {MapControls} from "./components/controls/MapControls.tsx";

function App() {

    return (
        <div style={{height: '100vh', width: '100vw'}}>
            <MapContainer
                center={[50.0509, 19.9100]}
                zoom={13}
                style={{height: '100%', width: '100%'}}
            >
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    opacity={0.6}
                />

                {FLAT_LAYERS.map((layer) => (
                    <Pane
                        key={layer.id}
                        name={`${layer.id}-pane`}
                        style={{zIndex: layer.zIndex}}
                    />
                ))}
                {FLAT_LAYERS.map((layer) => (
                    <MapLayer
                        key={layer.id}
                        pane={`${layer.id}-pane`}
                        layerConfig={layer}
                    />
                ))}
                <MapController/>
            </MapContainer>
            <MapControls/>
            <LayerTree/>
            <DetailsPanel/>
            <ModalContainer/>
        </div>
    );
}

export default App;
