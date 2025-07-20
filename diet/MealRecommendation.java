package diet;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MealRecommendation extends JFrame {
    private String username;

    public MealRecommendation(String username) {
    this.username = username;
    setTitle("Meal Recommendation");
    setSize(500, 400);
    setLocationRelativeTo(null);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JTextArea resultArea = new JTextArea();
    resultArea.setEditable(false);
    add(new JScrollPane(resultArea), BorderLayout.CENTER);

    try (Connection conn = DBConnection.getConnection()) {

        String userQuery = "SELECT height_cm, weight_kg, goal FROM users WHERE username = ?";
        PreparedStatement userStmt = conn.prepareStatement(userQuery);
        userStmt.setString(1, username);
        ResultSet userRs = userStmt.executeQuery();

        if (userRs.next()) {
            double heightCm = userRs.getDouble("height_cm");
            double weightKg = userRs.getDouble("weight_kg");
            String goal = userRs.getString("goal");

            double heightM = heightCm / 100.0;
            double bmi = weightKg / (heightM * heightM);
            String bmiCategory;

            if (bmi < 18.5) {
                bmiCategory = "Underweight";
            } else if (bmi < 25) {
                bmiCategory = "Normal weight";
            } else if (bmi < 30) {
                bmiCategory = "Overweight";
            } else {
                bmiCategory = "Obese";
            }

            resultArea.append("Hello, " + username + "\n");
            resultArea.append("Your BMI: " + String.format("%.2f", bmi) + " (" + bmiCategory + ")\n");
            resultArea.append("Goal: " + goal + "\n\n");

            resultArea.append("Recommended Meals:\n");

            // goal = goal.toLowerCase();

            String mealQuery = "SELECT name, type, calories, description FROM meals WHERE goal = ?";
            PreparedStatement mealStmt = conn.prepareStatement(mealQuery);
            mealStmt.setString(1, goal);
            ResultSet mealRs = mealStmt.executeQuery();

            while (mealRs.next()) {
                String name = mealRs.getString("name");
                int calories = mealRs.getInt("calories");
                resultArea.append("- " + name + " (" + calories + " kcal)\n");
            }
        } else {
            resultArea.setText("User data not found.");
        }
    } catch (Exception e) {
        resultArea.setText("Error: " + e.getMessage());
    }

    
    JButton updateProfileButton = new JButton("Update Profile");
    JButton logMealButton = new JButton("Log a Meal");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(updateProfileButton);
    buttonPanel.add(logMealButton);
    add(buttonPanel, BorderLayout.SOUTH);

    logMealButton.addActionListener(e -> {
    new MealLogger(username).setVisible(true);
    });

    JButton historyButton = new JButton("View History");
    buttonPanel.add(historyButton);

    historyButton.addActionListener(e -> {
    new MealHistory(username).setVisible(true);
});



    updateProfileButton.addActionListener(e -> {
        new UpdateProfile(username).setVisible(true);
    });
}
}
