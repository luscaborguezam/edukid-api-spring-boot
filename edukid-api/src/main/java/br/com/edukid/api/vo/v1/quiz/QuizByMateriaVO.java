package br.com.edukid.api.vo.v1.quiz;

import java.util.List;

import br.com.edukid.api.entities.Materia;
import br.com.edukid.api.vo.v1.configquiz.PerguntaVO;

/**
 * CLASSE REPRESENTA O QUIZ CRIADO COM AS CONFIGURAÇÕES PERSONALIZADAS DE UMA MATÉRIA ESPECÍFICA 
 * @Author LUCAS BORGUEZAM
 * @Sice 14 de set. de 2024
 */
public class QuizByMateriaVO {
	
	private String id;
	private String subject;
	private List<PerguntaVO> quiz;
	
	public QuizByMateriaVO() {}
	
	public QuizByMateriaVO(Materia subject) {
		this.id = subject.getId().toString();
		this.subject = subject.getName();		
	}
	
	/**
	 * METODO ORDENA A LISTA DE PERGUNTAS PELO THEMA
	 * @Author LUCAS BORGUEZAM
	 * @Sice 6 de out. de 2024
	 */
	public void orderQuizByIdTheme() {
		quiz.sort((p1, p2) -> p1.getIdTema().compareTo(p2.getIdTema()));
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<PerguntaVO> getQuiz() {
		return quiz;
	}
	public void setQuiz(List<PerguntaVO> quiz) {
		this.quiz = quiz;
	}



}
