package com.neosoft.httptest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/register")
public class Registration extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		out.write("<h1>Registration Successful!!!</h1>");
		out.write("<table border=2>");
		out.write("<tbody>");
		out.write("<tr><th>name: "+req.getParameter("name")+"</th></tr>");
		out.write("<tr><th>age: "+req.getParameter("age")+"</th></tr>");
		out.write("<tr><th>email Id: "+req.getParameter("emailId")+"</th></tr>");
		out.write("<tr><th>Qualification: "+req.getParameter("qualification")+"</th></tr>");
		out.write("<tr><th>Course: "+req.getParameter("course")+"</th></tr>");
		out.write("</tbody");
		out.write("</table>");
		out.close();
	}
	
}
