package com.geo.app.service;

import com.geo.app.domain.entity.Zone;
import com.geo.app.dto.response.ZoneResponseDto;
import com.geo.app.mapper.GeoJsonMapper;
import com.geo.app.mapper.ZoneMapper;
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
    private ZoneMapper zoneMapper;

    @InjectMocks
    private ZoneService zoneService;

    @Test
    void shouldReturnZoneResponseDtoWhenZoneExists() {
        Long zoneId = 1L;
        Zone zone = new Zone();
        ZoneResponseDto expectedDto = mock(ZoneResponseDto.class);

        when(zoneRepository.findById(zoneId)).thenReturn(Optional.of(zone));
        when(zoneMapper.toResponseDto(zone)).thenReturn(expectedDto);

        ZoneResponseDto result = zoneService.getZoneById(zoneId);

        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(zoneRepository).findById(zoneId);
        verify(zoneMapper).toResponseDto(zone);

    }
}
