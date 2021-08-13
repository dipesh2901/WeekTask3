package com.neosoft.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.neosoft.jdbc.model.UserInformation;

public class UserInfoController {

	public static void insertUser(UserInformation user) throws SQLException {
		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		String insert_query = "insert into userInformation values(?,?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(insert_query);
			pst.setString(1, user.getName());
			pst.setString(2, user.getAddress());
			pst.setString(3, user.getMobile());
			pst.setString(4, user.getUsername());
			pst.setString(5, user.getPassword());
			pst.setDouble(6, user.getDeposit());
			pst.setString(7, user.getTransaction());
			System.out.println("Inserted: " + pst.executeUpdate());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null)
				pst.close();
			JDBCConnection.closeConnection();
		}
	}

	public static String userPassword(String username) {
		String password = "";
		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String password_query = "select user_password from userInformation where username=?";
		try {
			pst = con.prepareStatement(password_query);
			pst.setString(1, username);
			rs = pst.executeQuery();
			// password=rs.getString(1);
			// System.out.println("Password is: "+password);
			while (rs.next()) {
				password = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println("Some error Occured: " + e.getMessage());
		} finally {
			if (pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			JDBCConnection.closeConnection();

		}
		return password;
	}

	public static void updateUser(String username, UserInformation user) throws SQLException {
		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		String query1 = "update userInformation set name=?,address=?,contact=? where username=?";
		try {
			pst = con.prepareStatement(query1);
			pst.setString(1, user.getName());
			pst.setString(2, user.getAddress());
			pst.setString(3, user.getMobile());
			pst.setString(4, username);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pst != null)
				pst.close();
			JDBCConnection.closeConnection();
		}
	}

	public static void updateBalance(String username, Double deposit) {
		Double balance = 0d;
		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query_getbalance = "select deposit from userInformation where username=?";
		try {
			pst = con.prepareStatement(query_getbalance);
			pst.setString(1, username);
			rs = pst.executeQuery();
			while (rs.next()) {
				balance = rs.getDouble(1);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		balance = balance + deposit;
		String updateBalance = "update userInformation set deposit=? where username=?";
		try {
			pst = con.prepareStatement(updateBalance);
			pst.setDouble(1, balance);
			pst.setString(2, username);
			pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	@SuppressWarnings("finally")
	public static UserInformation userInfo(String username) {
		UserInformation user = null;
		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String fetchInfo = "select name,address,contact from userInformation where username=?";
		try {
			pst = con.prepareStatement(fetchInfo);
			pst.setString(1, username);
			rs = pst.executeQuery();
			while (rs.next()) {
				user = new UserInformation(rs.getString(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			return user;
		}

	}

	public static void payUser(String username, String payeeUsername, Double amountToPay) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String date= sdf.format(new Date());;
		Double balance = 0d, payeeDeposit = 0d;
		String userTransaction="",payeeTransaction="";
		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String isUsernamePresent = "select * from userInformation where username=?";
		String getBalance = "select deposit from userInformation where username=?";
		String updateUserBalance = "update userInformation set deposit=? where username=?";
		String getPayeeBalance = "select deposit from userInformation where username=?";
		String updatePayeeBalance = "update userInformation set deposit=? where username=?";
		String getUserTransaction="select transaction from userInformation where username=?";
		String setUserTransaction="update userInformation set transaction=? where username=?";
		String getPayeeTransaction="select transaction from userInformation where username=?";
		String setPayeeTransaction="update userInformation set transaction=? where username=?";
		try {
			pst = con.prepareStatement(isUsernamePresent);
			pst.setString(1, payeeUsername);
			rs = pst.executeQuery();
			if (rs.next()) {
				System.out.println("Username Exists");
				pst = con.prepareStatement(getBalance);
				pst.setString(1, username);
				rs = pst.executeQuery();
				while (rs.next()) {
					balance = rs.getDouble(1);
					if (balance < amountToPay) {
						System.out.println("Dont have sufficient Balance");
					} else {
						balance = balance - amountToPay;
						pst = con.prepareStatement(updateUserBalance);
						pst.setDouble(1, balance);
						pst.setString(2, username);
						pst.executeUpdate();
						System.out.println("Update user balance");
						
						
						pst=con.prepareStatement(getUserTransaction);
						pst.setString(1,username);
						rs=pst.executeQuery();
						while(rs.next()) {
							userTransaction=rs.getString(1);
						}
						userTransaction="You have transferred Rs. "+amountToPay+" to Payee- "+payeeUsername+"as on "+date+" at "+java.time.LocalTime.now()+"\n"+userTransaction;
						
						pst=con.prepareStatement(setUserTransaction);
						pst.setString(1,userTransaction);
						pst.setString(2,username);
						pst.executeUpdate();
						
				
						pst = con.prepareStatement(getPayeeBalance);
						pst.setString(1, payeeUsername);
						rs = pst.executeQuery();
						while (rs.next()) {
							balance = rs.getDouble(1);
						}
						amountToPay = amountToPay + balance;
						pst = con.prepareStatement(updatePayeeBalance);
						pst.setDouble(1, amountToPay);
						pst.setString(2, payeeUsername);
						pst.executeUpdate();
						
						pst=con.prepareStatement(getPayeeTransaction);
						pst.setString(1,payeeUsername);
						rs=pst.executeQuery();
						while(rs.next()) {
							payeeTransaction=rs.getString(1);
						}
						payeeTransaction="You have been credited Rs. "+amountToPay+" from User- "+username+"as on "+date+" at "+java.time.LocalTime.now()+"\n"+payeeTransaction;
						
						pst=con.prepareStatement(setPayeeTransaction);
						pst.setString(1,payeeTransaction);
						pst.setString(2,payeeUsername);
						pst.executeUpdate();

					}
				}

			} else {
				System.out.println("Username does not exists");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			JDBCConnection.closeConnection();
		}

	}

	public static void updateTransaction(String username, Double amount) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String date= sdf.format(new Date());;
		String transaction = "";
		Double deposit = 0d;
		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String getTransaction = "select transaction from userInformation where username=?";
		String updateTransaction = "update userInformation set transaction=? where username=?";
		String getDeposit = "select deposit from userInformation where username=?";
		try {
			pst = con.prepareStatement(getTransaction);
			pst.setString(1, username);
			rs = pst.executeQuery();
			while (rs.next()) {
				transaction = rs.getString(1);
			}
			pst = con.prepareStatement(getDeposit);
			pst.setString(1, username);
			rs = pst.executeQuery();
			while (rs.next()) {
				deposit = rs.getDouble(1);
			}
			transaction = "Rs. " + amount + " credited to your account. Balance Rs. " + deposit +" on "+date+" at "+java.time.LocalTime.now()+"\n" + transaction;
			pst = con.prepareStatement(updateTransaction);
			pst.setString(1, transaction);
			pst.setString(2, username);
			pst.executeUpdate();
			System.out.println("Updated Transaction");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			JDBCConnection.closeConnection();
		}

	}

	public static String getTransaction(String username) {
		String transaction="";
		Connection con = JDBCConnection.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String getTransactionQuery="select transaction from userInformation where username=?";
		try {
			pst=con.prepareStatement(getTransactionQuery);
			pst.setString(1,username);
			rs=pst.executeQuery();
			while(rs.next()) {
				transaction=rs.getString(1);
			}
			return transaction;
		} catch (SQLException e) {
			System.out.println("Error Occured: "+e.getMessage());
		}
		finally {
			JDBCConnection.closeConnection();
		}
		return transaction;
		
	}
}
