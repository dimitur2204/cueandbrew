package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;

import javax.naming.spi.ResolveResult;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements the NotificationDao interface and defines the methods that are avaliable for interacting with the notifications in the database
 * @author Dimitar Nizamov
 */
public class NotificationDaoImpl implements NotificationDao {
    private static NotificationDaoImpl instance;

    /**
     * A private constructor that is used to create an instance of the NotificationDaoImpl class
     */
    private NotificationDaoImpl() {
    }

    /**
     * A method that is used to create an instance of the NotificationDaoImpl class
     * @return an instance of the NotificationDaoImpl class
     */
    public static NotificationDaoImpl getInstance() {
        if (instance == null) {
            instance = new NotificationDaoImpl();
        }
        return instance;
    }

    /**
     * A method that is used to fetch all notifications from the database
     * @return a list of all notifications
     * @throws SQLException
     */
    @Override
    public List<Notification> fetchNotifications() throws SQLException {
        try (Connection connection = Database.createConnection()) {
            var statement = connection.prepareStatement("""
                    select * from cueandbrew.notifications;
                    """);
            var result = statement.executeQuery();
            var notifications = new ArrayList<Notification>();
            while (result.next()) {
                Reservation res = ReservationDaoImpl.getInstance().getReservationById(result.getInt("reservation_id"));
                Notification not = new Notification(res);
                not.setNotificationId(result.getInt("notification_id"));
                not.setWasSeen(result.getInt("was_seen"));
                notifications.add(not);
            }
            return notifications;
        }
    }

    /**
     * A method that is used to mark a notification as read
     * @param notification the notification that is to be marked as read
     * @throws SQLException
     */
    @Override
    public void markNotificationAsRead(Notification notification) throws SQLException {
        try (Connection connection = Database.createConnection()) {
            var statement = connection.prepareStatement("""
                    update cueandbrew.notifications
                    set was_seen = ?
                    where notification_id = ?;
                    """);
            statement.setInt(1, 1);
            statement.setInt(2, notification.getNotificationId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * A method that is used to create a notification
     * @param notification the notification that is to be created
     * @throws SQLException
     */
    @Override
    public void createNotification(Notification notification) throws SQLException {
        try (Connection connection = Database.createConnection()) {
            var statement = connection.prepareStatement("""
                    insert into cueandbrew.notifications (was_seen, reservation_id, creation_time)
                    values (?, ?, ?);
                    """);
            statement.setInt(1, notification.getWasSeen());
            statement.setInt(2, notification.getReservation().getReservationId());
            statement.setTimestamp(3, notification.getReservation().getCreationDatetime());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
