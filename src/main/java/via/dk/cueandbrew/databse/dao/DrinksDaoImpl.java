package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DrinksDaoImpl implements DrinksDao
{
    private static DrinksDaoImpl instance;
    private DrinksDaoImpl() throws SQLException {
    }
    public static DrinksDaoImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new DrinksDaoImpl();
        }
        return instance;
    }
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