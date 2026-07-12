package com.geo.app.controller;

import com.geo.app.dto.BoundingBox;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.service.NodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping()
    public FeatureCollectionDto getNodes(BoundingBox bbox) {
        return nodeService.getNodes(bbox);
    }
}
