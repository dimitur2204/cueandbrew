package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;
import via.dk.cueandbrew.shared.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationDaoImplementation implements RegistrationDao
{
  private static RegistrationDaoImplementation instance;

  private RegistrationDaoImplementation() {
  }

  public static RegistrationDaoImplementation getInstance() throws SQLException {
    if (instance == null) {
      instance = new RegistrationDaoImplementation();
    }
    return instance;
  }

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
