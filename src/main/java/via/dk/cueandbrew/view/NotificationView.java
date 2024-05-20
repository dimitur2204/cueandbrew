package via.dk.cueandbrew.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NotificationView extends HBox {
    public NotificationView(via.dk.cueandbrew.shared.Notification notification, EventHandler<ActionEvent> onSeenButtonClicked) {
        super();
        this.styleProperty().set("-fx-padding: 0 5 0 5;");
        var reservation = notification.getReservation();
        var wrapper = new VBox();
        wrapper.setPrefHeight(120);
        wrapper.setPrefWidth(535);
        var timeLabel = new Label(reservation.getCreationDatetime().toString());
        wrapper.getChildren().add(timeLabel);
        StringBuilder tablesStr = new StringBuilder();
        tablesStr.append("Tables: ");
        for (var table : reservation.getBooking().getTables()) {
            tablesStr.append(table.getNumber()).append(" ");
        }
        var reservationString = "Name: " + reservation.getClientFirstName() + " " + reservation.getClientLastName() + "\n" +
                "Phone number: " + reservation.getClientPhoneNumber() + "\n" +
                "Date: " + reservation.getBooking().getDate().toString() + "\n" +
                "Time: " + reservation.getBooking().getStartTime().toLocalTime().toString() + " " + reservation.getBooking().getEndTime().toLocalTime().toString() + "\n" +
                tablesStr + "\n" +
                " ";
        var reservationLabel = new Label(reservationString);
        reservationLabel.setPrefHeight(91);
        reservationLabel.setPrefWidth(278);
        wrapper.getChildren().add(reservationLabel);

        var buttonWrapper = new VBox();
        buttonWrapper.setAlignment(javafx.geometry.Pos.CENTER);
        buttonWrapper.setPrefHeight(88);
        buttonWrapper.setPrefWidth(348);
        var button = new Button("Seen");
        button.setOnAction(onSeenButtonClicked);
        button.setAlignment(javafx.geometry.Pos.CENTER);
        button.setMnemonicParsing(false);
        buttonWrapper.getChildren().add(button);
        this.getChildren().addAll(wrapper, buttonWrapper);
        //add margin to the bottom of the view with a hbox appended to the bottom
        var bottomMargin = new HBox();
        bottomMargin.setPrefHeight(10);
        this.getChildren().add(bottomMargin);
    }
}
