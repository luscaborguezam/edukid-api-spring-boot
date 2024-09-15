package br.com.edukid.api.vo.v1.quiz;

import br.com.edukid.api.entities.Quiz;
import jakarta.validation.constraints.NotBlank;

/**
 * REPRESENTA O VALOR DE UM REGISTRO DA TABELA QUIZ 
 * @Author LUCAS BORGUEZAM
 * @Sice 14 de set. de 2024
 */
public class QuizVO {

	@NotBlank
	private String id;
	private String startDate; // yyyy-MM-dd'T'HH:mm:ss exemplo: 2024-09-11T15:30:45
	@NotBlank
	private String  endDate; // yyyy-MM-dd'T'HH:mm:ss exemplo: 2024-09-11T15:30:45
	@NotBlank
	private String isFinalized;
	@NotBlank
	private String idUserChild;

	private FieldQuizVO quiz;
	
	public QuizVO() {}
	
	public QuizVO(Quiz quizEntity, FieldQuizVO quiz) {
		id = quizEntity.getId().toString();
		startDate = quizEntity.getStartDate().toString();
		endDate = (quizEntity.getEndDate()!=null)?quizEntity.getEndDate().toString():null;
		isFinalized = quizEntity.getIsfinalized().toString();
		idUserChild = quizEntity.getIdUserChild().toString();
		this.quiz = quiz;
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FieldQuizVO getQuiz() {
		return quiz;
	}
	public void setQuiz(FieldQuizVO quiz) {
		this.quiz = quiz;
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
	public String getIdUserChild() {
		return idUserChild;
	}
	public void setIdUserChild(String idUserChild) {
		this.idUserChild = idUserChild;
	}
	
	
}
