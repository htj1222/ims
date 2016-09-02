package com.castis.c3.dao;

import java.util.List;

import com.castis.c3.dto.Project;

public interface ProjectDao {
	public void add(Project project);

	public void deleteAll();

	public Project get(String projectname);

	public List<Project> getAll();

	public int getCount();
}
