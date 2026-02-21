package com.bank.controller;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import com.bank.model.Transaction;

import java.util.List;

public class BankingController {

    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public BankingController() {
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // Create new account
    public String createAccount(String name, String accountType, double initialDeposit,
                                String email, String phone, String address, String pin) {
        try {
            if (initialDeposit < 0) {
                return "Initial deposit cannot be negative!";
            }

            if (pin.length() != 4 || !pin.matches("\\d{4}")) {
                return "PIN must be exactly 4 digits!";
            }

            String accountNumber = accountDAO.generateAccountNumber();
            Account account = new Account(accountNumber, name, accountType,
                    initialDeposit, email, phone, address, pin);

            if (accountDAO.createAccount(account)) {

                if (initialDeposit > 0) {
                    Transaction transaction = new Transaction(
                            accountNumber,
                            "Deposit",
                            initialDeposit,
                            initialDeposit,
                            "Initial deposit"
                    );
                    transactionDAO.recordTransaction(transaction);
                }

                return "SUCCESS:" + accountNumber;
            } else {
                return "Failed to create account!";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Deposit money
    public String deposit(String accountNumber, String pin, double amount) {
        try {
            if (amount <= 0) {
                return "Deposit amount must be positive!";
            }

            if (!accountDAO.validatePin(accountNumber, pin)) {
                return "Invalid account number or PIN!";
            }

            Account account = accountDAO.getAccount(accountNumber);
            if (account == null) {
                return "Account not found!";
            }

            double newBalance = account.getbalance() + amount;

            if (accountDAO.updateBalance(accountNumber, newBalance)) {

                Transaction transaction = new Transaction(
                        accountNumber,
                        "Deposit",
                        amount,
                        newBalance,
                        "Cash deposit"
                );

                transactionDAO.recordTransaction(transaction);

                return "SUCCESS:Deposit successful! New balance: ₹"
                        + String.format("%.2f", newBalance);
            } else {
                return "Failed to update balance!";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Withdraw money
    public String withdraw(String accountNumber, String pin, double amount) {
        try {
            if (amount <= 0) {
                return "Withdrawal amount must be positive!";
            }

            if (!accountDAO.validatePin(accountNumber, pin)) {
                return "Invalid account number or PIN!";
            }

            Account account = accountDAO.getAccount(accountNumber);
            if (account == null) {
                return "Account not found!";
            }

            if (account.getbalance() < amount) {
                return "Insufficient balance! Current balance: ₹"
                        + String.format("%.2f", account.getbalance());
            }

            double newBalance = account.getbalance() - amount;

            if (accountDAO.updateBalance(accountNumber, newBalance)) {

                Transaction transaction = new Transaction(
                        accountNumber,
                        "Withdrawal",
                        amount,
                        newBalance,
                        "Cash withdrawal"
                );

                transactionDAO.recordTransaction(transaction);

                return "SUCCESS:Withdrawal successful! New balance: ₹"
                        + String.format("%.2f", newBalance);
            } else {
                return "Failed to update balance!";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Transfer money
    public String transfer(String fromAccount, String pin,
                           String toAccount, double amount) {
        try {
            if (amount <= 0) {
                return "Transfer amount must be positive!";
            }

            if (fromAccount.equals(toAccount)) {
                return "Cannot transfer to the same account!";
            }

            if (!accountDAO.validatePin(fromAccount, pin)) {
                return "Invalid account number or PIN!";
            }

            Account sender = accountDAO.getAccount(fromAccount);
            Account recipient = accountDAO.getAccount(toAccount);

            if (sender == null) return "Sender account not found!";
            if (recipient == null) return "Recipient account not found!";

            if (sender.getbalance() < amount) {
                return "Insufficient balance! Current balance: ₹"
                        + String.format("%.2f", sender.getbalance());
            }

            double senderNewBalance = sender.getbalance() - amount;
            double recipientNewBalance = recipient.getbalance() + amount;

            if (accountDAO.updateBalance(fromAccount, senderNewBalance)
                    && accountDAO.updateBalance(toAccount, recipientNewBalance)) {

                Transaction senderTxn = new Transaction(
                        fromAccount,
                        "Transfer Out",
                        amount,
                        senderNewBalance,
                        "Transfer to " + toAccount
                );
                senderTxn.setRecipentAccount(toAccount);
                transactionDAO.recordTransaction(senderTxn);

                Transaction recipientTxn = new Transaction(
                        toAccount,
                        "Transfer In",
                        amount,
                        recipientNewBalance,
                        "Transfer from " + fromAccount
                );
                recipientTxn.setRecipentAccount(fromAccount);
                transactionDAO.recordTransaction(recipientTxn);

                return "SUCCESS:Transfer successful! New balance: ₹"
                        + String.format("%.2f", senderNewBalance);
            } else {
                return "Failed to complete transfer!";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Check balance
    public String checkBalance(String accountNumber, String pin) {
        try {
            if (!accountDAO.validatePin(accountNumber, pin)) {
                return "Invalid account number or PIN!";
            }

            Account account = accountDAO.getAccount(accountNumber);
            if (account == null) {
                return "Account not found!";
            }

            return "SUCCESS:₹" + String.format("%.2f", account.getbalance());

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Admin methods

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccount();
    }

    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    public boolean deleteAccount(String accountNumber) {
        return accountDAO.deleteAccount(accountNumber);
    }

	public Account getAccountDetails(String accountNumber, String pin) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Transaction> getMiniStatement(String accountNumber, String pin) {
		// TODO Auto-generated method stub
		return null;
	}
}
