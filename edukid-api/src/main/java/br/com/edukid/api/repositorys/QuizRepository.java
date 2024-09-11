package br.com.edukid.api.repositorys;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.Quiz;
import br.com.edukid.api.utils.Defines;

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
	

}
