package via.dk.cueandbrew.shared;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that is responsible for the Booking
 * @Author Dimitar Nizamov
 */
public class Booking implements Serializable {
    private List<Table> tables;
    private Date date;
    private Time startTime;
    private Time endTime;

    /** A constructor that initializes the Booking with null values*/
    public Booking() {
        this.tables = null;
        this.date = null;
        this.startTime = null;
        this.endTime = null;
    }

    /**
     * A constructor that initializes the Booking with the specified values
     * @param date The date of the booking
     * @param startTime The start time of the booking
     * @param endTime The end time of the booking
     */
    public Booking(Date date, Time startTime, Time endTime) {
        this.tables = new ArrayList<>();
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * A method that returns the date
     * @return The date of the booking
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * A method that sets the date
     * @param date The date of the booking
     */
    public void setDate(Date date)
    {
        this.date = date;
    }


    /**
     * Sets the start time of the booking
     * @param startTime The start time of the booking
     */
    public void setStartTime(Time startTime)
    {
        this.startTime = startTime;
    }

    /**
     * Sets the end time of the booking
     * @param endTime The end time of the booking
     */
    public void setEndTime(Time endTime)
    {
        this.endTime = endTime;
    }

    /**
     * A method that returns the tables
     * @return The tables of the booking
     */
    public List<Table> getTables() {
        return tables;
    }

    /**
     * A method that returns the start time of the booking
     * @return The start time of the booking
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * A method that returns the end time of the booking
     * @return The end time of the booking
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * A method that sets the tables
     * @param tables The tables of the booking
     */
    public void setTables(List<Table> tables)
    {
        this.tables = tables;
    }

    /**
     * A method that checks if the booking contains a table with the specified number
     * @param number The number of the table
     */
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

    /**
     * A method that returns a string with all tables
     * @return The tables of the booking
     */
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Booking other = (Booking) obj;
        return other.getTables().equals(this.getTables()) && other.getDate().equals(this.getDate()) && other.getStartTime().equals(this.getStartTime()) && other.getEndTime().equals(this.getEndTime());

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
