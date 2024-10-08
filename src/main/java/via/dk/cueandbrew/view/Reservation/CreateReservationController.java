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

/**
 * A class that is responsible for the CreateReservationController
 * @Author Darja Jefremova, Dimitar Nizamov
 */
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
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;

    private CreateReservationViewModel viewModel;

    /**
     * A method that initializes the CreateReservationViewModel
     * @param viewModel The CreateReservationViewModel
     */
    public void init(CreateReservationViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.updateDateTime(dateLabel, timeLabel);
        this.viewModel.startDateTimeUpdater(dateLabel, timeLabel);
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
        attachValidationListener(table2CheckBox);
        attachValidationListener(table3CheckBox);
        attachValidationListener(table4CheckBox);
    }

    /**
     * A method that handles the duration change
     * @param selectedDuration The selected duration
     * @throws RemoteException
     */
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

    /**
     * A method that handles the date change
     * @param date The date
     */
    private void handleDateChange(LocalDate date) {
        if (hourField.getValue() == null || minutesField.getValue() == null) {
            return;
        }
        viewModel.chooseDateTime(LocalDateTime.of(date, java.time.LocalTime.of(Integer.parseInt(hourField.getValue()), Integer.parseInt(minutesField.getValue()))));
    }

    /**
     * A method that updates the view based on the selection of the time
     * It disables the tables that are unavailable at the selected time
     * @param selectedTime The selected time
     * @param duration The duration
     * @throws RemoteException
     */
    public void updateBasedOnSelection(LocalDateTime selectedTime, int duration) throws RemoteException {
        List<Integer> unavailableTables = viewModel.getUnavailableTableIds(selectedTime, duration);

        disableTableSelections(unavailableTables);
    }

    /**
     * A method that disables the table selections if there are unavailable tables
     * @param unavailableTables The unavailable tables
     */
    private void disableTableSelections(List<Integer> unavailableTables) {
        table1CheckBox.setDisable(unavailableTables.contains(1));
        table2CheckBox.setDisable(unavailableTables.contains(2));
        table3CheckBox.setDisable(unavailableTables.contains(3));
        table4CheckBox.setDisable(unavailableTables.contains(4));
    }

    /**
     * A method that attaches a validation listener to toggle group
     * @param tb The toggle group
     */
    private void attachValidationListener(ToggleGroup tb) {
        tb.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (hourField.getValue() != null && minutesField.getValue() != null && datePicker.getValue() != null && durationGroup.getSelectedToggle() != null && (table1CheckBox.isSelected() || table2CheckBox.isSelected() || table3CheckBox.isSelected() || table4CheckBox.isSelected())) {
                nextButton.setDisable(false);
            } else {
                nextButton.setDisable(true);
            }
        });
    }


    /**
     * A method that attaches a validation listener to a checkbox
     * @param cb The checkbox
     */
    private void attachValidationListener(CheckBox cb) {
        cb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (hourField.getValue() != null && minutesField.getValue() != null && datePicker.getValue() != null && durationGroup.getSelectedToggle() != null && (table1CheckBox.isSelected() || table2CheckBox.isSelected() || table3CheckBox.isSelected() || table4CheckBox.isSelected())) {
                nextButton.setDisable(false);
            } else {
                nextButton.setDisable(true);
            }
        });
    }

    /**
     * A method that attaches a validation listener to a combobox
     * @param cb The combobox
     */
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

    /**
     * A method that attaches a validation listener to a text field
     * @param tf The text field
 */
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
		if(this.table1CheckBox.isSelected()) this.viewModel.chooseTable(1);
		if(this.table2CheckBox.isSelected()) this.viewModel.chooseTable(2);
		if(this.table3CheckBox.isSelected()) this.viewModel.chooseTable(3);
		if(this.table4CheckBox.isSelected()) this.viewModel.chooseTable(4);
        this.viewModel.onNext();
    }

    public void onCancel() {
        this.viewModel.onCancel();
    }
}