package yeri.csphub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import yeri.csphub.DTO.ContributionSummary;
import yeri.csphub.Entities.Contributions;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContributionsRepo extends JpaRepository<Contributions, Long> {
//    @Query("""
//    SELECT new yeri.csphub.DTO.ContributionSummary(
//        FUNCTION('DATE', c.timestamp), COUNT(c)
//    )
//    FROM Contributions c
//    WHERE c.userId.id = :userId
//    GROUP BY FUNCTION('DATE', c.timestamp)
//    ORDER BY FUNCTION('DATE', c.timestamp)
//""")
//    List<ContributionSummary> getUserContributionsPerDay(@Param("userId") UUID userId);
}
