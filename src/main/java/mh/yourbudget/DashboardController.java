package mh.yourbudget;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import mh.yourbudget.bdd.ExpenseDAO;
import mh.yourbudget.models.Expense;
import mh.yourbudget.models.TodoItem;

import javafx.scene.chart.XYChart;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardController {
    public BarChart budgetBarChart;
    @FXML
    private TabPane tabPane;
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextField newTodoTextField;

    private ObservableList<TodoItem> todoItems;

    @FXML
    private void initialize() throws IOException {
        initBudgetChartData();
        initExpenseTab();
        todoItems = FXCollections.observableArrayList();
        todoListView.setItems(todoItems);

        // Définir le CellFactory
        todoListView.setCellFactory(param -> new ListCell<TodoItem>() {
            private final CheckBox checkBox = new CheckBox();
            private final Label label = new Label();
            private final HBox hBox = new HBox(checkBox, label);

            {
                hBox.setSpacing(10);
                hBox.setPadding(new Insets(0, 10, 0, 0));

                // Supprimer l'élément lorsque la case à cocher est cochée
                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected()) {
                        TodoItem item = getItem();
                        todoItems.remove(item);
                    }
                });
            }

            @Override
            protected void updateItem(TodoItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item.getText());
                    setGraphic(hBox);
                }
            }
        });
    }

    private void initExpenseTab() throws IOException {
        Tab expenseTab = new Tab("Expenses");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("budget-view.fxml"));
        expenseTab.setContent(loader.load());
        tabPane.getTabs().add(expenseTab);
    }

    @FXML
    protected void handleAddTodoAction(ActionEvent event) {
        String text = newTodoTextField.getText();
        if (!text.isEmpty()) {
            TodoItem newItem = new TodoItem(text, false);
            todoItems.add(newItem);
            newTodoTextField.clear();
        }
    }

    private void initBudgetChartData() {
        List<Expense> expenses = ExpenseDAO.getAllExpenses();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Dépenses");

        int expenseIndex = 0;
        for (Expense expense : expenses) {
            String label = expense.getDate().toString() + "\n" + String.format("%.2f", expense.getAmount());
            XYChart.Data<String, Number> data = new XYChart.Data<>(label, expense.getAmount());
            series.getData().add(data);

            String color = (expenseIndex % 2 == 0) ? "#f0f7b2" : "#c0a7eb";
            data.nodeProperty().addListener((ov, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setStyle("-fx-bar-fill: " + color + ";");
                }
            });

            expenseIndex++;
        }

        budgetBarChart.getData().add(series);
        budgetBarChart.setLegendVisible(true);

        budgetBarChart.setCategoryGap(10);
        budgetBarChart.setBarGap(3);
    }


}
