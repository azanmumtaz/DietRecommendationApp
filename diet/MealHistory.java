package diet;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;

public class MealHistory extends JFrame {
    private String username;

    public MealHistory(String username) {
        this.username = username;
        setTitle("Meal History");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT m.name AS meal, m.calories, ml.date " +
               "FROM meal_logs ml " +
               "JOIN users u ON ml.user_id = u.id " +
               "JOIN meals m ON ml.meal_id = m.id " +
               "WHERE u.username = ? " +
               "ORDER BY ml.date DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            area.append("Meal History for " + username + ":\n\n");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                String meal = rs.getString("meal");
                int calories = rs.getInt("calories");
                Date date = rs.getDate("date");

                area.append(date.toString() + " - " + meal + " (" + calories + " kcal)\n");
            }

            if (!hasData) {
                area.append("No meals logged yet.");
            }

        } catch (Exception e) {
            area.setText("Error: " + e.getMessage());
        }
    }
}
