package via.dk.cueandbrew.shared;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Booking implements Serializable {
    private List<Table> tables;
    private Date date;
    private Time startTime;
    private Time endTime;

    public Booking() {
        this.tables = null;
        this.date = null;
        this.startTime = null;
        this.endTime = null;
    }

    public Booking(Date date, Time startTime, Time endTime) {
        this.tables = new ArrayList<>();
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setStartTime(Time startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime)
    {
        this.endTime = endTime;
    }

    public List<Table> getTables() {
        return tables;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setTables(List<Table> tables)
    {
        this.tables = tables;
    }

    public boolean containsTable(int number) {
      for (Table table : this.tables)
      {
        if (table.getNumber() == number)
        {
          return true;
        }
      }

        return false;
    }

    public String getStringTables() {
        String content = "";
        for (int i = 0; i < this.tables.size(); i++)
        {
          content += (this.tables.get(i).getNumber());
          if (i != this.tables.size() - 1) {
              content += ", ";
          }
        }
        return content;
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
