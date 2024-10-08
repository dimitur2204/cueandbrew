package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class that implements the methods that are available for interacting with the drinks in the database
 * @author Andreea Caisim
 */
public class DrinksDaoImpl implements DrinksDao
{
    private static DrinksDaoImpl instance;
    private DrinksDaoImpl() {
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
    @Override public boolean addDrink(String name, double price, int quantity) throws SQLException
    {
        try (Connection connection = Database.createConnection())
        {
            PreparedStatement checkStatement = connection.prepareStatement("""
                SELECT * FROM cueandbrew.drinks
                WHERE name = ? and price = ? and quantity = ?;
                """);
            checkStatement.setString(1, name);
            checkStatement.setDouble(2, price);
            checkStatement.setInt(3, quantity);
            ResultSet resultSet = checkStatement.executeQuery();
            if (!resultSet.next()) {
                PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO cueandbrew.drinks (name,price,quantity) VALUES (?, ?, ?)");
                statement.setString(1,name);
                statement.setDouble(2,price);
                statement.setInt(3,quantity);

                return statement.executeUpdate() > 0;
            }

            return false;
        }
    }
}