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

import com.castis.c3.dao.UserDao;
import com.castis.c3.dto.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoTest {
	@Autowired
	ApplicationContext context;

	User user1;
	User user2;
	User user3;
	UserDao dao;

	@Before
	public void setUp() throws SQLException {
		System.out.println(context);
		dao = context.getBean("userDao", UserDao.class);

		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);
		user1 = new User("kskim", "kskim", "kskimpwd", "c3", "팀장", "010-7164-9777", "kskim@castis.com");
		user2 = new User("tjha", "tjha", "tjhapwd", "c3", "팀원", "010-1111-2222", "tjha@castis.com");
		user3 = new User("jhbae", "jhbae", "jhbaepwd", "c3", "팀원", "010-2222-3333", "jhbae@castis.com");
	}

	@Test
	public void addAndGet() throws SQLException {
		dao.add(user1);
		Assert.assertTrue(dao.getCount() == 1);

		dao.add(user2);
		Assert.assertTrue(dao.getCount() == 2);

		User user1_rtn = dao.get(user1.getUsername());
		checkSameUser(user1, user1_rtn);

		User user2_rtn = dao.get(user2.getUsername());
		checkSameUser(user2, user2_rtn);
	}

	@Test
	public void count() throws SQLException {
		dao.add(user1);
		Assert.assertTrue(dao.getCount() == 1);

		dao.add(user2);
		Assert.assertTrue(dao.getCount() == 2);

		dao.add(user3);
		Assert.assertTrue(dao.getCount() == 3);

		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);

		dao.get("unknown_id");
	}

	@Test
	public void getAll() throws SQLException {
		dao.deleteAll();

		dao.add(user1);
		List<User> users1 = dao.getAll();
		Assert.assertTrue(users1.size() == 1);
		checkSameUser(user1, users1.get(0));

		dao.add(user2);
		List<User> users2 = dao.getAll();
		Assert.assertTrue(users2.size() == 2);
		checkSameUser(user1, users2.get(0));
		checkSameUser(user2, users2.get(1));

		dao.add(user3);
		List<User> users3 = dao.getAll();
		Assert.assertTrue(users3.size() == 3);
		checkSameUser(user3, users3.get(0));
		checkSameUser(user1, users3.get(1));
		checkSameUser(user2, users3.get(2));
	}

	private void checkSameUser(User user1, User user2) {
		Assert.assertTrue(user1.getUsername().equals(user2.getUsername()));
		Assert.assertTrue(user1.getPassword().equals(user2.getPassword()));
		Assert.assertTrue(user1.getTeamname().equals(user2.getTeamname()));
		Assert.assertTrue(user1.getPosition().equals(user2.getPosition()));
		Assert.assertTrue(user1.getCellphone().equals(user2.getCellphone()));
		Assert.assertTrue(user1.getEmail().equals(user2.getEmail()));
	}
}
