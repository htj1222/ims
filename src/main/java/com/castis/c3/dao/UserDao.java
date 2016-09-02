package com.castis.c3.dao;

import java.util.List;

import com.castis.c3.dto.User;

public interface UserDao {
	public void add(User user);

	public void deleteAll();

	public User get(String username);

	public List<User> getAll();

	public int getCount();
}
