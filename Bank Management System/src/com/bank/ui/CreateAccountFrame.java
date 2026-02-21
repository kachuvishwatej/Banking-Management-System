package com.bank.ui;

import com.bank.controller.BankingController;

import javax.swing.*;
import java.awt.*;

public class CreateAccountFrame extends JFrame {

    private BankingController controller;
    private LoginFrame loginFrame;

    private JTextField nameField, emailField, phoneField, depositField;
    private JTextArea addressArea;
    private JPasswordField pinField, confirmPinField;
    private JComboBox<String> accountTypeCombo;
    private JButton createButton, cancelButton;

    public CreateAccountFrame(BankingController controller, LoginFrame loginFrame) {
        this.controller = controller;
        this.loginFrame = loginFrame;
        initComponents();
    }

    private void initComponents() {

        setTitle("Create New Account");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(236, 240, 241));

        // Title
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setBounds(120, 20, 300, 30);
        mainPanel.add(titleLabel);

        int yPos = 70;
        int labelWidth = 130;
        int fieldWidth = 250;

        // Full Name
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setBounds(50, yPos, labelWidth, 25);
        mainPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setBounds(200, yPos, fieldWidth, 25);
        mainPanel.add(nameField);

        // Account Type
        yPos += 40;
        JLabel typeLabel = new JLabel("Account Type:");
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        typeLabel.setBounds(50, yPos, labelWidth, 25);
        mainPanel.add(typeLabel);

        String[] accountTypes = {"Savings", "Current", "Fixed Deposit"};
        accountTypeCombo = new JComboBox<>(accountTypes);
        accountTypeCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        accountTypeCombo.setBounds(200, yPos, fieldWidth, 25);
        mainPanel.add(accountTypeCombo);

        // Email
        yPos += 40;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setBounds(50, yPos, labelWidth, 25);
        mainPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBounds(200, yPos, fieldWidth, 25);
        mainPanel.add(emailField);

        // Phone
        yPos += 40;
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneLabel.setBounds(50, yPos, labelWidth, 25);
        mainPanel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        phoneField.setBounds(200, yPos, fieldWidth, 25);
        mainPanel.add(phoneField);

        // Address
        yPos += 40;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        addressLabel.setBounds(50, yPos, labelWidth, 25);
        mainPanel.add(addressLabel);

        addressArea = new JTextArea();
        addressArea.setFont(new Font("Arial", Font.PLAIN, 14));
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);

        JScrollPane addressScroll = new JScrollPane(addressArea);
        addressScroll.setBounds(200, yPos, fieldWidth, 60);
        mainPanel.add(addressScroll);

        // Initial Deposit
        yPos += 75;
        JLabel depositLabel = new JLabel("Initial Deposit (₹):");
        depositLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        depositLabel.setBounds(50, yPos, labelWidth, 25);
        mainPanel.add(depositLabel);

        depositField = new JTextField("0");
        depositField.setFont(new Font("Arial", Font.PLAIN, 14));
        depositField.setBounds(200, yPos, fieldWidth, 25);
        mainPanel.add(depositField);

        // PIN
        yPos += 40;
        JLabel pinLabel = new JLabel("Create PIN (4 digits):");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        pinLabel.setBounds(50, yPos, labelWidth, 25);
        mainPanel.add(pinLabel);

        pinField = new JPasswordField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.setBounds(200, yPos, fieldWidth, 25);
        mainPanel.add(pinField);

        // Confirm PIN
        yPos += 40;
        JLabel confirmPinLabel = new JLabel("Confirm PIN:");
        confirmPinLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPinLabel.setBounds(50, yPos, labelWidth, 25);
        mainPanel.add(confirmPinLabel);

        confirmPinField = new JPasswordField();
        confirmPinField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPinField.setBounds(200, yPos, fieldWidth, 25);
        mainPanel.add(confirmPinField);

        // Buttons
        yPos += 50;

        createButton = new JButton("CREATE ACCOUNT");
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setBounds(100, yPos, 160, 35);
        createButton.setBackground(new Color(46, 204, 113));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createButton.addActionListener(e -> handleCreateAccount());
        mainPanel.add(createButton);

        cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBounds(280, yPos, 120, 35);
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> {
            this.dispose();
            loginFrame.setVisible(true);
        });
        mainPanel.add(cancelButton);

        add(mainPanel);
    }

    private void handleCreateAccount() {

        String name = nameField.getText().trim();
        String accountType = (String) accountTypeCombo.getSelectedItem();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressArea.getText().trim();
        String depositStr = depositField.getText().trim();
        String pin = new String(pinField.getPassword());
        String confirmPin = new String(confirmPinField.getPassword());

        // Validation
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()
                || address.isEmpty() || pin.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!pin.equals(confirmPin)) {
            JOptionPane.showMessageDialog(
                    this,
                    "PINs do not match!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        double deposit;
        try {
            deposit = Double.parseDouble(depositStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid deposit amount!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String result = controller.createAccount(
                name, accountType, deposit, email, phone, address, pin
        );

        if (result.startsWith("SUCCESS")) {

            String accountNumber = result.split(":")[1];

            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!\n\nYour Account Number: "
                            + accountNumber
                            + "\n\nPlease save this number for future login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            this.dispose();
            loginFrame.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    result,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
