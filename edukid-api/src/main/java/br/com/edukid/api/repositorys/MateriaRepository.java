package br.com.edukid.api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.Materia;
import br.com.edukid.api.entities.TemaAprendizagem;

public interface MateriaRepository extends JpaRepository<Materia, Integer>{

	/**
	 * METODO BUSCA MATERIAS COM CONTEÚDOS RELACOINADOS AO ANO DO ENSINO FUMDAMENTAL
	 * @Author LUCAS BORGUEZAM
	 * @Sice 28 de ago. de 2024
	 * @param yearElementarySchool
	 * @return
	 */
	@Query("SELECT DISTINCT m FROM Materia m JOIN TemaAprendizagem ta ON m.id = ta.idSubject WHERE ta.yearElementarySchool = :yearElementarySchool")
    List<Materia> findDistinctMateriasByYearElementarySchool(@Param("yearElementarySchool") Integer yearElementarySchool);





	/**
	 * BUSCA MATERIAS RELACIONADAS A UM QUIZ EWSPECÍFICO
	 * @param idQuiz
	 * @return
	 */
	@Query(value = "SELECT DISTINCT m.* FROM materia m "
			+ "INNER JOIN pergunta p ON m.id_materia = p.id_materia "
			+ "INNER JOIN quiz_perguntas qp ON p.id_pergunta = qp.id_pergunta "
			+ "INNER JOIN quiz q ON qp.id_quiz = q.id_quiz "
			+ "WHERE q.id_quiz =:idQuiz"
		     , nativeQuery = true)
		List<Materia> findDistinctMateriasByIdQuiz(@Param("idQuiz") Integer idQuiz);

}
