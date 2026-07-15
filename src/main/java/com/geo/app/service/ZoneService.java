package com.geo.app.service;

import com.geo.app.dto.BoundingBox;
import com.geo.app.exception.ResourceNotFoundException;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.FeatureDto;
import com.geo.app.geojson.GeoJsonMapper;
import com.geo.app.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final GeoJsonMapper mapper;

    public FeatureCollectionDto getZones(BoundingBox bbox) {
        var entities = bbox.toGeometry()
                .map(zoneRepository::findByBBox)
                .orElseGet(zoneRepository::findAll);

        var features = entities.stream()
                .map(mapper::toFeatureDto)
                .toList();

        return new FeatureCollectionDto(features);
    }

    public FeatureDto getZoneById(Long id) {
        return zoneRepository.findById(id)
                .map(mapper::toFeatureDto)
                .orElseThrow(() -> new ResourceNotFoundException("Zone with ID " + id + " not found"));
    }
}
