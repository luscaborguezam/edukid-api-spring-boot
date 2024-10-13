package br.com.edukid.api.vo.v1.performance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.edukid.api.utils.Defines;

public class QuizPerformanceData {
	
	private String nameUserChild;
	private String nickName;
	
	/*Quantidade de quizes realizados, não realizados, em aberto, */
	private Integer totalQuizzes = 0;
	private Integer totalQuizzesInPeriod=0;
	private Integer totalQuizzesOpen=0;
	private Integer totalQuizzesFinalized=0;
	private Integer totalQuizzesIncomplet=0;
	private Integer totalQuizzesNotRealized=0;
	
	/*Dados Gerais*/
	private String dataInicial;
	private String dataFinal;
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
	
	/**
	 * METODO SOMA AS INFORMAÇÕES JÁ EXISTENTES DE PERFORMANCE COM NOVOS DADOS DE PERFORMANCE
	 * UTILIZADO PARA CALCULAR A PERFORMANCE DE MAIS DE UM QUIZ
	 * @Author LUCAS BORGUEZAM
	 * @Sice 13 de out. de 2024
	 * @param performance
	 */
	public void combinar(QuizPerformanceData performance) {
	    
	    // Somando os valores totais de perguntas, acertos e erros
	    totalQuestions += performance.getTotalQuestions();
	    totalHits += performance.getTotalHits();
	    totalErrors += performance.getTotalErrors();

	    // Iterando sobre as matérias da nova performance
	    for (SubjectPerformance newSubjectPerformance : performance.getSubjectPerformance()) {
	        
	        // Procurar a matéria correspondente na performance existente
	        Optional<SubjectPerformance> subjectEncontrado = subjectPerformance.stream()
	                .filter(se -> se.getSubject().equals(newSubjectPerformance.getSubject()))
	                .findFirst();

	        if (subjectEncontrado.isPresent()) {
	            SubjectPerformance subjectAtual = subjectEncontrado.get();

	            // Somar dados de performance da matéria
	            subjectAtual.setTotalQuestions(subjectAtual.getTotalQuestions() + newSubjectPerformance.getTotalQuestions());
	            subjectAtual.setTotalHits(subjectAtual.getTotalHits() + newSubjectPerformance.getTotalHits());
	            subjectAtual.setTotalErrors(subjectAtual.getTotalErrors() + newSubjectPerformance.getTotalErrors());

	            // Iterar sobre os temas da nova performance
	            for (ThemePerformance newThemePerformance : newSubjectPerformance.getThemesPerformance()) {
	                
	                // Procurar o tema correspondente na matéria existente
	                Optional<ThemePerformance> themeEncontrado = subjectAtual.getThemesPerformance().stream()
	                        .filter(te -> te.getTheme().equals(newThemePerformance.getTheme()))
	                        .findFirst();

	                if (themeEncontrado.isPresent()) {
	                    ThemePerformance themeAtual = themeEncontrado.get();

	                    // Somar dados de performance do tema
	                    themeAtual.setTotalQuestions(themeAtual.getTotalQuestions() + newThemePerformance.getTotalQuestions());
	                    themeAtual.setTotalHits(themeAtual.getTotalHits() + newThemePerformance.getTotalHits());
	                    themeAtual.setTotalErrors(themeAtual.getTotalErrors() + newThemePerformance.getTotalErrors());
	                } else {
	                    // Adicionar o novo tema se não foi encontrado
	                    subjectAtual.addThemePerformance(newThemePerformance);
	                }
	            }//for(Theme)
	        } else {
	            // Adicionar nova matéria se não foi encontrada
	            subjectPerformance.add(newSubjectPerformance);
	        }
	    }//for(subject)
	}
	
	/*Geters and Setters*/
	
	public Integer getTotalQuestions() {
		return totalQuestions;
	}

	public String getDataCreation() {
		return dataInicial;
	}

	public void setDataCreation(String dataCreation) {
		this.dataInicial = dataCreation;
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

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Integer getTotalQuizzesOpen() {
		return totalQuizzesOpen;
	}

	public void setTotalQuizzesOpen(Integer totalQuizzesOpen) {
		this.totalQuizzesOpen = totalQuizzesOpen;
	}

	public Integer getTotalQuizzesFinalized() {
		return totalQuizzesFinalized;
	}

	public void setTotalQuizzesFinalized(Integer totalQuizzesFinalized) {
		this.totalQuizzesFinalized = totalQuizzesFinalized;
	}

	public Integer getTotalQuizzesNotRealized() {
		return totalQuizzesNotRealized;
	}

	public void setTotalQuizzesNotRealized(Integer totalQuizzesNotRealized) {
		this.totalQuizzesNotRealized = totalQuizzesNotRealized;
	}

	public Integer getTotalQuizzesIncomplet() {
		return totalQuizzesIncomplet;
	}

	public void setTotalQuizzesIncomplet(Integer totalQuizzesIncomplet) {
		this.totalQuizzesIncomplet = totalQuizzesIncomplet;
	}

	public Integer getTotalQuizzes() {
		return totalQuizzes;
	}

	public void setTotalQuizzes(Integer totalQuizzes) {
		this.totalQuizzes = totalQuizzes;
	}

	public Integer getTotalQuizzesInPeriod() {
		return totalQuizzesInPeriod;
	}

	public void setTotalQuizzesInPeriod(Integer totalQuizzesInPeriod) {
		this.totalQuizzesInPeriod = totalQuizzesInPeriod;
	}
	
	
	
}
