package com.geo.app.service;

import com.geo.app.dto.BoundingBox;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.GeoJsonMapper;
import com.geo.app.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NodeService {
    private final NodeRepository nodeRepository;
    private final GeoJsonMapper mapper;

    public FeatureCollectionDto getNodes(BoundingBox bbox) {
        var entities = bbox.getBoundingBox()
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

}
