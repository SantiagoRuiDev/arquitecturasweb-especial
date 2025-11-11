package repository;

import entity.Skateboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkateboardRepository  extends JpaRepository<Skateboard,Long> {

   List<Skateboard> findByAvailable();

   List<Skateboard> findByInMaintenance();

   List<Skateboard> findByTotalKm(double minKm);

   List<Skateboard> findById();

    @Query("SELECT * FROM Skateboard sk " +
            "WHERE sk.totalKm = :km ")
    List<Skateboard> findByTotalKm(@Param("km") Integer km);
}
