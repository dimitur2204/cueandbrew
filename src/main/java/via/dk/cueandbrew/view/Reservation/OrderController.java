package via.dk.cueandbrew.view.Reservation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import via.dk.cueandbrew.shared.Drink;
import via.dk.cueandbrew.viewmodel.Reservation.OrderViewModel;

import java.sql.Timestamp;

public class OrderController {

    @FXML private ComboBox hourField;
    @FXML private ComboBox minutesField;
    @FXML private VBox menuContainer;
    @FXML private VBox selections;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    private OrderViewModel viewModel;

    public void init(OrderViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.updateDateTime(dateLabel, timeLabel);
        this.viewModel.startDateTimeUpdater(dateLabel, timeLabel);
        setTimeOfDrinks();
        for (Drink drink : this.viewModel.getDrinks()) {
            Label menuDrinkLabel = new Label();
            menuDrinkLabel.setText(drink.getName() + " - " + drink.getPrice() + "dkk - " + drink.getQuantityOfDrink() + "ml ");
            Button addButton = buildActionButton("+");
            HBox drinkBox = buildDrinkBox();
            drinkBox.getChildren().addAll(menuDrinkLabel, addButton);
            menuContainer.getChildren().add(drinkBox);
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    viewModel.getOrders().add(drink);
                    Label orderedDrinkLabel = new Label();
                    orderedDrinkLabel.setText(drink.getName() + " - " + drink.getPrice() + "dkk - " + drink.getQuantityOfDrink() + "ml ");
                    Button orderedDrinkButton = buildActionButton("-");
                    HBox box2 = buildDrinkBox();
                    box2.getChildren().addAll(orderedDrinkLabel, orderedDrinkButton);
                    selections.getChildren().add(box2);
                    orderedDrinkButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            viewModel.getOrders().remove(drink);
                            selections.getChildren().remove(box2);
                        }
                    });
                }
            });
        }
    }

    private Button buildActionButton(String text) {
        Button button = new Button();
        button.setTranslateX(20);
        button.setText(text);
        return button;
    }

    private HBox buildDrinkBox() {
        HBox box = new HBox();
        box.setPrefHeight(30.0);
        box.setPrefWidth(290.0);
        box.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx: 0 0 0 0;");
        return box;
    }

    private void setTimeOfDrinks() {
        for (int i = 0; i < 24; i++) {
            hourField.getItems().add(i);
        }

        for (int i = 0; i <= 59; i += 15) {
            minutesField.getItems().add(i);
        }
        this.hourField.setValue(this.viewModel.getBookingHour());
        this.minutesField.setValue(this.viewModel.getBookingMinute());
    }

    public void onCancel() {
        this.viewModel.onCancel();
    }

    public void onBackToReservations() {
        this.viewModel.onBackToReservations();
    }

    public void onSkip() {
        this.viewModel.onSkip();
    }

    public void onConfirm() {
        var hours = (int) hourField.getValue();
        var minutes = (int) minutesField.getValue();
        var date = this.viewModel.getBookingDate();
        var expected = new Timestamp(date.getYear(), date.getMonth(), date.getDate(), hours, minutes, 0, 0);
        this.viewModel.onConfirm(expected);
    }

}
