package yeri.csphub.DTO;

import java.time.LocalDate;

public class ContributionSummary {
    private LocalDate date;
    private Long count;

    public ContributionSummary(LocalDate date, Long count) {
        this.date = date;
        this.count = count;
    }

    // Getters
    public LocalDate getDate() {
        return date;
    }

    public Long getCount() {
        return count;
    }

    // Setters (optional for DTOs but often included)
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ContributionSummary{" +
                "date=" + date +
                ", count=" + count +
                '}';
    }
}