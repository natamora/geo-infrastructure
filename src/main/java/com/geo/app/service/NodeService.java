package com.geo.app.service;

import com.geo.app.domain.entity.Node;
import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.nodes.NodeDto;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.GeoJsonMapper;
import com.geo.app.repository.CableRepository;
import com.geo.app.repository.NodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wololo.jts2geojson.GeoJSONReader;

@Service
@RequiredArgsConstructor
public class NodeService {
    private final NodeRepository nodeRepository;
    private final CableRepository cableRepository;

    private final GeoJsonMapper mapper;

    public FeatureCollectionDto getNodes(BoundingBox bbox) {
        var entities = bbox.toGeometry()
                .map(nodeRepository::findByBBox)
                .orElseGet(nodeRepository::findAll);

        var features = entities.stream()
                .map(mapper::toFeatureDto)
                .toList();

        return new FeatureCollectionDto(features);
    }

    public FeatureCollectionDto getNodesInZone(Long zoneId) {

        var features = nodeRepository.findNodesInZone(zoneId).stream()
                .map(mapper::toFeatureDto)
                .toList();

        return new FeatureCollectionDto(features);
    }

    @Transactional
    public Node createNode(NodeDto dto) {
        GeoJSONReader reader = new GeoJSONReader();
        Point point = (Point) reader.read(dto.shape());
        point.setSRID(4326);

        Node node = new Node();
        node.setName(dto.name());
        node.setType(dto.type());
        node.setStatus(dto.status());
        node.setInstallationDate(dto.installationDate());
        node.setShape(point);

        return nodeRepository.save(node);
    }

    @Transactional
    public Node updateNode(Long id, NodeDto dto) {
        // TODO: check if u can update geometry if cables connected

        Node node = nodeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Node not found with id: " + id));

        node.setName(dto.name());
        node.setType(dto.type());
        node.setStatus(dto.status());
        node.setInstallationDate(dto.installationDate());

        GeoJSONReader reader = new GeoJSONReader();
        Point shape = (Point) reader.read(dto.shape());
        shape.setSRID(4326);
        node.setShape(shape);

        return nodeRepository.save(node);
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
