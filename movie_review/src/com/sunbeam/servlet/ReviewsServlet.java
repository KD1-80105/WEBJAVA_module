package com.sunbeam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunbeam.dao.ReviewDao;
import com.sunbeam.dao.ReviewDaoImpl;
import com.sunbeam.pojos.Reviews;
import com.sunbeam.pojos.Users;

@WebServlet("/review")
public class ReviewsServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (ReviewDao reviewDao = new ReviewDaoImpl()) {
			List<Reviews> list = reviewDao.findAll();
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Movie Reviews</title>");
			out.println("</head>");
			out.println("<body>");

			HttpSession session = req.getSession();
			Users curUser = (Users) session.getAttribute("curUser");
			out.println("Hello " + curUser.getFirstName() + curUser.getLastName());

			out.println("<h5>Movie Reviews</h5>");
			out.println("<a href='review?type=all'>All reviews</a>");
			out.println("<a href='review?type=my'>My reviews</a>");
			out.println("<a href='review?type=shared'>Shared reviews</a>");
			out.println("<table border='1'>");
			out.println("<thead>");
			out.println("<th>Id</th>");
			out.println("<th>Movie</th>");
			out.println("<th>Rating</th>");
			out.println("<th>Review</th>");
			out.println("<th>Action</th>");
			out.println("</thead>");
			out.println("<tbody>");
			for (Reviews reviews : list) {
				out.println("<tr>");
				out.printf("<td>%s</td>\r\n", reviews.getId());
				out.printf("<td>%s</td>\r\n", reviews.getMovieId());
				out.printf("<td>%s</td>\r\n", reviews.getRating());
				out.printf("<td>%s</td>\r\n", reviews.getReview());
				out.printf(
						"<td><a href='revedit?id=%s'><img src='edit.png' alt='Edit' width='24' height='24'/></a> <a href='revdel?id=%s'><img src='delete.png' alt='Delete' width='24' height='24'/></a></td>\r\n",
						reviews.getId(), reviews.getId());
				out.println("</tr>");
			}
			out.println("</tbody>");
			out.println("</table>");

			out.println("<a href='addreview'>Add Reviews</a>");
			out.println("<a href='signout'>Sign Out</a>");
			
			
			

		} catch (Exception e) {

			e.printStackTrace();
			throw new ServletException(e);
		}

	}
}
