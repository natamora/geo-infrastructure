package com.geo.app.service;

import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.FeatureDto;
import com.geo.app.repository.NodeRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NodeService {
    private final NodeRepository nodeRepository;

    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public FeatureCollectionDto getNodesInZone(@Param("zoneId") Long zoneId) {

        var features = nodeRepository.findNodesInZone(zoneId).stream()
                .map(node -> new FeatureDto(
                        node.getLocation(),
                        Map.of("id", node.getId(), "name", node.getName())
                ))
                .toList();

        return new FeatureCollectionDto(features);
    }
}
