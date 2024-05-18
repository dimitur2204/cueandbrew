package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.shared.Notification;

import java.sql.SQLException;
import java.util.List;

/**
 * An interface that defines the methods that are avaliable for interacting with the notifications in the database
 * @Author Dimitar Nizamov
 */
public interface NotificationDao {
    List<Notification> fetchNotifications() throws SQLException;

    void markNotificationAsRead(Notification notification) throws SQLException;

    void createNotification(Notification notification) throws SQLException;
}
