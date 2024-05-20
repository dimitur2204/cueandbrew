package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;
import via.dk.cueandbrew.shared.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A class that implements the RegistrationDao interface and defines the methods that are avaliable for interacting with the registration in the database
 * @author Marius Marcoci
 */
public class RegistrationDaoImplementation implements RegistrationDao
{
  private static RegistrationDaoImplementation instance;

  private RegistrationDaoImplementation() {
  }

  /**
   * A method that creates an instance of the RegistrationDaoImplementation class
   * @return an instance of the RegistrationDaoImplementation class
   * @throws SQLException
   */
  public static RegistrationDaoImplementation getInstance() throws SQLException {
    if (instance == null) {
      instance = new RegistrationDaoImplementation();
    }
    return instance;
  }

    /**
     * A method that gets the registration from the database
     * @param login the login of the registration
     * @param password the password of the registration
     * @return the registration
     * @throws SQLException
     */
  @Override public Registration getRegistration(String login,
      String password) throws SQLException
  {
    try (Connection connection = Database.createConnection()) {
      PreparedStatement statement = connection.prepareStatement("""
          select * from cueandbrew.registrations\s
          where login = ? and password = ?;
          """);
      statement.setString(1, login);
      statement.setString(2, password);
      var result = statement.executeQuery();
      Registration registration = Registration.getInstance();
      if (result.next()) {
        registration.setLogin(result.getString("login"));
        registration.setManager_id(result.getInt("manager_id"));
        return registration;
      }
      return null;
    }
  }
}
