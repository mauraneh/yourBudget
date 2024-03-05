package mh.yourbudget.bdd;

import org.sqlite.JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mh.yourbudget.models.Expense;

//import static mh.yourbudget.DashboardApplication.findAndCreateOSFolder;

public class Database {
   // private static final org.slf4j.Logger log = LoggerFactory.getLogger(Database.class);
    private static final String location = "./database.db";

    public static boolean isOK() {
        if (!checkDrivers()) return false; //driver errors
        if (!checkConnection()) return false; //can't connect to db
        return createTableIfNotExists(); //tables didn't exist
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to database");
            return false;
        }
    }

    private static boolean createTableIfNotExists() {
        String createTables =
                """
                         CREATE TABLE IF NOT EXISTS expense(
                                  date TEXT NOT NULL,
                                  housing REAL NOT NULL,
                                  food REAL NOT NULL,
                                  goingOut REAL NOT NULL,
                                  transportation REAL NOT NULL,
                                  travel REAL NOT NULL,
                                  tax REAL NOT NULL,
                                  other REAL NOT NULL
                          );
                        """;

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(createTables);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            return false;
        }
    }

    protected static Connection connect() {
        //String osFolder = findAndCreateOSFolder();
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix  + location);
        } catch (SQLException exception) {
            return null;
        }
        return connection;
    }

}
