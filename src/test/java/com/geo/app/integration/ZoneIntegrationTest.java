package com.geo.app.integration;

import com.geo.app.domain.Zone;
import com.geo.app.repository.ZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ZoneIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ZoneRepository zoneRepository;

    Long zoneId;

    @BeforeEach
    void init() {

        GeometryFactory gf = new GeometryFactory();

        Polygon polygon = gf.createPolygon(new Coordinate[]{
                new Coordinate(20.0, 50.0),
                new Coordinate(22.0, 50.0),
                new Coordinate(22.0, 52.0),
                new Coordinate(20.0, 52.0),
                new Coordinate(20.0, 50.0)
        });

        Zone zone = new Zone();
        zone.setName("Integration Zone");
        zone.setShape(polygon);

        zone = zoneRepository.save(zone);

        zoneId = zone.getId();
    }

    @Test
    void shouldReturnFeatureDto() throws Exception {

        mockMvc.perform(get("/api/zones/{id}", zoneId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Feature"))
                .andExpect(jsonPath("$.geometry.type").value("Polygon"))
                .andExpect(jsonPath("$.geometry.coordinates[0][0][0]").value(20.0))
                .andExpect(jsonPath("$.properties.id").value(zoneId))
                .andExpect(jsonPath("$.properties.name").value("Integration Zone"));
    }

    @Test
    void shouldReturn404() throws Exception {

        mockMvc.perform(get("/api/zones/999999"))
                .andExpect(status().isNotFound());
    }
}
