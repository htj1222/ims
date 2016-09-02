package com.castis.c3;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.castis.c3.dao.ProjectDao;
import com.castis.c3.dao.TeamDao;
import com.castis.c3.dao.TicketDao;
import com.castis.c3.dao.UserDao;
import com.castis.c3.dto.Project;
import com.castis.c3.dto.Team;
import com.castis.c3.dto.Ticket;
import com.castis.c3.dto.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TicketDaoTest {
	@Autowired
	ApplicationContext context;

	Ticket ticket1;
	Ticket ticket2;
	Ticket ticket3;
	TicketDao dao;

	@Before
	public void setUp() throws SQLException {
		System.out.println(context);
		dao = context.getBean("ticketDao", TicketDao.class);

		// team dummy
		TeamDao teamDao = context.getBean("teamDao", TeamDao.class);
		teamDao.deleteAll();
		Assert.assertTrue(teamDao.getCount() == 0);
		Team team1 = new Team("C1", "팀장이 누군지 모르겠음");
		Team team2 = new Team("C2", "김대호팀장님이 이끄는 CDN");
		Team team3 = new Team("C3", "우리팀");
		teamDao.add(team1);
		teamDao.add(team2);
		teamDao.add(team3);
		
		// user dummy
		UserDao userDao = context.getBean("userDao", UserDao.class);
		userDao.deleteAll();
		Assert.assertTrue(userDao.getCount() == 0);
		User user1 = new User("김경송", "kskimpwd", "c3", "팀장", "010-7164-9777", "kskim@castis.com");
		User user2 = new User("배정환", "tjhapwd", "c3", "팀원", "010-1111-2222", "tjha@castis.com");
		User user3 = new User("하태진", "jhbaepwd", "c3", "팀원", "010-2222-3333", "jhbae@castis.com");
		userDao.add(user1);
		userDao.add(user2);
		userDao.add(user3);
		
		// project dummy
		ProjectDao projectDao = context.getBean("projectDao", ProjectDao.class);
		projectDao.deleteAll();
		Assert.assertTrue(projectDao.getCount() == 0);
		try {
			Date startDate1 = new SimpleDateFormat("yyyyMMdd").parse("20160601");
			Date endDate1 = new SimpleDateFormat("yyyyMMdd").parse("20160823");
			Project project1 = new Project("KT 큐톤광고", "KT", "KT큐톤광고 프로젝트 테스트", startDate1, endDate1);
			Date startDate2 = new SimpleDateFormat("yyyyMMdd").parse("20150422");
			Date endDate2 = new SimpleDateFormat("yyyyMMdd").parse("20161231");
			Project project2 = new Project("CJ SDV", "CJ", "SDV 프로젝트 테스트", startDate2, endDate2);
			Date startDate3 = new SimpleDateFormat("yyyyMMdd").parse("20140101");
			Date endDate3 = new SimpleDateFormat("yyyyMMdd").parse("20161231");
			Project project3 = new Project("Viettel MultiScreen", "Viettel", "viettel 멀티 스크린 프로젝트", startDate3, endDate3);
			
			projectDao.add(project1);
			projectDao.add(project2);
			projectDao.add(project3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);
		ticket1 = new Ticket("C1", "김경송", "KT 큐톤광고", "큐톤광고title", " 큐톤광고SubTitle", "큐톤광고content", 1, 1, "{history}", "none");
		ticket2 = new Ticket("C2", "배정환", "CJ SDV", "CJ SDVtitle", " CJ SDVSubTitle", "CJ SDVcontent", 100, 121, "{history}", "none");
		ticket3 = new Ticket("C3", "하태진", "Viettel MultiScreen", "Viettel MultiScreen title", "Viettel MultiScreen SubTitle", "Viettel MultiScreen content", 12,
				12, "{history}", "none");
	}

	@Test
	public void addAndGet() throws SQLException {
		Number autoGenNum1 = dao.add(ticket1);
		ticket1.setNo(autoGenNum1);
		Assert.assertTrue(dao.getCount() == 1);

		Number autoGenNum2 = dao.add(ticket2);
		ticket2.setNo(autoGenNum2);
		Assert.assertTrue(dao.getCount() == 2);

		Ticket ticket1_rtn = dao.get(ticket1.getNo());
		checkSameTicket(ticket1, ticket1_rtn);

		Ticket ticket2_rtn = dao.get(ticket2.getNo());
		checkSameTicket(ticket2, ticket2_rtn);
	}

	@Test
	public void count() throws SQLException {
		dao.add(ticket1);
		Assert.assertTrue(dao.getCount() == 1);

		dao.add(ticket2);
		Assert.assertTrue(dao.getCount() == 2);

		dao.add(ticket3);
		Assert.assertTrue(dao.getCount() == 3);

		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getTicketFailure() throws SQLException {
		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);

		dao.get(-1);
	}

	@Test
	public void getAll() throws SQLException {
		dao.deleteAll();

		Number autoGenNum1 = dao.add(ticket1);
		ticket1.setNo(autoGenNum1);
		List<Ticket> tickets1 = dao.getAll();
		Assert.assertTrue(tickets1.size() == 1);
		checkSameTicket(ticket1, tickets1.get(0));

		Number autoGenNum2 = dao.add(ticket2);
		ticket2.setNo(autoGenNum2);
		List<Ticket> tickets2 = dao.getAll();
		Assert.assertTrue(tickets2.size() == 2);
		checkSameTicket(ticket1, tickets2.get(0));
		checkSameTicket(ticket2, tickets2.get(1));

		Number autoGenNum3 = dao.add(ticket3);
		ticket3.setNo(autoGenNum3);
		List<Ticket> tickets3 = dao.getAll();
		Assert.assertTrue(tickets3.size() == 3);
		checkSameTicket(ticket1, tickets3.get(0));
		checkSameTicket(ticket2, tickets3.get(1));
		checkSameTicket(ticket3, tickets3.get(2));
	}

	private void checkSameTicket(Ticket ticket1, Ticket ticket2) {
		Assert.assertTrue(ticket1.getNo().equals(ticket2.getNo()));
		Assert.assertTrue(ticket1.getTeamname().equals(ticket2.getTeamname()));
		Assert.assertTrue(ticket1.getUsername().equals(ticket2.getUsername()));
		Assert.assertTrue(ticket1.getProjectname().equals(ticket2.getProjectname()));
		Assert.assertTrue(ticket1.getTitle().equals(ticket2.getTitle()));
		Assert.assertTrue(ticket1.getSubtitle().equals(ticket2.getSubtitle()));
		Assert.assertTrue(ticket1.getContent().equals(ticket2.getContent()));
		Assert.assertTrue(ticket1.getNmd() == ticket2.getNmd());
		Assert.assertTrue(ticket1.getEmd() == ticket2.getEmd());
		Assert.assertTrue(ticket1.getHistory().equals(ticket2.getHistory()));
		Assert.assertTrue(ticket1.getAttachedfile().equals(ticket2.getAttachedfile()));
	}
}
