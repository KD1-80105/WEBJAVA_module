package com.sunbeam.dao;


import java.sql.Connection;
import java.sql.DriverManager;

import com.sunbeam.util.Dbutil;



public class Dao implements AutoCloseable {
	protected Connection con;
	public Dao() throws Exception {
		con = Dbutil.getConnection();
	}
	@Override
	public void close() {
		try {
			if(con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
