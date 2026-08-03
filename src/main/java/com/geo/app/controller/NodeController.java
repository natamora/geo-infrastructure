package com.geo.app.controller;

import com.geo.app.dto.common.BoundingBox;
import com.geo.app.dto.filter.NodeFilterDto;
import com.geo.app.dto.request.NodeRequestDto;
import com.geo.app.dto.response.NodeResponseDetailsDto;
import com.geo.app.dto.response.NodeResponseDto;
import com.geo.app.dto.common.FeatureCollectionDto;
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
    public FeatureCollectionDto getNodes(@ModelAttribute BoundingBox bbox, @ModelAttribute NodeFilterDto filter) {
        return nodeService.getNodes(bbox, filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NodeResponseDetailsDto> getNodeById(@PathVariable Long id) {
        NodeResponseDetailsDto responseDto = nodeService.getNodeById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<NodeResponseDto> createNode(@RequestBody @Valid NodeRequestDto nodeDto) {
        var savedNode = nodeService.createNode(nodeDto);
        return ResponseEntity.ok(savedNode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NodeResponseDto> updateNode(@PathVariable Long id, @Valid @RequestBody NodeRequestDto dto) {
        var updatedNode = nodeService.updateNode(id, dto);
        return ResponseEntity.ok(updatedNode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        nodeService.deleteNode(id);
        return ResponseEntity.noContent().build();
    }
}
