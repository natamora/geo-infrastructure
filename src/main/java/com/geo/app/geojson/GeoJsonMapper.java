package com.geo.app.geojson;

import com.geo.app.domain.Zone;
import org.springframework.stereotype.Component;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.Map;

@Component
public class GeoJsonMapper {
    private final GeoJSONWriter writer = new GeoJSONWriter();

    public FeatureDto toFeatureDto(Zone zone) {
        return new FeatureDto(
                writer.write(zone.getArea()),
                Map.of("id", zone.getId(), "name", zone.getName())
        );
    }
}
