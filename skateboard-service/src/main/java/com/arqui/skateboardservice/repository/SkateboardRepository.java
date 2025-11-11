package com.arqui.skateboardservice.repository;

import com.arqui.skateboardservice.entity.Skateboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkateboardRepository  extends JpaRepository<Skateboard,Long> {
    List<Skateboard> findByAvailableTrue();

   List<Skateboard> findByInMaintenanceTrue();

    // Buscar por kilómetros totales mayor o igual a cierto valor
    List<Skateboard> findByTotalKmGreaterThanEqual(double minKm);

    // Buscar por kilómetros exactos (usando query personalizada)
    @Query("SELECT sk FROM Skateboard sk WHERE sk.totalKm = :km")
    List<Skateboard> findByExactTotalKm(@Param("km") double km);
}
