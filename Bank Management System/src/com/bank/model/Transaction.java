package com.bank.model;
import java.sql.Timestamp;
import java.util.List;

public class Transaction {
	private int transactionId;
	private String accountNumber;
	private String transactionType;
	private double amount;
	private double balanceAfter;
	private String description;
	private Timestamp transactionDate;
	private String recipentAccount;
	
	//Constructor
	public Transaction() {
	}
		public Transaction (String accountNumber, String transactionType,double amount,double balanceAfter, String description) {
			this.accountNumber=accountNumber;
			this.transactionType=transactionType;
			this.amount=amount;
			this.balanceAfter=balanceAfter;
			this.description=description;
		}
		// Getters And Setters
		public int gettransactionId() {
			return transactionId;
			
		}
		public void settransactionId(int transactionId) {
			this.transactionId=transactionId;
		}
		public String getAccountnumber() {
			return accountNumber;
		}
		public void setAccountNumber(String accountNumber) {
			this.accountNumber=accountNumber;
		}
		public String getTransactionType() {
			return transactionType;
		}
		public void setTransactionType(String transactionType) {
			this.transactionType= transactionType;
		}
		public double getAmount() {
		return amount;
		}
		public void setAmount(double amount) {
			this.amount=amount;
		}
		public double getBalanceAfter() {
			return balanceAfter;
			
		}
		public void setBalanceAfter(double balanceAfter) {
			this.balanceAfter=balanceAfter;
		}
		public String getDiscription() {
			return description;
		}
		public void setDescription(String description) {
			this.description=description;
		}
		public Timestamp getTransactionDate() {
			return transactionDate;
			
		}
		public void setTransactionDate(Timestamp transactionDate) {
			this.transactionDate=transactionDate;
		}
		public String getRecipentAccount () {
			return recipentAccount;
		}
		public void setRecipentAccount(String recipentAccount) {
			this.recipentAccount=recipentAccount;
		}
		
		@Override
		public String toString() {
			return "Transaction { " +
		"transactionType= '" + transactionType +'\''+
		",amount=" + amount+ 
		",balanceAfter="+ balanceAfter +
		",transactionDate=" + transactionDate +
		'}';
		}
		public void add(Transaction transactions) {
			// TODO Auto-generated method stub
			
		}
}
