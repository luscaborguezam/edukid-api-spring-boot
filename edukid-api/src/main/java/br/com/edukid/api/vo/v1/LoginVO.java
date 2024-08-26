package br.com.edukid.api.vo.v1;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * CLASSE REPRESENTA OS DADOS PARA LOGIN
 * @Author LUCAS BORGUEZAM
 * @Sice 9 de jul. de 2024
 */


public class LoginVO {
	@Email @NotBlank
	private String emailOrNickName;
	@NotBlank
	private String password;
	private String codVerificacao;
	
	public String getEmailOrNickName() {
		return emailOrNickName;
	}
	public void setEmailOrNickName(String emailOrNickName) {
		this.emailOrNickName = emailOrNickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCodVerificacao() {
		return codVerificacao;
	}
	public void setCodVerificacao(String codVerificacao) {
		this.codVerificacao = codVerificacao;
	}
	
	
	
}
