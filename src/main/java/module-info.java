module mh.yourbudget {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires javafx.graphics;
    requires java.sql;

    opens mh.yourbudget to javafx.fxml;
    exports mh.yourbudget;

    exports mh.yourbudget.models;
    opens mh.yourbudget.models to javafx.fxml;

    exports mh.yourbudget.controllers;
    opens mh.yourbudget.controllers to javafx.fxml;

}