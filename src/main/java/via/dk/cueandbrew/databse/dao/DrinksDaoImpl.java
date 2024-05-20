package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A class that implements the methods that are avaliable for interacting with the drinks in the database
 * @author Andreea Caisim
 */
public class DrinksDaoImpl implements DrinksDao
{
    private static DrinksDaoImpl instance;
    private DrinksDaoImpl() throws SQLException {
    }
    /**
     * A method that creates an instance of the class (Singleton)
     * @return an instance of the class
     * @throws SQLException
     */
    public static DrinksDaoImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new DrinksDaoImpl();
        }
        return instance;
    }

    /**
     * A method that adds a drink to the database
     * @param name the name of the drink
     * @param price the price of the drink
     * @param quantity the quantity of the drink
     * @throws SQLException
     */
    @Override public void addDrink(String name, double price, int quantity) throws SQLException
    {
        try (Connection connection = Database.createConnection())
        {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO cueandbrew.drinks (name,price,quantity) VALUES (?, ?, ?)");
            statement.setString(1,name);
            statement.setDouble(2,price);
            statement.setInt(3,quantity);
            boolean execute = statement.execute();
            System.out.println(execute);
        }
        catch (SQLException e)
        {
            throw e;
        }
    }
}