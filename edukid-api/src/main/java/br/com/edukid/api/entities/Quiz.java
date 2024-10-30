package br.com.edukid.api.entities;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.edukid.api.utils.JsonService;
import br.com.edukid.api.vo.v1.quiz.FieldQuizVO;
import br.com.edukid.api.vo.v1.quiz.QuizVO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="quiz")
public class Quiz {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "id_quiz")
	private Integer id;
	@Column(name = "data_inicio", nullable = false)
	private LocalDateTime startDate; // yyyy-MM-dd'T'HH:mm:ss exemplo: 2024-09-11T15:30:45
	@Column(name = "data_fim")
	private LocalDateTime  endDate; // yyyy-MM-dd'T'HH:mm:ss exemplo: 2024-09-11T15:30:45
	@Column(name = "finalizado", nullable = false)
	private Integer isFinalized;
	@Column(name = "id_user_filho", nullable = false)
	private Integer idUserChild;
	
	
	public Quiz() {}
	/**
	 * Cria objeto carregando informações necessárias para o cadastro
	 * @param quiz
	 * @param userChild
	 */
	public Quiz(String quiz, Configuration confUserChild) {
		//this.startDate = LocalDateTime.now();
		this.idUserChild = confUserChild.getId();
		this.isFinalized = 0;
	}
	
	/**
	 * METODO ATUALIZA OBJETO COM DADOS DO QUIZ REALIZADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 17 de set. de 2024
	 * @param quizRegistred
	 * @param quiz
	 */
	public void updateDataWithQuizRealized(QuizVO quizRegistred, String quiz) {
		this.endDate = LocalDateTime.parse(quizRegistred.getEndDate());
		//this.endDate = LocalDateTime.now();
		this.isFinalized = Integer.parseInt(quizRegistred.getIsFinalized());
		
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public Integer getIsfinalized() {
		return isFinalized;
	}
	public void setIsfinalized(Integer isfinalized) {
		isFinalized = isfinalized;
	}
	public Integer getIdUserChild() {
		return idUserChild;
	}
	public void setIdUserChild(Integer idUserChild) {
		this.idUserChild = idUserChild;
	}

	
}
