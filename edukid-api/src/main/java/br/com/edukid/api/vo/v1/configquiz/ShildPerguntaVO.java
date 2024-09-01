package br.com.edukid.api.vo.v1.configquiz;

import java.util.List;

public class ShildPerguntaVO {

	private String question;
	private List<String> options;
	private String correct_answer;
	
	
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
	public String getCorrect_answer() {
		return correct_answer;
	}
	public void setCorrect_answer(String correct_answer) {
		this.correct_answer = correct_answer;
	}

	
	
}
