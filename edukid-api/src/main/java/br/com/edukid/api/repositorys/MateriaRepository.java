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




	
	@Query(	"SELECT DISTINCT m FROM Materia m "
			+ "INNER JOIN Pergunta p ON m.id = p.idMateria"
			+ "INNER JOIN QuizPergunta qp ON p.id = qp.idQuestion "
			+ "INNER JOIN Quiz q ON qp.idQuiz = q.id "
			+ "WHERE q.id = :idQuiz")
    List<Materia> findDistinctMateriasByIdQuiz(@Param("idQuiz") Integer idQuiz);

}
