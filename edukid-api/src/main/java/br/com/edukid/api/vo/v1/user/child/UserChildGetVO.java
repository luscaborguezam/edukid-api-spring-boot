package br.com.edukid.api.vo.v1.user.child;

import java.util.List;

import br.com.edukid.api.vo.v1.configquiz.MateriaVO;
import br.com.edukid.api.vo.v1.configquiz.MateriasETemasVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * CLASSE REPRESENTA DADOS DO USUÁRIO FILHO
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */
public class UserChildGetVO {
	
	
	private String id;
	private String firstName;
	private String lastName;
	private String nickname;
	private String password;
	private String schoolYear;
	private String timeOfQuiz;
	private String notificationQuantity;
	private List<MateriaVO> configuration;
	private String fkUserPai;
	
	
	
	
	
	/*Getters and Setters*/
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
	public String getTimeOfQuiz() {
		return timeOfQuiz;
	}
	public void setTimeOfQuiz(String timeOfQuiz) {
		this.timeOfQuiz = timeOfQuiz;
	}
	public String getNotificationQuantity() {
		return notificationQuantity;
	}
	public void setNotificationQuantity(String notificationQuantity) {
		this.notificationQuantity = notificationQuantity;
	}
	public List<MateriaVO> getConfiguration() {
		return configuration;
	}
	public void setConfiguration(List<MateriaVO> configuration) {
		this.configuration = configuration;
	}
	public String getFkUserPai() {
		return fkUserPai;
	}
	public void setFkUserPai(String fkUserPai) {
		this.fkUserPai = fkUserPai;
	}

	
	
	
}
