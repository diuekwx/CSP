package yeri.csphub.DTO;

import java.time.LocalDate;

public class ContributionSummary {
    private LocalDate date;
    private long count;

    public ContributionSummary(LocalDate localDate, long count){
        this.date = localDate;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
