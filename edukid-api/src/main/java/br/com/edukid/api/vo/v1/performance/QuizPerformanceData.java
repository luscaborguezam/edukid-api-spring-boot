package br.com.edukid.api.vo.v1.performance;

import java.util.ArrayList;
import java.util.List;

public class QuizPerformanceData {
	
	private String nameUserChild;
	private String nickName;
	
	/*Dados Gerais*/
	private Integer totalQuestions;
	private Integer totalHits;
	private Integer totalErrors;
	
	/*Dados Por matérias*/
	private List<SubjectPerformance> subjectPerformance;
	
	public QuizPerformanceData() {
		subjectPerformance = new ArrayList<>();
		totalQuestions=0;
		totalHits=0;
		totalErrors=0;
	}
	
	/**
	 * METODO INCREMENTA NO TOTAL DE QUESTÕES A QUANTIDADE DESEJADA
	 * @Author LUCAS BORGUEZAM
	 * @Sice 6 de out. de 2024
	 * @param quantity
	 */
	public void incrementTotalQuestions(Integer quantity) {
		totalQuestions+=quantity;
	}
	
	/**
	 * METODO CALCULA A QUANTIDADE DE ERROS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 6 de out. de 2024
	 * @return
	 */
	public Integer calcularTotalDeErros() {
		return totalErrors = totalQuestions - totalHits;
	}
	
	/**
	 * METODO INCREMENTA UM ACERTO NO TOTAL DE ACERTOS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 6 de out. de 2024
	 */
	public void incrementTotalHit() {
		totalHits++;
	}
	
	public void addSubject(SubjectPerformance subjectPerformance) {
		this.subjectPerformance.add(subjectPerformance);
	}
	
	/*Geters and Setters*/
	
	public Integer getTotalQuestions() {
		return totalQuestions;
	}

	public String getNameUserChild() {
		return nameUserChild;
	}

	public void setNameUserChild(String nameUserChild) {
		this.nameUserChild = nameUserChild;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setTotalQuestions(Integer totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public Integer getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(Integer totalHits) {
		this.totalHits = totalHits;
	}

	public Integer getTotalErrors() {
		return totalErrors;
	}

	public void setTotalErrors(Integer totalErrors) {
		this.totalErrors = totalErrors;
	}

	public List<SubjectPerformance> getSubjectPerformance() {
		return subjectPerformance;
	}

	public void setSubjectPerformance(List<SubjectPerformance> subjectPerformance) {
		this.subjectPerformance = subjectPerformance;
	}
	
	
}
