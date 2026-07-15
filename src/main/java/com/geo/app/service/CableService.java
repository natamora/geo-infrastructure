package com.geo.app.service;

import com.geo.app.dto.BoundingBox;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.GeoJsonMapper;
import com.geo.app.repository.CableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CableService {
    private final CableRepository cableRepository;
    private final GeoJsonMapper mapper;

    public FeatureCollectionDto getCables(BoundingBox bbox) {
        var entities = bbox.toGeometry()
                .map(cableRepository::findByBBox)
                .orElseGet(cableRepository::findAll);

        var features = entities.stream()
                .map(mapper::toFeatureDto)
                .toList();

        return new FeatureCollectionDto(features);
    }
}
