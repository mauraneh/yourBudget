module mh.yourbudget {
    requires javafx.controls;
    requires javafx.fxml;


    opens mh.yourbudget to javafx.fxml;
    exports mh.yourbudget;
}