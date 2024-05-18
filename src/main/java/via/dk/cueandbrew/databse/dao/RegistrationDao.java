package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.shared.Registration;

import java.sql.SQLException;

/**
 * An interface that defines the methods that are avaliable for interacting with the registration in the database
 * @Author Dimitar Nizamov
 */
public interface RegistrationDao
{
  Registration getRegistration(String login, String password) throws
      SQLException;
}
