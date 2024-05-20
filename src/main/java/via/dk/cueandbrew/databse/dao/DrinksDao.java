package via.dk.cueandbrew.databse.dao;

import java.sql.SQLException;

/**
 * An interface that defines the methods that are avaliable for interacting with the drinks in the database
 * @Author Dimitar Nizamov
 */
public interface DrinksDao
{
    boolean addDrink(String name,double price,int quantity) throws SQLException;
}