package br.com.edukid.api.entities;

import java.io.Serializable;

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
public class UserChild implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user_filho")
	private Integer id;
	@Column(name = "nome", nullable = false, length = 255)
	private String firstName;
	@Column(name = "sobrenome", nullable = false, length = 255)
	private String lastName;
	@Column(name = "apelido", nullable = false, length = 255)
	private String nickname;
	@Column(name = "passwd", nullable = false, length = 255)
	private String password;
	@Column(name = "id_user_pai", nullable = false)
	private Integer fkUserPai;
	
	
	/*GETEERS AND SETTERS*/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getFkUserPai() {
		return fkUserPai;
	}
	public void setFkUserPai(Integer fkUserPai) {
		this.fkUserPai = fkUserPai;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	

}
