package com.geo.app.mapper;

import com.geo.app.domain.entity.Node;
import com.geo.app.dto.nodes.NodeRequestDto;
import com.geo.app.dto.nodes.NodeResponseDto;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

@Component
public class NodeMapper {

    private final GeoJSONReader reader = new GeoJSONReader();
    private final GeoJSONWriter writer = new GeoJSONWriter();

    public Node toEntity(NodeRequestDto dto) {
        Point point = (Point) reader.read(dto.shape());
        point.setSRID(4326);

        Node node = new Node();
        node.setName(dto.name());
        node.setType(dto.type());
        node.setStatus(dto.status());
        node.setInstallationDate(dto.installationDate());
        node.setShape(point);

        return node;
    }

    public NodeResponseDto toResponseDto(Node node) {
        org.wololo.geojson.Point geoJsonPoint = (org.wololo.geojson.Point) writer.write(node.getShape());

        return new NodeResponseDto(
                node.getId(),
                node.getName(),
                node.getType(),
                node.getStatus(),
                node.getInstallationDate(),
                geoJsonPoint
        );
    }

    public void updateEntityFromDto(Node node, NodeRequestDto dto) {
        node.setName(dto.name());
        node.setType(dto.type());
        node.setStatus(dto.status());
        node.setInstallationDate(dto.installationDate());

        Point point = (Point) reader.read(dto.shape());
        point.setSRID(4326);
        node.setShape(point);
    }

}
