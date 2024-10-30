package br.com.edukid.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edukid.api.entities.QuizPergunta;

public interface QuizPerguntaRepository extends JpaRepository<QuizPergunta, Integer> {
	
}
