package br.com.edukid.api.vo.v1.performance;

import java.util.ArrayList;
import java.util.List;

public class SubjectPerformance {
	/*Dados Gerais*/
	private String subject;
	private Integer totalQuestions;
	private Integer totalHits;
	private Integer totalErrors;
	
	private List<ThemePerformance> themesPerformance;
	
	public SubjectPerformance() {
		themesPerformance = new ArrayList<>();
		totalQuestions=0;
		totalHits=0;
		totalErrors=0;
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
	
	public void addThemePerformance(ThemePerformance themePerformance) {
		themesPerformance.add(themePerformance);
	}
	
	/*Getters and Setters*/

	public Integer getTotalQuestions() {
		return totalQuestions;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public List<ThemePerformance> getThemesPerformance() {
		return themesPerformance;
	}

	public void setThemesPerformance(List<ThemePerformance> themesPerformance) {
		this.themesPerformance = themesPerformance;
	}
	
}
