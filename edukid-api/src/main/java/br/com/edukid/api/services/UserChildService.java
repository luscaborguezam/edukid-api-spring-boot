package br.com.edukid.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.edukid.api.entities.Configuration;
import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.exceptions.ResourceNotFoundException;
import br.com.edukid.api.mapper.EdukidMapper;
import br.com.edukid.api.repositorys.ConfigurationRepository;
import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.utils.JsonService;
import br.com.edukid.api.utils.StringServices;
import br.com.edukid.api.utils.UtilsService;
import br.com.edukid.api.vo.v1.LoginVO;
import br.com.edukid.api.vo.v1.configquiz.MateriasETemasVO;
import br.com.edukid.api.vo.v1.user.child.UserChildGetVO;
import br.com.edukid.api.vo.v1.user.child.UserChildVO;

@Service
public class UserChildService {
	
	@Autowired
	UtilsService utilsService;
	@Autowired
	UserChildRepository childRepository;
	@Autowired
	HashSaltService hashSaltService;
	@Autowired
	StringServices stringService;
	@Autowired
	JsonService jsonService;
	@Autowired
	ConfigurationRepository configurationRepository;
	
	/**
	 * 
	 * METODO CADASTRA DADOS DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	public ResponseEntity<?> registerUserChild(UserChildVO data) {		
		/*Faz o Hash da senha do usuario*/
		data.setPassword(hashSaltService.hash(data.getPassword()));
		
		/*Verificar se o apelido já foi cadastrado*/
		if(childRepository.existsByNickname(data.getNickname()))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Apelido já está em uso.");

		/*Converte VO para Entity e cadastra na base de dados*/
		UserChild entity = EdukidMapper.parseObject(data, UserChild.class);
		
		
		
		var vo = EdukidMapper.parseObject(childRepository.save(entity), UserChildVO.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(vo);
	}
	
	/**
	 * METODO ALTERA DADOS DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 10 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	public ResponseEntity<?> updateUserChild(UserChildVO data) {
		/*Verificar se existe o novo nickname*/
		if(childRepository.existsNicknameToUpdate(data.getNickname(), Integer.parseInt(data.getFkUserPai())))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nickname já está em uso.");
		
		Optional<UserChild> optional = childRepository.findById(Integer.parseInt(data.getId()));
		UserChild userEntity = optional.get();
		userEntity = EdukidMapper.parseObject(data, UserChild.class);
		var entity = childRepository.save(userEntity);
		
		return ResponseEntity.status(HttpStatus.OK).body("Alterado com sucesso.");
	}
	
	/**
	 * METODO DELETA A CONTA DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 10 de ago. de 2024
	 * @param id
	 * @return
	 */
	public ResponseEntity<?> deleteUserChild(Integer id) {
		var entity = childRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Esse ID não foi encontrado."));
		childRepository.delete(entity);
		return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso.");
	}
	
	
	/**
	 * METODO REALIZA A AUTHENTIFICAÇÃO DO LOGINS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 9 de jul. de 2024
	 * @param login
	 * @return STATUS CODE HTTP + DESCRICAO
	 */
	public ResponseEntity<?> authenticateLogin(LoginVO login) {
			if(childRepository.existsByNickname(login.getEmailOrNickName())) {
				
				UserChild user = childRepository.findByNickname(login.getEmailOrNickName());
				if(hashSaltService.verifyHash(login.getPassword(), user.getPassword())) {
					UserChildVO userChild = EdukidMapper.parseObject(user, UserChildVO.class);
					return ResponseEntity.status(HttpStatus.OK).body(userChild);
				}
			}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credênciais invalidas.");
	}

	/**
	 * METODO BUSCA USER CHILD PELO SEU ID
	 * @Author LUCAS BORGUEZAM
	 * @Sice 10 de set. de 2024
	 * @param int1
	 * @return
	 */
	public ResponseEntity<?> getUserChild(Integer id) {
		if(childRepository.existsById(id)) {
			
			Optional<UserChild> userOptional = childRepository.findById(id);
			UserChild user = userOptional.get();
			
			Optional<Configuration> opConfEntity = configurationRepository.findById(id);
			Configuration confEntity = opConfEntity.get();
			
			
			MateriasETemasVO conf = jsonService.fromJson(confEntity.getConfiguration(), MateriasETemasVO.class);
			
			UserChildGetVO userChild = EdukidMapper.parseObject(user, UserChildGetVO.class);
			if(conf != null)
				userChild.setConfiguration(conf.getMaterias());
			
			return ResponseEntity.status(HttpStatus.OK).body(userChild);
			
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credênciais invalidas.");
	}

}
