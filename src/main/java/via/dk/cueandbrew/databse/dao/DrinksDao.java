package via.dk.cueandbrew.databse.dao;

import java.sql.SQLException;

public interface DrinksDao
{
    void addDrink(String name,double price,int quantity) throws SQLException;
}