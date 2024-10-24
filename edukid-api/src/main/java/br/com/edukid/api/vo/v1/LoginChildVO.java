package br.com.edukid.api.vo.v1;

import jakarta.validation.constraints.NotBlank;

/**
 * CLASSE REPRESENTA OS DADOS PARA LOGIN
 * @Author LUCAS BORGUEZAM
 * @Sice 9 de jul. de 2024
 */


public class LoginChildVO {
	@NotBlank
	private String nickName;
	@NotBlank
	private String password;
	
	public LoginChildVO() {}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
