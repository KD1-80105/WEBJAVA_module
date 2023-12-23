package com.sunbeam.dao;

import java.sql.Date;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.List;

import com.sun.javadoc.ThrowsTag;
import com.sunbeam.pojos.Users;

public class UserDaoImpl extends Dao implements UserDao {

	public UserDaoImpl() throws Exception {

		// TODO Auto-generated constructor stub

	}

	public int save(Users u) throws Exception {

		String sql = "INSERT INTO users(id, first_name, last_name, email, mobile, birth, password) VALUES(default, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, u.getFirstName());
			stmt.setString(2, u.getLastName());
			stmt.setString(3, u.getEmail());
			stmt.setString(4, u.getMobile());
			stmt.setDate(5, (Date) DateTimeUtil.utilDateToSqlDate(u.getBirth()));
			stmt.setString(6, u.getPassword());
			int count = stmt.executeUpdate();
			return count;
		} // stmt.close();

	}

	@Override

	public int update(Users u) throws Exception {

		String sql = "UPDATE users SET first_name=?, last_name=?, mobile=?, birth=? WHERE id=?";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, u.getFirstName());

			stmt.setString(2, u.getLastName());

			stmt.setString(3, u.getEmail());

			stmt.setDate(4, (Date) DateTimeUtil.utilDateToSqlDate(u.getBirth()));

			stmt.setInt(5, u.getId());

			int count = stmt.executeUpdate();

			return count;

		} // stmt.close();

	}

	@Override

	public int updatePassword(int userId, String newPassword) throws Exception {
		String sql = "UPDATE users SET password=? WHERE id=?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, newPassword);
			stmt.setInt(2, userId);
			int count = stmt.executeUpdate();
			return count;
		} // stmt.close();
	}

	@Override

	public Users findByEmail(String email) throws Exception {
		String sql = "SELECT * FROM users WHERE email=?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, email);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String fname = rs.getString("first_name");
					String lname = rs.getString("last_name");
					String password = rs.getString("password");
					email = rs.getString("email");
					String mobile = rs.getString("mobile");
					java.util.Date udate = DateTimeUtil.sqlDateToUtilDate(rs.getDate("birth"));
					return new Users(id, fname, lname, email, mobile, udate, password);
				}
			}
		}
		return null;
	}

	@Override

	public List<Users> findAll() throws Exception {
		List<Users> list = new ArrayList<Users>();
		String sql = "SELECT * FROM users";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String fname = rs.getString("first_name");
					String lname = rs.getString("last_name");
					String password = rs.getString("password");
					String email = rs.getString("email");
					String mobile = rs.getString("mobile");
					Date uDate = (Date) DateTimeUtil.sqlDateToUtilDate(rs.getDate("birth"));
					Users u = new Users(id, fname, lname, email, password, uDate, mobile);
					list.add(u);
				}

			} // rs.close();

		} // stmt.close();

		return list;

	}
}
