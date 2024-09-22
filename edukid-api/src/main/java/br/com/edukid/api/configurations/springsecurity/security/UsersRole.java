package br.com.edukid.api.configurations.springsecurity.security;

public enum UsersRole {
	ADMIN("admin"),
	FATHER("father"),
	CHILD("child");
	
	private String role;
	
	private UsersRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
	

}
