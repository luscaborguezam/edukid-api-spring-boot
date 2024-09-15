package br.com.edukid.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.Configuration;
import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.entities.UserFather;

/**
 * INTERFACE DISPONIBILIZA OPERACOES PARA O BANCO DE DADOS NA TABELA USER_FILHO, PARA O CAMPO DE CONFIGURAÇÕES POR MEIO DO JPAREPOSITORY
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */

public interface UserChildRepository extends JpaRepository<UserChild, Integer>{
	
	/**
	 * METODO BUSCA A QUANTIDADE DE REGISTRO COM UM APELIDO ESPECÍFICO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param nickname
	 * @return boolean
	 */
	boolean existsByNickname(String nickname);
	
	/**
	 * METODO QUE BUSCA USERCHILD PELO NICKNAME
 	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param nickname
	 * @return ENTITY USERCHILD 
	 */
	UserChild findByNickname(@Param("nickname") String nickname);
	
    /**
     * Verifica se existe um usuário filho com o mesmo nickname de um fkUserPai diferente do fkUserPai.
     * @param nickname O e-nickname a ser verificado
     * @param fkUserPai O FK do usuário pai que está sendo atualizado
     * @return true se existir outro usuário com o mesmo e-mail, caso contrário false
     */
    @Query("SELECT COUNT(u) > 0 FROM UserChild u WHERE u.nickname = :nickname AND u.fkUserPai <> :fkUserPai")
    boolean existsNicknameToUpdate(@Param("nickname") String nickname, @Param("fkUserPai") Integer fkUserPai);
}
