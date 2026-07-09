package com.geo.app.controller;

import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.service.NodeService;
import com.geo.app.service.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {
    private final NodeService nodeService;
    private final ZoneService zoneService;

    public ZoneController(NodeService nodeService, ZoneService zoneService) {
        this.nodeService = nodeService;
        this.zoneService = zoneService;
    }

    @GetMapping("/{zoneId}/nodes")
    public ResponseEntity<FeatureCollectionDto> getNodes(@PathVariable Long zoneId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping()
    public ResponseEntity<FeatureCollectionDto> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }

}
