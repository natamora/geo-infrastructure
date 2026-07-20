package com.geo.app.repository;

import com.geo.app.domain.entity.Node;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

    @Query("SELECT n FROM Node n WHERE ST_Intersects(n.shape, :bbox) = true")
    List<Node> findByBBox(@Param("bbox") Geometry bbox);

    @Query("SELECT n FROM Node n JOIN Zone z ON z.id = :zoneId WHERE ST_Intersects(n.shape, z.shape) = true")
    List<Node> findNodesInZone(@Param("zoneId") Long zoneId);

}
