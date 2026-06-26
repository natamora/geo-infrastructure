package com.geo.app.geojson;

import java.util.Map;

public record FeatureDto (
    String type,
    Object geometry,
    Map<String, Object> properties){

    public FeatureDto(Object geometry, Map<String, Object> properties) {
        this("Feature", geometry, properties);
    }
};
