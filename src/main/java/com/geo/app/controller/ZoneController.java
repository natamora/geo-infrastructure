package com.geo.app.controller;

import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.geojson.FeatureDto;
import com.geo.app.service.NodeService;
import com.geo.app.service.ZoneService;
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

    @GetMapping()
    public FeatureCollectionDto getAllZones() {
        return zoneService.getAllZones();
    }

    @GetMapping("/{id}")
    public FeatureDto getZoneById(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

    @GetMapping("/{id}/nodes")
    public FeatureCollectionDto getNodes(@PathVariable Long id) {
        return nodeService.getNodesInZone(id);
    }
}
