package com.geo.app.dto.response;

import com.geo.app.domain.enums.LifeCycleStatus;
import com.geo.app.domain.enums.ZoneClass;
import org.wololo.geojson.Polygon;


public record ZoneResponseDto(
        Long id,
        String name,
        ZoneClass zoneClass,
        LifeCycleStatus status,
        Polygon shape
) {
}
