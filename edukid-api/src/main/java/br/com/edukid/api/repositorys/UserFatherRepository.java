package br.com.edukid.api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.edukid.api.entities.UserFather;


/**
 * INTERFACE DISPONIBILIZA OPERACOES PARA O BANCO DE DADOS POR MEIO DO JPAREPOSITORY
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
	long countByEmail(String email);
	
	/**
	 * METODO QUE BUSCA USERFATHER PELO EMAIL
 	 * @Author LUCAS BORGUEZAM
	 * @Sice 9 de jul. de 2024
	 * @param userName
	 * @return OBJETO USERFATHER
	 */
	UserFather findByEmail(@Param("email") String email);
	
}
