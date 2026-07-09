package com.geo.app.service;

import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.GeoJsonMapper;
import com.geo.app.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final GeoJsonMapper mapper;


    public FeatureCollectionDto getAllZones() {
        var features = zoneRepository.findAll().stream()
                .map(mapper::toFeatureDto)
                .toList();

        return new FeatureCollectionDto(features);
    }
}
