package via.dk.cueandbrew.shared;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable
{
    private List<Drink> drinks;
    private Timestamp expectedDatetime;

    public Order()
    {
        this.drinks = null;
        this.expectedDatetime = null;
    }

    public Order(Timestamp expectedDatetime) {
        this.drinks = new ArrayList<>();
        this.expectedDatetime = expectedDatetime;
    }

    public void setDrinks(List<Drink> drinks)
    {
        this.drinks = drinks;
    }

    public Timestamp getExpectedDatetime()
    {
        return expectedDatetime;
    }

    public void setExpectedDatetime(Timestamp expectedDatetime)
    {
        this.expectedDatetime = expectedDatetime;
    }

    public boolean containsDrink(String drink) {
      for (Drink value : this.drinks)
      {
        if (value.getName().equals(drink))
        {
          return true;
        }
      }

        return false;
    }

    public String getDrinksToString() {
        String content = "";
        for (int i = 0; i < this.drinks.size(); i++)
        {
            content += (this.drinks.get(i).getName());
            if (i != this.drinks.size() - 1) {
                content += ", ";
            }
        }
        return content;
    }

    public List<Drink> getDrinks()
    {
        return drinks;
    }
    public void addDrink(Drink drink){
        drinks.add(drink);
    }

    @Override public String toString()
    {
        return "Order{" + "drinks=" + drinks + ", expectedDatetime="
            + expectedDatetime + '}';
    }
}
