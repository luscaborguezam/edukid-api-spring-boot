package br.com.edukid.api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.utils.Defines;
import jakarta.transaction.Transactional;

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
     * Verifica se existe um usuário filho com o mesmo nickname
     * @param nickname O e-nickname a ser verificado
     * @param fkUserPai O FK do usuário pai que está sendo atualizado
     * @return true se existir outro usuário com o mesmo e-mail, caso contrário false
     */
    @Query("SELECT COUNT(u) > 0 FROM UserChild u WHERE u.nickname = :nickname AND u.id <> :id")
    boolean existsNicknameToUpdate(@Param("nickname") String nickname, @Param("id") Integer id);

    /**
     * METODO VERIFICA SE EXISTE ALGUM USUÁRIO FILHO COM O FK DO USER PAI
     * @Author LUCAS BORGUEZAM
     * @Sice 18 de set. de 2024
     * @param fkUserPai
     * @return
     */
	boolean existsByFkUserPai(Integer fkUserPai);

	/**
	 * METODO BUSCA TODOS USER CHILDS QUE TEM O MESMO USUÁRIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 18 de set. de 2024
	 * @param fkUserPai
	 * @return
	 */
	List<UserChild> findByFkUserPai(Integer fkUserPai);
	
	/**
	 * METODO BUSCA USER PELO EMAIL
	 * @Author LUCAS BORGUEZAM
	 * @Sice 21 de set. de 2024
	 * @param login
	 * @return UserDetails
	 */
	@Query("SELECT u FROM UserChild u WHERE u.nickname = :nickname")
	UserDetails findByNicknameUserDetails(@Param("nickname") String nickname);
	
	/**
	 * METODO BUSCA OS ID'S DE USER CHILD RELACIONADO A UM USER FATHER
	 * @Author LUCAS BORGUEZAM
	 * @Sice 1 de out. de 2024
	 * @param fkUserPai
	 * @return
	 */
	@Query("SELECT u.id FROM UserChild u WHERE u.fkUserPai = :fkUserPai")
	List<Integer> findIdByFkUserPai(@Param("fkUserPai") Integer fkUserPai);
	
	/**
	 * METODO BUSCA OS ID'S DE USER CHILD RELACIONADO A UM USER FATHER
	 * @Author LUCAS BORGUEZAM
	 * @Sice 1 de out. de 2024
	 * @param fkUserPai
	 * @return
	 */
	@Query("SELECT u FROM UserChild u WHERE u.fkUserPai = :fkUserPai")
	List<UserChild> findChildByFkUserPai(@Param("fkUserPai") Integer fkUserPai);
	
	/**
	 * METODO BUSCA TODOS OS ID'S DE USER CHILD QUE TEM O USERPAI ATIVO 
	 * @Author LUCAS BORGUEZAM
	 * @Sice 1 de out. de 2024
	 * @return
	 */
	@Query("SELECT uc.id FROM UserChild uc " +
		       "INNER JOIN UserFather uf ON uf.id = uc.fkUserPai " +
		       "WHERE uf.status LIKE '"+ Defines.STATUS_ATIVO_ACCOUNT_USER_FATHER +"'"
		  )
	List<Integer> findAllIdWhereUserFatherEqualsActive();

	/**
	 * METODO RESETA A PONTUAÇÃO SEMANAL DO USER CHILD
	 * @Author LUCAS BORGUEZAM
	 * @Sice 13 de out. de 2024
	 */
	@Modifying
    @Transactional
    @Query(value = "UPDATE user_filho SET score_semanal = 0", nativeQuery = true)
    void resetScoreWeek();

	/**
	 * METODO BUSCA TODOS USERS CHILD ID ODENADOS PELO SCORE SEMANAL
	 * @Author LUCAS BORGUEZAM
	 * @Sice 13 de out. de 2024
	 * @return
	 */
	@Query("SELECT u FROM UserChild u WHERE u.schoolYear =:schoolYear ORDER BY u.scoreWeek DESC")
	List<UserChild> getRankingForScoreWeek(@Param("schoolYear") Integer schoolYear);
}
