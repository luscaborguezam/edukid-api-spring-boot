package br.com.edukid.api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.ConfMateria;
import br.com.edukid.api.entities.ids.ConfMateriaId;

/**
 * INTERFACE DISPONIBILIZA OPERACOES PARA O BANCO DE DADOS NA TABELA USER_FILHO POR MEIO DO JPAREPOSITORY
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */

public interface ConfMateriaRepository extends JpaRepository<ConfMateria, ConfMateriaId>{
	
	/**
	 * BUSCA ID DAS MATÉRIDAS CADASTRADAS PARA NA CONFIGURAÇÃO DO USER CHILD
	 * @param idUserChild
	 * @return
	 */
	@Query(value = "SELECT cm.* FROM conf_materia cm "
			+ "INNER JOIN  user_filho uf ON uf.id_user_filho= cm.id_user_filho "
			+ "WHERE uf.id_user_filho =:idUserChild", nativeQuery = true)
    List<ConfMateria> findByIdUserChild(@Param("idUserChild") Integer idUserChild);
}
