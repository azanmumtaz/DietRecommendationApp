package diet;

import database.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserDataViewer extends JFrame {

    public UserDataViewer() {
        setTitle("Registered Users");
        setSize(650, 400);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT username, height_cm, weight_kg, goal FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            area.append("USERNAME\t\tHEIGHT(cm)\t\tWEIGHT(kg)\t\t   GOAL\n");
            area.append("---------------------------------------------------------------------------------------------------------------------------------\n");

            while (rs.next()) {
                String username = rs.getString("username");
                double height = rs.getDouble("height_cm");
                double weight = rs.getDouble("weight_kg");
                String goal = rs.getString("goal");

                area.append(username + "\t\t  " + height + "\t\t   " + weight + "\t\t" + goal + "\n");
            }

        } catch (Exception e) {
            area.setText("Error: " + e.getMessage());
        }
    }
}
