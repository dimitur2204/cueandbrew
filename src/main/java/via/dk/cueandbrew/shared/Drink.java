package via.dk.cueandbrew.shared;

import java.io.Serializable;

/**
 * A class that is responsible for the Drink
 * @Author Dimitar Nizamov
 */
public class Drink implements Serializable {
    private int id;
    private String name;
    private double price;
    private int quantityOfDrink;

    /**
     * A constructor that initializes the Drink with the specified values
     * @param id The id of the drink
     * @param name The name of the drink
     * @param price The price of the drink
     * @param quantityOfDrink The quantity of the drink
     */
    public Drink(int id, String name, double price, int quantityOfDrink) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityOfDrink = quantityOfDrink;
    }

    /**
     * A method that returns the id
     * @return The id of the drink
     */
    public int getId() {
        return id;
    }

    /**
     * A method that returns the name
     * @return The name of the drink
     */
    public String getName() {
        return name;
    }

    /**
     * A method that returns the price
     * @return The price of the drink
     */
    public double getPrice() {
        return price;
    }

    /**
     * A method that returns the quantity of the drink
     * @return The quantity of the drink
     */
    public int getQuantityOfDrink() {
        return quantityOfDrink;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Drink)) {
            return false;
        }
        Drink other = (Drink) obj;
        return id == other.id && name.equals(other.name) && price == other.price && quantityOfDrink == other.quantityOfDrink;
    }
}
