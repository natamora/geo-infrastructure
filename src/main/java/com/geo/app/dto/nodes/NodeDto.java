package com.geo.app.dto.nodes;

import com.geo.app.domain.enums.LifeCycleStatus;
import com.geo.app.domain.enums.NodeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.wololo.geojson.Point;

import java.time.LocalDate;

public record NodeDto(
        @NotBlank(message = "Name is required")
        String name,

        NodeType type,

        LifeCycleStatus status,

        LocalDate installationDate,

        @NotNull(message = "Geometry shouldn't be empty")
        Point shape
) {
}
