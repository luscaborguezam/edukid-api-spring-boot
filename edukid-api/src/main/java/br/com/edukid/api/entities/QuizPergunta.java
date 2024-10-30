package br.com.edukid.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="quiz_perguntas")
public class QuizPergunta {
	
	private static final long serialVersionUID = 1L;
	

	@Column(name = "id_quiz")
	private Integer idQuiz;
	@Column(name = "id_pergunta", nullable = false)
	private Integer idQuestion;
	@Column(name = "resposta_selecionada")
	private String selectedAnswer;
	
	
	public Integer getIdQuiz() {
		return idQuiz;
	}
	public void setIdQuiz(Integer idQuiz) {
		this.idQuiz = idQuiz;
	}
	public Integer getIdQuestion() {
		return idQuestion;
	}
	public void setIdQuestion(Integer idQuestion) {
		this.idQuestion = idQuestion;
	}
	public String getSelectedAnswer() {
		return selectedAnswer;
	}
	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	
	
}