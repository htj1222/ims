package com.castis.c3.dto;

public class Ticket {
	private Number no;
	private String teamname;
	private String username;
	private String projectname;
	private String title;
	private String subtitle;
	private String content;
	private int nmd;
	private int emd;
	private String history;
	private String attachedfile;

	public Ticket() {
	}

	public Ticket(String teamname, String username, String projectname, String title, String subtitle, String content, int nmd, int emd, String history,
			String attachedfile) {
		this.teamname = teamname;
		this.username = username;
		this.projectname = projectname;
		this.title = title;
		this.subtitle = subtitle;
		this.content = content;
		this.nmd = nmd;
		this.emd = emd;
		this.history = history;
		this.attachedfile = attachedfile;
	}

	public Number getNo() {
		return no;
	}

	public void setNo(Number no) {
		this.no = no;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getNmd() {
		return nmd;
	}

	public void setNmd(int nmd) {
		this.nmd = nmd;
	}

	public int getEmd() {
		return emd;
	}

	public void setEmd(int emd) {
		this.emd = emd;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getAttachedfile() {
		return attachedfile;
	}

	public void setAttachedfile(String attachedfile) {
		this.attachedfile = attachedfile;
	}
}
