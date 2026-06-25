package com.geo.app.config;

import com.geo.app.domain.Node;
import com.geo.app.repository.NodeRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(NodeRepository nodeRepository) {
        return args -> {
            GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
            Point point = gf.createPoint(new Coordinate(19.944544, 50.049683));

            Node node = new Node();
            node.setName("Kraków");
            node.setLocation(point);

            nodeRepository.save(node);
            System.out.println("Test point saved to database");

        };
    }
}
