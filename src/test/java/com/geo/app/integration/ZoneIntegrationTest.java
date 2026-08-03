package com.geo.app.integration;

import com.geo.app.domain.entity.Zone;
import com.geo.app.repository.ZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    String zoneName = "Integration Zone";

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
        zone.setName(zoneName);
        zone.setShape(polygon);

        zone = zoneRepository.save(zone);

        zoneId = zone.getId();
    }

    @Test
    void shouldReturnZoneResponseDto() throws Exception {

        mockMvc.perform(get("/api/zones/{id}", zoneId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(zoneId))
                .andExpect(jsonPath("$.name").value(zoneName))
                .andExpect(jsonPath("$.shape").exists());
    }

    @Test
    void shouldReturn404() throws Exception {

        mockMvc.perform(get("/api/zones/999999"))
                .andExpect(status().isNotFound());
    }
}
