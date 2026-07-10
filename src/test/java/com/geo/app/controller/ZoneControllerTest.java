package com.geo.app.controller;

import com.geo.app.exception.ResourceNotFoundException;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.FeatureDto;
import com.geo.app.service.NodeService;
import com.geo.app.service.ZoneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.wololo.geojson.GeoJSON;
import org.wololo.geojson.Polygon;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ZoneController.class)
public class ZoneControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ZoneService zoneService;

    @MockitoBean
    private NodeService nodeService;

    @Test
    void shouldReturn404WhenZoneNotFound() throws Exception {
        when(zoneService.getZoneById(1L)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/zones/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Resource Not Found"));
    }

    @Test
    void shouldReturnFeatureDtoWhenZoneExists() throws Exception {
        Long zoneId = 1L;
        String zoneName = "Zone1";
        Map<String, Object> properties = Map.of("name", zoneName, "id", zoneId);
        GeoJSON geometry = new Polygon(new double[][][]{{{21.0, 52.0}, {21.1, 52.1}, {21.0, 52.0}}});
        FeatureDto mockFeature = new FeatureDto(geometry, properties);

        when(zoneService.getZoneById(zoneId)).thenReturn(mockFeature);

        mockMvc.perform(get("/api/zones/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Feature"))
                .andExpect(jsonPath("$.geometry.type").value("Polygon"))
                .andExpect(jsonPath("$.geometry.coordinates[0][0][0]").value(21.0))
                .andExpect(jsonPath("$.properties.name").value(zoneName))
                .andExpect(jsonPath("$.properties.id").value(zoneId));
    }

    @Test
    void shouldReturnFeatureCollection() throws Exception {
        Long zoneId = 1L;
        Long zoneId2 = 2L;
        String zoneName = "Zone1";
        String zoneName2 = "Zone2";

        Map<String, Object> properties = Map.of("name", zoneName, "id", zoneId);
        Map<String, Object> properties2 = Map.of("name", zoneName2, "id", zoneId2);
        GeoJSON geometry = new Polygon(new double[][][]{{{21.0, 52.0}, {21.1, 52.1}, {21.0, 52.0}}});
        FeatureDto mockFeature1 = new FeatureDto(geometry, properties);
        FeatureDto mockFeature2 = new FeatureDto(geometry, properties2);
        FeatureCollectionDto mockFeatureCollection = new FeatureCollectionDto(List.of(mockFeature1, mockFeature2));

        when(zoneService.getAllZones()).thenReturn(mockFeatureCollection);

        mockMvc.perform(get("/api/zones").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("FeatureCollection"))
                .andExpect(jsonPath("$.features").isArray())
                .andExpect(jsonPath("$.features.length()").value(2))
                .andExpect(jsonPath("$.features[0].type").value("Feature"))
                .andExpect(jsonPath("$.features[0].geometry.type").value("Polygon"))
                .andExpect(jsonPath("$.features[0].geometry.coordinates[0][0][0]").value(21.0));
    }

}
