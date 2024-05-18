package via.dk.cueandbrew.shared;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that is responsible for the Order
 * @Author Andreea Caisim
 */
public class Order implements Serializable
{
    private List<Drink> drinks;
    private Timestamp expectedDatetime;
    /**
     * A constructor that initializes the Order with null values
     */
    public Order() {
        this.drinks = new ArrayList<>();
        this.expectedDatetime = null;
    }
    /**
     * A constructor that initializes the Order with the specified values
     * @param expectedDatetime The expected date and time of the order
     */
    public Order(Timestamp expectedDatetime) {
        this.drinks = new ArrayList<>();
        this.expectedDatetime = expectedDatetime;
    }

    /**
     * A constructor that initializes the Order with the specified values
     * @param drinks The drinks of the order
     * @param expectedDatetime The expected date and time of the order
     */
    public Order(List<Drink> drinks, Timestamp expectedDatetime) {
        this.drinks = drinks;
        this.expectedDatetime = expectedDatetime;
    }
    /**
     * A method that returns the expected date and time
     * @return The expected date and time of the order
     */
    public Timestamp getExpectedDatetime() {
        return expectedDatetime;
    }

    /**
     * A method that checks if the drink is already in the order
     * @param drink The drink to be checked
     */
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

    /**
     * A method that converts the drinks to a string
     * @return The drinks of the order as a string
     */
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

    /**
     * A method that return the list of drinks
     */
    public List<Drink> getDrinks()
    {
        return drinks;
    }

    /**
     * A method that sets the drinks
     * @param drinks The drinks of the order
     */
    public void setDrinks(List<Drink> drinks) {
this.drinks = drinks;
    }

    /**
     * A method that sets the expected date and time
     * @param expectedDatetime The expected date and time of the order
     */
    public void setExpectedDatetime(Timestamp expectedDatetime) {
        this.expectedDatetime = expectedDatetime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order) {
            Order other = (Order) obj;
            return this.drinks.equals(other.drinks) && this.expectedDatetime.equals(other.expectedDatetime);
        }
        return false;
    }
}
