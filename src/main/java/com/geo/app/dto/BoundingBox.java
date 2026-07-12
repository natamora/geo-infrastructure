package com.geo.app.dto;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.util.Optional;

public record BoundingBox(Double minX, Double minY, Double maxX, Double maxY) {
    public boolean isComplete() {
        return minX != null && maxX != null && minY != null && maxY != null;
    }

    public Optional<Geometry> getBoundingBox() {
        if (!isComplete()) {
            return Optional.empty();
        }
        Geometry geometry = new GeometryFactory().toGeometry(new Envelope(minX, maxX, minY, maxY));
        geometry.setSRID(4326);
        return Optional.of(geometry);
    }
}
