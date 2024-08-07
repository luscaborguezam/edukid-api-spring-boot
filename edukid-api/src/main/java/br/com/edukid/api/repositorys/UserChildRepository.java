package br.com.edukid.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.entities.UserFather;

/**
 * INTERFACE DISPONIBILIZA OPERACOES PARA O BANCO DE DADOS NA TABELA USER_FILHO POR MEIO DO JPAREPOSITORY
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */

public interface UserChildRepository extends JpaRepository<UserChild, Integer>{
	/**
	 * METODO BUSCA A QUANTIDADE DE REGISTRO COM UM APELIDO ESPECÍFICO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param nickname
	 * @return NUMERO DO TIPO LONG
	 */
	long countByNickname(String nickname);
	
	/**
	 * METODO QUE BUSCA USERFATHER PELO EMAIL
 	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param nickname
	 * @return ENTITY USERCHILD 
	 */
	UserFather findByNickname(@Param("nickname") String nickname);
}
