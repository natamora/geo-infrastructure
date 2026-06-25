package com.geo.app.config;

import com.geo.app.domain.Cable;
import com.geo.app.domain.Node;
import com.geo.app.repository.CableRepository;
import com.geo.app.repository.NodeRepository;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private CableRepository cableRepository;

    @Override
    public void run(String... args) throws Exception {
        GeometryFactory gf = new GeometryFactory();
        Point p= gf.createPoint(new Coordinate(20.0, 50.0));
        Point p2 = gf.createPoint(new Coordinate(20.1, 50.1));

        Node node = new Node();
        node.setName("Node1");
        node.setLocation(p);
        nodeRepository.save(node);
        System.out.println("Test point saved to database: " + node);

        Node node2 = new Node();
        node2.setName("Node2");
        node2.setLocation(p2);
        nodeRepository.save(node2);
        System.out.println("Test point2 saved to database: " + node2);

        Cable cable = new Cable();
        cable.setName("Cable1");
        cable.setStartNode(node);
        cable.setEndNode(node2);

        LineString path = gf.createLineString(new Coordinate[] {
                node.getLocation().getCoordinate(),
                node2.getLocation().getCoordinate()
        });
        cable.setPath(path);

        cableRepository.save(cable);
        System.out.println("Test cable saved to database: " + cable);
    }
}
