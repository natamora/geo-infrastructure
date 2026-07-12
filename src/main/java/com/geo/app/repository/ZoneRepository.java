package com.geo.app.repository;

import com.geo.app.domain.Zone;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    @Query("SELECT z FROM Zone z WHERE ST_Intersects(z.shape, :bbox) = true")
    List<Zone> findByBBox(@Param("bbox") Geometry bbox);
}
