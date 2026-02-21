package com.bank.ui;

import com.bank.controller.BankingController;
import com.bank.model.Account;
import com.bank.model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DashboardFrame extends JFrame {

    private BankingController controller;
    private String accountNumber;
    private String pin;

    private JLabel balanceLabel, nameLabel, accountNumLabel;
    private JTabbedPane tabbedPane;

    public DashboardFrame(String accountNumber, String pin, BankingController controller) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.controller = controller;

        initComponents();
        updateAccountInfo();
    }

    private void initComponents() {

        setTitle("Banking Dashboard");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(236, 240, 241));

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Deposit", createDepositPanel());
        tabbedPane.addTab("Withdraw", createWithdrawPanel());
        tabbedPane.addTab("Transfer", createHeaderPanel());
        tabbedPane.addTab("Transactions", createTransactionsPanel());
        tabbedPane.addTab("Settings", createSettingsPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    // ================= HEADER PANEL =================

    private Component createSettingsPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	private Component createTransactionsPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	private JPanel createHeaderPanel() {

        JPanel headerPanel = new JPanel(null);
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setPreferredSize(new Dimension(900, 120));

        JLabel titleLabel = new JLabel("Welcome to Your Banking Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 10, 400, 30);
        headerPanel.add(titleLabel);

        nameLabel = new JLabel();
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(30, 45, 400, 20);
        headerPanel.add(nameLabel);

        accountNumLabel = new JLabel();
        accountNumLabel.setForeground(Color.WHITE);
        accountNumLabel.setBounds(30, 70, 400, 20);
        headerPanel.add(accountNumLabel);

        JLabel balText = new JLabel("Current Balance");
        balText.setForeground(Color.WHITE);
        balText.setBounds(600, 20, 200, 20);
        headerPanel.add(balText);

        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 28));
        balanceLabel.setForeground(new Color(46, 204, 113));
        balanceLabel.setBounds(600, 40, 250, 40);
        headerPanel.add(balanceLabel);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(780, 15, 90, 30);
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.black);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        headerPanel.add(logoutBtn);

        return headerPanel;
    }

    // ================= DASHBOARD PANEL =================

    private JPanel createDashboardPanel() {

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel quickLabel = new JLabel("Quick Actions");
        quickLabel.setFont(new Font("Arial", Font.BOLD, 20));
        quickLabel.setBounds(30, 20, 200, 30);
        panel.add(quickLabel);

        int x = 50, y = 70;

        JButton checkBalBtn = createActionButton("Check Balance", x, y);
        checkBalBtn.setForeground(Color.BLACK); 
        checkBalBtn.addActionListener(e -> checkBalance());
        panel.add(checkBalBtn);

        JButton miniBtn = createActionButton("Mini Statement", x + 220, y);
        miniBtn.setForeground(Color.BLACK); 
        miniBtn.addActionListener(e -> showMiniStatement());
        panel.add(miniBtn);

        JButton depositBtn = createActionButton("Deposit Money", x + 440, y);
        depositBtn.setForeground(Color.BLACK);
        depositBtn.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        panel.add(depositBtn);

        y += 100;

        JButton withdrawBtn = createActionButton("Withdraw Money", x, y);
        withdrawBtn.setForeground(Color.BLACK);
        withdrawBtn.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        panel.add(withdrawBtn);

        JButton transferBtn = createActionButton("Transfer Money", x + 220, y);
        transferBtn.setForeground(Color.BLACK);
        transferBtn.addActionListener(e -> tabbedPane.setSelectedIndex(3));
        panel.add(transferBtn);

        JButton historyBtn = createActionButton("View History", x + 440, y);
        historyBtn.setForeground(Color.BLACK);
        historyBtn.addActionListener(e -> tabbedPane.setSelectedIndex(4));
        panel.add(historyBtn);

        return panel;
    }

    private JButton createActionButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBounds(x, y, 200, 70);
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    // ================= DEPOSIT PANEL =================

    private JPanel createDepositPanel() {

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Deposit Money");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBounds(50, 30, 300, 30);
        panel.add(label);

        JLabel amtLabel = new JLabel("Enter Amount (₹):");
        amtLabel.setBounds(50, 100, 200, 25);
        panel.add(amtLabel);

        JTextField amtField = new JTextField();
        amtField.setBounds(250, 100, 300, 35);
        panel.add(amtField);

        JButton btn = new JButton("DEPOSIT");
        btn.setBounds(250, 160, 150, 40);
        btn.setBackground(new Color(46, 204, 113));
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amtField.getText());
                String result = controller.deposit(accountNumber, pin, amount);

                if (result.startsWith("SUCCESS")) {
                    JOptionPane.showMessageDialog(this, result.split(":")[1]);
                    amtField.setText("");
                    updateAccountInfo();
                } else {
                    JOptionPane.showMessageDialog(this, result);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!");
            }
        });

        panel.add(btn);
        return panel;
    }

    // ================= WITHDRAW PANEL =================

    private JPanel createWithdrawPanel() {

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Withdraw Money");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBounds(50, 30, 300, 30);
        panel.add(label);

        JLabel amtLabel = new JLabel("Enter Amount (₹):");
        amtLabel.setBounds(50, 100, 200, 25);
        panel.add(amtLabel);

        JTextField amtField = new JTextField();
        amtField.setBounds(250, 100, 300, 35);
        panel.add(amtField);

        JButton btn = new JButton("WITHDRAW");
        btn.setBounds(250, 160, 150, 40);
        btn.setBackground(new Color(231, 76, 60));
        btn.setForeground(Color.black);

        btn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amtField.getText());
                String result = controller.withdraw(accountNumber, pin, amount);

                if (result.startsWith("SUCCESS")) {
                    JOptionPane.showMessageDialog(this, result.split(":")[1]);
                    amtField.setText("");
                    updateAccountInfo();
                } else {
                    JOptionPane.showMessageDialog(this, result);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!");
            }
        });

        panel.add(btn);
        return panel;
    }

    // ================= ACCOUNT INFO =================

    private void updateAccountInfo() {
        Account account = controller.getAccountDetails(accountNumber, pin);
        if (account != null) {
            nameLabel.setText("Name: " + account.getAccountHolderName());
            accountNumLabel.setText("Account: " + account.getAccountNumber());
            balanceLabel.setText("₹ " + String.format("%.2f", account.getbalance()));
        }
    }

    private void checkBalance() {
        String result = controller.checkBalance(accountNumber, pin);
        if (result.startsWith("SUCCESS")) {
            JOptionPane.showMessageDialog(this,
                    "Current Balance: " + result.split(":")[1]);
        }
    }

    private void showMiniStatement() {
        List<Transaction> transactions =
                controller.getMiniStatement(accountNumber, pin);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        StringBuilder sb = new StringBuilder("Last 5 Transactions:\n\n");

        for (Transaction txn : transactions) {
            sb.append(sdf.format(txn.getTransactionDate()))
              .append(" | ")
              .append(txn.getTransactionType())
              .append(" | ₹")
              .append(String.format("%.2f", txn.getAmount()))
              .append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }
}
