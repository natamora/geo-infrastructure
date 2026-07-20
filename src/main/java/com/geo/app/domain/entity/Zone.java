package com.geo.app.domain.entity;

import com.geo.app.domain.enums.LifeCycleStatus;
import com.geo.app.domain.enums.ZoneClass;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Polygon;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "zone_class")
    @Enumerated(EnumType.STRING)
    private ZoneClass zoneClass;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LifeCycleStatus status;

    @Column(name = "shape", columnDefinition = "geometry(Polygon, 4326)")
    private Polygon shape;
}
