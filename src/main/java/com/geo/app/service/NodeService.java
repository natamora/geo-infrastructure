package com.geo.app.service;

import com.geo.app.domain.entity.Node;
import com.geo.app.dto.common.BoundingBox;
import com.geo.app.dto.filter.NodeFilterDto;
import com.geo.app.dto.request.NodeRequestDto;
import com.geo.app.dto.response.NodeResponseDetailsDto;
import com.geo.app.dto.response.NodeResponseDto;
import com.geo.app.dto.common.FeatureCollectionDto;
import com.geo.app.mapper.GeoJsonMapper;
import com.geo.app.mapper.NodeMapper;
import com.geo.app.repository.CableRepository;
import com.geo.app.repository.NodeRepository;
import com.geo.app.specification.NodeSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NodeService {
    private final NodeRepository nodeRepository;
    private final CableRepository cableRepository;

    private final GeoJsonMapper mapper;
    private final NodeMapper nodeMapper;

    public FeatureCollectionDto getNodes(BoundingBox bbox, NodeFilterDto filter) {

        Geometry bboxGeometry = Optional.ofNullable(bbox)
                .flatMap(BoundingBox::toGeometry)
                .orElse(null);

        var spec = NodeSpecification.filterNodes(bboxGeometry, filter);

        var features = nodeRepository.findAll(spec)
                .stream()
                .map(mapper::toFeatureDto)
                .toList();

        return new FeatureCollectionDto(features);
    }

    public NodeResponseDetailsDto getNodeById(Long id) {
        Node node = nodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Node not found with id: " + id));

        return nodeMapper.toResponseDetailsDto(node);
    }

    public FeatureCollectionDto getNodesInZone(Long zoneId) {

        var features = nodeRepository.findNodesInZone(zoneId).stream().map(mapper::toFeatureDto).toList();
        return new FeatureCollectionDto(features);
    }

    @Transactional
    public NodeResponseDto createNode(NodeRequestDto dto) {

        Node node = nodeMapper.toEntity(dto);
        Node savedNode = nodeRepository.save(node);

        return nodeMapper.toResponseDto(savedNode);
    }

    @Transactional
    public NodeResponseDto updateNode(Long id, NodeRequestDto dto) {
        // TODO: check if u can update geometry if cables connected

        Node node = nodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Node not found with id: " + id));

        nodeMapper.updateEntityFromDto(node, dto);
        Node updatedNode = nodeRepository.save(node);

        return nodeMapper.toResponseDto(updatedNode);
    }

    @Transactional
    public void deleteNode(Long id) {
        if (!nodeRepository.existsById(id)) {
            throw new EntityNotFoundException("Node not found with id: " + id);
        }

        if (cableRepository.existsByStartNodeIdOrEndNodeId(id, id)) {
            throw new IllegalStateException("Cannot delete node with id " + id + " because it is connected to one or more cables.");
        }

        nodeRepository.deleteById(id);
    }
}
