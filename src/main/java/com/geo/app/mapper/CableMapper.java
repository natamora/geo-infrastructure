package com.geo.app.mapper;

import com.geo.app.domain.entity.Cable;
import com.geo.app.domain.entity.Node;
import com.geo.app.dto.request.CableRequestDto;
import com.geo.app.dto.response.CableResponseDto;
import org.locationtech.jts.geom.LineString;
import org.springframework.stereotype.Component;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.Objects;

@Component
public class CableMapper {
    private final GeoJSONReader reader = new GeoJSONReader();
    private final GeoJSONWriter writer = new GeoJSONWriter();

    public Cable toEntity(CableRequestDto dto, Node startNode, Node endNode) {
        LineString shape = (LineString) reader.read(dto.shape());
        shape.setSRID(4326);

        Cable cable = new Cable();
        cable.setName(dto.name());
        cable.setType(dto.type());
        cable.setStatus(dto.status());
        cable.setInstallationDate(dto.installationDate());
        cable.setStartNode(startNode);
        cable.setEndNode(endNode);
        cable.setShape(shape);

        return cable;
    }

    public CableResponseDto toResponseDto(Cable cable) {
        org.wololo.geojson.LineString geoJsonLineString = (org.wololo.geojson.LineString) writer.write(cable.getShape());

        return new CableResponseDto(
                cable.getId(),
                cable.getName(),
                cable.getType(),
                cable.getStatus(),
                cable.getInstallationDate(),
                cable.getStartNode().getId(),
                cable.getEndNode().getId(),
                geoJsonLineString
        );
    }

    public void updateEntityFromDto(Cable cable, CableRequestDto dto, Node startNode, Node endNode) {
        Objects.requireNonNull(startNode);
        Objects.requireNonNull(endNode);

        cable.setName(dto.name());
        cable.setType(dto.type());
        cable.setStatus(dto.status());
        cable.setInstallationDate(dto.installationDate());
        cable.setStartNode(startNode);
        cable.setEndNode(endNode);

        LineString shape = (LineString) reader.read(dto.shape());
        shape.setSRID(4326);
        cable.setShape(shape);
    }
}
