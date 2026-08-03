package com.geo.app.dto.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record NodeFilterDto(
    String type,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime installedAfter,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime installedBefore
)
{}
