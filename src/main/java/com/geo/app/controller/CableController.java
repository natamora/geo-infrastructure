package com.geo.app.controller;

import com.geo.app.dto.BoundingBox;
import com.geo.app.dto.cables.CableRequestDto;
import com.geo.app.dto.cables.CableResponseDto;
import com.geo.app.dto.nodes.NodeResponseDto;
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

    @GetMapping("/{id}")
    public ResponseEntity<CableResponseDto> getCableById(@PathVariable Long id) {
        CableResponseDto responseDto = cableService.getCableById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<CableResponseDto> createCable(@RequestBody @Valid CableRequestDto cableDto) {
        var savedCable = cableService.createCable(cableDto);
        return ResponseEntity.ok(savedCable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CableResponseDto> updateCables(@PathVariable Long id, @Valid @RequestBody CableRequestDto dto) {
        var updatedCable = cableService.updateCable(id, dto);
        return ResponseEntity.ok(updatedCable);
    }
// string templates
    // switch expressions
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCable(@PathVariable Long id) {
        cableService.deleteCable(id);
        return ResponseEntity.noContent().build();
    }
}
