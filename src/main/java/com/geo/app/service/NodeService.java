package com.geo.app.service;

import com.geo.app.domain.entity.Node;
import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.nodes.NodeRequestDto;
import com.geo.app.dto.nodes.NodeResponseDetailsDto;
import com.geo.app.dto.nodes.NodeResponseDto;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.GeoJsonMapper;
import com.geo.app.mapper.NodeMapper;
import com.geo.app.repository.CableRepository;
import com.geo.app.repository.NodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NodeService {
    private final NodeRepository nodeRepository;
    private final CableRepository cableRepository;

    private final GeoJsonMapper mapper;
    private final NodeMapper nodeMapper;

    public FeatureCollectionDto getNodes(BoundingBox bbox) {
        var entities = bbox.toGeometry()
                .map(nodeRepository::findByBBox)
                .orElseGet(nodeRepository::findAll);

        var features = entities.stream().map(mapper::toFeatureDto).toList();

        return new FeatureCollectionDto(features);
    }

    public NodeResponseDetailsDto getNodeById(Long id) {
        Node node = nodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Node not found with id: " + id));
        long connectedCablesCount = node.getStartingCables().size() + node.getEndingCables().size();
        boolean isDeletable = connectedCablesCount == 0;
        return new NodeResponseDetailsDto(
                node.getId(),
                node.getName(),
                node.getType(),
                node.getStatus(),
                node.getInstallationDate(),
                null,
                isDeletable,
                connectedCablesCount
        );
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
