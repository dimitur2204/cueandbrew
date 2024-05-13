package via.dk.cueandbrew.shared;

public class Table {
    private Integer number;
    public Table(Integer number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
