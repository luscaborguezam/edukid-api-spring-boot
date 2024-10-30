package br.com.edukid.api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.ConfTema;
import br.com.edukid.api.entities.Configuration;
import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.entities.UserFather;

/**
 * INTERFACE DISPONIBILIZA OPERACOES PARA O BANCO DE DADOS NA TABELA USER_FILHO POR MEIO DO JPAREPOSITORY
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */

public interface ConfTemaRepository extends JpaRepository<ConfTema, Integer>{
	
	@Query("SELECT ct FROM ConfTema ct "
			+ "INNER JOIN ConfMateria cm ON ct.idSubject = cm.idSubject "
			+ "INNER JOIN UserChild uf ON ct.idUserChild = uf.id "
			+ "WHERE uf.id = :idUserChild "
			+ "AND cm.idSubject = :idSubject")
    List<ConfTema> findByIdUserChild(
    		@Param("idUserChild") Integer idUserChild,
    		@Param("idSubject") Integer idSubject
	);
}
