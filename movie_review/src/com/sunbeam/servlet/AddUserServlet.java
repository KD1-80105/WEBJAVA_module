package com.sunbeam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.dao.UserDao;
import com.sunbeam.dao.UserDaoImpl;
import com.sunbeam.pojos.Users;

@WebServlet("/adduser")
public class AddUserServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fname = req.getParameter("fname");
		String lname = req.getParameter("lname");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		String birth = req.getParameter("birth");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		java.util.Date udate = null;
		try {
			udate = sdf.parse(birth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String password = req.getParameter("password");
		System.out.println("you got :" + fname + "," + lname + ":" + email + ":" + password);
		Users u = new Users(0, fname, lname, email, mobile, udate, password);
		try (UserDao userDao = new UserDaoImpl()) {
			int cnt = userDao.save(u);
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();

			if (cnt == 1) {
				out.println("user added successfully!!!");
				out.println("</br>");
				out.println("<a href='index.html'>login again</a>");
			} else {
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Adduser Failed</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h5>Cannot Add you :( </h5>");
				out.println("<a href='index.html'>login again</a>");
				out.println("</body>");
				out.println("</html>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
}
