package com.arqui.travelservice.domain.repository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arqui.travelservice.dto.ScooterUsageDTO;
import com.arqui.travelservice.dto.TravelReportDTO;
import com.arqui.travelservice.domain.model.Travel;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<ScooterUsageDTO> findTopScooters(int year, int minTrips);
    List<TravelReportDTO> findUserTripsByYear(Long userId, int year);
    List<TravelReportDTO> findTripsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}