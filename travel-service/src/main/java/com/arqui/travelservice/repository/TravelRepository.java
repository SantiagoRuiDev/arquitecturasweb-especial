package com.arqui.travelservice.repository;
import com.arqui.travelservice.entity.Travel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TravelRepository extends MongoRepository<Travel, String> {
    /*   @Query("SELECT new com.arqui.travelservice.dto.ScooterUsageDTO(t.scooterId, COUNT(t)) " +
                     "FROM Travel t " +
                     "WHERE YEAR(t.startTime) = :year " +
                     "GROUP BY t.scooterId " +
                     "HAVING COUNT(t) >= :minTrips " +
                     "ORDER BY COUNT(t) DESC")
    List<ScooterUsageDTO> findTopScooters(int year, int minTrips);

    @Query("SELECT new com.arqui.travelservice.dto.TravelReportDTO(t.userId, t.accountId, COUNT(t), SUM(t.distanceKm)) " +
           "FROM Travel t " +
           "WHERE t.startTime >= :startDate AND t.endTime <= :endDate " +
           "GROUP BY t.userId " +
           "ORDER BY COUNT(t) DESC")
    List<TravelReportDTO> findTripsByPeriod(LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
        SELECT new com.arqui.travelservice.dto.response.UserScooterUsageDTO(
            t.scooterId,
            t.userId,
            MIN(t.startTime),
            MAX(t.endTime),
            SUM(t.distanceKm),
            COUNT(t)
        )
        FROM Travel t
        WHERE t.startTime >= :startDate
          AND t.endTime <= :endDate
          AND t.userId = :userId
        GROUP BY t.scooterId, t.userId
        ORDER BY COUNT(t) DESC
        """)
    List<UserScooterUsageDTO> findScooterUsageByUser(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Long userId);

    @Query("""
        SELECT new com.arqui.travelservice.dto.response.UserScooterUsageDTO(
            t.scooterId,
            t.userId,
            MIN(t.startTime),
            MAX(t.endTime),
            SUM(t.distanceKm),
            COUNT(t)
        )
        FROM Travel t
        WHERE t.startTime >= :startDate
          AND t.endTime <= :endDate
          AND t.accountId IN :accounts
        GROUP BY t.scooterId, t.userId
        ORDER BY COUNT(t) DESC
        """)
    List<UserScooterUsageDTO> findScooterUsageByAccount(
            LocalDateTime startDate,
            LocalDateTime endDate,
            List<Long> accounts);*/
}