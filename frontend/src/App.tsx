import {MapContainer, TileLayer, Tooltip as LeafletTooltip, Marker} from "react-leaflet";
import 'leaflet/dist/leaflet.css';
import './App.css'
import {IconHome2Filled} from '@tabler/icons-react'
import {useMemo, useState} from "react";
import {renderToStaticMarkup} from "react-dom/server";
import L from "leaflet";
import {LayerTree} from "./components/LayerTree.tsx";
import {ZoomHandler} from "./components/ZoomHandler.tsx";
import {MapEvents} from "./components/MapEvents.tsx";
import {CreationMenu} from "./components/CreationMenu.tsx";

function App() {

    const [mode, setMode] = useState<'IDLE' | 'DRAW_POINT' | 'DRAW_CABLE' | 'DRAW_ZONE'>('IDLE');
    const [zoom, setZoom] = useState(13);

    const [visibleLayers, setVisibleLayers] = useState({
        nodes: true,
        cables: true,
        zones: true,
    });

    const [randomNodes, setRandomNodes] = useState([
        {id: 1, lat: 52.23, lng: 21.01},
        {id: 2, lat: 52.24, lng: 21.02},
        {id: 3, lat: 52.22, lng: 21.00},
    ]);

    const homeIcon = useMemo(() => L.divIcon({
        className: 'custom-icon',
        html: renderToStaticMarkup(<div style={{color: 'blue'}}><IconHome2Filled size={24}/></div>),
        iconSize: [24, 24],
        iconAnchor: [12, 12],
    }), []);

    return (
        <div style={{height: '100vh', width: '100vw'}}>
            <MapContainer
                center={[52.2297, 21.0122]}
                zoom={13}
                style={{height: '100%', width: '100%'}}
            >
                <ZoomHandler onZoomChange={setZoom}/>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    opacity={0.6}
                />
                {/*<TileLayer*/}
                {/*    attribution='&copy; <a href="https://carto.com/attributions">CARTO</a>'*/}
                {/*    url="https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png"*/}
                {/*/>*/}
                {zoom >= 12 && visibleLayers.nodes && randomNodes.map((node) => (
                    <Marker
                        key={node.id}
                        position={[node.lat, node.lng]}
                        icon={homeIcon}
                    >
                        {/*<LeafletTooltip*/}
                        {/*    permanent*/}
                        {/*    direction="top"*/}
                        {/*    offset={[0, 0]}*/}
                        {/*    className="my-custom-label"*/}
                        {/*>*/}
                        {/*    Random name bla bla bla*/}
                        {/*</LeafletTooltip>*/}
                    </Marker>
                ))}

                <MapEvents mode={mode}/>
            </MapContainer>
            <CreationMenu setMode={setMode}/>
            <LayerTree visibleLayers={visibleLayers} setVisibleLayers={setVisibleLayers}/>
        </div>
    );
}

export default App;
