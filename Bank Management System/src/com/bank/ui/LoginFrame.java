package com.bank.ui;

import com.bank.controller.BankingController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private BankingController controller;
    private JTextField accountNumberField;
    private JPasswordField pinField;
    private JButton loginButton, createAccountButton;

    public LoginFrame() {
        controller = new BankingController();
        initComponents();
    }

    private void initComponents() {

        setTitle("Banking Management System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(41, 128, 185),
                        0, getHeight(), new Color(109, 213, 250)
                );

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        mainPanel.setLayout(null);

        // Title Label
        JLabel titleLabel = new JLabel("BANKING SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 20, 450, 40);
        mainPanel.add(titleLabel);

        // Bank Icon
        JLabel iconLabel = new JLabel("🏦", JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        iconLabel.setBounds(0, 70, 450, 60);
        mainPanel.add(iconLabel);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBounds(50, 150, 350, 140);
        loginPanel.setBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2)
        );

        // Account Number Label
        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        accountLabel.setBounds(20, 20, 120, 25);
        loginPanel.add(accountLabel);

        // Account Number Field
        accountNumberField = new JTextField();
        accountNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        accountNumberField.setBounds(150, 20, 170, 25);
        loginPanel.add(accountNumberField);

        // PIN Label
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        pinLabel.setBounds(20, 55, 120, 25);
        loginPanel.add(pinLabel);

        // PIN Field
        pinField = new JPasswordField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.setBounds(150, 55, 170, 25);
        loginPanel.add(pinField);

        // Login Button
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBounds(50, 95, 120, 30);
        loginButton.setBackground(new Color(46, 204, 113));
        loginButton.setForeground(Color.black);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());
        loginPanel.add(loginButton);

        // Create Account Button
        createAccountButton = new JButton("NEW ACCOUNT");
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
        createAccountButton.setBounds(180, 95, 140, 30);
        createAccountButton.setBackground(new Color(52, 152, 219));
        createAccountButton.setForeground(Color.black);
        createAccountButton.setFocusPainted(false);
        createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountButton.addActionListener(e -> openCreateAccountFrame());
        loginPanel.add(createAccountButton);

        mainPanel.add(loginPanel);

        // Enter key listener for PIN field
        pinField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });

        add(mainPanel);
    }

    private void handleLogin() {

        String accountNumber = accountNumberField.getText().trim();
        String pin = new String(pinField.getPassword());

        if (accountNumber.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both account number and PIN!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String result = controller.checkBalance(accountNumber, pin);

        if (result.startsWith("SUCCESS")) {
            this.dispose();
            new DashboardFrame(accountNumber, pin, controller).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    result,
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
            pinField.setText("");
        }
    }

    private void openCreateAccountFrame() {
        new CreateAccountFrame(controller, this).setVisible(true);
        this.setVisible(false);
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() ->
                new LoginFrame().setVisible(true)
        );
    }
}
