package com.geo.app.config;

import com.geo.app.domain.Cable;
import com.geo.app.domain.Node;
import com.geo.app.domain.Zone;
import com.geo.app.repository.CableRepository;
import com.geo.app.repository.NodeRepository;
import com.geo.app.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        Node n = createNode("Node-" + UUID.randomUUID().toString().substring(0, 8), 20.0, 50.0);
        Node n2 = createNode("Node-" + UUID.randomUUID().toString().substring(0, 8), 20.1, 50.1);

        nodeRepository.save(n);
        System.out.println("Test point saved to database: " + n);
        nodeRepository.save(n2);
        System.out.println("Test point2 saved to database: " + n2);

        Cable cable = new Cable();
        cable.setName("Cable-" + UUID.randomUUID().toString().substring(0, 8));
        cable.setStartNode(n);
        cable.setEndNode(n2);

        LineString path = gf.createLineString(new Coordinate[]{
                n.getShape().getCoordinate(),
                n2.getShape().getCoordinate()
        });
        path.setSRID(4326);
        cable.setShape(path);

        cableRepository.save(cable);
        System.out.println("Test cable saved to database: " + cable);

        Polygon poly = gf.createPolygon(new Coordinate[]{
                new Coordinate(20.0, 50.0),
                new Coordinate(22.0, 50.0),
                new Coordinate(22.0, 52.0),
                new Coordinate(20.0, 52.0),
                new Coordinate(20.0, 50.0)
        });
        poly.setSRID(4326);

        Zone zone = new Zone();
        zone.setName("Zone-" + UUID.randomUUID().toString().substring(0, 8));
        zone.setShape(poly);
        zoneRepository.save(zone);
    }

    private Node createNode(String name, double x, double y) {
        Point p = gf.createPoint(new Coordinate(x, y));
        p.setSRID(4326);
        Node node = new Node();
        node.setName(name);
        node.setShape(p);
        return node;
    }
}
