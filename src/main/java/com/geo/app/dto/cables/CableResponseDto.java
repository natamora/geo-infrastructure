package com.geo.app.dto.cables;

import com.geo.app.domain.enums.CableType;
import com.geo.app.domain.enums.LifeCycleStatus;
import org.wololo.geojson.LineString;

import java.time.LocalDate;

public record CableResponseDto(
        Long id,
        String name,
        CableType type,
        LifeCycleStatus status,
        LocalDate installationDate,
        Long startNodeId,
        Long endNodeId,
        LineString shape
) {
}
