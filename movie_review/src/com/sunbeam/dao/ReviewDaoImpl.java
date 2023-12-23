package com.sunbeam.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.sunbeam.pojos.Reviews;

public class ReviewDaoImpl extends Dao implements ReviewDao{

	public ReviewDaoImpl() throws Exception {
		super();

	}

	public int save(Reviews r) throws Exception {
		String sql = "INSERT INTO reviews(id, movie_id, review, rating, user_id, modified) VALUES(default, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, r.getMovieId());
			stmt.setString(2, r.getReview());
			stmt.setInt(3, r.getRating());
			stmt.setInt(4, r.getUserId());
			stmt.setDate(5, DateTimeUtil.utilDateToSqlDate(r.getModified()));
			int count = stmt.executeUpdate();
			return count;
		} // stmt.close();
	}

	public int update(Reviews r) throws Exception {
		String sql = "UPDATE reviews SET movie_id=?, review=?, rating=?, user_id=?, modified=? WHERE id=?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, r.getMovieId());
			stmt.setString(2, r.getReview());
			stmt.setInt(3, r.getRating());
			stmt.setInt(4, r.getUserId());
			stmt.setDate(5, DateTimeUtil.utilDateToSqlDate(r.getModified()));
			stmt.setInt(6, r.getId());
			int count = stmt.executeUpdate();
			return count;
		} // stmt.close();
	}

	public List<Reviews> findAll() throws Exception {
		List<Reviews> list = new ArrayList<Reviews>();
		String sql = "SELECT * FROM reviews ORDER BY modified DESC";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("id");
					int movieId = rs.getInt("movie_id");
					String review = rs.getString("review");
					int rating = rs.getInt("rating");
					int userId = rs.getInt("user_id");
					java.util.Date modified = DateTimeUtil.sqlDateToUtilDate(rs.getDate("modified"));
					Reviews r = new Reviews(id, movieId, review, userId, rating, modified);
					list.add(r);
				}
			} // rs.close();
		} // stmt.close();
		return list;
	}

	public List<Reviews> findByUserId(int userId) throws Exception {
		List<Reviews> list = new ArrayList<Reviews>();
		String sql = "SELECT * FROM reviews WHERE user_id=? ORDER BY modified DESC";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("id");
					int movieId = rs.getInt("movie_id");
					String review = rs.getString("review");
					int rating = rs.getInt("rating");
					int usrId = rs.getInt("user_id");
					java.util.Date modified = DateTimeUtil.sqlDateToUtilDate(rs.getDate("modified"));
					Reviews r = new Reviews(id, movieId, review, usrId, rating, modified);
					list.add(r);
				}
			} // rs.close();
		} // stmt.close();
		return list;
	}

	public List<Reviews> getSharedWithUser(int userId) throws Exception {
		List<Reviews> list = new ArrayList<Reviews>();
		String sql = "SELECT r.* FROM reviews r INNER JOIN shares s ON r.id=s.review_id WHERE s.user_id=? ORDER BY modified DESC";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("id");
					int movieId = rs.getInt("movie_id");
					String review = rs.getString("review");
					int rating = rs.getInt("rating");
					int usrId = rs.getInt("user_id");
					java.util.Date modified = DateTimeUtil.sqlDateToUtilDate(rs.getDate("modified"));
					Reviews r = new Reviews(id, movieId, review, usrId, rating, modified);
					list.add(r);
				}
			} // rs.close();
		} // stmt.close();
		return list;
	}

	public Reviews findById(int id) throws Exception {
		String sql = "SELECT * FROM reviews WHERE id=?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					id = rs.getInt("id");
					int movieId = rs.getInt("movie_id");
					String review = rs.getString("review");
					int rating = rs.getInt("rating");
					int usrId = rs.getInt("user_id");
					java.util.Date modified = DateTimeUtil.sqlDateToUtilDate(rs.getDate("modified"));
					Reviews r = new Reviews(id, movieId, review, usrId, rating, modified);
					return r;
				}
			} // rs.close();
		} // stmt.close();
		return null;
	}

	public int deleteById(int reviewId) throws Exception {
		String sql = "DELETE FROM reviews WHERE id=?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, reviewId);
			int count = stmt.executeUpdate();
			return count;
		}
	}

	public int shareReview(int reviewId, int userId) throws Exception {
		String sql = "INSERT INTO shares(review_id, user_id) VALUES (?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, reviewId);
			stmt.setInt(2, userId);
			int count = stmt.executeUpdate();
			return count;
		} catch (SQLIntegrityConstraintViolationException e) {
			// do nothing
		}
		return 0;
	}

}
