package com.geo.app.repository;

import com.geo.app.domain.Cable;
import com.geo.app.domain.Zone;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CableRepository extends JpaRepository<Cable, Long> {

    @Query("SELECT c FROM Cable c WHERE ST_Intersects(c.shape, :bbox) = true")
    List<Cable> findByBBox(@Param("bbox") Geometry bbox);

}
