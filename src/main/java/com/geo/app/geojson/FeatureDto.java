package com.geo.app.geojson;

import org.wololo.geojson.GeoJSON;
import org.wololo.geojson.Geometry;

import java.util.Map;

public record FeatureDto(
        String type,
        Geometry geometry,
        Map<String, Object> properties) {

    public FeatureDto(Geometry geometry, Map<String, Object> properties) {
        this("Feature", geometry, properties);
    }
};
