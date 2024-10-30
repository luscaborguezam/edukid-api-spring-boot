package br.com.edukid.api.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * CLASSE REPRESENTA A TABELA USER
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */
@Entity
@Table(name="conf_tema")
public class ConfTema implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "id_user_filho")
	private Integer idUserChild;
	@Column(name="id_materia")
	private String idSubject;
	@Column(name = "id_tema")
	private Integer idTema;
	
	public Integer getIdUserChild() {
		return idUserChild;
	}
	public void setIdUserChild(Integer idUserChild) {
		this.idUserChild = idUserChild;
	}
	public String getIdSubject() {
		return idSubject;
	}
	public void setIdSubject(String idSubject) {
		this.idSubject = idSubject;
	}
	public Integer getIdTema() {
		return idTema;
	}
	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}
}
