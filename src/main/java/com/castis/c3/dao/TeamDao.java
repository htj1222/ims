package com.castis.c3.dao;

import java.util.List;

import com.castis.c3.dto.Team;

public interface TeamDao {
	public void add(Team team);

	public void deleteAll();

	public Team get(String teamname);

	public List<Team> getAll();

	public int getCount();
}
