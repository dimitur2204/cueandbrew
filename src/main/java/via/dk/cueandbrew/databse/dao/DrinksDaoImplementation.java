package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;
import via.dk.cueandbrew.shared.Drink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DrinksDaoImplementation implements DrinksDao
{
  private static DrinksDaoImplementation instance;
  private DrinksDaoImplementation() throws SQLException {
  }
  public static DrinksDaoImplementation getInstance() throws SQLException {
    if (instance == null) {
      instance = new DrinksDaoImplementation();
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
