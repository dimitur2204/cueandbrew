package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.shared.Drink;

import java.sql.SQLException;

public interface DrinksDao
{
  void addDrink(String name,double price,int quantity) throws SQLException;
}
