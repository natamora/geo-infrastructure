package com.geo.app.dto.response;

import com.geo.app.domain.enums.LifeCycleStatus;
import com.geo.app.domain.enums.NodeType;
import org.wololo.geojson.Point;

import java.time.LocalDate;

public record NodeResponseDto(
        Long id,
        String name,
        NodeType type,
        LifeCycleStatus status,
        LocalDate installationDate,
        Point shape
) {
}
