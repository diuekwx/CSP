package yeri.csphub.Service;

import org.springframework.stereotype.Service;
import yeri.csphub.DTO.ContributionSummary;
import yeri.csphub.Entities.Contributions;
import yeri.csphub.Repository.ContributionsRepo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ContributionService {

    private final ContributionsRepo contributionsRepo;

    public ContributionService(ContributionsRepo contributionsRepo){
        this.contributionsRepo = contributionsRepo;
    }

    public Map<LocalDate, Long> getContributionDates(String username){
        Map<LocalDate, Long> res = new HashMap<>();
        List<ContributionSummary> summary = contributionsRepo.getUserContributionsPerDay(username);
        for (ContributionSummary c: summary){
            res.put(c.getDate(), c.getCount());
        }
        return res;

    }

}
