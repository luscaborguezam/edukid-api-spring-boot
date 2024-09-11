package br.com.edukid.api.vo.v1.quiz;

import java.util.List;

import br.com.edukid.api.entities.Pergunta;
import br.com.edukid.api.vo.v1.configquiz.PerguntaVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class QuizVO {
	
	@NotBlank
	@Pattern(regexp = "^[+-]?([0-9]*[.])?[0-9]+$", message = "'score' deve ser uma string com formato float válido")
	private String score;
	@NotBlank
	@Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$", message = "'totalTime' deve ser uma string com formato de tempo, experado HH:mm:ss")
	private String totalTime;
	@NotBlank
	private List<QuizMateriaVO> materias;

	public List<QuizMateriaVO> getMaterias() {
		return materias;
	}

	public void setMaterias(List<QuizMateriaVO> materias) {
		this.materias = materias;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	
	
	
	
	
}
