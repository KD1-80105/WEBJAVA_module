package com.sunbeam.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunbeam.dao.MovieDao;
import com.sunbeam.dao.MovieDaoImpl;
import com.sunbeam.dao.ReviewDao;
import com.sunbeam.dao.ReviewDaoImpl;
import com.sunbeam.pojos.Movies;
import com.sunbeam.pojos.Reviews;
import com.sunbeam.pojos.Users;

@WebServlet("/revedit")
public class ReviewEditServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		int reviewId = Integer.parseInt(id);
		Reviews r = null;
		try (ReviewDao reviewDao = new ReviewDaoImpl()) {
			r = reviewDao.findById(reviewId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<head>");
		out.println("<title>Add reviews</title>");
		out.println("</head>");
		out.println("<body>");
		HttpSession session = req.getSession();
		Users curUser = (Users) session.getAttribute("curUser");
		out.println("Hello " + curUser.getFirstName() + curUser.getLastName());
		out.println("<form method='post' action='revedit'>");
		out.printf("Review ID: <input type='text' name='reviewId' value='%d' readonly><br/>", r.getId());
		out.println("<select name='movie'>");
		try (MovieDao dao = new MovieDaoImpl()) {
			List<Movies> list = dao.findAll();
			for (Movies movies : list) {
				out.printf("<option value='%d'>%s</option>", movies.getId(), movies.getTitle());
			}
			out.println("</select>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		}
		out.printf("Rating: <input type='number' name='rating' value='%d'><br/>", r.getRating());
		out.printf("UserID: <input type='text' name='userId' value='%d' readonly><br/>", r.getUserId());
		out.println("Review: <textarea name='review'>" + r.getReview() + "</textarea><br/>");
		out.println("<input type='submit' value='Update'>");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userid = req.getParameter("reviewId");
		int id = Integer.parseInt(userid);
		String mid = req.getParameter("movie");
		int movieId = Integer.parseInt(mid);
		String review = req.getParameter("review");
		String rate = req.getParameter("rating");
		int rating = Integer.parseInt(rate);
		String uid = req.getParameter("userId");
		int userId = Integer.parseInt(uid);
		Reviews r = new Reviews(id, movieId, review, rating, userId, new Date());
		System.out.println(r);
		int cnt = 0;
		try (ReviewDao reviewDao = new ReviewDaoImpl()) {
			cnt = reviewDao.update(r);
			RequestDispatcher rd = req.getRequestDispatcher("review");
			rd.forward(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
}
