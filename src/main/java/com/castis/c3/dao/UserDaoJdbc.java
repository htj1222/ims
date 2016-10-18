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

import com.castis.c3.dto.User;

public class UserDaoJdbc implements UserDao {
	private JdbcTemplate jdbcTemplate;

	public UserDaoJdbc() {
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void add(User user) {
		if (user != null) {
			this.jdbcTemplate.update(
					"insert into user(userid, username,password,teamname,position,cellphone,email) values(?,?,?,?,?,?,?)",
					user.getUserid(), user.getUsername(), user.getPassword(), user.getTeamname(), user.getPosition(),
					user.getCellphone(), user.getEmail());
		}
	}

	public void deleteAll() {
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement("delete from user");
			}
		});
	}

	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUserid(rs.getString("userid"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setTeamname(rs.getString("teamname"));
			user.setPosition(rs.getString("position"));
			user.setCellphone(rs.getString("cellphone"));
			user.setEmail(rs.getString("email"));
			return user;
		}
	};

	public User get(String username) {
		return jdbcTemplate.queryForObject("select * from user where username = ?", new Object[] { username },
				this.userMapper);
	}

	public List<User> getAll() {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement("select * from user order by username");
			}
		}, this.userMapper);
	}

	public int getCount() {
		return jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
	}
}
