package com.geo.app.geojson;

import com.geo.app.domain.entity.Cable;
import com.geo.app.domain.entity.Node;
import com.geo.app.domain.entity.Zone;
import org.springframework.stereotype.Component;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.HashMap;
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
                mapProperties(zone)
        );
    }

    public FeatureDto toFeatureDto(Node node) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(node.getShape());

        return new FeatureDto(
                writer.write(node.getShape()),
                mapProperties(node)
        );
    }

    public FeatureDto toFeatureDto(Cable cable) {
        Objects.requireNonNull(cable);
        Objects.requireNonNull(cable.getShape());

        return new FeatureDto(
                writer.write(cable.getShape()),
                mapProperties(cable)
        );
    }

    private Map<String, Object> mapProperties(Node node) {
        Map<String, Object> props = new HashMap<>();
        props.put("id", node.getId());
        props.put("name", node.getName());
        props.put("type", node.getType());
        props.put("status", node.getStatus());
        props.put("installation_date", node.getInstallationDate());
        return props;
    }

    private Map<String, Object> mapProperties(Cable cable) {
        Map<String, Object> props = new HashMap<>();
        props.put("id", cable.getId());
        props.put("name", cable.getName());
        props.put("type", cable.getType());
        props.put("status", cable.getStatus());
        props.put("installation_date", cable.getInstallationDate());
        props.put("start_node_id", cable.getStartNode().getId());
        props.put("end_node_id", cable.getEndNode().getId());
        return props;
    }

    private Map<String, Object> mapProperties(Zone zone) {
        Map<String, Object> props = new HashMap<>();
        props.put("id", zone.getId());
        props.put("name", zone.getName());
        props.put("zone_class", zone.getZoneClass());
        props.put("status", zone.getStatus());
        return props;
    }
}
