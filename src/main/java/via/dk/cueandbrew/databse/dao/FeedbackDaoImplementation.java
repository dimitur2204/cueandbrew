package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;
import via.dk.cueandbrew.shared.Feedback;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.List;

/**
 * An interface that defines the methods that are avaliable for interacting with the feedback in the database
 * @Author Marius Marcoci
 */
public class FeedbackDaoImplementation implements FeedbackDao
{
  private static FeedbackDaoImplementation instance;

  private FeedbackDaoImplementation() {}

  /**
   * A method that creates an instance of the FeedbackDaoImplementation class
   * @return an instance of the FeedbackDaoImplementation class
   * @throws SQLException
   */
  public static FeedbackDaoImplementation getInstance() throws SQLException {
    if (instance == null) {
      instance = new FeedbackDaoImplementation();
    }
    return instance;
  }

  /**
   * A method that creates a feedback
   * @param content the content of the feedback
   * @param selectedType the type of the feedback
   * @param firstname the first name of the author of the feedback
   * @param lastname the last name of the author of the feedback
   * @return true if the feedback is created, false if not
   * @throws SQLException
   */
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

    /**
     * A method that gets all the feedbacks from the database
     * @return a list of all the feedbacks
     * @throws RemoteException
     */
  @Override public List<Feedback> getFeedbacks() throws RemoteException
  {
    return null;
  }
}
