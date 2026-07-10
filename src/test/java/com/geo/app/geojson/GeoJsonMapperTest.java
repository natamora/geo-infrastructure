package com.geo.app.geojson;

import com.geo.app.domain.Zone;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;


public class GeoJsonMapperTest {
    private GeoJsonMapper geoJsonMapper = new GeoJsonMapper();

    @Test
    void shouldMapZoneToFeatureDto() {
        GeometryFactory gf = new GeometryFactory();

        Polygon polygon = gf.createPolygon(new Coordinate[]{
                new Coordinate(20.0, 50.0),
                new Coordinate(22.0, 50.0),
                new Coordinate(22.0, 52.0),
                new Coordinate(20.0, 52.0),
                new Coordinate(20.0, 50.0)
        });

        Zone zone = new Zone();
        String zoneName = "Test Zone";
        zone.setName(zoneName);
        zone.setShape(polygon);
        ReflectionTestUtils.setField(zone, "id", 1L);

        FeatureDto resultDto = geoJsonMapper.toFeatureDto(zone);

        assertThat(resultDto).isNotNull();
        assertThat(resultDto.type()).isEqualTo("Feature");
        assertThat(resultDto.geometry()).isNotNull();
        assertThat(resultDto.geometry()).isInstanceOf(org.wololo.geojson.Polygon.class);
        assertThat(resultDto.properties()).containsEntry("name", zoneName);
    }
}
