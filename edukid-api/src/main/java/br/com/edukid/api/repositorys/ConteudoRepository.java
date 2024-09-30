package br.com.edukid.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edukid.api.entities.Conteudo;

public interface ConteudoRepository extends JpaRepository<Conteudo, Integer>{

}
