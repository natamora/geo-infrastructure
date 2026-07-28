package com.geo.app.controller;

import com.geo.app.domain.entity.Node;
import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.nodes.NodeRequestDto;
import com.geo.app.dto.nodes.NodeResponseDto;
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

    @GetMapping("/{id}")
    public ResponseEntity<NodeResponseDto> getNodeById(@PathVariable Long id) {
        NodeResponseDto responseDto = nodeService.getNodeById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<NodeResponseDto> createNode(@RequestBody @Valid NodeRequestDto nodeDto) {
        var savedNode = nodeService.createNode(nodeDto);
        return ResponseEntity.ok(savedNode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NodeResponseDto> updateNode(@PathVariable Long id, @Valid @RequestBody NodeRequestDto dto) {
        var updated = nodeService.updateNode(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        nodeService.deleteNode(id);
        return ResponseEntity.noContent().build();
    }
}
