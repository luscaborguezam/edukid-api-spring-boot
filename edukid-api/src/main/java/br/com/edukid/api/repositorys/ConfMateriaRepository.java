package br.com.edukid.api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edukid.api.entities.ConfMateria;

/**
 * INTERFACE DISPONIBILIZA OPERACOES PARA O BANCO DE DADOS NA TABELA USER_FILHO POR MEIO DO JPAREPOSITORY
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */

public interface ConfMateriaRepository extends JpaRepository<ConfMateria, Integer>{
	
	//@Query("SELECT DISTINCT m FROM Materia m JOIN TemaAprendizagem ta ON m.id = ta.idSubject WHERE ta.yearElementarySchool = :yearElementarySchool")
    List<ConfMateria> findByIdUserChild(Integer idUserChild);
}
