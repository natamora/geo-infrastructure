package com.geo.app.service;

import com.geo.app.domain.entity.Zone;
import com.geo.app.dto.common.BoundingBox;
import com.geo.app.dto.common.FeatureCollectionDto;
import com.geo.app.dto.filter.ZoneFilterDto;
import com.geo.app.dto.request.ZoneRequestDto;
import com.geo.app.dto.response.ZoneResponseDto;
import com.geo.app.exception.ResourceNotFoundException;
import com.geo.app.mapper.GeoJsonMapper;
import com.geo.app.mapper.ZoneMapper;
import com.geo.app.repository.ZoneRepository;
import com.geo.app.specification.ZoneSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final GeoJsonMapper mapper;

    private final ZoneMapper zoneMapper;

    public FeatureCollectionDto getZones(BoundingBox bbox, ZoneFilterDto filter) {

        Geometry bboxGeometry = Optional.ofNullable(bbox)
                .flatMap(BoundingBox::toGeometry)
                .orElse(null);

        var spec = ZoneSpecification.filterZones(bboxGeometry, filter);

        var features = zoneRepository.findAll(spec)
                .stream()
                .map(mapper::toFeatureDto)
                .toList();
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
