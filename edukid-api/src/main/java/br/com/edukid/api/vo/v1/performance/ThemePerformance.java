package br.com.edukid.api.vo.v1.performance;

public class ThemePerformance {
	/*Dados Gerais*/
	private String idTheme;
	private String theme;
	private Integer totalQuestions;
	private Integer totalHits;
	private Integer totalErrors;
	
	public ThemePerformance() {
		totalQuestions=0;
		totalHits=0;
		totalErrors=0;
	}
	
	/**
	 * METODO INCREMENTA UM ACERTO NO TOTAL DE ACERTOS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 6 de out. de 2024
	 */
	public void incrementTotalQuestions() {
		totalQuestions++;
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
	
	/*Getters and Setters*/
	
	public String getTheme() {
		return theme;
	}

	public String getIdTheme() {
		return idTheme;
	}

	public void setIdTheme(String idTheme) {
		this.idTheme = idTheme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Integer getTotalQuestions() {
		return totalQuestions;
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
	
	
}
