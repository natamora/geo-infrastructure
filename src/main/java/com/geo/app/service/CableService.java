package com.geo.app.service;

import com.geo.app.domain.entity.Cable;
import com.geo.app.domain.entity.Node;
import com.geo.app.dto.common.BoundingBox;
import com.geo.app.dto.filter.CableFilterDto;
import com.geo.app.dto.request.CableRequestDto;
import com.geo.app.dto.response.CableResponseDto;
import com.geo.app.dto.common.FeatureCollectionDto;
import com.geo.app.mapper.GeoJsonMapper;
import com.geo.app.mapper.CableMapper;
import com.geo.app.repository.CableRepository;
import com.geo.app.repository.NodeRepository;
import com.geo.app.specification.CableSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CableService {
    private final CableRepository cableRepository;
    private final NodeRepository nodeRepository;

    private final GeoJsonMapper mapper;
    private final CableMapper cableMapper;

    public FeatureCollectionDto getCables(BoundingBox bbox, CableFilterDto filter) {

        Geometry bboxGeometry = Optional.ofNullable(bbox)
                .flatMap(BoundingBox::toGeometry)
                .orElse(null);
        var spec = CableSpecification.filterCables(bboxGeometry, filter);

        var features = cableRepository.findAll(spec)
                .stream()
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

        return cableMapper.toResponseDto(cableRepository.save(cable));
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

        return cableMapper.toResponseDto(cableRepository.save(cable));
    }

    @Transactional
    public void deleteCable(Long id) {
        if (!cableRepository.existsById(id)) {
            throw new EntityNotFoundException("Cable not found with id: " + id);
        }
        cableRepository.deleteById(id);
    }
}
