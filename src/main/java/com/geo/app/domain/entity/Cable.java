package com.geo.app.domain.entity;

import com.geo.app.domain.enums.CableType;
import com.geo.app.domain.enums.LifeCycleStatus;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.LineString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"startNode", "endNode"})
public class Cable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CableType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LifeCycleStatus status;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @ManyToOne
    @JoinColumn(name = "start_node_id")
    private Node startNode;

    @ManyToOne
    @JoinColumn(name = "end_node_id")
    private Node endNode;

    @Column(name = "shape", columnDefinition = "geometry(LineString, 4326)")
    private LineString shape;
}
