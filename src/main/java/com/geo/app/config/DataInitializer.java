package com.geo.app.config;

import com.geo.app.domain.entity.Cable;
import com.geo.app.domain.entity.Node;
import com.geo.app.domain.entity.Zone;
import com.geo.app.domain.enums.CableType;
import com.geo.app.domain.enums.LifeCycleStatus;
import com.geo.app.domain.enums.NodeType;
import com.geo.app.domain.enums.ZoneClass;
import com.geo.app.repository.CableRepository;
import com.geo.app.repository.NodeRepository;
import com.geo.app.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final NodeRepository nodeRepository;
    private final ZoneRepository zoneRepository;
    private final CableRepository cableRepository;
    private final GeometryFactory gf = new GeometryFactory();

    @Override
    public void run(String... args) throws Exception {
        Node nMarketPole = createNode("Pole-Rynek", 19.9374, 50.0614, NodeType.POLE);
        Node nWawelManhole = createNode("Manhole-Wawel", 19.9352, 50.0541, NodeType.MANHOLE);
        Node nKazimierzCabinet = createNode("Cabinet-Kazimierz", 19.9482, 50.0512, NodeType.CABINET);
        Node nVistulaBuilding = createNode("Building-Vistula", 19.9410, 50.0480, NodeType.BUILDING);
        Node nPlantyPole = createNode("Pole-Planty", 19.9330, 50.0630, NodeType.POLE);
        Node nFlorianManhole = createNode("Manhole-Florianska", 19.9405, 50.0645, NodeType.MANHOLE);

        nodeRepository.save(nMarketPole);
        nodeRepository.save(nWawelManhole);
        nodeRepository.save(nKazimierzCabinet);
        nodeRepository.save(nVistulaBuilding);
        nodeRepository.save(nPlantyPole);
        nodeRepository.save(nFlorianManhole);

        createCable("Cable-Fiber-Main", nMarketPole, nWawelManhole, CableType.FIBER);
        createCable("Cable-Copper-OldTown", nMarketPole, nPlantyPole, CableType.COPPER);
        createCable("Cable-Coaxial-Route", nWawelManhole, nKazimierzCabinet, CableType.COAXIAL);
        createCable("Cable-Fiber-South", nKazimierzCabinet, nVistulaBuilding, CableType.FIBER);
        createCable("Cable-Copper-North", nPlantyPole, nFlorianManhole, CableType.COPPER);

        createZone("Zone-Residential-Center", ZoneClass.RESIDENTIAL, new Coordinate[]{
                new Coordinate(19.920, 50.050),
                new Coordinate(19.935, 50.048), // Wcięcie/wypukłość na dole
                new Coordinate(19.955, 50.050),
                new Coordinate(19.958, 50.062), // Niejednorodna prawa granica
                new Coordinate(19.955, 50.075),
                new Coordinate(19.938, 50.078), // Nieregularna góra
                new Coordinate(19.920, 50.075),
                new Coordinate(19.918, 50.062), // Niejednorodna lewa granica
                new Coordinate(19.920, 50.050)
        });

        createZone("Zone-Industrial-East", ZoneClass.INDUSTRIAL, new Coordinate[]{
                new Coordinate(19.965, 50.035),
                new Coordinate(19.985, 50.032),
                new Coordinate(20.010, 50.035),
                new Coordinate(20.015, 50.050),
                new Coordinate(20.010, 50.065),
                new Coordinate(19.990, 50.068),
                new Coordinate(19.965, 50.065),
                new Coordinate(19.962, 50.050),
                new Coordinate(19.965, 50.035)
        });

        createZone("Zone-Protected-West", ZoneClass.PROTECTED, new Coordinate[]{
                new Coordinate(19.825, 50.040),
                new Coordinate(19.845, 50.037),
                new Coordinate(19.865, 50.042),
                new Coordinate(19.880, 50.040),
                new Coordinate(19.885, 50.055),
                new Coordinate(19.880, 50.075),
                new Coordinate(19.855, 50.078),
                new Coordinate(19.835, 50.073),
                new Coordinate(19.825, 50.075),
                new Coordinate(19.820, 50.055),
                new Coordinate(19.825, 50.040)
        });
    }

    private Node createNode(String name, double x, double y, NodeType type) {
        Point p = gf.createPoint(new Coordinate(x, y));
        p.setSRID(4326);
        Node node = new Node();
        node.setName(name);
        node.setShape(p);
        node.setType(type);
        node.setStatus(LifeCycleStatus.ACTIVE);
        node.setInstallationDate(LocalDate.now());
        return node;
    }

    private void createCable(String name, Node start, Node end, CableType type) {
        LineString path = gf.createLineString(new Coordinate[]{
                start.getShape().getCoordinate(),
                end.getShape().getCoordinate()
        });
        path.setSRID(4326);

        Cable cable = new Cable();
        cable.setName(name);
        cable.setStartNode(start);
        cable.setEndNode(end);
        cable.setType(type);
        cable.setInstallationDate(LocalDate.now());
        cable.setStatus(LifeCycleStatus.ACTIVE);
        cable.setShape(path);

        cableRepository.save(cable);
    }

    private void createZone(String name, ZoneClass zoneClass, Coordinate[] coordinates) {
        Polygon poly = gf.createPolygon(coordinates);
        poly.setSRID(4326);

        Zone zone = new Zone();
        zone.setName(name);
        zone.setShape(poly);
        zone.setZoneClass(zoneClass);
        zone.setStatus(LifeCycleStatus.ACTIVE);
        zoneRepository.save(zone);
    }
}
