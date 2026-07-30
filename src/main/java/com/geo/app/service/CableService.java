package com.geo.app.service;

import com.geo.app.domain.entity.Cable;
import com.geo.app.domain.entity.Node;
import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.cables.CableRequestDto;
import com.geo.app.dto.cables.CableResponseDto;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.GeoJsonMapper;
import com.geo.app.mapper.CableMapper;
import com.geo.app.repository.CableRepository;
import com.geo.app.repository.NodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CableService {
    private final CableRepository cableRepository;
    private final NodeRepository nodeRepository;

    private final GeoJsonMapper mapper;
    private final CableMapper cableMapper;

    public FeatureCollectionDto getCables(BoundingBox bbox) {
        var entities = bbox.toGeometry()
                .map(cableRepository::findByBBox)
                .orElseGet(cableRepository::findAll);

        var features = entities.stream()
                .map(mapper::toFeatureDto)
                .toList();

        return new FeatureCollectionDto(features);
    }

    public CableResponseDto getCableById(Long id) {
        Cable cable = cableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cable not found with id: " + id));

        return cableMapper.toResponseDto(cable);
    }

    @Transactional
    public CableResponseDto createCable(CableRequestDto dto) {
        Node startNode = nodeRepository.findById(dto.startNodeId())
                .orElseThrow(() -> new EntityNotFoundException("Start node not found with id: " + dto.startNodeId()));
        Node endNode = nodeRepository.findById(dto.endNodeId())
                .orElseThrow(() -> new EntityNotFoundException("End node not found with id: " + dto.endNodeId()));

        Cable cable = cableMapper.toEntity(dto, startNode, endNode);
        Cable savedCable = cableRepository.save(cable);

        return cableMapper.toResponseDto(savedCable);
    }

    @Transactional
    public CableResponseDto updateCable(Long id, CableRequestDto dto) {
        Cable cable = cableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cable not found with id: " + id));

        Node startNode = nodeRepository.findById(dto.startNodeId())
                .orElseThrow(() -> new EntityNotFoundException("Start node not found with id: " + dto.startNodeId()));

        Node endNode = nodeRepository.findById(dto.endNodeId())
                .orElseThrow(() -> new EntityNotFoundException("End node not found with id: " + dto.endNodeId()));

        cableMapper.updateEntityFromDto(cable, dto, startNode, endNode);
        Cable updatedCable = cableRepository.save(cable);

        return cableMapper.toResponseDto(updatedCable);
    }

    @Transactional
    public void deleteCable(Long id) {
        if (!cableRepository.existsById(id)) {
            throw new EntityNotFoundException("Cable not found with id: " + id);
        }
        cableRepository.deleteById(id);
    }
}
