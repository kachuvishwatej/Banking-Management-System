package com.bank.model;
import java.sql.Timestamp;
public class Account {
	
	private int accountId;
	private String accountNumber;
	private String accountHolderName;
	private String accountType;
	private double balance;
	private String Email;
	private String phone;
	private String address;
	private String pin;
	private Timestamp CreateDate;
	private boolean isActive;
	
	//Constructors
	public Account() {
		
	}
public Account(String accountNumber, String accountHolderName, String accountType, double balance, String Email,String phone,String address, String pin) {
	this.accountNumber= accountNumber;
	this.accountHolderName= accountHolderName;
	this.accountType=accountType;
	this.balance = balance;
	this.Email=Email;
	this.address= address;
	this.pin = pin;
	this.isActive = true;
		
	}
// Getters and Setters
public int getAccountId() {
	return accountId;
	
}
public void setAccountId(int accountId) {
	this.accountId= accountId;
	
}
public String getAccountNumber() {
	return accountNumber;
}
public void setAccountNumber(String accountNumber) {
	this.accountNumber= accountNumber;
}
public String getAccountHolderName() {
	return accountHolderName;
}
public void setAccountHolderName(String accountHolderName) {
	this.accountHolderName= accountHolderName;
}
public String getAccountType() {
	return accountType;
	
}
public void serAccountType(String accountType) {
	this.accountType= accountType;
}
public double getbalance() {
	return balance;
	
}
public void setbalance(double balance) {
	this.balance=balance;
}
public String getEmail() {
	return Email;
}
public void setEmail(String Email) {
	this.Email= Email;
}
public String getphone() {
	return phone;
	
}
public void setphone(String phone) {
	this.phone=phone;
}
public String getaddress() {
	return address;
}
public void setaddress(String address) {
	this.address=address;
	
}
public String getpin() {
	return pin;
	
}
public void setpin(String pin) {
	this.pin= pin;
}
public Timestamp getCreateDate() {
	return CreateDate;
}
public void setCreateDate(Timestamp CreateDate) {
	this.CreateDate= CreateDate;
	
}
public boolean getisActive() {
	return isActive;
}
public void setisActive(boolean isActive) {
	this.isActive=isActive;
}
 @Override
 public String toString() {
	 return "Account{" + 
 "accountNumber='" + accountNumber+'\''+
",accountHolderName='"+ accountHolderName + '\''+
",accountType='" + accountType+ '\'' + 
",balance='" + balance +
", Email='" + Email + '\'' +
",phone='"+ phone +'\''+
'}';
 }
public void add(Account account) {
	// TODO Auto-generated method stub
	
}

}
