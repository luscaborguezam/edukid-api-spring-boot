package br.com.edukid.api.entities;

import br.com.edukid.api.entities.ids.QuizPerguntaId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="quiz_perguntas")
public class QuizPergunta {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private QuizPerguntaId id; 
	
	@Column(name = "resposta_selecionada")
	private String selectedAnswer;
	
	
	public QuizPergunta() {
		id = new QuizPerguntaId();
	}
	
	public QuizPergunta(Integer idQuiz, Integer idQuestion) {
		id = new QuizPerguntaId(idQuiz, idQuestion);
	}
	
	public QuizPerguntaId getId() {
		return id;
	}
	public void setId(QuizPerguntaId id) {
		this.id = id;
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