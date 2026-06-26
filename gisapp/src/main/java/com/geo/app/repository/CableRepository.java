package com.geo.app.repository;

import com.geo.app.domain.Cable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CableRepository extends JpaRepository<Cable, Long> {
    Cable findByName(String name);
}
