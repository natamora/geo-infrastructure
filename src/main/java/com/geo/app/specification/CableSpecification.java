package com.geo.app.specification;

import com.geo.app.domain.entity.Cable;
import com.geo.app.dto.filter.CableFilterDto;
import jakarta.persistence.criteria.Predicate;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CableSpecification {
    public static Specification<Cable> filterCables(Geometry bbox, CableFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter != null) {
                if (filter.type() != null && !filter.type().isBlank()) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), filter.type()));
                }
                if (filter.installedAfter() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("installationDate"), filter.installedAfter()));
                }
                if (filter.installedBefore() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("installationDate"), filter.installedBefore()));
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
