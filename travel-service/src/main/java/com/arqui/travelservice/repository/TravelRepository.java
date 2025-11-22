package com.arqui.travelservice.repository;
import com.arqui.travelservice.entity.Travel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TravelRepository extends MongoRepository<Travel, String> {

    // C – Scooters con más de X viajes en un año
    @Aggregation(pipeline = {
        "{ $match: { $expr: { $eq: [ { $year: '$startTime' }, ?0 ] } } }",
        "{ $group: { _id: '$scooterId', totalTrips: { $count: {} } } }",
        "{ $match: { totalTrips: { $gte: ?1 } } }",
        "{ $sort: { totalTrips: -1 } }",
        "{ $project: { scooterId: '$_id', totalTrips: 1, _id: 0 } }"
    })
    List<ScooterUsageDTO> findTopScooters(int year, int minTrips);


    // E – Usuarios que más viajan en un período
    @Aggregation(pipeline = {
        "{ $match: { startTime: { $gte: ?0 }, endTime: { $lte: ?1 } } }",
        "{ $group: { _id: '$userId', accountId: { $first: '$accountId' }, " +
            "tripCount: { $count: {} }, totalKm: { $sum: '$distanceKm' } } }",
        "{ $sort: { tripCount: -1 } }",
        "{ $project: { userId: '$_id', accountId: 1, tripCount: 1, totalKm: 1, _id: 0 } }"
    })
    List<TravelReportDTO> findTripsByPeriod(LocalDateTime startDate, LocalDateTime endDate);


    // H – Uso de un usuario
    @Aggregation(pipeline = {
        "{ $match: { startTime: { $gte: ?0 }, endTime: { $lte: ?1 }, userId: ?2 } }",
        "{ $group: { _id: { scooterId: '$scooterId', userId: '$userId' }, " +
            "firstUse: { $min: '$startTime' }, lastUse: { $max: '$endTime' }, " +
            "totalKm: { $sum: '$distanceKm' }, totalTrips: { $count: {} } } }",
        "{ $sort: { totalTrips: -1 } }",
        "{ $project: { scooterId: '$_id.scooterId', userId: '$_id.userId', firstUse: 1, lastUse: 1, totalKm: 1, totalTrips: 1, _id: 0 } }"
    })
    List<UserScooterUsageDTO> findScooterUsageByUser(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Long userId);


    @Aggregation(pipeline = {
        "{ $match: { startTime: { $gte: ?0 }, endTime: { $lte: ?1 }, accountId: { $in: ?2 } } }",
        "{ $group: { _id: { scooterId: '$scooterId', userId: '$userId' }, " +
            "firstUse: { $min: '$startTime' }, lastUse: { $max: '$endTime' }, " +
            "totalKm: { $sum: '$distanceKm' }, totalTrips: { $count: {} } } }",
        "{ $sort: { totalTrips: -1 } }",
        "{ $project: { scooterId: '$_id.scooterId', userId: '$_id.userId', firstUse: 1, lastUse: 1, totalKm: 1, totalTrips: 1, _id: 0 } }"
    })
    List<UserScooterUsageDTO> findScooterUsageByAccount(
            LocalDateTime startDate,
            LocalDateTime endDate,
            List<Long> accounts);
}
