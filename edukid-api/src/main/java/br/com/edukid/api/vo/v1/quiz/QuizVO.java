package br.com.edukid.api.vo.v1.quiz;

import java.util.List;

import br.com.edukid.api.entities.Pergunta;
import br.com.edukid.api.vo.v1.configquiz.PerguntaVO;

public class QuizVO {
	
	private List<QuizMateriaVO> materias;

	public List<QuizMateriaVO> getMaterias() {
		return materias;
	}

	public void setMaterias(List<QuizMateriaVO> materias) {
		this.materias = materias;
	}
	
	
	
	
}
