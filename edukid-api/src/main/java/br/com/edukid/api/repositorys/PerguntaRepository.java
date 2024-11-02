package br.com.edukid.api.repositorys;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
    
	
	List<Pergunta> findPerguntasByIdTema(Integer idTema);
	
	
	/**
     * METODO PARA BUSCAR PERGUNTAS ALEATÓRIAS SEM REPETICAO
     * @Author LUCAS BORGUEZAM
     * @Sice 31 de ago. de 2024
     * @param idTema
     * @param pageable
     * @return
     */
	@Query("SELECT p FROM Pergunta p WHERE p.idTema = :temaId ORDER BY RAND()")
	List<Pergunta> findRandomPerguntasByTema(@Param("temaId") Integer temaId, Pageable pageable);
	
	
	/**
	 * METODO PARA BUSCAR PERGUNTAS RELACIONADAS A UM QUIZ DE UM USUÁRIO
	 * @param idUserChild
	 * @param idQuiz
	 * @return
	 */
	@Query(value= "SELECT p.* FROM Pergunta p "
				+ "INNER JOIN quiz_perguntas qp ON p.id_pergunta = qp.id_pergunta "
				+ "INNER JOIN quiz q ON qp.id_quiz = q.id_quiz "
				+ "INNER JOIN user_filho uf ON q.id_user_filho = uf.id_user_filho "
				+ "WHERE uf.id_user_filho =:idUserChild "
				+ "AND q.id_quiz =:idQuiz ", nativeQuery = true)
	List<Pergunta> findRandomPerguntasOfQuizByTema(@Param("idUserChild") Integer idUserChild,
											 	   @Param("idQuiz") Integer idQuiz
			);
	
	/**
	 * METODO PARA BUSCAR PERGUNTAS RELACIONADAS A UMA MATERIA ESPECIFICA DE UM QUIZ ESPECIFICO DE UM USUÁRIO ESPECIFICO
	 * @param idUserChild
	 * @param idQuiz
	 * @return
	 */	
	@Query(value= "SELECT p.* FROM pergunta p "
				+ "INNER JOIN materia m ON p.id_materia = m.id_materia "
				+ "INNER JOIN quiz_perguntas qp ON p.id_pergunta = qp.id_pergunta "
				+ "INNER JOIN quiz q ON qp.id_quiz = q.id_quiz "
				+ "INNER JOIN user_filho uf ON q.id_user_filho = uf.id_user_filho "
				+ "WHERE uf.id_user_filho =:idUserChild "
				+ "AND q.id_quiz =:idQuiz "
				+ "AND m.id_materia =:idMateria ORDER BY RAND()", nativeQuery = true)
	List<Pergunta> findRandomPerguntasOfQuizByTemaAndMatria(@Param("idUserChild") Integer idUserChild,
															@Param("idQuiz") Integer idQuiz,
															@Param("idMateria") Integer idMateria
			);
	
}
