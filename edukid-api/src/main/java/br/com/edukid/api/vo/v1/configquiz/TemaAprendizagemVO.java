package br.com.edukid.api.vo.v1.configquiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class TemaAprendizagemVO {
	
	
	private String id;
	@NotEmpty
	@Pattern(regexp = "^-?\\d+$", message = "Key 'schoolYear' must be a string with the value of a valid integer")
	private String idSubject;
	@NotEmpty
	@Pattern(regexp = "^-?\\d+$", message = "Key 'schoolYear' must be a string with the value of a valid integer")
	private String idSuperTheme;
	@NotBlank
	private String theme;
	@NotEmpty
	@Pattern(regexp = "^[1-9]$", message = "Key 'yearHighScool' must be a numeric string with the value equals or between 1 and 9")
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
