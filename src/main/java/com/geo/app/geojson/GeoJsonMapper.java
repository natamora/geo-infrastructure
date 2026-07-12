package com.geo.app.geojson;

import com.geo.app.domain.Cable;
import com.geo.app.domain.Node;
import com.geo.app.domain.Zone;
import org.springframework.stereotype.Component;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.Map;
import java.util.Objects;

@Component
public class GeoJsonMapper {
    private final GeoJSONWriter writer = new GeoJSONWriter();

    public FeatureDto toFeatureDto(Zone zone) {
        Objects.requireNonNull(zone);
        Objects.requireNonNull(zone.getShape());

        return new FeatureDto(
                writer.write(zone.getShape()),
                Map.of("id", zone.getId(), "name", zone.getName())
        );
    }

    public FeatureDto toFeatureDto(Node node) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(node.getShape());

        return new FeatureDto(
                writer.write(node.getShape()),
                Map.of("id", node.getId(), "name", node.getName())
        );
    }

    public FeatureDto toFeatureDto(Cable cable) {
        Objects.requireNonNull(cable);
        Objects.requireNonNull(cable.getShape());

        return new FeatureDto(
                writer.write(cable.getShape()),
                Map.of("id", cable.getId(), "name", cable.getName())
        );
    }
}
