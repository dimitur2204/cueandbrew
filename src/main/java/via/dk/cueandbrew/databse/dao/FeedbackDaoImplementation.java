package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;
import via.dk.cueandbrew.shared.Feedback;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
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

  @Override public Feedback createFeedback(String content, String selectedType, String firstname, String lastname)
      throws SQLException
  {
    try(Connection connection = Database.createConnection()) {
      PreparedStatement statement = connection.prepareStatement("""
          insert into cueandbrew.feedbacks (author_firstname, author_lastname, content, type, checked_by_id)
          values (?, ?, ?, ?, ?);
          """, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, firstname);
      statement.setString(2, lastname);
      statement.setString(3, content);
      statement.setString(4, selectedType);
      statement.setNull(5, Types.INTEGER);

      if (statement.executeUpdate() > 0) {
        ResultSet insertedRow = statement.getGeneratedKeys();
        if (insertedRow.next()) {
          return new Feedback(insertedRow.getInt("feedback_id"), firstname, lastname, content, selectedType, 0);
        }
      }

      return null;
    }
  }

  @Override public List<Feedback> getFeedbacks()
      throws RemoteException, SQLException
  {
    try (Connection connection = Database.createConnection()) {
      PreparedStatement statement = connection.prepareStatement("""
          select * from cueandbrew.feedbacks
          where checked_by_id is null;
          """);
      ResultSet resultSet = statement.executeQuery();
      List<Feedback> feedbacks = new ArrayList<>();
      while (resultSet.next()) {
        feedbacks.add(new Feedback(resultSet.getInt("feedback_id"),
            resultSet.getString("author_firstname"), resultSet.getString("author_lastname"),
            resultSet.getString("content"), resultSet.getString("type"),
            resultSet.getInt("checked_by_id")));
      }
      return feedbacks;
    }
  }

  @Override public boolean checkFeedback(int managerId, int feedbackId)
      throws SQLException
  {
    try(Connection connection = Database.createConnection()) {
      PreparedStatement statement = connection.prepareStatement("""
          update cueandbrew.feedbacks
          set checked_by_id = ?
          where feedback_id = ?;
          """);
      statement.setInt(1, managerId);
      statement.setInt(2, feedbackId);

      return statement.executeUpdate() > 0;
    }

  }
}
