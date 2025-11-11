package com.station.repository;

import com.station.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    // Buscar estación por nombre exacto
    Optional<Station> findByName(String name);

    // Buscar estaciones por una parte del nombre (ignorando mayúsculas/minúsculas)
    List<Station> findByNameContainingIgnoreCase(String name);

    // Buscar estaciones activas/inactivas (si tu entity tiene ese campo)
    List<Station> findByActive(boolean active);
}

