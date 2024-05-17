package via.dk.cueandbrew.databse.dao;

import via.dk.cueandbrew.databse.Database;
import via.dk.cueandbrew.shared.Notification;
import via.dk.cueandbrew.shared.Reservation;

import javax.naming.spi.ResolveResult;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDaoImpl implements NotificationDao {
    private static NotificationDaoImpl instance;

    private NotificationDaoImpl() {
    }

    public static NotificationDaoImpl getInstance() {
        if (instance == null) {
            instance = new NotificationDaoImpl();
        }
        return instance;
    }

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

    @Override
    public void markNotificationAsRead(Notification notification) throws SQLException {
        try (Connection connection = Database.createConnection()) {
            var statement = connection.prepareStatement("""
                    update cueandbrew.notifications
                    set was_seen = ?
                    where notification_id = ?;
                    """);
            statement.setBoolean(1, true);
            statement.setInt(2, notification.getNotificationId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

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
