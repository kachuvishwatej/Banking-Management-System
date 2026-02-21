package com.bank.dao;
import com.bank.model.Transaction;
import com.bank.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
	
	//Record transaction 
	public boolean recordTransaction(Transaction transaction) {
		String sql="INSERT INTO transaction (account_number,transaction_type,amount,"+"balance_after,description,recipient_account) VALUES (?,?,?,?,?,?)";
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, transaction.getAccountnumber());
			pstmt.setString(2, transaction.getTransactionType());
			pstmt.setDouble(3, transaction.getAmount());
			pstmt.setDouble(4, transaction.getBalanceAfter());
			pstmt.setString(5, transaction.getDiscription());
			pstmt.setString(6, transaction.getRecipentAccount());
			
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected>0;	
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Get transaction history foe an account
	
	public List<Transaction> getTransactionHistory(String accountNumber){
		List<Transaction>transaction = new ArrayList<>();
		String sql ="SELECT * FROM transaction WHERE account_number =? "+
		            "ORDER BY transaction_date DESC";
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1,accountNumber);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Transaction transactions = new Transaction();
				transactions.settransactionId(rs.getInt("transaction_id"));
				transactions.setAccountNumber(rs.getString("account_number"));
				transactions.setTransactionType(rs.getString("tansaction_type"));
				transactions.setAmount(rs.getDouble("amount"));
				transactions.setBalanceAfter(rs.getDouble("balnce_after"));
				transactions.setDescription(rs.getNString("description"));
				transactions.setRecipentAccount(rs.getString("recipient_account"));
				transactions.setTransactionDate(rs.getTimestamp("transaction_date"));
				transactions.add(transactions);
					}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return transaction;
		}
	// Get last N transaction
	public List<Transaction> getRecentTransactions (String accountNumber, int limit){
		List<Transaction> transactions = new ArrayList<>();
		String sql ="SELECT * FROM transaction WHERE account_number =? "+
		            "ORDER BY transaction_date DESC LIMIT?";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, accountNumber);
			pstmt.setInt(2, limit);
			ResultSet rs= pstmt.executeQuery();
			
			while (rs.next()) {
				Transaction transaction = new Transaction();
				transaction.settransactionId(rs.getInt("transaction_id"));
				transaction.setAccountNumber(rs.getString("account_number"));
				transaction.setTransactionType(rs.getString("tansaction_type"));
				transaction.setAmount(rs.getDouble("amount"));
				transaction.setBalanceAfter(rs.getDouble("balnce_after"));
				transaction.setDescription(rs.getNString("description"));
				transaction.setRecipentAccount(rs.getString("recipient_account"));
				transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
				transaction.add(transaction);
			}
		
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		List<Transaction> transaction = null;
		return transaction;
	}
	
	//Get all transaction
	public List<Transaction> getAllTrasaction(){
		List<Transaction> transactions =new ArrayList<>();
		String sql ="SELECT * FRom transction ORDER BY transaction_date DESC";
		
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			while(rs.next()) {
			Transaction transaction = new Transaction();
			transaction.settransactionId(rs.getInt("transaction_id"));
			transaction.setAccountNumber(rs.getString("account_number"));
			transaction.setTransactionType(rs.getString("tansaction_type"));
			transaction.setAmount(rs.getDouble("amount"));
			transaction.setBalanceAfter(rs.getDouble("balnce_after"));
			transaction.setDescription(rs.getNString("description"));
			transaction.setRecipentAccount(rs.getString("recipient_account"));
			transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
			transaction.add(transaction);
		}
	}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public List<Transaction> getAllTransactions() {
		// TODO Auto-generated method stub
		return null;
	}
}
			
	