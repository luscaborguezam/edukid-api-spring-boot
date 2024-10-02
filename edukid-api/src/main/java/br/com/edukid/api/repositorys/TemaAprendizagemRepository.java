package br.com.edukid.api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edukid.api.entities.TemaAprendizagem;

public interface TemaAprendizagemRepository extends JpaRepository<TemaAprendizagem, Integer>{


	/**
	 * METODO PARA ENCONTRAR TODOS OS TEMAS COM O ANO DE ENSINO FUNDAMENTAL ESPECIFICO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 28 de ago. de 2024
	 * @param yearElementarySchool
	 * @return
	 */
	List<TemaAprendizagem> findByYearElementarySchoolAndIdSubject(Integer yearElementarySchool, Integer idSubject);
	
}
