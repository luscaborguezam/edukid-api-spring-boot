package br.com.edukid.api.entities;

import java.io.Serializable;
import java.sql.Time;
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

/**
 * CLASSE REPRESENTA A TABELA USER
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */
@Entity
@Table(name="user_filho")
public class UserChild implements Serializable, UserDetails{
	
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
	@Column(name="ano_escolar")
	private Integer schoolYear;
	@Column(name="horario_quiz")
	private Time timeOfQuiz; //formato aceitado HH:mm:ss exemplo 14:30:00
	@Column(name="qtd_notificacao")
	private Integer notificationQuantity;
	@Column(name = "id_user_pai", nullable = false)
	private Integer fkUserPai;
	
	private UsersRole role = UsersRole.CHILD;
	
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
//	public String getPassword() {
//		return password;
//	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}
	public Time getTimeOfQuiz() {
		return timeOfQuiz;
	}
	public void setTimeOfQuiz(Time timeOfQuiz) {
		this.timeOfQuiz = timeOfQuiz;
	}
	public Integer getNotificationQuantity() {
		return notificationQuantity;
	}
	public void setNotificationQuantity(Integer notificationQuantity) {
		this.notificationQuantity = notificationQuantity;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getFkUserPai() {
		return fkUserPai;
	}
	public void setFkUserPai(Integer fkUserPai) {
		this.fkUserPai = fkUserPai;
	}
	public UsersRole getRole() {
		return role;
	}
	
	/**
	 * CONSULTA A ENTIDADE PARA VERIFICAR DEFINIR QUAL ROLES DO SECURITY ESTÁ RELACIONADAS AO USUÁRIO
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_CHILD"));
			
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return nickname;
	}

	

	
	
	
	

}
