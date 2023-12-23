package com.sunbeam.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.dao.ReviewDao;
import com.sunbeam.dao.ReviewDaoImpl;

@WebServlet("/revdel")
public class ReviewDeleteServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userID = req.getParameter("id");
		int id = Integer.parseInt(userID);
		int cnt = 0;
		try (ReviewDao revDao = new ReviewDaoImpl()) {
			cnt = revDao.deleteById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		}

		String message = "Review Deleted: " + cnt;
		req.setAttribute("message", message);
		
		RequestDispatcher rd = req.getRequestDispatcher("review");
		rd.forward(req, resp);
	}

}
