package via.dk.cueandbrew.view.Reservation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import via.dk.cueandbrew.viewmodel.Reservation.CreateReservationViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateReservationController {

    @FXML
    public DatePicker datePicker;
    @FXML
    ComboBox<String> hourField, minuteField;

    @FXML
    ComboBox<String> minutesField;
    @FXML
    RadioButton duration30m, duration1h, duration2h;
    @FXML
    ToggleGroup durationGroup;
    @FXML
    CheckBox table1CheckBox;
    @FXML
    CheckBox table2CheckBox;
    @FXML
    CheckBox table3CheckBox;
    @FXML
    CheckBox table4CheckBox;
    private CreateReservationViewModel viewModel;

    public void init(CreateReservationViewModel viewModel) {
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
        this.viewModel = viewModel;
    }

    public void initialize() {
        durationGroup = new ToggleGroup();
        duration30m.setToggleGroup(durationGroup);
        duration1h.setToggleGroup(durationGroup);
        duration2h.setToggleGroup(durationGroup);

        durationGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleDurationChange((RadioButton) newValue);
            }
        });
    }

    private void handleDurationChange(RadioButton selectedDuration) {
        String durationText = selectedDuration.getText();
        int duration = "30m".equals(durationText) ? 30 : "1h".equals(durationText) ? 60 : 120;
        viewModel.chooseDuration(duration);
    }
    public void updateBasedOnSelection(LocalDateTime selectedTime, int duration) {
        List<Integer> unavailableTables = viewModel.getUnavailableTableIds(selectedTime, duration);
        disableTableSelections(unavailableTables);
    }
    private void disableTableSelections(List<Integer> unavailableTables) {
        table1CheckBox.setDisable(unavailableTables.contains(1));
        table2CheckBox.setDisable(unavailableTables.contains(2));
        table3CheckBox.setDisable(unavailableTables.contains(3));
        table4CheckBox.setDisable(unavailableTables.contains(4));
    }
    public void onNext() {
        viewModel.chooseDateTime(LocalDateTime.of(datePicker.getValue(), LocalDateTime.now().toLocalTime()));
        this.viewModel.onNext();
    }

    public void onCancel() {
        this.viewModel.onCancel();
    }
}