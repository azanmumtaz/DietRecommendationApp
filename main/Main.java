package main;

import auth.Login;
import auth.Register;
import diet.UserDataViewer;
import diet.DeleteUser;

import javax.swing.*;
import java.awt.event.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Diet Recommendation App");
        setSize(400, 400);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton viewUsersButton = new JButton("View All Users");
        JButton deleteUserButton = new JButton("Delete User"); 

        loginButton.setBounds(130, 50, 140, 40);
        registerButton.setBounds(130, 110, 140, 40);
        viewUsersButton.setBounds(130, 170, 140, 40);
        deleteUserButton.setBounds(130, 230, 140, 40);


        add(loginButton);
        add(registerButton);
        add(viewUsersButton);
        add(deleteUserButton); 

        loginButton.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        registerButton.addActionListener(e -> {
            dispose();
            new Register().setVisible(true);
        });

        viewUsersButton.addActionListener(e -> {
            new UserDataViewer().setVisible(true);
        });

        deleteUserButton.addActionListener(e -> {
            new DeleteUser().setVisible(true);
        });
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}
