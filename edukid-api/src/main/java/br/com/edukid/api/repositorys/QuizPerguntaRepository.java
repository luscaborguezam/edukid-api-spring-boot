package br.com.edukid.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.QuizPergunta;
import br.com.edukid.api.entities.ids.QuizPerguntaId;

public interface QuizPerguntaRepository extends JpaRepository<QuizPergunta, QuizPerguntaId> {
	
	/**
	 * METODO PARA BUSCAR QUIZ UM REGISTRO DE QUIZ PERGUNTA ESPECÍFICO
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


}
