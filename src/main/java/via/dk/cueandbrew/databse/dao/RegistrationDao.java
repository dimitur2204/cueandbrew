package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.shared.Registration;

import java.sql.SQLException;

public interface RegistrationDao
{
  Registration getRegistration(String login, String password) throws
      SQLException;
}
