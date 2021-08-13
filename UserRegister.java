package com.neosoft.Task;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/registerUser")
public class UserRegister extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter writer = resp.getWriter();
		UserList.adduser(new User(Integer.parseInt(req.getParameter("uid")), req.getParameter("uemail"),
				req.getParameter("umobile"), req.getParameter("username"), req.getParameter("pass")));
		RequestDispatcher rd = req.getRequestDispatcher("index.html");
		rd.forward(req, resp);
	}

}