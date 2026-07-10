package com.geo.app.domain;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.LineString;

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

    private String name;

    @Column(columnDefinition = "geometry(LineString, 4326)")
    private LineString shape;

    @ManyToOne
    @JoinColumn(name = "start_node_id")
    private Node startNode;

    @ManyToOne
    @JoinColumn(name = "end_node_id")
    private Node endNode;
}
