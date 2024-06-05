package com.ssafy.dto.member;

public class SignUpDto {
	String name;
	String id;
	String password;
	String emailFront;
	String emailBack;
	String sido;
	String gugun;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailFront() {
		return emailFront;
	}
	public void setEmailFront(String emailFront) {
		this.emailFront = emailFront;
	}
	public String getEmailBack() {
		return emailBack;
	}
	public void setEmailBack(String emailBack) {
		this.emailBack = emailBack;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getGugun() {
		return gugun;
	}
	public void setGugun(String gugun) {
		this.gugun = gugun;
	}
	
}
