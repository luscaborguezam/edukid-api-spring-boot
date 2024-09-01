package br.com.edukid.api.repositorys;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
    
	
	List<Pergunta> findPerguntasByIdTema(Integer idTema);
	
	
	/**
     * METODO PARA BUSCAR PERGUNTAS ALEATÓRIAS SEM REPETICAO
     * @Author LUCAS BORGUEZAM
     * @Sice 31 de ago. de 2024
     * @param idTema
     * @param pageable
     * @return
     */
	@Query("SELECT p FROM Pergunta p WHERE p.idTema = :temaId ORDER BY RAND()")
	List<Pergunta> findRandomPerguntasByTema(@Param("temaId") Integer temaId, Pageable pageable);
	
	

	
}
