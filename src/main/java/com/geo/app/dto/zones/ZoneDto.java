package com.geo.app.dto.zones;

import com.geo.app.domain.enums.LifeCycleStatus;
import com.geo.app.domain.enums.ZoneClass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.wololo.geojson.Polygon;


public record ZoneDto(
        @NotBlank(message = "Name is required")
        String name,

        ZoneClass zoneClass,

        LifeCycleStatus status,

        @NotNull(message = "Geometry shouldn't be empty")
        Polygon shape
) {
}
