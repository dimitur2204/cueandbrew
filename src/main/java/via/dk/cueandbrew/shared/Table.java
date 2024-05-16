package via.dk.cueandbrew.shared;

import java.io.Serializable;

public class Table implements Serializable {
    private final Integer number;
    public Table(Integer number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override public String toString()
    {
        return "Table{" + "number=" + number + '}';
    }
}
