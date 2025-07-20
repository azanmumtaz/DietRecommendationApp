package diet;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class MealLogger extends JFrame {
    private String username;

    public MealLogger(String username) {
        this.username = username;
        setTitle("Meal Logger");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel mealLabel = new JLabel("Meal:");
        JLabel calLabel = new JLabel("Calories:");
        JLabel goalLabel = new JLabel("Goal:");

        JTextField mealField = new JTextField();
        JTextField calField = new JTextField();

        String[] goals = {"Weight Loss", "Weight Gain", "Maintain Weight"};
        JComboBox<String> goalComboBox = new JComboBox<>(goals);

        JButton saveButton = new JButton("Log Meal");

        mealLabel.setBounds(30, 30, 100, 25);
        mealField.setBounds(130, 30, 200, 25);
        calLabel.setBounds(30, 70, 100, 25);
        calField.setBounds(130, 70, 200, 25);
        goalLabel.setBounds(30, 110, 100, 25);
        goalComboBox.setBounds(130, 110, 200, 25);
        saveButton.setBounds(130, 160, 120, 30);

        add(mealLabel);
        add(mealField);
        add(calLabel);
        add(calField);
        add(goalLabel);
        add(goalComboBox);
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String mealName = mealField.getText().trim();
                String goal = goalComboBox.getSelectedItem().toString().trim();
                int calories;

                try {
                    calories = Integer.parseInt(calField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Calories must be a number.");
                    return;
                }

                try (Connection conn = DBConnection.getConnection()) {

                    PreparedStatement getUserId = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
                    getUserId.setString(1, username);
                    ResultSet rsUser = getUserId.executeQuery();

                    if (!rsUser.next()) {
                        JOptionPane.showMessageDialog(null, "User not found.");
                        return;
                    }
                    int userId = rsUser.getInt("id");

                    PreparedStatement getMealId = conn.prepareStatement("SELECT id FROM meals WHERE name = ? AND calories = ? AND goal = ?");
                    getMealId.setString(1, mealName);
                    getMealId.setInt(2, calories);
                    getMealId.setString(3, goal);
                    ResultSet rsMeal = getMealId.executeQuery();

                    int mealId;
                    if (rsMeal.next()) {
                        mealId = rsMeal.getInt("id");
                    } else {
                        PreparedStatement insertMeal = conn.prepareStatement("INSERT INTO meals (name, calories, goal) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        insertMeal.setString(1, mealName);
                        insertMeal.setInt(2, calories);
                        insertMeal.setString(3, goal);
                        insertMeal.executeUpdate();
                        ResultSet keys = insertMeal.getGeneratedKeys();
                        if (keys.next()) {
                            mealId = keys.getInt(1);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to insert new meal.");
                            return;
                        }
                    }

                    PreparedStatement insertLog = conn.prepareStatement("INSERT INTO meal_logs (user_id, meal_id, date) VALUES (?, ?, ?)");
                    insertLog.setInt(1, userId);
                    insertLog.setInt(2, mealId);
                    insertLog.setDate(3, Date.valueOf(LocalDate.now()));

                    insertLog.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Meal logged successfully!");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error logging meal: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }
}
