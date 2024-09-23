package br.com.edukid.api.repositorys;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.Quiz;
import br.com.edukid.api.utils.Defines;
import jakarta.transaction.Transactional;

public interface QuizRepository extends JpaRepository<Quiz, Integer>{
	
	/**
	 * METODO VERIFICAR SE EXISTE UM QUIZ NA DATA ATUAL EM ABERTO DE UM USUÁRIO ESPECÍFICO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 28 de ago. de 2024
	 * @param yearHighScool
	 * @return
	 */
	@Query("SELECT CASE WHEN COUNT(q) > 0 THEN TRUE ELSE FALSE END "
	        + "FROM Quiz q JOIN UserChild uc ON q.idUserChild = uc.id "
	        + "WHERE uc.id = :idUserChild "
	        + "AND q.isFinalized =  "+Defines.QUIZ_EM_ABERTO+" "
	        + "AND q.endDate IS NULL "
	        + "AND DATE(q.startDate) = DATE(:currentDate)")
    Boolean existsQuizOpenByIdUserChild(@Param("idUserChild") Integer idUserChild, @Param("currentDate") LocalDate currentDate);
	

	/**
	 * METODO VERIFICAR SE EXISTE UM QUIZ NA DATA ATUAL EM ABERTO DE UM USUÁRIO ESPECÍFICO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 28 de ago. de 2024
	 * @param yearHighScool
	 * @return
	 */
	@Query("SELECT CASE WHEN COUNT(q) > 0 THEN TRUE ELSE FALSE END "
	        + "FROM Quiz q JOIN UserChild uc ON q.idUserChild = uc.id "
	        + "WHERE uc.id = :idUserChild "
	        + "AND q.isFinalized =  "+Defines.QUIZ_EM_ABERTO+" "
	        + "AND q.endDate IS NULL "
	        + "AND q.id = :idQuiz "
	        + "AND DATE(q.startDate) = DATE(:currentDate)")
    Boolean existsQuizOpenByIdWhereIdUserChild(
    		@Param("idUserChild") Integer idUserChild,
    		@Param("idQuiz") Integer idQuiz,  
    		@Param("currentDate") LocalDate currentDate);
	
	
	/**
	 * METODO BUSCA UM QUIZ NA DATA ATUAL EM ABERTO DE UM USUÁRIO ESPECÍFICO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 28 de ago. de 2024
	 * @param yearHighScool
	 * @return
	 */
	@Query("SELECT q FROM Quiz q JOIN UserChild uc ON q.idUserChild = uc.id "
	        + "WHERE uc.id = :idUserChild "
	        + "AND q.isFinalized =  "+Defines.QUIZ_EM_ABERTO+" "
	        + "AND q.endDate IS NULL "
	        + "AND DATE(q.startDate) = DATE(:currentDate)")
	Quiz FindQuizOpenByIdUserChild(@Param("idUserChild") Integer idUserChild, @Param("currentDate") LocalDate currentDate);
	
	/**
	 * METODO ATUALIZA O STATUS DE FINALIZAÇÃO DO QUIZ, PARA QUIZ_NAO_REALIZADO, DOS QUIZES QUE ESTÃO EM ABERTO 
	 * E QUE A DATA DE INICIO SEJA MENOR DO QUE A DATA ATUAL.
	 * @Author LUCAS BORGUEZAM
	 * @Sice 17 de set. de 2024
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Quiz q set q.isFinalized = "+Defines.QUIZ_NAO_REALIZADO+" "
			+ "where q.isFinalized = "+Defines.QUIZ_EM_ABERTO+" "
			+ "AND DATE(q.startDate) < CURRENT_DATE")
	void updateIsFinalizedWhereStartDateMinorWhithCurrent();
	
	/**
	 * METODO ATUALIZA O STATUS DE FINALIZAÇÃO DO QUIZ, PARA QUIZ_NAO_REALIZADO, 
	 * DOS QUIZES QUE ESTÃO EM ABERTO 
	 * @Author LUCAS BORGUEZAM
	 * @Sice 17 de set. de 2024
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Quiz q set q.isFinalized = "+Defines.QUIZ_NAO_REALIZADO+" "
			+ "where q.isFinalized = "+Defines.QUIZ_EM_ABERTO+" "
			+ "AND DATE(q.startDate) < CURRENT_DATE")
	void updateIsFinalizeD();
	
	
}
