package yeri.csphub.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.time.LocalDate;

public class ContributionSummary {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private Long count;

    public ContributionSummary(Object date, Object count) {

        if (date instanceof java.sql.Date sqlDate) {
            this.date = new Date(sqlDate.getTime());
        } else if (date instanceof java.time.LocalDate localDate) {
            this.date = java.sql.Date.valueOf(localDate);
        } else {
            throw new IllegalArgumentException("Unsupported date type: " + date.getClass());
        }

        if (count instanceof Number num) {
            this.count = num.longValue();
        } else {
            throw new IllegalArgumentException("Unsupported count type: " + count.getClass());
        }
    }

    public Date getDate() {
        return date;
    }

    public Long getCount() {
        return count;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public void setCount(Long count) {
        this.count = count;
    }


}