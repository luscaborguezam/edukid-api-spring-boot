package br.com.edukid.api.configurations.springsecurity.security.DTO;

public class Jwt {
	
	private String subject;
	private String userType;
	
	public Jwt() {}
	
	public Jwt(String subject, String userTyString) {
		this.subject = subject;
		this.userType = userTyString;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}
