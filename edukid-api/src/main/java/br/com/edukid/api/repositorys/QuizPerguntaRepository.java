package br.com.edukid.api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.QuizPergunta;
import br.com.edukid.api.entities.ids.QuizPerguntaId;

public interface QuizPerguntaRepository extends JpaRepository<QuizPergunta, QuizPerguntaId> {
	
	/**
	 * METODO PARA BUSCAR UM REGISTRO DE QUIZ_PERGUNTA ESPECÍFICO
	 * @param idUserChild
	 * @param idQuiz
	 * @return
	 */
	@Query(value= "SELECT qp.* FROM quiz_perguntas qp "
				+ "INNER JOIN  pergunta p ON p.id_pergunta = qp.id_pergunta "
				+ "INNER JOIN quiz q ON q.id_quiz = qp.id_quiz "
				+ "WHERE p.id_pergunta =:idQuestion "
				+ "AND q.id_quiz =:idQuiz", nativeQuery = true)
	QuizPergunta findByidQuizAndidQuestion(@Param("idQuestion") Integer idQuestion,
										   @Param("idQuiz") Integer idQuiz
			);


	/**
	 * METODO PARA BUSCAR UM REGISTRO DE QUIZ_PERGUNTAS DE UM QUIZ ESPECÍFICO DE UMA MATÉRIA ESPECÍFICA DO QUIZ
	 * @param idUserChild
	 * @param idQuiz
	 * @return
	 */
	@Query(value= "SELECT qp.* FROM quiz_perguntas qp "
			+ "	INNER JOIN  pergunta p ON p.id_pergunta = qp.id_pergunta "
			+ "	INNER JOIN quiz q ON q.id_quiz = qp.id_quiz "
			+ "	INNER JOIN  materia m ON m.id_materia = p.id_materia "
			+ "	WHERE q.id_quiz=:idQuiz "
			+ "	AND m.id_materia=:idSubject", nativeQuery = true)
	List<QuizPergunta>findByidQuizAndidSubject(@Param("idQuiz") Integer idQuiz,
										  @Param("idSubject") Integer idSubject
			);
	


	
}
