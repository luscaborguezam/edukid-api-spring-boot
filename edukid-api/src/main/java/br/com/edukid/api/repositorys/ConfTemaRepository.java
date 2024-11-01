package br.com.edukid.api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.ConfTema;
import br.com.edukid.api.entities.ids.ConfTemaId;

/**
 * INTERFACE DISPONIBILIZA OPERACOES PARA O BANCO DE DADOS NA TABELA USER_FILHO POR MEIO DO JPAREPOSITORY
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */

public interface ConfTemaRepository extends JpaRepository<ConfTema, ConfTemaId>{
	
	/**
	 * BUSCA ID DOS TEMAS CADASTRADOS PARA CADA MATERIA NA CONFIGURAÇÃO PERSONALIIZADA DO USER CHILD
	 * @param idUserChild
	 * @param idSubject
	 * @return
	 */
	@Query(value="SELECT DISTINCT ct.* FROM conf_tema ct "
			+ "INNER JOIN  user_filho uf ON uf.id_user_filho= ct.id_user_filho "
			+ "INNER JOIN  conf_materia cm ON cm.id_materia = ct.id_materia "
			+ "WHERE uf.id_user_filho =:idUserChild "
			+ "AND ct.id_materia =:idSubject", nativeQuery = true)
    List<ConfTema> findByIdUserChild(
    		@Param("idUserChild") Integer idUserChild,
    		@Param("idSubject") Integer idSubject
	);
}
