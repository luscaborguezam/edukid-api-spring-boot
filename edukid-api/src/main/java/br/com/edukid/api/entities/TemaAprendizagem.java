package br.com.edukid.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tema_aprendizagem")
public class TemaAprendizagem {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tema")
	private Integer id;
	@Column(name = "id_materia", nullable = false)
	private Integer idSubject;
	@Column(name = "id_super_tema")
	private Integer idSuperTheme;
	@Column(name = "tema", nullable = false, length = 255)
	private String theme;
	@Column(name = "ano_ensino_fundamental", length = 1)
	private Integer yearElementarySchool;
	@Column(name = "bimestre", length = 1)
	private Integer bimonthly;
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdSubject() {
		return idSubject;
	}
	public void setIdSubject(Integer idSubject) {
		this.idSubject = idSubject;
	}
	public Integer getIdSuperTheme() {
		return idSuperTheme;
	}
	public void setIdSuperTheme(Integer idSuperTheme) {
		this.idSuperTheme = idSuperTheme;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public Integer getYearElementarySchool() {
		return yearElementarySchool;
	}
	public void setYearElementarySchool(Integer yearElementarySchool) {
		this.yearElementarySchool = yearElementarySchool;
	}
	public Integer getBimonthly() {
		return bimonthly;
	}
	public void setBimonthly(Integer bimonthly) {
		this.bimonthly = bimonthly;
	}
	
	
	
}
