package br.com.edukid.api.vo.v1.quiz;

import br.com.edukid.api.entities.Quiz;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe VO criada para atender somente os campos necessários do quizVO para a rotina de histórico por data
 * http://localhost:8080/edukid/user-father/quiz-hystory/1-11-2024
 * http://localhost:8080/edukid/conf-quiz/quiz/1/date:02-11-2024
 */
public class QuizVOForHystory {

	@NotBlank
	private String id;
	private String startDate; // yyyy-MM-dd'T'HH:mm:ss exemplo: 2024-09-11T15:30:45
	@NotBlank
	private String  endDate; // yyyy-MM-dd'T'HH:mm:ss exemplo: 2024-09-11T15:30:45
	@NotBlank
	private String isFinalized;
	//@NotBlank
	//private String idUserChild;

	//private FieldQuizVO quiz;
	
	public QuizVOForHystory() {}
	
	public QuizVOForHystory(Quiz quizEntity) {
		id = quizEntity.getId().toString();
		startDate = quizEntity.getStartDate().toString();
		endDate = (quizEntity.getEndDate()!=null)?quizEntity.getEndDate().toString():null;
		isFinalized = quizEntity.getIsfinalized().toString();
		//idUserChild = quizEntity.getIdUserChild().toString();		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getIsFinalized() {
		return isFinalized;
	}

	public void setIsFinalized(String isFinalized) {
		this.isFinalized = isFinalized;
	}
	
	
}
