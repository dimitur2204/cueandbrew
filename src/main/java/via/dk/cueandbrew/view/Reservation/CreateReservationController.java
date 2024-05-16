package via.dk.cueandbrew.view.Reservation;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import via.dk.cueandbrew.viewmodel.Reservation.CreateReservationViewModel;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateReservationController {

    @FXML
    private DatePicker datePicker;
    @FXML
    private Button nextButton;
    @FXML
    ComboBox<String> hourField;
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
        this.nextButton.setDisable(true);
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
        this.viewModel = viewModel;
        durationGroup = new ToggleGroup();
        duration30m.setToggleGroup(durationGroup);
        duration1h.setToggleGroup(durationGroup);
        duration2h.setToggleGroup(durationGroup);
        attachValidationListener(datePicker);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            handleDateChange(newValue);
        });
        attachValidationListener(hourField);
        hourField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (minutesField.getValue() == null || datePicker.getValue() == null) {
                return;
            }
            viewModel.chooseDateTime(LocalDateTime.of(datePicker.getValue(), java.time.LocalTime.of(Integer.parseInt(newValue), Integer.parseInt(minutesField.getValue()))));
        });
        attachValidationListener(minutesField);
        minutesField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (hourField.getValue() == null || datePicker.getValue() == null) {
                return;
            }
            viewModel.chooseDateTime(LocalDateTime.of(datePicker.getValue(), java.time.LocalTime.of(Integer.parseInt(hourField.getValue()), Integer.parseInt(newValue))));
        });
        attachValidationListener(durationGroup);
        durationGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    handleDurationChange((RadioButton) newValue);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        attachValidationListener(table1CheckBox);
        table1CheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.chooseTable(1);
        });
        attachValidationListener(table2CheckBox);
        table2CheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.chooseTable(2);
        });
        attachValidationListener(table3CheckBox);
        table3CheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.chooseTable(3);
        });
        attachValidationListener(table4CheckBox);
        table4CheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.chooseTable(4);
        });
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
    private void attachValidationListener(ToggleGroup tb) {
        tb.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (hourField.getValue() != null && minutesField.getValue() != null && datePicker.getValue() != null && durationGroup.getSelectedToggle() != null && (table1CheckBox.isSelected() || table2CheckBox.isSelected() || table3CheckBox.isSelected() || table4CheckBox.isSelected())) {
                nextButton.setDisable(false);
            } else {
                nextButton.setDisable(true);
            }
        });
    }

    private void attachValidationListener(CheckBox cb) {
        cb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (hourField.getValue() != null && minutesField.getValue() != null && datePicker.getValue() != null && durationGroup.getSelectedToggle() != null && (table1CheckBox.isSelected() || table2CheckBox.isSelected() || table3CheckBox.isSelected() || table4CheckBox.isSelected())) {
                nextButton.setDisable(false);
            } else {
                nextButton.setDisable(true);
            }
        });
    }

    private <T> void attachValidationListener(ComboBoxBase<T> cb) {
        //if all fields are filled, enable the next button
        cb.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (hourField.getValue() != null && minutesField.getValue() != null && datePicker.getValue() != null && durationGroup.getSelectedToggle() != null && (table1CheckBox.isSelected() || table2CheckBox.isSelected() || table3CheckBox.isSelected() || table4CheckBox.isSelected())) {
                nextButton.setDisable(false);
            } else {
                nextButton.setDisable(true);
            }
        });
    }

    private void attachValidationListener(TextField tf) {
        //if all fields are filled, enable the next button
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (hourField.getValue() != null && minutesField.getValue() != null && datePicker.getValue() != null && durationGroup.getSelectedToggle() != null && (table1CheckBox.isSelected() || table2CheckBox.isSelected() || table3CheckBox.isSelected() || table4CheckBox.isSelected())) {
                nextButton.setDisable(false);
            } else {
                nextButton.setDisable(true);
            }
        });
    }

    public void onNext() {
        this.viewModel.onNext();
    }

    public void onCancel() {
        this.viewModel.onCancel();
    }
}