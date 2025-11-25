package com.arqui.travelservice.repository;
import com.arqui.travelservice.dto.ScooterUsageDTO;
import com.arqui.travelservice.dto.TravelReportDTO;
import com.arqui.travelservice.dto.response.AccountBalanceResponseDTO;
import com.arqui.travelservice.dto.response.UserScooterUsageDTO;
import com.arqui.travelservice.entity.Travel;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TravelRepository extends MongoRepository<Travel, String> {


    @Aggregation(pipeline = {
            "{ $match: { startTime: { $gte: ?0 }, endTime: { $lte: ?1 } } }",
            "{ $group: { _id: '$accountId', " +
                    "accountId: { $first: '$accountId' }, " +
                    "tripCount: { $sum: 1 }, " +
                    "totalDistance: { $sum: '$distanceKm' }, " +
                    "totalSpent: { $sum: '$cost' } } }",
            "{ $sort: { tripCount: -1 } }",
            "{ $project: { tripCount: 1, totalDistance: 1, totalSpent: 1, _id: 0 } }"
    })
    AccountBalanceResponseDTO getTravelBalanceByAccount(LocalDateTime fromDate, LocalDateTime toDate, Long accountId);

    // C – Scooters con más de X viajes en un año
    @Aggregation(pipeline = {
            "{ $match: { $expr: { $eq: [ { $year: '$startTime' }, ?0 ] } } }",
            "{ $group: { _id: '$scooterId', totalTrips: { $sum: 1 } } }",
            "{ $match: { totalTrips: { $gte: ?1 } } }",
            "{ $sort: { totalTrips: -1 } }",
            "{ $project: { scooterId: '$_id', tripCount: '$totalTrips', _id: 0 } }"
    })
    List<ScooterUsageDTO> findTopScooters(int year, int minTrips);


    // E – Usuarios que más viajan en un período
    @Aggregation(pipeline = {
            "{ $match: { startTime: { $gte: ?0 }, endTime: { $lte: ?1 } } }",
            "{ $group: { _id: '$userId', accountId: { $first: '$accountId' }, " +
                    "tripCount: { $sum: 1 }, totalDistance: { $sum: '$distanceKm' } } }",
            "{ $sort: { tripCount: -1 } }",
            "{ $project: { userId: '$_id', accountId: 1, tripCount: 1, totalDistance: 1, _id: 0 } }"
    })
    List<TravelReportDTO> findTripsByPeriod(LocalDateTime startDate, LocalDateTime endDate);


    // H – Uso de un usuario
    @Aggregation(pipeline = {
            "{ $match: { startTime: { $gte: ?0 }, endTime: { $lte: ?1 }, userId: ?2 } }",
            "{ $group: { _id: { scooterId: '$scooterId', userId: '$userId' }, " +
                    "firstUse: { $min: '$startTime' }, lastUse: { $max: '$endTime' }, " +
                    "totalKm: { $sum: '$distanceKm' }, totalTrips: { $sum: 1 } } }",
            "{ $sort: { totalTrips: -1 } }",
            "{ $project: { " +
                    "scooterId: '$_id.scooterId', " +
                    "userId: '$_id.userId', " +
                    "startDate: '$firstUse', " +
                    "endDate: '$lastUse', " +
                    "distance: '$totalKm', " +
                    "tripCount: '$totalTrips', " +
                    "_id: 0 } }"
    })
    List<UserScooterUsageDTO> findScooterUsageByUser(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Long userId);


    @Aggregation(pipeline = {
            "{ $match: { startTime: { $gte: ?0 }, endTime: { $lte: ?1 }, accountId: { $in: ?2 } } }",
            "{ $group: { _id: { scooterId: '$scooterId', userId: '$userId' }, " +
                    "firstUse: { $min: '$startTime' }, lastUse: { $max: '$endTime' }, " +
                    "totalKm: { $sum: '$distanceKm' }, totalTrips: { $sum: 1 } } }",
            "{ $sort: { totalTrips: -1 } }",
            "{ $project: { " +
                    "scooterId: '$_id.scooterId', " +
                    "userId: '$_id.userId', " +
                    "startDate: '$firstUse', " +
                    "endDate: '$lastUse', " +
                    "distance: '$totalKm', " +
                    "tripCount: '$totalTrips', " +
                    "_id: 0 } }"
    })
    List<UserScooterUsageDTO> findScooterUsageByAccount(
            LocalDateTime startDate,
            LocalDateTime endDate,
            List<Long> accounts);
}
