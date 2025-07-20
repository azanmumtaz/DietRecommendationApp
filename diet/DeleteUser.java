package diet;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteUser extends JFrame {

    public DeleteUser() {
        setTitle("Delete User");
        setSize(400, 300);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        JLabel userLabel = new JLabel("Username to delete:");
        JTextField userField = new JTextField();

        panel.add(userLabel);
        panel.add(userField);

        JButton deleteBtn = new JButton("Delete");
        panel.add(new JLabel()); // empty cell
        panel.add(deleteBtn);

        add(panel, BorderLayout.CENTER);

        deleteBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete user '" + username + "'?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DBConnection.getConnection()) {
                    String query = "DELETE FROM users WHERE username = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, username);
                    int rows = stmt.executeUpdate();

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "User deleted successfully.");

                        dispose(); // Close this window
                        new main.Main().setVisible(true); // Return to main menu
                    } else {
                        JOptionPane.showMessageDialog(this, "No such user found.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });
    }
}
