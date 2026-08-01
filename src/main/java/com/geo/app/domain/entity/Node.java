package com.geo.app.domain.entity;

import com.geo.app.domain.enums.LifeCycleStatus;
import com.geo.app.domain.enums.NodeType;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NodeType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LifeCycleStatus status;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @Column(name = "shape", columnDefinition = "geometry(Point, 4326)")
    private Point shape;

    @OneToMany(mappedBy = "startNode", fetch = FetchType.LAZY)
    private Set<Cable> startingCables = new HashSet<>();

    @OneToMany(mappedBy = "endNode", fetch = FetchType.LAZY)
    private Set<Cable> endingCables = new HashSet<>();

}
