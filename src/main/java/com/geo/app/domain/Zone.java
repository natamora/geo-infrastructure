package com.geo.app.domain;

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

    private String name;

    @Column(columnDefinition = "geometry(Polygon, 4326)")
    private Polygon shape;
}
