package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.shared.Feedback;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface FeedbackDao
{
  Feedback createFeedback(String feedback, String selectedType, String firstname, String lastname) throws RemoteException, SQLException;
  List<Feedback> getFeedbacks() throws RemoteException, SQLException;
  boolean checkFeedback(int managerId, int feedbackId) throws SQLException;
}
