package com.castis.c3;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.castis.c3.dao.TeamDao;
import com.castis.c3.dto.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TeamDaoTest {
	@Autowired
	ApplicationContext context;

	Team team1;
	Team team2;
	Team team3;
	TeamDao dao;

	@Before
	public void setUp() throws SQLException {
		System.out.println(context);
		dao = context.getBean("teamDao", TeamDao.class);

		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);
		team1 = new Team("C1", "팀장이 누군지 모르겠음");
		team2 = new Team("C2", "김대호팀장님이 이끄는 CDN");
		team3 = new Team("C3", "우리팀");
	}

	@Test
	public void addAndGet() throws SQLException {
		dao.add(team1);
		Assert.assertTrue(dao.getCount() == 1);

		dao.add(team2);
		Assert.assertTrue(dao.getCount() == 2);

		Team team1_rtn = dao.get(team1.getTeamname());
		checkSameTeam(team1, team1_rtn);

		Team team2_rtn = dao.get(team2.getTeamname());
		checkSameTeam(team2, team2_rtn);
	}

	@Test
	public void count() throws SQLException {
		dao.add(team1);
		Assert.assertTrue(dao.getCount() == 1);

		dao.add(team2);
		Assert.assertTrue(dao.getCount() == 2);

		dao.add(team3);
		Assert.assertTrue(dao.getCount() == 3);

		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getTeamFailure() throws SQLException {
		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);

		dao.get("unknown_id");
	}

	@Test
	public void getAll() throws SQLException {
		dao.deleteAll();

		dao.add(team1);
		List<Team> projects1 = dao.getAll();
		Assert.assertTrue(projects1.size() == 1);
		checkSameTeam(team1, projects1.get(0));

		dao.add(team2);
		List<Team> projects2 = dao.getAll();
		Assert.assertTrue(projects2.size() == 2);
		checkSameTeam(team1, projects2.get(0));
		checkSameTeam(team2, projects2.get(1));

		dao.add(team3);
		List<Team> projects3 = dao.getAll();
		Assert.assertTrue(projects3.size() == 3);
		checkSameTeam(team3, projects3.get(2));
		checkSameTeam(team1, projects3.get(0));
		checkSameTeam(team2, projects3.get(1));
	}

	private void checkSameTeam(Team team1, Team team2) {
		Assert.assertTrue(team1.getTeamname().equals(team2.getTeamname()));
		Assert.assertTrue(team1.getTeamdescription().equals(team2.getTeamdescription()));
	}
}
