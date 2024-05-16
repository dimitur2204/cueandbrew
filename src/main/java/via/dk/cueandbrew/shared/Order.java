package via.dk.cueandbrew.shared;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private List<Drink> drinks;
    private Timestamp expectedDatetime;
    public Order(Timestamp expectedDatetime) {
        this.drinks = new ArrayList<>();
        this.expectedDatetime = expectedDatetime;
    }

    public Timestamp getExpectedDatetime() {
        return expectedDatetime;
    }

    public List<Drink> getDrinks()
    {
        return drinks;
    }
}
