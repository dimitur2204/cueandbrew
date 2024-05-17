package via.dk.cueandbrew.view.Reservation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import via.dk.cueandbrew.viewmodel.Reservation.CreateReservationViewModel;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateReservationController {

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> hourField;
    @FXML private ComboBox<String> minutesField;
    @FXML private RadioButton duration30m, duration1h, duration2h;
    @FXML private ToggleGroup durationGroup;
    @FXML private CheckBox table1CheckBox;
    @FXML private CheckBox table2CheckBox;
    @FXML private CheckBox table3CheckBox;
    @FXML private CheckBox table4CheckBox;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;

    private CreateReservationViewModel viewModel;

    public void init(CreateReservationViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.updateDateTime(dateLabel, timeLabel);
        this.viewModel.startDateTimeUpdater(dateLabel, timeLabel);
        ArrayList<String> hours = new ArrayList<>();
        ArrayList<String> minutes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hours.add(String.format("%02d", i));
        }
        for (int i = 0; i <= 59; i += 15) {
            minutes.add(String.format("%02d", i));
        }
        hourField.setItems(FXCollections.observableArrayList(hours));
        minutesField.setItems(FXCollections.observableArrayList(minutes));
        durationGroup = new ToggleGroup();
        duration30m.setToggleGroup(durationGroup);
        duration1h.setToggleGroup(durationGroup);
        duration2h.setToggleGroup(durationGroup);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> handleDateChange(newValue));
        hourField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (minutesField.getValue() == null || datePicker.getValue() == null){
                return;
            }
            viewModel.chooseDateTime(LocalDateTime.of(datePicker.getValue(), java.time.LocalTime.of(Integer.parseInt(newValue), Integer.parseInt(minutesField.getValue()))));
        });
        minutesField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (hourField.getValue() == null || datePicker.getValue() == null){
                return;
            }
            viewModel.chooseDateTime(LocalDateTime.of(datePicker.getValue(), java.time.LocalTime.of(Integer.parseInt(hourField.getValue()), Integer.parseInt(newValue))));
        });
        durationGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    handleDurationChange((RadioButton) newValue);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        table1CheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> viewModel.chooseTable(1));
        table2CheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> viewModel.chooseTable(2));
        table3CheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> viewModel.chooseTable(3));
        table4CheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> viewModel.chooseTable(4));
    }

    private void handleDurationChange(RadioButton selectedDuration) throws RemoteException {
        String durationText = selectedDuration.getText();
        int duration = "30m".equals(durationText) ? 30 : "1h".equals(durationText) ? 60 : 120;
        var hours = hourField.getValue();
        var minutes = minutesField.getValue();
        var datePickerValue = datePicker.getValue();
        if (hours != null && minutes != null && datePickerValue != null) {
            LocalDateTime selectedTime = LocalDateTime.of(datePickerValue, java.time.LocalTime.of(Integer.parseInt(hours), Integer.parseInt(minutes)));
            updateBasedOnSelection(selectedTime, duration);
        }
        viewModel.chooseDuration(duration);
    }

    private void handleDateChange(LocalDate date) {
        if (hourField.getValue() == null || minutesField.getValue() == null) {
            return;
        }
        viewModel.chooseDateTime(LocalDateTime.of(date, java.time.LocalTime.of(Integer.parseInt(hourField.getValue()), Integer.parseInt(minutesField.getValue()))));
    }

    public void updateBasedOnSelection(LocalDateTime selectedTime, int duration) throws RemoteException {
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
        this.viewModel.onNext();
    }

    public void onCancel() {
        this.viewModel.onCancel();
    }
}