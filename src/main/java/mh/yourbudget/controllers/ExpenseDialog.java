package mh.yourbudget.controllers;

import javafx.event.ActionEvent;
import mh.yourbudget.DashboardApplication;
import mh.yourbudget.models.Expense;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class ExpenseDialog extends Dialog<Expense> {
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField houseRentField;
    @FXML
    private TextField entertainmentField;
    @FXML
    private TextField foodField;
    @FXML
    private TextField transportField;
    @FXML
    private TextField travelField;
    @FXML
    private TextField taxesField;
    @FXML
    private TextField othersField;
    @FXML
    private ButtonType createButton;
    public ExpenseDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(DashboardApplication.class.getResource("expense-dialog.fxml"));
            loader.setController(this);

            DialogPane dialogPane = loader.load();

            datePicker.setValue(LocalDate.now());

            setTitle("Add expense");
            setDialogPane(dialogPane);
            initResultConverter();

            // Disable button when all field are not filled
            computeIfButtonIsDisabled();

            // Ensure only numeric input are set in the fields
            forceDoubleFormat();

            forceDateFormat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void forceDateFormat() {
        UnaryOperator<TextFormatter.Change> dateValidationFormatter = t -> {
            if (t.isAdded()) {
                if (t.getControlText().length() > 8) {
                    t.setText("");
                } else if (t.getControlText().matches(".*[0-9]{2}")) {
                    if (t.getText().matches("[^/]")) {
                        t.setText("");
                    }
                } else if (t.getText().matches("[^0-9]")) {
                    t.setText("");
                }
            }
            return t;
        };
    }

    private void forceDoubleFormat() {
        UnaryOperator<TextFormatter.Change> numberValidationFormatter = t -> {
            if (t.isReplaced())
                if(t.getText().matches("[^0-9]"))
                    t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));


            if (t.isAdded()) {
                if (t.getControlText().contains(".")) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText("");
                    }
                } else if (t.getText().matches("[^0-9.]")) {
                    t.setText("");
                }
            }
            return t;
        };
        houseRentField.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        foodField.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        entertainmentField.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        transportField.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        travelField.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        taxesField.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
        othersField.setTextFormatter(new TextFormatter<>(numberValidationFormatter));
    }

    private void computeIfButtonIsDisabled() {
        getDialogPane().lookupButton(createButton).disableProperty().bind(
                        Bindings.createBooleanBinding(() -> houseRentField.getText().trim().isEmpty(), houseRentField.textProperty())
                                .or(Bindings.createBooleanBinding(() -> foodField.getText().trim().isEmpty(), foodField.textProperty())
                                        .or(Bindings.createBooleanBinding(() -> entertainmentField.getText().trim().isEmpty(), entertainmentField.textProperty())
                                                .or(Bindings.createBooleanBinding(() -> transportField.getText().trim().isEmpty(), transportField.textProperty())
                                                        .or(Bindings.createBooleanBinding(() -> travelField.getText().trim().isEmpty(), travelField.textProperty())
                                                                .or(Bindings.createBooleanBinding(() -> taxesField.getText().trim().isEmpty(), taxesField.textProperty())
                                                                        .or(Bindings.createBooleanBinding(() -> othersField.getText().trim().isEmpty(), othersField.textProperty())
                                                                        ))))
                                        )));
    }

    private void initResultConverter() {
        setResultConverter(buttonType -> {
            if (!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                return null;
            }
            LocalDate selectedDate = LocalDate.now();

            return new Expense(
                    selectedDate,
                    Float.parseFloat(houseRentField.getText()),
                    Float.parseFloat(foodField.getText()),
                    Float.parseFloat(entertainmentField.getText()),
                    Float.parseFloat(transportField.getText()),
                    Float.parseFloat(travelField.getText()),
                    Float.parseFloat(taxesField.getText()),
                    Float.parseFloat(othersField.getText())
            );
        });
    }
    @FXML
    public void handleDatePickerAction(ActionEvent event) {
        LocalDate selectedDate = datePicker.getValue();
        System.out.println("Selected date: " + selectedDate);
    }
}