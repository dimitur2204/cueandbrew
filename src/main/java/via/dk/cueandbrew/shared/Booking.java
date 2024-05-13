package via.dk.cueandbrew.shared;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private List<Table> tables;
    private Date date;
    private Time startTime;
    private Time endTime;
    public Booking(Date date, Time startTime, Time endTime) {
        this.tables = new ArrayList<>();
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public List<Table> getTables() {
        return tables;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "tables=" + tables +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
