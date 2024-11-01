package br.com.edukid.api.entities.ids;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class QuizPerguntaId implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "id_quiz", nullable = false)
	private Integer idQuiz;
	@Column(name = "id_pergunta", nullable = false)
	private Integer idQuestion;
	
	public QuizPerguntaId() {
		// TODO Auto-generated constructor stub
	}
	
	public QuizPerguntaId(Integer idQuiz, Integer idQuestion) {
		this.idQuiz = idQuiz;
		this.idQuestion = idQuestion;
	}

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
	
	
}
