package com.geo.app.dto.cables;

import com.geo.app.domain.enums.CableType;
import com.geo.app.domain.enums.LifeCycleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.wololo.geojson.LineString;

import java.time.LocalDate;

public record CableDto(
        @NotBlank(message = "Name is required")
        String name,

        CableType type,

        LifeCycleStatus status,

        LocalDate installationDate,

        @NotNull(message = "Start node is required")
        Long startNodeId,

        @NotNull(message = "End node is required")
        Long endNodeId,

        @NotNull(message = "Geometry shouldn't be empty")
        LineString shape
) {
}
