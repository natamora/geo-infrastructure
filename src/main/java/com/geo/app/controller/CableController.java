package com.geo.app.controller;

import com.geo.app.dto.common.BoundingBox;
import com.geo.app.dto.filter.CableFilterDto;
import com.geo.app.dto.request.CableRequestDto;
import com.geo.app.dto.response.CableResponseDto;
import com.geo.app.dto.common.FeatureCollectionDto;
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
    public FeatureCollectionDto getCables(@ModelAttribute BoundingBox bbox, @ModelAttribute CableFilterDto filter) {
        return cableService.getCables(bbox, filter);
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
