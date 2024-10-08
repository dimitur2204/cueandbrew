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

/**
 * A class that is responsible for the OrderController
 * @author Dimitar Nizamov, Darja Jefremova, Andreea Caisim
 */
public class OrderController {

    @FXML private ComboBox hourField;
    @FXML private ComboBox minutesField;
    @FXML private VBox menuContainer;
    @FXML private VBox selections;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML
    private Button confirmBtn;

    private OrderViewModel viewModel;
    /**
     * A method that initializes the OrderViewModel
     * @param viewModel The OrderViewModel
     */
    public void init(OrderViewModel viewModel) {
        this.confirmBtn.setDisable(true);
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
                    viewModel.addDrink(drink);
                    if(!viewModel.getOrderedDrinks().isEmpty()) {
                        confirmBtn.setDisable(false);
                    }
                    Label orderedDrinkLabel = new Label();
                    orderedDrinkLabel.setText(drink.getName() + " - " + drink.getPrice() + "dkk - " + drink.getQuantityOfDrink() + "ml ");
                    Button removeDrinkButton = buildActionButton("-");
                    HBox box2 = buildDrinkBox();
                    box2.getChildren().addAll(orderedDrinkLabel, removeDrinkButton);
                    selections.getChildren().add(box2);
                    removeDrinkButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            var drinks = viewModel.getOrderedDrinks();
                            drinks.remove(drink);
                            if(drinks.isEmpty()) {
                                confirmBtn.setDisable(true);
                            }
                            selections.getChildren().remove(box2);
                        }
                    });
                }
            });
        }
    }

    /**
     * A method that builds the action button
     * @param text The text
     * @return The button
 */
    private Button buildActionButton(String text) {
        Button button = new Button();
        button.setTranslateX(20);
        button.setText(text);
        return button;
    }

    /**
     * A method that builds the drink box
     * @return The HBox
     */
    private HBox buildDrinkBox() {
        HBox box = new HBox();
        box.setPrefHeight(30.0);
        box.setPrefWidth(290.0);
        return box;
    }

    /**
     * A method that sets the time of drinks
     */
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

    /**
     * A method that cancels the order
     */
    public void onCancel() {
        this.viewModel.onCancel();
    }

    /**
     * A method that goes back to reservations
     */
    public void onBackToReservations() {
        this.viewModel.onBackToReservations();
    }

    /**
     * A method that skips the ordering of drinks
     */
    public void onSkip() {
        this.viewModel.onSkip();
    }

    /**
     * A method that confirms the order
     */
    public void onConfirm() {
        var hours = (int) hourField.getValue();
        var minutes = (int) minutesField.getValue();
        var date = this.viewModel.getBookingDate();
        var expected = new Timestamp(date.getYear(), date.getMonth(), date.getDate(), hours, minutes, 0, 0);
        this.viewModel.onConfirm(expected);
    }

}
