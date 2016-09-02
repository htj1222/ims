package com.castis.c3.dao;

import java.util.List;

import com.castis.c3.dto.Vacation;

public interface VacationDao {
	public void add(Vacation vacation);

	public void deleteAll();

	public Vacation get(String username);

	public List<Vacation> getAll();

	public int getCount();
}
