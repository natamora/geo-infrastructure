package com.geo.app.specification;

import com.geo.app.domain.entity.Node;
import com.geo.app.domain.entity.Zone;
import com.geo.app.dto.filter.NodeFilterDto;
import com.geo.app.dto.filter.ZoneFilterDto;
import jakarta.persistence.criteria.Predicate;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ZoneSpecification {
    public static Specification<Zone> filterZones(Geometry bbox, ZoneFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter != null) {
                if (filter.zoneClass() != null && !filter.zoneClass().isBlank()) {
                    predicates.add(criteriaBuilder.equal(root.get("zoneClass"), filter.zoneClass()));
                }
            }

            if (bbox != null) {
                Predicate intersects = criteriaBuilder.isTrue(
                        criteriaBuilder.function(
                                "ST_Intersects",
                                Boolean.class,
                                root.get("shape"),
                                criteriaBuilder.literal(bbox)
                        )
                );
                predicates.add(intersects);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
