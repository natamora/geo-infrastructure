package com.geo.app.mapper;

import com.geo.app.domain.entity.Zone;
import com.geo.app.dto.request.ZoneRequestDto;
import com.geo.app.dto.response.ZoneResponseDto;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Component;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

@Component
public class ZoneMapper {

    private final GeoJSONReader reader = new GeoJSONReader();
    private final GeoJSONWriter writer = new GeoJSONWriter();

    public Zone toEntity(ZoneRequestDto dto) {
        Polygon polygon = (Polygon) reader.read(dto.shape());
        polygon.setSRID(4326);

        Zone zone = new Zone();
        zone.setName(dto.name());
        zone.setZoneClass(dto.zoneClass());
        zone.setStatus(dto.status());
        zone.setShape(polygon);

        return zone;
    }

    public ZoneResponseDto toResponseDto(Zone zone) {
        org.wololo.geojson.Polygon geoJsonPoint = (org.wololo.geojson.Polygon) writer.write(zone.getShape());

        return new ZoneResponseDto(
                zone.getId(),
                zone.getName(),
                zone.getZoneClass(),
                zone.getStatus(),
                geoJsonPoint
        );
    }

    public void updateEntityFromDto(Zone zone, ZoneRequestDto dto) {
        zone.setName(dto.name());
        zone.setZoneClass(dto.zoneClass());
        zone.setStatus(dto.status());

        Polygon polygon = (Polygon) reader.read(dto.shape());
        polygon.setSRID(4326);
        zone.setShape(polygon);
    }
}
