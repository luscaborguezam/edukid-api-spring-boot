package br.com.edukid.api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
	
	/**
	 * METODO RETORNA O NOME DO TEMA, BUSCADO PELO ID
	 * @Author LUCAS BORGUEZAM
	 * @Sice 6 de out. de 2024
	 * @param id
	 * @return
	 */
    @Query("SELECT t.theme FROM TemaAprendizagem t WHERE t.id = :id")
    String findThemeById(Integer id);
}
