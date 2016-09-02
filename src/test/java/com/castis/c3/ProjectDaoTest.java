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
import com.castis.c3.dto.Project;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class ProjectDaoTest {
	@Autowired
	ApplicationContext context;

	Project project1;
	Project project2;
	Project project3;
	ProjectDao dao;

	@Before
	public void setUp() throws SQLException {
		System.out.println(context);
		dao = context.getBean("projectDao", ProjectDao.class);

		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);
		try {
			Date startDate1 = new SimpleDateFormat("yyyyMMdd").parse("20160601");
			Date endDate1 = new SimpleDateFormat("yyyyMMdd").parse("20160823");
			project1 = new Project("KT큐톤광고", "KT", "KT큐톤광고 프로젝트 테스트", startDate1, endDate1);
			Date startDate2 = new SimpleDateFormat("yyyyMMdd").parse("20150422");
			Date endDate2 = new SimpleDateFormat("yyyyMMdd").parse("20161231");
			project2 = new Project("CJ SDV 프로젝트", "CJ", "SDV 프로젝트 테스트", startDate2, endDate2);
			Date startDate3 = new SimpleDateFormat("yyyyMMdd").parse("20140101");
			Date endDate3 = new SimpleDateFormat("yyyyMMdd").parse("20161231");
			project3 = new Project("Viettel multiscreen", "Viettel", "viettel 멀티 스크린 프로젝트", startDate3, endDate3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addAndGet() throws SQLException {
		dao.add(project1);
		Assert.assertTrue(dao.getCount() == 1);

		dao.add(project2);
		Assert.assertTrue(dao.getCount() == 2);

		Project project1_rtn = dao.get(project1.getProjectname());
		checkSameProject(project1, project1_rtn);

		Project project2_rtn = dao.get(project2.getProjectname());
		checkSameProject(project2, project2_rtn);
	}

	@Test
	public void count() throws SQLException {
		dao.add(project1);
		Assert.assertTrue(dao.getCount() == 1);

		dao.add(project2);
		Assert.assertTrue(dao.getCount() == 2);

		dao.add(project3);
		Assert.assertTrue(dao.getCount() == 3);

		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getProjectFailure() throws SQLException {
		dao.deleteAll();
		Assert.assertTrue(dao.getCount() == 0);

		dao.get("unknown_id");
	}

	@Test
	public void getAll() throws SQLException {
		dao.deleteAll();

		dao.add(project1);
		List<Project> projects1 = dao.getAll();
		Assert.assertTrue(projects1.size() == 1);
		checkSameProject(project1, projects1.get(0));

		dao.add(project2);
		List<Project> projects2 = dao.getAll();
		Assert.assertTrue(projects2.size() == 2);
		checkSameProject(project1, projects2.get(1));
		checkSameProject(project2, projects2.get(0));

		dao.add(project3);
		List<Project> projects3 = dao.getAll();
		Assert.assertTrue(projects3.size() == 3);
		checkSameProject(project3, projects3.get(2));
		checkSameProject(project1, projects3.get(1));
		checkSameProject(project2, projects3.get(0));
	}

	private void checkSameProject(Project project1, Project project2) {
		Assert.assertTrue(project1.getProjectname().equals(project2.getProjectname()));
		Assert.assertTrue(project1.getSite().equals(project2.getSite()));
		Assert.assertTrue(project1.getDescription().equals(project2.getDescription()));
		Assert.assertTrue(project1.getStartdate().equals(project2.getStartdate()));
		Assert.assertTrue(project1.getEnddate().equals(project2.getEnddate()));
	}
}
