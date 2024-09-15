package br.com.edukid.api.entities;

import java.io.Serializable;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * CLASSE REPRESENTA A TABELA USER
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */
@Entity
@Table(name="user_filho")
public class Configuration implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user_filho")
	private Integer id;
	@Column(name="configuracao", columnDefinition = "json")
	private String configuration;
	@Column(name = "id_user_pai", nullable = false)
	private Integer fkUserPai;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	public Integer getFkUserPai() {
		return fkUserPai;
	}
	public void setFkUserPai(Integer fkUserPai) {
		this.fkUserPai = fkUserPai;
	}
	
	
	

	
	
	
	

}
