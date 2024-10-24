package br.com.edukid.api.vo.v1.user.child;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class TrocarSenhaUserChild {
	@NotBlank
	@Pattern(regexp = "^-?\\d+$", message = "Key 'idUSerChild' must be a string with the value of a valid integer")
	private String idUSerChild;
	@NotBlank
	private String password;
	
	public TrocarSenhaUserChild() {
		// TODO Auto-generated constructor stub
	}
	
	public String getIdUSerChild() {
		return idUSerChild;
	}
	public void setIdUSerChild(String idUSerChild) {
		this.idUSerChild = idUSerChild;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
