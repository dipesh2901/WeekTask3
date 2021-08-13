package com.neosoft.jdbc.view;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.neosoft.jdbc.controller.JDBCConnection;
import com.neosoft.jdbc.controller.UserInfoController;
import com.neosoft.jdbc.model.UserInformation;

interface Regex_pattern {
	default String regex_value() {
		return "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
	}

	default String regex_number_value() {
		return "[+]{1}[0-9]{2}[-]{1}\\d{10}";
	}
}

public class Poc_bank_version_3 implements Regex_pattern {

	public static void main(String[] args) {
//		JDBCConnection.openConnection();
//		JDBCConnection.closeConnection();

		Poc_bank_version_3 poc3 = new Poc_bank_version_3();
		Scanner sc = new Scanner(System.in);
		boolean condition_password, loggedIn = false;
		Base64.Encoder password_encoder = Base64.getEncoder();
		Base64.Decoder password_decoder = Base64.getDecoder();

		List<String> choice_list1 = new ArrayList<String>(
				Arrays.asList("1. Register Account", "2. Login", "3. Update Accounts", "4. Exit"));
		List<String> choice_list2 = new ArrayList<String>(
				Arrays.asList("----------------------------", "W E L C O M E", "----------------------------",
						"1. Deposit", "2. Transfer", "3. Last 5 Transactions", "4. User Information", "5. Log Out"));
		String name, address, number, username, password, regexNumber, regexPassword, encodedPassword = null,
				decodedPassword, transaction;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String date = sdf.format(new Date());
		;
		Double deposit;

		while (true) {
			condition_password = false;
			name = "";
			address = "";
			number = "";
			username = "";
			password = "";
			encodedPassword = "";
			System.out.println("----------------");
			System.out.println("BANK OF  MYBANK");
			System.out.println("----------------");
			choice_list1.stream().forEach(System.out::println);
			System.out.println("Enter your choice: ");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter Name: ");
				name = sc.next();
				System.out.println("Enter Address: ");
				address = sc.next();
				System.out.println("Enter contact number");
				number = sc.next();
				regexNumber = poc3.regex_number_value();
				if (Pattern.matches(regexNumber, number)) {
				} else {
					System.out.println("Please put country code and 10 digit number!! example- +91-**********");
					number = sc.next();
					if (Pattern.matches(regexNumber, number)) {
					} else {
						break;
					}
				}
				sc.nextLine();
				System.out.println("Set Username: ");
				username = sc.next();
				System.out.println("Set a Password:( (minimum 8 chars; minimum 1 digit, 1 lowercase, 1 \r\n"
						+ "uppercase, 1 special character[!@#$%^&*_]) ) ");
				while (condition_password != true) {
					password = sc.next();
					if (password.equals("")) {
						System.out.println("Write a Password Please: ");
					} else {
						regexPassword = poc3.regex_value();
						if (Pattern.matches(regexPassword, password)) {
							encodedPassword = password_encoder.encodeToString(password.getBytes());
							condition_password = true;
						} else {
							System.out.println("Invalid Password Condition.Set again: ");
						}
					}
				}
				System.out.println("Enter Initial Deposit: ");
				deposit = Double.parseDouble(sc.next());

				transaction = "Initial deposit: Rs. " + deposit + " as on " + date + " " + java.time.LocalTime.now();
				System.out.println(name + " " + address + " " + number + " " + username + " " + encodedPassword + " "
						+ deposit + " " + transaction);
				// JDBCConnection.openConnection();
				// JDBCConnection.closeConnection();
				try {
					UserInfoController.insertUser(new UserInformation(name, address, number, username, encodedPassword,
							deposit, transaction));
				} catch (SQLException e) {
					System.out.println("Some Error Occured: " + e.getMessage());
				}
				break;
			case 2:
				username = "";
				password = "";
				condition_password = false;
				loggedIn = false;
				System.out.println("Enter your Username: ");
				username = sc.next();
				System.out.println("Enter your Password: ");
				password = sc.next();
				String encoded_password = UserInfoController.userPassword(username);
				// System.out.println(encoded_password);
				String decoded_password = new String(password_decoder.decode(encoded_password));
				// System.out.println(decoded_password);
				while (condition_password != true) {
					choice = 0;
					if (password.equals(decoded_password)) {
						System.out.println("Login Successful!!");
						condition_password = true;
						while (loggedIn != true) {
							choice_list2.stream().forEach(System.out::println);
							System.out.println("Enter your choice: ");
							choice = sc.nextInt();
							switch (choice) {
							case 1:
								deposit = 0d;
								System.out.println("Enter Amount: ");
								deposit = sc.nextDouble();
								UserInfoController.updateBalance(username, deposit);
								UserInfoController.updateTransaction(username, deposit);
								break;
							case 2:
								String payeeUsername = "";
								Double payeeAmount = 0d;
								System.out.println("Enter payee username: ");
								payeeUsername = sc.next();
								System.out.println("Enter amount: ");
								payeeAmount = sc.nextDouble();
								UserInfoController.payUser(username, payeeUsername, payeeAmount);
								break;
							case 3:
								String last5Trans = UserInfoController.getTransaction(username);
								System.out.println(last5Trans);
								break;
							case 4:
								System.out.println("AccountHolder name: "
										+ UserInfoController.userInfo(username).getName() + "\n"
										+ "AccountHolder Address: " + UserInfoController.userInfo(username).getAddress()
										+ "\n" + "AccountHolder Mobile: "
										+ UserInfoController.userInfo(username).getMobile());
								break;
							case 5:
								loggedIn = true;
								break;
							}
						}

					} else {
						System.out.println("Wrong Password!!");
						System.out.println("Enter your Password: ");
						password = sc.next();
					}
				}
				break;
			case 3:
				username = "";
				name = "";
				address = "";
				number = "";
				System.out.println("Enter your Username: ");
				username = sc.next();
				System.out.println("Enter you name: ");
				name = sc.next();
				System.out.println("Enter you Address: ");
				address = sc.next();
				System.out.println("Enter your number: ");
				number = sc.next();
				try {
					UserInfoController.updateUser(username, new UserInformation(name, address, number));
				} catch (SQLException e) {
					System.out.println("Error Occured: " + e.getMessage());
				}
				break;
			case 4:
				System.exit(0);
				break;

			}
		}
	}
}
