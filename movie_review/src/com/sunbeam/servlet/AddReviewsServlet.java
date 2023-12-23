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

@WebServlet("/addreview")
public class AddReviewsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<head>");
		out.println("<title>Add reviews</title>");
		out.println("</head>");
		out.println("<body>");
		HttpSession session = req.getSession();
		Users curUser = (Users) session.getAttribute("curUser");

		out.println("Hello " + curUser.getFirstName() + curUser.getLastName());

		out.println("<form method='post' action='addreview'>");
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

		out.println("<input type='number' name='rating'>");
		
		out.println("<input type='textarea' name='review'>");
		out.println("<input type='submit' value='addreview'>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String movieName = req.getParameter("movie");
		int movieID = Integer.parseInt(movieName);
		String rating = req.getParameter("rating");
		int rate = Integer.parseInt(rating);
		String review = req.getParameter("review");
		HttpSession session = req.getSession();
		Users curUser = (Users) session.getAttribute("curUser");
		Reviews r = new Reviews(0, movieID, review, rate, curUser.getId(), new Date());
		int cnt = 0;
		try (ReviewDao dao = new ReviewDaoImpl()) {
			int list = dao.save(r);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		RequestDispatcher rd=req.getRequestDispatcher("review");
		rd.forward(req, resp);
	}

}
