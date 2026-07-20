package com.geo.app.service;

import com.geo.app.domain.entity.Cable;
import com.geo.app.domain.entity.Node;
import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.cables.CableDto;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.GeoJsonMapper;
import com.geo.app.repository.CableRepository;
import com.geo.app.repository.NodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.LineString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wololo.jts2geojson.GeoJSONReader;

@Service
@RequiredArgsConstructor
public class CableService {
    private final CableRepository cableRepository;
    private final NodeRepository nodeRepository;
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

    @Transactional
    public Cable createCable(CableDto dto) {
        Node startNode = nodeRepository.findById(dto.startNodeId())
                .orElseThrow(() -> new EntityNotFoundException("Start node not found with id: " + dto.startNodeId()));
        Node endNode = nodeRepository.findById(dto.endNodeId())
                .orElseThrow(() -> new EntityNotFoundException("End node not found with id: " + dto.endNodeId()));

        GeoJSONReader reader = new GeoJSONReader();
        LineString shape = (LineString) reader.read(dto.shape());
        shape.setSRID(4326);

        Cable cable = new Cable();
        cable.setName(dto.name());
        cable.setType(dto.type());
        cable.setStatus(dto.status());
        cable.setInstallationDate(dto.installationDate());
        cable.setStartNode(startNode);
        cable.setEndNode(endNode);
        cable.setShape(shape);

        return cableRepository.save(cable);
    }

    @Transactional
    public Cable updateCable(Long id, CableDto dto) {
        Cable cable = cableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cable not found with id: " + id));

        Node startNode = nodeRepository.findById(dto.startNodeId())
                .orElseThrow(() -> new EntityNotFoundException("Start node not found with id: " + dto.startNodeId()));

        Node endNode = nodeRepository.findById(dto.endNodeId())
                .orElseThrow(() -> new EntityNotFoundException("End node not found with id: " + dto.endNodeId()));

        cable.setName(dto.name());
        cable.setType(dto.type());
        cable.setStatus(dto.status());
        cable.setInstallationDate(dto.installationDate());
        cable.setStartNode(startNode);
        cable.setEndNode(endNode);

        GeoJSONReader reader = new GeoJSONReader();
        LineString shape = (LineString) reader.read(dto.shape());
        shape.setSRID(4326);

        cable.setShape(shape);

        return cableRepository.save(cable);
    }

    @Transactional
    public void deleteCable(Long id) {
        if (!cableRepository.existsById(id)) {
            throw new EntityNotFoundException("Cable not found with id: " + id);
        }
        cableRepository.deleteById(id);
    }
}
