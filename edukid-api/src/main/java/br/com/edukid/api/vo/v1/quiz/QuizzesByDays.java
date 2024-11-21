package br.com.edukid.api.vo.v1.quiz;

import java.util.ArrayList;
import java.util.List;

import br.com.edukid.api.entities.Quiz;

/**
 * Classe criada para retorno de histórico de quizzes
 *  
 * @Author LUCAS BORGUEZAM
 * @Sice 19 de out. de 2024
 */
public class QuizzesByDays {

	
	private String id;
	private String nome;
	private List<QuizVOForHystory> quizzes;
	
	public QuizzesByDays() {
		quizzes = new ArrayList<>();
	}
	
	public void addQuiz(QuizVOForHystory quizVO) {
		quizzes.add(quizVO);
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<QuizVOForHystory> getQuizzes() {
		return quizzes;
	}
	public void setQuizzes(List<QuizVOForHystory> quizzes) {
		this.quizzes = quizzes;
	}
	
	
}
