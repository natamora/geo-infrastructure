package com.geo.app.controller;

import com.geo.app.domain.entity.Node;
import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.nodes.NodeDto;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.service.NodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Long> createNode(@RequestBody @Valid NodeDto nodeDto) {
        var savedNode = nodeService.createNode(nodeDto);
        return ResponseEntity.ok(savedNode.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateNode(@PathVariable Long id, @Valid @RequestBody NodeDto dto) {
        Node updated = nodeService.updateNode(id, dto);
        return ResponseEntity.ok(updated.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        nodeService.deleteNode(id);
        return ResponseEntity.noContent().build();
    }
}
