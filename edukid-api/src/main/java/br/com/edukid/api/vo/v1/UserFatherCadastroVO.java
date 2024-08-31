package br.com.edukid.api.vo.v1;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * CLASSE REPRESENTA OS DADOS DO USUÁRIO PAI PARA CADASTRO (RECEBE O CAMPO SENHA)
 *  
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */

public class UserFatherCadastroVO {
	
	private String id;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@CPF
	private String cpf;
	@NotBlank
	private String phone;
	@Email
	private String email;
	@NotBlank
	private String password;
	
	private String status;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}