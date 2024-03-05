package mh.yourbudget.bdd;

import mh.yourbudget.models.Expense;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    private static final String location = "./database.db";

    private static Connection connect() {
        // Connection code similar to Database.connect()
        String dbPrefix = "jdbc:sqlite:";
        try {
            return DriverManager.getConnection(dbPrefix + location);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public static void addExpense(Expense expense) {
        String insertSql = "INSERT INTO expense(date, housing, food, goingOut, transportation, travel, tax, other) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(insertSql)) {

            pstmt.setString(1, expense.getDate().toString());
            pstmt.setDouble(2, expense.getHouseRent());
            pstmt.setDouble(3, expense.getFood());
            pstmt.setDouble(4, expense.getEntertainment());
            pstmt.setDouble(5, expense.getTransport());
            pstmt.setDouble(6, expense.getTravel());
            pstmt.setDouble(7, expense.getTaxes());
            pstmt.setDouble(8, expense.getOthers());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expense";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getString("date"));
                float housing = rs.getFloat("housing");
                float food = rs.getFloat("food");
                float goingOut = rs.getFloat("goingOut");
                float transportation = rs.getFloat("transportation");
                float travel = rs.getFloat("travel");
                float tax = rs.getFloat("tax");
                float other = rs.getFloat("other");
                expenses.add(new Expense(date, housing, food, goingOut, transportation, travel, tax, other));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return expenses;
    }

    public static boolean deleteExpense(LocalDate date, float houseRent, float food, float entertainment, float transport, float travel, float taxes, float others) {
        String sql = "DELETE FROM expense WHERE date = ? AND housing = ? AND food = ? AND goingOut = ? AND transportation = ? AND travel = ? AND tax = ? AND other = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date.toString());
            pstmt.setFloat(2, houseRent);
            pstmt.setFloat(3, food);
            pstmt.setFloat(4, entertainment);
            pstmt.setFloat(5, transport);
            pstmt.setFloat(6, travel);
            pstmt.setFloat(7, taxes);
            pstmt.setFloat(8, others);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
