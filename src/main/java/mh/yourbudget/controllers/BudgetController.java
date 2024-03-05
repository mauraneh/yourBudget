package mh.yourbudget.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import mh.yourbudget.bdd.ExpenseDAO;
import mh.yourbudget.models.Expense;

import java.util.List;
import java.util.Optional;


public class BudgetController {
    @FXML
    public Button deleteExpenseButton;
    @FXML
    private TableView<Expense> budgetTable;
    @FXML
    private ObservableList<Expense> expensesData = FXCollections.observableArrayList();

    private ExpenseDAO expenseDAO = new ExpenseDAO();
    public void initialize() {
        List<Expense> loadedExpenses = ExpenseDAO.getAllExpenses();
        expensesData.addAll(loadedExpenses);
        budgetTable.setItems(expensesData);
    }

    @FXML
    public void addExpense(ActionEvent actionEvent) {
        Dialog<Expense> addPersonDialog = new ExpenseDialog();
        Optional<Expense> result = addPersonDialog.showAndWait();
        result.ifPresent(expense -> {
            expensesData.add(expense);
            ExpenseDAO.addExpense(expense);
        });
        System.out.println(result);
//        event.consume();
    }

    @FXML
    public void deleteExpense(ActionEvent actionEvent) {
        Expense selectedExpense = budgetTable.getSelectionModel().getSelectedItem();
        if (selectedExpense != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this expense?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    if (ExpenseDAO.deleteExpense(selectedExpense.getDate(), selectedExpense.getHouseRent(), selectedExpense.getFood(),
                            selectedExpense.getEntertainment(), selectedExpense.getTransport(), selectedExpense.getTravel(),
                            selectedExpense.getTaxes(), selectedExpense.getOthers())) {
                        expensesData.remove(selectedExpense);
                    } else {
                        showErrorDialog();
                    }
                }
            });
        }
    }

    private void showErrorDialog() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "There was a problem deleting the expense. Please try again.");
        errorAlert.showAndWait();
    }

}