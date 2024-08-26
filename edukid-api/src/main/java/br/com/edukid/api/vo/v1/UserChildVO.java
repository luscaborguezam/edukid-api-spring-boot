package br.com.edukid.api.vo.v1;

import jakarta.validation.constraints.NotBlank;

/**
 * CLASSE REPRESENTA DADOS DO USUÁRIO FILHO
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */
public class UserChildVO {
	
	
	private String id;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String nickname;
	@NotBlank
	private String password;
	@NotBlank
	private String fkUserPai;
	
	
	
	/*Getters and Setters*/
	public String getFirstName() {
		return firstName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFkUserPai() {
		return fkUserPai;
	}
	public void setFkUserPai(String fkUserPai) {
		this.fkUserPai = fkUserPai;
	}
	
	
	
}
