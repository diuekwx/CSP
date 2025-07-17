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
    List<Contributions> findAllByUsername(String username);
    @Query("""
    SELECT new yeri.csphub.DTO.ContributionSummary(DATE(c.timestamp), COUNT(c))
    FROM Contribution c
    WHERE c.user.id = :username
    GROUP BY DATE(c.timestamp)
    ORDER BY DATE(c.timestamp)""")
    List<ContributionSummary> getUserContributionsPerDay(@Param("username") String username);
}
