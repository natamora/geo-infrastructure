package com.geo.app.controller;

import com.geo.app.domain.entity.Zone;
import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.zones.ZoneDto;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.FeatureDto;
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
    public FeatureDto getZoneById(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

    @GetMapping("/{id}/nodes")
    public FeatureCollectionDto getNodes(@PathVariable Long id) {
        return nodeService.getNodesInZone(id);
    }

    @PostMapping
    public ResponseEntity<Long> createZone(@RequestBody @Valid ZoneDto createZoneDto) {
        var savedZone = zoneService.createZone(createZoneDto);
        return ResponseEntity.ok(savedZone.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateZone(@PathVariable Long id, @Valid @RequestBody ZoneDto dto) {
        Zone updated = zoneService.updateZone(id, dto);
        return ResponseEntity.ok(updated.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}
