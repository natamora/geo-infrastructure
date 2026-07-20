package com.geo.app.service;

import com.geo.app.domain.entity.Zone;
import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.zones.ZoneDto;
import com.geo.app.exception.ResourceNotFoundException;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.FeatureDto;
import com.geo.app.geojson.GeoJsonMapper;
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

    public FeatureCollectionDto getZones(BoundingBox bbox) {
        var entities = bbox.toGeometry().map(zoneRepository::findByBBox).orElseGet(zoneRepository::findAll);

        var features = entities.stream().map(mapper::toFeatureDto).toList();

        return new FeatureCollectionDto(features);
    }

    public FeatureDto getZoneById(Long id) {
        return zoneRepository.findById(id).map(mapper::toFeatureDto).orElseThrow(() -> new ResourceNotFoundException("Zone with ID " + id + " not found"));
    }

    @Transactional
    public Zone createZone(ZoneDto dto) {
        GeoJSONReader reader = new GeoJSONReader();
        Polygon shape = (Polygon) reader.read(dto.shape());
        shape.setSRID(4326);

        Zone zone = new Zone();
        zone.setName(dto.name());
        zone.setZoneClass(dto.zoneClass());
        zone.setStatus(dto.status());
        zone.setShape(shape);

        return zoneRepository.save(zone);
    }

    @Transactional
    public Zone updateZone(Long id, ZoneDto dto) {

        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Zone not found with id: " + id));

        zone.setName(dto.name());
        zone.setZoneClass(dto.zoneClass());
        zone.setStatus(dto.status());

        GeoJSONReader reader = new GeoJSONReader();
        Polygon shape = (Polygon) reader.read(dto.shape());
        shape.setSRID(4326);
        zone.setShape(shape);

        return zoneRepository.save(zone);
    }

    @Transactional
    public void deleteZone(Long id) {
        if (!zoneRepository.existsById(id)) {
            throw new EntityNotFoundException("Zone not found with id: " + id);
        }
        zoneRepository.deleteById(id);
    }
}
