package br.com.edukid.api.vo.v1.configquiz;

import java.util.List;

/**
 * CLASSE REPRESENTA O JSON SALVO NO CAMPO INFO_PERGUNTA DA TABELA PERGUNTAS NA BASE DE DADOS 
 * @Author LUCAS BORGUEZAM
 * @Sice 1 de set. de 2024
 */
public class InfoPergunta {

	private String question;
	private List<String> options;
	private String correctAnswer;
	private String selectedAnswer;
	
	public InfoPergunta() {
		// TODO Auto-generated constructor stub
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public String getSelectedAnswer() {
		return selectedAnswer;
	}
	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

}
