package br.com.edukid.api.services;

import java.util.Optional;
import java.util.logging.Logger;

import org.apache.catalina.mapper.Mapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.stereotype.Service;

//import com.github.dozermapper.core.Mapper;

import br.com.edukid.api.entities.UserFather;
import br.com.edukid.api.mapper.EdukidMapper;
import br.com.edukid.api.repositorys.UserFatherRepository;
import br.com.edukid.api.utils.Defines;
import br.com.edukid.api.utils.EmailService;
import br.com.edukid.api.utils.UtilsService;
import br.com.edukid.api.vo.v1.LoginVO;
import br.com.edukid.api.vo.v1.SolicitarMudancaSenhaVO;
import br.com.edukid.api.vo.v1.UserFatherCadastroVO;
import br.com.edukid.api.vo.v1.UserFatherVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * CLASSE CRIADA PARA FAZER O CONTROLE DE END POINTS RELACIONADOS AO LOGIN E CADASTRO DE USUÁRIO PAI.
 * @Author LUCAS BORGUEZAM
 * @Sice 14 de jul. de 2024
 */
@Service
public class UserFatherService {
	
	@Autowired
	UtilsService utilsService;
	@Autowired
	UserFatherRepository fatherRepository;
	@Autowired
	HashSaltService hashSaltService;
	@Autowired
	EmailService emailService;
	
	/* Registrar mensagens de log em uma aplicação Java.*/
	Logger logger = Logger.getLogger(UserFatherService.class.getName());
	
	
	/**
	 * METODO CADASTRA DADOS DO USUARIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 21 de jul. de 2024
	 * @param dataAccount
	 * @return STATUS CODE OK + OBJETO CADASRADO
	 */
	public ResponseEntity<?> registerUserFather(UserFatherCadastroVO data) {		
		/*Verificar se o email já foi cadastrado*/
		if(fatherRepository.existsByEmail(data.getEmail()))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email já está em uso.");
		if(fatherRepository.existsByCpf(data.getCpf()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CPF já está em uso.");
		
		/*Faz o Hash da senha do usuario*/
		data.setPassword(hashSaltService.hash(data.getPassword()));
		/*Define status da conta como ativa*/
		data.setStatus(Defines.STATUS_ATIVO_ACCOUNT_USER_FATHER);
		/*Converte VO para Entity e cadastra na base de dados*/
		UserFather userEntity = EdukidMapper.parseObject(data, UserFather.class);
		/*Gerar código de verificação*/
		userEntity.setCodMudarSenha(RandomStringUtils.randomAlphanumeric(8));
		/*Enviar email*/
		emailService.sendEmailChangePasword(userEntity.getEmail(), userEntity.getCodMudarSenha());
		/*Cadastrar regisro*/
		var vo = EdukidMapper.parseObject(fatherRepository.save(userEntity), UserFatherVO.class);
		
		return ResponseEntity.status(HttpStatus.OK).body("Um e-mail com o código de verifiação foi enviado ao seu e-mail, "
														+ "ultilize-o para realizar o login");
	}

	/**
	 * METODO ALTERA DADOS DO USUARIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 10 de ago. de 2024
	 * @param data
	 * @return
	 */
	public ResponseEntity<?> updateUserFather(@Valid UserFatherCadastroVO data) {
		/*Verificar se os dados que não podem ter duplicitdade está cadastrado na base de dados*/
		if(fatherRepository.existsEmailToUpdate(data.getEmail(), Integer.parseInt(data.getId())))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email já está em uso.");
		if(fatherRepository.existsCpfToUpdate(data.getCpf(), Integer.parseInt(data.getId())))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CPF já está em uso.");
		
		/*Faz o Hash da senha do usuario*/
		data.setPassword(hashSaltService.hash(data.getPassword()));
		data.setStatus(Defines.STATUS_ATIVO_ACCOUNT_USER_FATHER);

		/*Faz o Update*/
		UserFather entityUpdate = EdukidMapper.parseObject(data, UserFather.class);
		var entity = fatherRepository.save(entityUpdate);
		
		return ResponseEntity.status(HttpStatus.OK).body("Alterado com sucesso.");
	}

	/**
	 * METODO DESATIVA A CONTA DO USUARIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 10 de ago. de 2024
	 * @param id
	 * @return
	 */
	public ResponseEntity<?> desactivteUserFather(@Valid @NotBlank Integer id) {
		
		var userData = fatherRepository.findById(id);
		UserFather user= EdukidMapper.parseObject(userData, UserFather.class);
		user.setStatus(Defines.STATUS_DESATIVADO_ACCOUNT_USER_FATHER);
		fatherRepository.save(user);
		
		return ResponseEntity.status(HttpStatus.OK).body("Desativado com sucesso.");
	}
	
	
	/**
	 * METODO REALIZA A AUTHENTIFICAÇÃO DO LOGINS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 9 de jul. de 2024
	 * @param login
	 * @return STATUS CODE HTTP + DESCRICAO
	 */
	public ResponseEntity<?> authenticateLogin(LoginVO login) {
		logger.info("Autentificando Login");
		logger.info(login.getEmailOrNickName()+" "+ login.getPassword());
		
			if(fatherRepository.existsByEmail(login.getEmailOrNickName())) {
				/*Buscar registro*/				
				UserFather user = fatherRepository.findByEmail(login.getEmailOrNickName());
				logger.info(user.getCodMudarSenha());
				/*Verificar confrimação de cadastro*/
				if(user.getCodMudarSenha() != null) {
					
					/*Verificar se passou o código no json*/
					if(login.getCodVerificacao()==null)
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
								body("Credênciais inválidas, codVerificacao não pode ser nulo em caso de confirmação de conta ou troca de senha");
						
					
			    	/*Verificar código de confirmação*/
			    	if(login.getCodVerificacao().equals(user.getCodMudarSenha())) {
			    		/*Mudar password, cod_mudar_senha para null e salvar*/
			    		user.setPassword(login.getPassword());
			    		user.setCodMudarSenha(null);
			    		UserFatherCadastroVO userVO = EdukidMapper.parseObject(user, UserFatherCadastroVO.class);
			    		updateUserFather(userVO);	
			    		return ResponseEntity.status(HttpStatus.OK).body(EdukidMapper.parseObject(user, UserFatherVO.class));
			    	} else
			    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credênciais inválidas, utilize o código de verificação enviádo em seu email de cadastrado");			    		
				}
				/*Login normal*/	
			    if (hashSaltService.verifyHash(login.getPassword(), user.getPassword())) {
			        UserFatherVO userFather = EdukidMapper.parseObject(user, UserFatherVO.class);
			        return ResponseEntity.status(HttpStatus.OK).body(userFather);
			    
			    }
			}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credênciais inválidas");
	}	

	/**
	 * METODO REALIZA A TROCA DE SENHA
	 * @Author LUCAS BORGUEZAM
	 * @Sice 9 de jul. de 2024
	 * @param data
	 * @return STATUS CODE HTTP + DESCRICAO
	 */
	public ResponseEntity<?> changePassword(SolicitarMudancaSenhaVO data) {
		logger.info("Change password");
		logger.info(data.getEmail()+" ");
		
			if(fatherRepository.existsByEmail(data.getEmail())) {
				/*Buscar registro*/				
				UserFather user = fatherRepository.findByEmail(data.getEmail());
				/*Gerar código de verificação*/
				user.setCodMudarSenha(RandomStringUtils.randomAlphanumeric(8));
				/*Enviar email*/
				emailService.sendEmailChangePasword(data.getEmail(), user.getCodMudarSenha());
				/*Cadastrar codigo de troca de senha*/
				fatherRepository.save(user);
				
				return ResponseEntity.status(HttpStatus.OK).body("Um e-mail com o código de verifiação foi enviado ao seu e-mail, "
																+ "ultilize-o para realizar o login");
			    }
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credênciais inválidas");
	}	

}
	


