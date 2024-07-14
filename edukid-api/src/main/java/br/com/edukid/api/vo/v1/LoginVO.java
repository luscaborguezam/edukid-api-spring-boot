package br.com.edukid.api.vo.v1;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * CLASSE REPRESENTA OS DADOS PARA LOGIN
 * @Author LUCAS BORGUEZAM
 * @Sice 9 de jul. de 2024
 */


public class LoginVO {
	@Email
	private String email;
	@NotBlank
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
