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

import com.castis.c3.dao.UserDao;
import com.castis.c3.dao.VacationDao;
import com.castis.c3.dto.User;
import com.castis.c3.dto.Vacation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class VacationDaoTest {
	@Autowired
	ApplicationContext context;

	Vacation vacation1;
	Vacation vacation2;
	Vacation vacation3;
	VacationDao vacationDao;

	@Before
	public void setUp() throws SQLException {
		System.out.println(context);
		vacationDao = context.getBean("vacationDao", VacationDao.class);

		vacationDao.deleteAll();
		Assert.assertTrue(vacationDao.getCount() == 0);
		
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

		try {
			Date resetDate1 = new SimpleDateFormat("yyyyMMdd").parse("20101010");
			Date resetDate2 = new SimpleDateFormat("yyyyMMdd").parse("20140501");
			Date resetDate3 = new SimpleDateFormat("yyyyMMdd").parse("20140615");

			vacation1 = new Vacation("김경송", 15, 3, 0, resetDate1);
			vacation2 = new Vacation("배정환", 17, 0, 0, resetDate2);
			vacation3 = new Vacation("하태진", 17, 0, 0, resetDate3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addAndGet() throws SQLException {
		vacationDao.add(vacation1);
		Assert.assertTrue(vacationDao.getCount() == 1);

		vacationDao.add(vacation2);
		Assert.assertTrue(vacationDao.getCount() == 2);

		Vacation vacation1_rtn = vacationDao.get(vacation1.getUsername());
		checkSameVacation(vacation1, vacation1_rtn);

		Vacation vacation2_rtn = vacationDao.get(vacation2.getUsername());
		checkSameVacation(vacation2, vacation2_rtn);
	}

	@Test
	public void count() throws SQLException {
		vacationDao.add(vacation1);
		Assert.assertTrue(vacationDao.getCount() == 1);

		vacationDao.add(vacation2);
		Assert.assertTrue(vacationDao.getCount() == 2);

		vacationDao.add(vacation3);
		Assert.assertTrue(vacationDao.getCount() == 3);

		vacationDao.deleteAll();
		Assert.assertTrue(vacationDao.getCount() == 0);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getVacationFailure() throws SQLException {
		vacationDao.deleteAll();
		Assert.assertTrue(vacationDao.getCount() == 0);

		vacationDao.get("unknown_id");
	}

	@Test
	public void getAll() throws SQLException {
		vacationDao.deleteAll();

		vacationDao.add(vacation1);
		List<Vacation> users1 = vacationDao.getAll();
		Assert.assertTrue(users1.size() == 1);
		checkSameVacation(vacation1, users1.get(0));

		vacationDao.add(vacation2);
		List<Vacation> users2 = vacationDao.getAll();
		Assert.assertTrue(users2.size() == 2);
		checkSameVacation(vacation1, users2.get(0));
		checkSameVacation(vacation2, users2.get(1));

		vacationDao.add(vacation3);
		List<Vacation> users3 = vacationDao.getAll();
		Assert.assertTrue(users3.size() == 3);
		checkSameVacation(vacation3, users3.get(2));
		checkSameVacation(vacation1, users3.get(0));
		checkSameVacation(vacation2, users3.get(1));
	}

	private void checkSameVacation(Vacation vacation1, Vacation vacation2) {
		Assert.assertTrue(vacation1.getUsername().equals(vacation2.getUsername()));
		Assert.assertTrue(vacation1.getVacationyear() == vacation2.getVacationyear());
		Assert.assertTrue(vacation1.getVacationreward() == vacation2.getVacationreward());
		Assert.assertTrue(vacation1.getVacationspeacial() == vacation2.getVacationspeacial());
		Assert.assertTrue(vacation1.getResetdate().equals(vacation2.getResetdate()));
	}
}
