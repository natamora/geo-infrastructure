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

    @Override
    public void run(String... args) throws Exception {
        GeometryFactory gf = new GeometryFactory();
        Point p= gf.createPoint(new Coordinate(20.0, 50.0));
        Point p2 = gf.createPoint(new Coordinate(20.1, 50.1));
        p.setSRID(4326);
        p2.setSRID(4326);

        Node node = new Node();
        node.setName("Node-"+ UUID.randomUUID().toString().substring(0,8));
        node.setLocation(p);
        nodeRepository.save(node);
        System.out.println("Test point saved to database: " + node);

        Node node2 = new Node();
        node2.setName("Node-"+ UUID.randomUUID().toString().substring(0,8));
        node2.setLocation(p2);
        nodeRepository.save(node2);
        System.out.println("Test point2 saved to database: " + node2);

        Cable cable = new Cable();
        cable.setName("Cable-"+ UUID.randomUUID().toString().substring(0,8));
        cable.setStartNode(node);
        cable.setEndNode(node2);

        LineString path = gf.createLineString(new Coordinate[] {
                node.getLocation().getCoordinate(),
                node2.getLocation().getCoordinate()
        });
        path.setSRID(4326);
        cable.setPath(path);

        cableRepository.save(cable);
        System.out.println("Test cable saved to database: " + cable);

        Coordinate[] coords = new Coordinate[] {
            new Coordinate(20.0, 50.0),
            new Coordinate(22.0, 50.0),
            new Coordinate(22.0, 52.0),
            new Coordinate(20.0, 52.0),
            new Coordinate(20.0, 50.0)
        };

        Polygon polygon = gf.createPolygon(coords);
        polygon.setSRID(4326);

        Zone zone = new Zone();
        zone.setName("Zone-"+ UUID.randomUUID().toString().substring(0,8));
        zone.setArea(polygon);
        zoneRepository.save(zone);

    }
}
