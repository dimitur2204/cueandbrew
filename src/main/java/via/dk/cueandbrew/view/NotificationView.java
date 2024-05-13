package via.dk.cueandbrew.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NotificationView extends HBox {
//<HBox prefHeight="100.0" prefWidth="369.0" style="-fx-padding: 0 5 0 5; -fx-border-color: gray; -fx-border-radius: 5;">
//   <children>
//      <VBox prefHeight="100.0" prefWidth="535.0">
//         <children>
//            <Label text="10.05.2024 11:46" />
//
//            <Label prefHeight="51.0" prefWidth="278.0" text="Dimitar Nizamov booked table 1 and 2 for 10.05.2024 17:00 until 10.05.2024 19:00" wrapText="true" />
//            <Label text="Phone number: +4512345678" />
//         </children>
//      </VBox>
//      <VBox alignment="CENTER" prefHeight="88.0" prefWidth="348.0">
//         <children>
//            <Button alignment="CENTER" mnemonicParsing="false" text="Button" />
//         </children>
//      </VBox>
//   </children>
//</HBox>
    public NotificationView(via.dk.cueandbrew.shared.Notification notification){
        super();
        this.styleProperty().set("-fx-padding: 0 5 0 5; -fx-border-color: gray; -fx-border-radius: 5;");
        var reservation = notification.getReservation();
        var wrapper = new VBox();
        wrapper.setPrefHeight(100);
        wrapper.setPrefWidth(535);
        var timeLabel = new Label(reservation.getCreationDatetime().toString());
        wrapper.getChildren().add(timeLabel);
        var reservationLabel = new Label(reservation.toString());
        reservationLabel.setPrefHeight(51);
        reservationLabel.setPrefWidth(278);
        wrapper.getChildren().add(reservationLabel);
        var phoneNumberLabel = new Label("Phone number: " + reservation.getClientPhoneNumber());
        wrapper.getChildren().add(phoneNumberLabel);

        var buttonWrapper = new VBox();
        buttonWrapper.setAlignment(javafx.geometry.Pos.CENTER);
        buttonWrapper.setPrefHeight(88);
        buttonWrapper.setPrefWidth(348);
        var button = new Button("Button");
        button.setAlignment(javafx.geometry.Pos.CENTER);
        button.setMnemonicParsing(false);
        buttonWrapper.getChildren().add(button);
        this.getChildren().addAll(wrapper, buttonWrapper);
    }
}
