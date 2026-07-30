package com.geo.app.service;

import com.geo.app.domain.entity.Zone;
import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.zones.ZoneRequestDto;
import com.geo.app.dto.zones.ZoneResponseDto;
import com.geo.app.exception.ResourceNotFoundException;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.FeatureDto;
import com.geo.app.geojson.GeoJsonMapper;
import com.geo.app.mapper.ZoneMapper;
import com.geo.app.repository.ZoneRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wololo.jts2geojson.GeoJSONReader;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final GeoJsonMapper mapper;

    private final ZoneMapper zoneMapper;

    public FeatureCollectionDto getZones(BoundingBox bbox) {
        var entities = bbox.toGeometry()
                .map(zoneRepository::findByBBox)
                .orElseGet(zoneRepository::findAll);

        var features = entities.stream().map(mapper::toFeatureDto).toList();

        return new FeatureCollectionDto(features);
    }

    public ZoneResponseDto getZoneById(Long id) {
        return zoneRepository.findById(id)
                .map(zoneMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Zone with ID " + id + " not found"));
    }

    @Transactional
    public ZoneResponseDto createZone(ZoneRequestDto dto) {

        Zone zone = zoneMapper.toEntity(dto);
        Zone savedZone = zoneRepository.save(zone);

        return zoneMapper.toResponseDto(savedZone);
    }

    @Transactional
    public ZoneResponseDto updateZone(Long id, ZoneRequestDto dto) {

        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Zone not found with id: " + id));

        zoneMapper.updateEntityFromDto(zone, dto);
        Zone updatedZone = zoneRepository.save(zone);

        return zoneMapper.toResponseDto(updatedZone);
    }

    @Transactional
    public void deleteZone(Long id) {
        if (!zoneRepository.existsById(id)) {
            throw new EntityNotFoundException("Zone not found with id: " + id);
        }
        zoneRepository.deleteById(id);
    }
}
