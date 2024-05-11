package via.dk.cueandbrew.view.Reservation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class CreateReservationController {

    @FXML
    public DatePicker datePicker;
    @FXML
    ComboBox<String> hourField;
    @FXML
    ComboBox<String> minutesField;
    private Region root;

    public void init(Region root) {
        ArrayList<String> hours = new ArrayList<>();
        ArrayList<String> minutes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hours.add(String.format("%02d", i));
        }
        for (int i = 0; i <= 59; i += 15) {
            minutes.add(String.format("%02d", i));
        }
        String currentHour = String.format("%02d", java.time.LocalTime.now().getHour());
        String currentMinute = String.format("%02d", java.time.LocalTime.now().getMinute());
        hourField.setItems(FXCollections.observableArrayList(hours));
        minutesField.setItems(FXCollections.observableArrayList(minutes));
        hourField.setValue(currentHour);
        minutesField.setValue(currentMinute);
        this.root = root;
    }

    public Region getRoot() {
        return root;
    }
}