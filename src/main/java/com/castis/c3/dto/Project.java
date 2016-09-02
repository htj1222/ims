package com.castis.c3.dto;

import java.util.Date;

public class Project {
	private String projectname;
	private String site;
	private String description;
	private Date startdate;
	private Date enddate;

	public Project() {
	}

	public Project(String projectname, String site, String description, Date startdate, Date enddate) {
		this.projectname = projectname;
		this.site = site;
		this.description = description;
		this.startdate = startdate;
		this.enddate = enddate;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

}
