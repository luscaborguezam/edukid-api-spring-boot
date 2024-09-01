package br.com.edukid.api.vo.v1.configquiz;

import java.util.List;

import br.com.edukid.api.entities.Pergunta;

public class QuizVO {
	
	private List<Pergunta> quiz;

	public List<Pergunta> getQuiz() {
		return quiz;
	}

	public void setQuiz(List<Pergunta> quiz) {
		this.quiz = quiz;
	}
	
	
}
