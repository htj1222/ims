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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.castis.c3.dto.Ticket;

public class TicketDaoJdbc implements TicketDao {
	private JdbcTemplate jdbcTemplate;

	public TicketDaoJdbc() {
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Number add(Ticket ticket) {
		final String ticketInsertSQL = "insert into ticket(teamname,username,projectname,title,subtitle,content,nmd,emd,history,attachedfile) values(?,?,?,?,?,?,?,?,?,?)";
		final String ticketName = ticket.getTeamname();
		final String ticketUserName = ticket.getUsername();
		final String ticketProjectName = ticket.getProjectname();
		final String ticketTitle = ticket.getTitle();
		final String ticketSubTitle = ticket.getSubtitle();
		final String ticketContent = ticket.getContent();
		final int ticketNmd = ticket.getNmd();
		final int ticketEmd = ticket.getEmd();
		final String ticketHistory = ticket.getHistory();
		final String ticketAttachedFile = ticket.getAttachedfile();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (ticket != null) {
			this.jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(ticketInsertSQL, new String[] { "no" });
					ps.setString(1, ticketName);
					ps.setString(2, ticketUserName);
					ps.setString(3, ticketProjectName);
					ps.setString(4, ticketTitle);
					ps.setString(5, ticketSubTitle);
					ps.setString(6, ticketContent);
					ps.setInt(7, ticketNmd);
					ps.setInt(8, ticketEmd);
					ps.setString(9, ticketHistory);
					ps.setString(10, ticketAttachedFile);
					return ps;
				}
			}, keyHolder);
		}
		return keyHolder.getKey();
	}

	public void deleteAll() {
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement("delete from ticket");
			}
		});
	}

	private RowMapper<Ticket> userMapper = new RowMapper<Ticket>() {
		@Override
		public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ticket ticket = new Ticket();
			ticket.setNo(rs.getLong("no"));
			ticket.setTeamname(rs.getString("teamname"));
			ticket.setUsername(rs.getString("username"));
			ticket.setProjectname(rs.getString("projectname"));
			ticket.setTitle(rs.getString("title"));
			ticket.setSubtitle(rs.getString("subtitle"));
			ticket.setContent(rs.getString("content"));
			ticket.setNmd(rs.getInt("nmd"));
			ticket.setEmd(rs.getInt("emd"));
			ticket.setHistory(rs.getString("history"));
			ticket.setAttachedfile(rs.getString("attachedfile"));
			return ticket;
		}
	};

	public Ticket get(Number ticketNumber) {
		return jdbcTemplate.queryForObject("select * from ticket where no = ?", new Object[] { ticketNumber }, this.userMapper);
	}

	public List<Ticket> getAll() {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement("select * from ticket order by no");
			}
		}, this.userMapper);
	}

	public int getCount() {
		return jdbcTemplate.queryForObject("select count(*) from ticket", Integer.class);
	}

}
