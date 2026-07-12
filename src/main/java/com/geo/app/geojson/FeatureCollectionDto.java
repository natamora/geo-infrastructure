package com.geo.app.geojson;

import java.util.List;

public record FeatureCollectionDto(
        String type,
        List<FeatureDto> features) {

    public FeatureCollectionDto(List<FeatureDto> features) {
        this("FeatureCollection", features);
    }
};
