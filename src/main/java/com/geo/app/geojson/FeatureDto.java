package com.geo.app.geojson;

import org.wololo.geojson.GeoJSON;

import java.util.Map;

public record FeatureDto(
        String type,
        GeoJSON geometry,
        Map<String, Object> properties) {

    public FeatureDto(GeoJSON geometry, Map<String, Object> properties) {
        this("Feature", geometry, properties);
    }
};
