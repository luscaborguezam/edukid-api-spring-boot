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
	@Pattern(regexp = "^[1-9]$", message = "A chave 'yearElementarySchool' deve ser uma string numérica com valor igual ou entre 1 e 9")
	private String yearElementarySchool;
	@NotEmpty
	@Pattern(regexp = "^[1-4]$", message = "A chave 'bimonth' deve ser uma string numérica com valor igual ou entre 1 e 4")
	private String bimonth;
	
	
	
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
	public String getYearElementarySchool() {
		return yearElementarySchool;
	}
	public void setYearElementarySchool(String yearElementarySchool) {
		this.yearElementarySchool = yearElementarySchool;
	}
	public String getBimonth() {
		return bimonth;
	}
	public void setBimonth(String bimonth) {
		this.bimonth = bimonth;
	}
	
	
}
