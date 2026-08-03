package com.geo.app.controller;

import com.geo.app.dto.common.BoundingBox;
import com.geo.app.dto.filter.NodeFilterDto;
import com.geo.app.dto.filter.ZoneFilterDto;
import com.geo.app.dto.request.ZoneRequestDto;
import com.geo.app.dto.response.ZoneResponseDto;
import com.geo.app.dto.common.FeatureCollectionDto;
import com.geo.app.service.NodeService;
import com.geo.app.service.ZoneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {
    private final NodeService nodeService;
    private final ZoneService zoneService;

    public ZoneController(NodeService nodeService, ZoneService zoneService) {
        this.nodeService = nodeService;
        this.zoneService = zoneService;
    }

    @GetMapping()
    public FeatureCollectionDto getZones(@ModelAttribute BoundingBox bbox, @ModelAttribute ZoneFilterDto filter) {
        return zoneService.getZones(bbox, filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneResponseDto> getZoneById(@PathVariable Long id) {
        ZoneResponseDto responseDto = zoneService.getZoneById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}/nodes")
    public FeatureCollectionDto getNodes(@PathVariable Long id) {
        return nodeService.getNodesInZone(id);
    }

    @PostMapping
    public ResponseEntity<ZoneResponseDto> createZone(@RequestBody @Valid ZoneRequestDto createZoneDto) {
        var savedZone = zoneService.createZone(createZoneDto);
        return ResponseEntity.ok(savedZone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneResponseDto> updateZone(@PathVariable Long id, @Valid @RequestBody ZoneRequestDto dto) {
        var updatedZone = zoneService.updateZone(id, dto);
        return ResponseEntity.ok(updatedZone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}
