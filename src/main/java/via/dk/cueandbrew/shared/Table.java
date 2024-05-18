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
