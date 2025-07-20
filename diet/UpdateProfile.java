package diet;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateProfile extends JFrame {
    private String username;

    public UpdateProfile(String username) {
        this.username = username;
        setTitle("Update Profile");
        setSize(400, 400);
        setLocationRelativeTo(null);

        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel heightLabel = new JLabel("Height (cm):");
        JLabel weightLabel = new JLabel("Weight (kg):");
        JLabel goalLabel = new JLabel("Goal:");

        JTextField heightField = new JTextField();
        JTextField weightField = new JTextField();
        String[] goals = {"Weight Loss", "Weight Gain", "Maintain Weight"};
        JComboBox<String> goalBox = new JComboBox<>(goals);

        JButton updateButton = new JButton("Update");

        heightLabel.setBounds(50, 40, 120, 30);
        heightField.setBounds(180, 40, 160, 30);

        weightLabel.setBounds(50, 90, 120, 30);
        weightField.setBounds(180, 90, 160, 30);
 
        goalLabel.setBounds(50, 140, 120, 30);
        goalBox.setBounds(180, 140, 160, 30);

        updateButton.setBounds(140, 200, 120, 35);


        add(heightLabel);
        add(heightField);
        add(weightLabel);
        add(weightField);
        add(goalLabel);
        add(goalBox);
        add(updateButton);

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT height_cm, weight_kg, goal FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                heightField.setText(rs.getString("height_cm"));
                weightField.setText(rs.getString("weight_kg"));
                goalBox.setSelectedItem(rs.getString("goal"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (Connection conn = DBConnection.getConnection()) {
                    String query = "UPDATE users SET height_cm = ?, weight_kg = ?, goal = ? WHERE username = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setDouble(1, Double.parseDouble(heightField.getText()));
                    stmt.setDouble(2, Double.parseDouble(weightField.getText()));
                    stmt.setString(3, (String) goalBox.getSelectedItem());
                    stmt.setString(4, username);

                    if (stmt.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Profile updated successfully!");
                    dispose(); 

                    new MealRecommendation(username).setVisible(true);
                    }


                    else {
                        JOptionPane.showMessageDialog(null, "Update failed.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });
    }
}
