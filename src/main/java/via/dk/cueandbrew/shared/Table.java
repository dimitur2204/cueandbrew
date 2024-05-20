package via.dk.cueandbrew.shared;

import java.io.Serializable;

/**
 * A class that is responsible for the Table
 * @Author Darja Jefremova, Marius Marcoci
 */
public class Table implements Serializable {
    private final Integer number;
    /**
     * A constructor that initializes the Table with the specified values
     * @param number The number of the table
     */
    public Table(Integer number) {
        this.number = number;
    }

    /**
     * A method that returns the number
     * @return The number of the table
     */
    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Table table = (Table) obj;
        return table.getNumber() == this.getNumber();
    }

    @Override public String toString()
    {
        return "Table{" + "number=" + number + '}';
    }
}
