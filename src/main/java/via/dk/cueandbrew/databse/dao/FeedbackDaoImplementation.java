package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;
import via.dk.cueandbrew.shared.Feedback;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.List;

public class FeedbackDaoImplementation implements FeedbackDao
{
  private static FeedbackDaoImplementation instance;

  private FeedbackDaoImplementation() {}

  public static FeedbackDaoImplementation getInstance() throws SQLException {
    if (instance == null) {
      instance = new FeedbackDaoImplementation();
    }
    return instance;
  }

  @Override public boolean createFeedback(String content, String selectedType, String firstname, String lastname)
      throws SQLException
  {
    try(Connection connection = Database.createConnection()) {
      PreparedStatement statement = connection.prepareStatement("""
          insert into cueandbrew.feedbacks (author_firstname, author_lastname, content, type, checked_by_id)
          values (?, ?, ?, ?, ?);
          """);
      statement.setString(1, firstname);
      statement.setString(2, lastname);
      statement.setString(3, content);
      statement.setString(4, selectedType);
      statement.setNull(5, Types.INTEGER);
      int rowsAffected = statement.executeUpdate();

      return rowsAffected > 0;
    }
  }

  @Override public List<Feedback> getFeedbacks() throws RemoteException
  {
    return null;
  }
}
