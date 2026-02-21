package com.bank.dao;
import com.bank.model.Account;
import com.bank.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountDAO {
	// Generate Unique account number
	public String generateAccountNumber() {
		Random random = new Random ();
		String accountNumber;
		do {
			long number =1000000000L +(long)(random.nextLong()*9000000000L);
			accountNumber = String.valueOf(number);	
		}
		while (accountExits(accountNumber));
		return accountNumber;
	}
	// check if account exits
	private boolean accountExits(String accountNumber) {
		String sql ="SELECT COUNT(*) FROM accounts WHERE account_number=?";
		try(Connection conn =DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
					pstmt.setString(1, accountNumber);
					ResultSet rs =pstmt.executeQuery();
					return rs.getInt(1)>0;
				}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Create new account
	public boolean createAccount(Account account) {
		String sql ="INSERT INTO accounts (account_number,account_holder_name,account_type,"+"balance,Email,phone,address,pin) VALUES (?,?,?,?,?,?,?,?)";
		try(Connection conn = DatabaseConnection.getConnection();
			PreparedStatement pstmt =conn.prepareStatement(sql)){
				pstmt.setString(1, account.getAccountNumber());
				pstmt.setString(2, account.getAccountHolderName());
				pstmt.setString(3, account.getAccountType());
				pstmt.setDouble(4, account.getbalance());
				pstmt.setString(5, account.getEmail());
				pstmt.setString(6, account.getphone());
				pstmt.setString(7,account.getaddress());
				pstmt.setString(8, account.getpin());
				
				int rowsAffected = pstmt.executeUpdate();
				return rowsAffected>0;	
			}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
				
	}
	// Get account by account number
	public Account getAccount(String accountNumber) {
		String sql ="SELECT * FROM accounts WHERE account_number=? AND is_active=1";
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt =conn.prepareStatement(sql)){
			pstmt.setString(1, accountNumber);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Account account =new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setAccountNumber(rs.getString("account_number"));
				account.setAccountHolderName(rs.getString("account_holder_name"));
				account.serAccountType(rs.getString("account_type"));
				account.setbalance(rs.getDouble("balance"));
				account.setEmail(rs.getString("email"));
				account.setphone(rs.getString("phone"));
				account.setaddress(rs.getString("address"));
				account.setpin(rs.getString("pin"));
				account.setCreateDate(rs.getTimestamp("created_date"));
				account.setisActive(rs.getBoolean("is_active"));
				return account;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	// Validate PIN 
	public boolean validatePin(String accountNumber,String pin) {
		Account account = getAccount(accountNumber);
		return account !=null && account.getpin().equals(pin);
		
	}
	//Update balance
	public boolean updateBalance(String accountNumber,double newBalance) {
		String sql ="UPDATE accounts SET balance = ? WHERE account_number = ?";
		try(Connection conn =DatabaseConnection.getConnection();
				PreparedStatement pstmt =conn.prepareStatement(sql)){
			pstmt.setDouble(1, newBalance);
			pstmt.setString(2, accountNumber);
			
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected>0;
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	// Update account details
	public boolean updateAccount(Account account) {
		String sql = "UPDATE accounts SET  account_holder_name=?,email=?,"+"phone=?,address=?,WHERE account_number=?";
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, account.getAccountHolderName());
			pstmt.setString(2, account.getEmail());
			pstmt.setString(3, account.getphone());
			pstmt.setString(4, account.getAccountNumber());
			
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Change PIN 
	public boolean changePin (String accountNumber, String oldPin, String newPin) {
		
		if (!validatePin(accountNumber,oldPin)) {
			return false;
		}
		String sql ="UPDATE accounts SET pin = ? WHERE account_number=?";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, newPin);
			pstmt.setString(2,  accountNumber);
			
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;	
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//Get all accounts
	public List<Account> getAllAccount(){
		List<Account> accounts = new ArrayList<>();
		String sql ="SELECT * FROM accounts WHERE is_active =1 ORDER BY created_date DESC";
		
		try(Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while (rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setAccountNumber(rs.getString("account_number"));
				account.setAccountHolderName(rs.getString("account_holder_name"));
				account.serAccountType(rs.getString("account_type"));
				account.setbalance(rs.getDouble("balance"));
				account.setEmail(rs.getString("email"));
				account.setphone(rs.getString("phone"));
				account.setaddress(rs.getString("address"));
				account.setCreateDate(rs.getTimestamp("created_date"));
				account.setisActive(rs.getBoolean("is_active"));
				account.add(account);	
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
	
		}
		return accounts;
	}
	// Delete account (soft delete)
	public boolean deleteAccount(String accountNumber) {
		String sql ="UPDATE accounts SET is_active =0 WHERE accounts_number=?";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, accountNumber);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
			
		}
		catch (SQLException e) {
		e.printStackTrace();
		return false;
		}
	}
}
