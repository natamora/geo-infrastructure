package com.geo.app.controller;

import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.zones.ZoneRequestDto;
import com.geo.app.dto.zones.ZoneResponseDto;
import com.geo.app.geojson.FeatureCollectionDto;
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
    public FeatureCollectionDto getZones(BoundingBox bbox) {
        return zoneService.getZones(bbox);
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
