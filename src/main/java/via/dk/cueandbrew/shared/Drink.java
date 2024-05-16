package via.dk.cueandbrew.shared;

import java.io.Serializable;

public class Drink implements Serializable
{
    private final String name;
    private final double price;
    private final int quantityOfDrink;
    public Drink(String name, double price, int quantityOfDrink) {
        this.name = name;
        this.price = price;
        this.quantityOfDrink = quantityOfDrink;
    }

    public String getName()
    {
        return name;
    }

    public double getPrice()
    {
        return price;
    }

    public int getQuantityOfDrink()
    {
        return quantityOfDrink;
    }

    @Override public String toString()
    {
        return "Drink{" + "name='" + name + '\'' + ", price=" + price
            + ", quantityOfDrink=" + quantityOfDrink + '}';
    }
}
