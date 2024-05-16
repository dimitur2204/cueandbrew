package via.dk.cueandbrew.shared;

import java.io.Serializable;

public class Drink implements Serializable {
    private int id;
    private String name;
    private double price;
    private int quantityOfDrink;

    public Drink(int id, String name, double price, int quantityOfDrink) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityOfDrink = quantityOfDrink;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityOfDrink() {
        return quantityOfDrink;
    }
}
