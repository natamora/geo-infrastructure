package com.geo.app.controller;

import com.geo.app.dto.BoundingBox;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.service.CableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cables")
public class CableController {

    private final CableService cableService;

    public CableController(CableService cableService) {
        this.cableService = cableService;
    }

    @GetMapping()
    public FeatureCollectionDto getCables(BoundingBox bbox) {
        return cableService.getCables(bbox);
    }
}
