package br.com.edukid.api.vo.v1.user.child;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * CLASSE REPRESENTA DADOS DO USUÁRIO FILHO
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */
public class UserChildUpdateVO {
	@NotBlank
	@Pattern(regexp = "^-?\\d+$", message = "Key 'id' must be a string with the value of a valid integer")
	private String id;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String nickname;
	@NotEmpty
	@Pattern(regexp = "^-?\\d+$", message = "Key 'schoolYear' must be a string with the value of a valid integer")
	private String schoolYear;//Numerico
	@Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$", message = "Invalid time format for key 'timeOfQuiz' . Expected format is HH:mm:ss")
	private String timeOfQuiz; //formato aceitado HH:mm:ss exemplo 14:30:00
	@NotEmpty
	@Pattern(regexp = "^-?\\d+$", message = "Key 'timeOfQuiz' must be a string with the value of a valid integer")
	private String notificationQuantity;//Numerico	
	@NotBlank
	@Pattern(regexp = "^-?\\d+$", message = "Key 'fkUserPai' must be a string with the value of a valid integer")
	private String fkUserPai;//Numerico
	private String token;
	
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
	public String getFkUserPai() {
		return fkUserPai;
	}
	public void setFkUserPai(String fkUserPai) {
		this.fkUserPai = fkUserPai;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	
	
	
}
