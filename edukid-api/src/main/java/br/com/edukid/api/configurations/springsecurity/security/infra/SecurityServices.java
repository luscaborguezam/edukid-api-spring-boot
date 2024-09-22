package br.com.edukid.api.configurations.springsecurity.security.infra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.edukid.api.entities.UserFather;
import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.repositorys.UserFatherRepository;
import br.com.edukid.api.utils.JsonService;
import br.com.edukid.api.vo.v1.UserFatherCadastroVO;
import br.com.edukid.api.vo.v1.user.child.UserChildVO;

@Service
public class SecurityServices {

	@Autowired
	UserFatherRepository fatherRepository;
	@Autowired
	UserChildRepository childRepository;
	@Autowired
	JsonService jsonService;
	
	/**
	 * METODO VERIFICA SE O USERPAI SOLICITANTE É O MESMO EM QUE ELE DESEJA ALTERAR OU BUSCAR INFORMAÇÕES
	 * @Author LUCAS BORGUEZAM
	 * @Sice 22 de set. de 2024
	 * @param user
	 * @return true SE FOR COMPATÍVEL
	 */
	public Boolean verifyUserFahterWithSolicitation(UserFatherCadastroVO user) {
		UserFather userEntity = obtainApplicantFatherData();
		if(userEntity != null && user.getId().equals(userEntity.getId().toString()))
			return true;
		return false;
	}
	
	/**
	 * METODO VERIFICA SE O USERPAI SOLICITANTE É O MESMO EM QUE ELE DESEJA ALTERAR OU BUSCAR INFORMAÇÕES
	 * @Author LUCAS BORGUEZAM
	 * @Sice 22 de set. de 2024
	 * @param true SE FOR COMPATÍVEL
	 */
	public Boolean verifyUserFahterWithSolicitation(String id) {
		UserFather userEntity = obtainApplicantFatherData();
		if(userEntity != null && id.equals(userEntity.getId().toString()))
			return true;
		return false;
	}

	/**
	 * METODO  VERIFICA SE O USERPAI SOLICITANTE É O MESMO RESPONSÁVEL PELA CRIANÇA
	 * @Author LUCAS BORGUEZAM
	 * @Sice 22 de set. de 2024
	 * @param data
	 * @return true SE FOR COMPATÍVEL
	 */
	public boolean verifyUserFahterWithSolicitation(UserChildVO data) {
		UserFather userEntity = obtainApplicantFatherData();
		if(userEntity == null) 
			return false;
		/*Buscat lista de filhos do user pai*/
		List<Integer> childsId = childRepository.findIdByFkUserPai(userEntity.getId());
		System.out.println(userEntity.getId());
		System.out.println(jsonService.toJson(childsId));
		if(childsId.contains(Integer.parseInt(data.getId())))
			return true;

		return false;
	}
	
	/**
	 * METODO  VERIFICA SE O USERPAI SOLICITANTE É O MESMO RESPONSÁVEL PELA CRIANÇA
	 * @Author LUCAS BORGUEZAM
	 * @Sice 22 de set. de 2024
	 * @param data
	 * @return true SE FOR COMPATÍVEL
	 */
	public boolean verifyUserFahterWithSolicitation(Integer id) {
		UserFather userEntity = obtainApplicantFatherData();
		if(userEntity == null) 
			return false;
		/*Buscat lista de filhos do user pai*/
		List<Integer> childsId = childRepository.findIdByFkUserPai(userEntity.getId());
		System.out.println(userEntity.getId());
		System.out.println(jsonService.toJson(childsId));
		if(childsId.contains(id))
			return true;

		return false;
	}
	
	/**
	 * METODO BUSCA OS DADOS DO USER FATHER REQUERENTE
	 * @Author LUCAS BORGUEZAM
	 * @Sice 22 de set. de 2024
	 * @return
	 */
	private UserFather obtainApplicantFatherData() {
		/*Pegar email do solicitante*/
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();
		
		/*Buscar dados do solicitante*/
		UserFather userEntity = null;
		if(fatherRepository.existsByEmail(email))
			return fatherRepository.findByEmail(email);
		else
			return null;	
	}
	
	
}
