package br.com.edukid.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.edukid.api.entities.UserFather;


/**
 * INTERFACE DISPONIBILIZA OPERACOES PARA O BANCO DE DADOS NA TABELA USER_PAI POR MEIO DO JPAREPOSITORY
 * @Author LUCAS BORGUEZAM
 * @Sice 9 de jul. de 2024
 */
public interface UserFatherRepository extends JpaRepository<UserFather, Integer>{	

	/**
	 * METODO BUSCA A QUANTIDADE DE REGISTRO COM UM EMAIL ESPECÍFICO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 9 de jul. de 2024
	 * @param email
	 * @return NUMERO DO TIPO LONG
	 */
	boolean existsByEmail(String email);
	
	/**
	 * METODO BUSCA A QUANTIDADE DE REGISTRO COM UM CPF ESPECÍFICO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 9 de jul. de 2024
	 * @param email
	 * @return NUMERO DO TIPO LONG
	 */
	boolean existsByCpf(String cpf);
	
	/**
	 * METODO QUE BUSCA USERFATHER PELO EMAIL
 	 * @Author LUCAS BORGUEZAM
	 * @Sice 9 de jul. de 2024
	 * @param userName
	 * @return ENTITY USERFATHER
	 */
	UserFather findByEmail(@Param("email") String email);
	
    /**
     * VERIFICA SE EXISTE UM USUÁRIO COM O MESMO E-MAIL E UM ID DIFERENTE DO ID FORNECIDO
     * @param email O e-mail a ser verificado
     * @param userId O ID do usuário que está sendo atualizado
     * @return n>0 se existir outro usuário com o mesmo e-mail, caso contrário 0
     */
    @Query("SELECT COUNT(u) > 0 FROM UserFather u WHERE u.email = :email AND u.id <> :userId")
    boolean existsEmailToUpdate(@Param("email") String email, @Param("userId") Integer userId);
    
    /**
     * VERIFICA SE EXISTE UM USUÁRIO COM O MESMO CPF DE UM ID DIFERENTE DO ID FORNECIDO
     * @param cpf O cpf a ser verificado
     * @param userId O ID do usuário que está sendo atualizado
     * @return n>0 se existir outro usuário com o mesmo cpf, caso contrário 0
     */
    @Query("SELECT COUNT(u) > 0 FROM UserFather u WHERE u.cpf = :cpf AND u.id <> :userId")
    boolean existsCpfToUpdate(@Param("cpf") String cpf,@Param("userId") Integer userId);
    
    /**
     * VERIFICA SE O CAMPO cod_mudar_senha == NULL
     * 
     * @Author LUCAS BORGUEZAM
     * @Sice 25 de ago. de 2024
     * @param id
     * @return true se o campo cod_mudar_senha for NULL para o id especificado, e false caso contrário.
     */
    boolean existsByIdAndCodMudarSenhaIsNull(Integer userId);
    
	/**
	 * METODO BUSCA USER PELO EMAIL
	 * @Author LUCAS BORGUEZAM
	 * @Sice 21 de set. de 2024
	 * @param login
	 * @return UserDetails
	 */
    @Query("SELECT u FROM UserFather u WHERE u.email = :email")
	UserDetails findByEmailUserDetails(@Param("email") String email);
	
}
