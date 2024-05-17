package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.shared.Notification;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDao {
    List<Notification> fetchNotifications() throws SQLException;

    void markNotificationAsRead(Notification notification) throws SQLException;

    void createNotification(Notification notification) throws SQLException;
}
