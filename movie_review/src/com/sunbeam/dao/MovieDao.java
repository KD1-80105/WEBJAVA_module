package com.sunbeam.dao;


import java.util.List;


import com.sunbeam.pojos.Movies;

public interface MovieDao extends AutoCloseable {
	
	public List<Movies> findAll() throws Exception;
	public Movies findById(int id) throws Exception;

}
