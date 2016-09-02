package com.castis.c3.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import com.castis.c3.dto.Project;

public class ProjectDaoJdbc implements ProjectDao {
	private JdbcTemplate jdbcTemplate;

	public ProjectDaoJdbc() {
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void add(Project project) {
		if (project != null) {
			this.jdbcTemplate.update("insert into project(projectname,site,description,startdate,enddate) values(?,?,?,?,?)", project.getProjectname(),
					project.getSite(), project.getDescription(), project.getStartdate(), project.getEnddate());
		}
	}

	public void deleteAll() {
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement("delete from project");
			}
		});
	}

	private RowMapper<Project> userMapper = new RowMapper<Project>() {
		@Override
		public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project project = new Project();
			project.setProjectname(rs.getString("projectname"));
			project.setSite(rs.getString("site"));
			project.setDescription(rs.getString("description"));
			project.setStartdate(rs.getDate("startdate"));
			project.setEnddate(rs.getDate("enddate"));
			return project;
		}
	};

	public Project get(String projectname) {
		return jdbcTemplate.queryForObject("select * from project where projectname = ?", new Object[] { projectname }, this.userMapper);
	}

	public List<Project> getAll() {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement("select * from project order by projectname");
			}
		}, this.userMapper);
	}

	public int getCount() {
		return jdbcTemplate.queryForObject("select count(*) from project", Integer.class);
	}
}
