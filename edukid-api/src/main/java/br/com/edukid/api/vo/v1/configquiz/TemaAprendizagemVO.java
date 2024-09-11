package br.com.edukid.api.vo.v1.configquiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class TemaAprendizagemVO {
	
	
	private String id;
	@NotEmpty
	@Pattern(regexp = "^-?\\d+$", message = "A chave 'idSubject' deve ser uma string com o valor de um inteiro válido")
	private String idSubject;
	@NotEmpty
	@Pattern(regexp = "^-?\\d+$", message = "A chave 'idSuperTheme' deve ser uma string com o valor de um inteiro válido")
	private String idSuperTheme;
	@NotBlank
	private String theme;
	@NotEmpty
	@Pattern(regexp = "^[1-9]$", message = "A chave 'yearHighScool' deve ser uma string numérica com valor igual ou entre 1 e 9")
	private String yearHighScool;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdSubject() {
		return idSubject;
	}
	public void setIdSubject(String idSubject) {
		this.idSubject = idSubject;
	}
	public String getIdSuperTheme() {
		return idSuperTheme;
	}
	public void setIdSuperTheme(String idSuperTheme) {
		this.idSuperTheme = idSuperTheme;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getYearHighScool() {
		return yearHighScool;
	}
	public void setYearHighScool(String yearHighScool) {
		this.yearHighScool = yearHighScool;
	}
	
	
}
