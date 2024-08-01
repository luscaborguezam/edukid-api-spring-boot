package br.com.edukid.api.entities;

import java.io.Serializable;

import jakarta.persistence.*;

/**
 * CLASSE REPRESENTA A TABELA USUARIO
 * @Author LUCAS BORGUEZAM
 * @Sice 9 de jul. de 2024
 */
@Entity
@Table(name="user_pai")
public class UserFather implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user_pai")
	private Integer id;
	@Column(name = "nome", nullable = false, length = 255)
	private String firstName;
	@Column(name = "sobrenome", nullable = false, length = 255)
	private String lastName;
	@Column(name = "cpf", nullable = false, length = 11)
	private String cpf;
	@Column(name = "telefone", nullable = false, length = 13)
	private String phone;
	@Column(name = "email", nullable = false, length = 255)
	private String email;
	@Column(name = "passwd", nullable = false, length = 255)
	private String password;
	
	public UserFather() {}

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	};
	
	
	
	
}
