package com.geo.app.service;

import com.geo.app.domain.Zone;
import com.geo.app.geojson.FeatureDto;
import com.geo.app.geojson.GeoJsonMapper;
import com.geo.app.repository.ZoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZoneServiceTest {

    @Mock
    private ZoneRepository zoneRepository;

    @Mock
    private GeoJsonMapper geoJsonMapper;

    @InjectMocks
    private ZoneService zoneService;

    @Test
    void shouldReturnFeatureDtoWhenZoneExists() {
        Long zoneId = 1L;
        Zone zone = new Zone();
        FeatureDto expectedDto = mock(FeatureDto.class);

        when(zoneRepository.findById(zoneId)).thenReturn(Optional.of(zone));
        when(geoJsonMapper.toFeatureDto(zone)).thenReturn(expectedDto);

        FeatureDto result = zoneService.getZoneById(zoneId);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(zoneRepository).findById(zoneId);
        verify(geoJsonMapper).toFeatureDto(zone);

    }
}
