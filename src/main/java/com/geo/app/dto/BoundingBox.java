package com.geo.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.util.Optional;

public record BoundingBox(
        @Schema(description = "Minimum X", example = "19.0")
        Double minX,
        @Schema(description = "Minimum Y", example = "49.0")
        Double minY,
        @Schema(description = "Maximum X", example = "23.0")
        Double maxX,
        @Schema(description = "Maximum Y", example = "53.0")
        Double maxY
) {
    @JsonIgnore
    public boolean isComplete() {
        return minX != null && maxX != null && minY != null && maxY != null;
    }

    public Optional<Geometry> toGeometry() {
        if (!isComplete()) {
            return Optional.empty();
        }
        Geometry geometry = new GeometryFactory().toGeometry(new Envelope(minX, maxX, minY, maxY));
        geometry.setSRID(4326);
        return Optional.of(geometry);
    }
}
