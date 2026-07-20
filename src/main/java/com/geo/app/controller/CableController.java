package com.geo.app.controller;

import com.geo.app.domain.entity.Cable;
import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.cables.CableDto;
import com.geo.app.geojson.FeatureCollectionDto;
import com.geo.app.service.CableService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Long> createCable(@RequestBody @Valid CableDto cableDto) {
        var savedCable = cableService.createCable(cableDto);
        return ResponseEntity.ok(savedCable.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @Valid @RequestBody CableDto dto) {
        Cable updated = cableService.updateCable(id, dto);
        return ResponseEntity.ok(updated.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCable(@PathVariable Long id) {
        cableService.deleteCable(id);
        return ResponseEntity.noContent().build();
    }
}
