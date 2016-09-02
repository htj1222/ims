package com.castis.c3.dto;

public class User {
	private String username;
	private String password;
	private String teamname;
	private String position;
	private String cellphone;
	private String email;

	public User() {
	}
	
	public User(String username, String password, String teamname, String position, String cellphone, String email) {
		this.username = username;
		this.password = password;
		this.teamname = teamname;
		this.position = position;
		this.cellphone = cellphone;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
