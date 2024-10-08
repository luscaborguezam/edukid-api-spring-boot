package br.com.edukid.api.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.edukid.api.configurations.springsecurity.security.UsersRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * CLASSE REPRESENTA A TABELA USUARIO
 * @Author LUCAS BORGUEZAM
 * @Sice 9 de jul. de 2024
 */
@Entity
@Table(name="user_pai")
public class UserFather implements Serializable, UserDetails{
	
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
	@Column(name = "status_", nullable = false)
	private String status;
	@Column(name="cod_mudar_senha", nullable = false, length = 8)
	private String codMudarSenha;
	@Transient
	private UsersRole role = UsersRole.FATHER;
	
	
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

//	public String getPasswordAtt() {
//		return password;
//	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCodMudarSenha() {
		return codMudarSenha;
	}
	
	public void setCodMudarSenha(String codMudarSenha) {
		this.codMudarSenha = codMudarSenha;
	}

	public UsersRole getRole() {
		return role;
	}	
	

	/**
	 * CONSULTA A ENTIDADE PARA VERIFICAR DEFINIR QUAL ROLES DO SECURITY ESTÁ RELACIONADAS AO USUÁRIO
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_FATHER"), new SimpleGrantedAuthority("ROLE_USER"));
			
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	
}
