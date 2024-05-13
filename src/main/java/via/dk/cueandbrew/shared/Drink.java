package via.dk.cueandbrew.shared;

public class Drink {
    private String name;
    private double price;
    private int quantityOfDrink;
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
}
