package auth;

import database.DBConnection;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Register extends JFrame {
    private JTextField usernameField, ageField, heightField, weightField;
    private JPasswordField passwordField;
    private JComboBox<String> goalBox;
    private JButton registerButton;

    public Register() {
        setTitle("User Registration");
        setSize(400, 400);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel heightLabel = new JLabel("Height (cm):");
        JLabel weightLabel = new JLabel("Weight (kg):");
        JLabel goalLabel = new JLabel("Goal:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        ageField = new JTextField();
        heightField = new JTextField();
        weightField = new JTextField();

        String[] goals = {"Weight Loss", "Weight Gain", "Maintain Weight"};
        goalBox = new JComboBox<>(goals);

        registerButton = new JButton("Register");

        usernameLabel.setBounds(50, 30, 120, 30);
        usernameField.setBounds(180, 30, 160, 30);

        passwordLabel.setBounds(50, 70, 120, 30);
        passwordField.setBounds(180, 70, 160, 30);

        ageLabel.setBounds(50, 110, 120, 30);
        ageField.setBounds(180, 110, 160, 30);

        heightLabel.setBounds(50, 150, 120, 30);
        heightField.setBounds(180, 150, 160, 30);

        weightLabel.setBounds(50, 190, 120, 30);
        weightField.setBounds(180, 190, 160, 30);

        goalLabel.setBounds(50, 230, 120, 30);
        goalBox.setBounds(180, 230, 160, 30);

        registerButton.setBounds(150, 280, 120, 35);


        add(usernameLabel); add(usernameField);
        add(passwordLabel); add(passwordField);
        add(ageLabel); add(ageField);
        add(heightLabel); add(heightField);
        add(weightLabel); add(weightField);
        add(goalLabel); add(goalBox);
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        int age = Integer.parseInt(ageField.getText());
        double height = Double.parseDouble(heightField.getText());
        double weight = Double.parseDouble(weightField.getText());
        String goal = goalBox.getSelectedItem().toString();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, age, height_cm, weight_kg, goal) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, age);
            stmt.setDouble(4, height);
            stmt.setDouble(5, weight);
            stmt.setString(6, goal);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                dispose(); 
                new main.Main().setVisible(true); 
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
