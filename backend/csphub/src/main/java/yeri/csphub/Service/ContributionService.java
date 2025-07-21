package yeri.csphub.Service;

import org.springframework.stereotype.Service;
import yeri.csphub.DTO.ContributionSummary;
import yeri.csphub.Entities.Contributions;
import yeri.csphub.Entities.Users;
import yeri.csphub.Repository.ContributionsRepo;
import yeri.csphub.utils.UserUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ContributionService {

    private final ContributionsRepo contributionsRepo;
    private final UserUtil userUtil;

    public ContributionService(ContributionsRepo contributionsRepo, UserUtil userUtil){
        this.contributionsRepo = contributionsRepo;
        this.userUtil = userUtil;
    }

    public Map<LocalDate, Long> getContributionDates(String username){
        Map<LocalDate, Long> res = new HashMap<>();
        Users users = userUtil.findUser();
        UUID id = users.getId();
//        List<ContributionSummary> summary = contributionsRepo.getUserContributionsPerDay(id);
//        for (ContributionSummary c: summary){
//            res.put(c.getDate(), c.getCount());
//        }
        return res;

    }

}
