package com.neosoft.Task;

import java.util.ArrayList;
import java.util.Arrays;

public class UserList {

	public static ArrayList<User> user_list = new ArrayList<User>();

	public static void adduser(User u) {
		if (user_list != null) {
			System.out.println("Entered1");
			user_list.add(u);
		}

	}

	public static void printUser() {
		System.out.println("Entered 2");
		user_list.stream().forEach(user -> System.out.println(user.getUsername() + "\t" + user.getPassword()));

	}

}
