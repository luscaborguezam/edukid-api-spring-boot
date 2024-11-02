package br.com.edukid.api.services;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.com.edukid.api.configurations.springsecurity.security.AuthorizationService;
import br.com.edukid.api.configurations.springsecurity.security.infra.SecurityServices;
import br.com.edukid.api.configurations.springsecurity.security.infra.TokenService;
import br.com.edukid.api.entities.UserChild;

//import com.github.dozermapper.core.Mapper;

import br.com.edukid.api.entities.UserFather;
import br.com.edukid.api.mapper.EdukidMapper;
import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.repositorys.UserFatherRepository;
import br.com.edukid.api.utils.Defines;
import br.com.edukid.api.utils.EmailService;
import br.com.edukid.api.utils.UtilsService;
import br.com.edukid.api.vo.v1.LoginFatherVO;
import br.com.edukid.api.vo.v1.SolicitarMudancaSenhaVO;
import br.com.edukid.api.vo.v1.UserFatherCadastroVO;
import br.com.edukid.api.vo.v1.UserFatherVO;
import br.com.edukid.api.vo.v1.quiz.QuizzesByDays;
import br.com.edukid.api.vo.v1.ranking.RankingVO;
import br.com.edukid.api.vo.v1.ranking.RankingsForYearElementarySchoolVO;
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
	@Autowired
	TokenService tokenService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    SecurityServices securityServices;
    @Autowired 
    UserChildService childService;
    @Autowired
    ConfigurationQuizService configurationQuizService;
	
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
		/*Verificar se o email e cpf já foi cadastrado*/
		if(fatherRepository.existsByEmail(data.getEmail()))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email já está em uso.");
		if(fatherRepository.existsByCpf(data.getCpf()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CPF já está em uso.");
		if(fatherRepository.existsByPhone(data.getPhone()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Telefone já está em uso.");
		
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
	public ResponseEntity<?> updateUserFather(@Valid UserFatherCadastroVO data, Boolean isAlterDataUser) {
		
		if(isAlterDataUser)
			if(!securityServices.verifyUserFahterWithSolicitation(data))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'id' enviado não corresponde ao 'id' do usuário");
		
        
		/*Verificar se os dados que não podem ter duplicitdade está cadastrado na base de dados*/
		if(fatherRepository.existsEmailToUpdate(data.getEmail(), Integer.parseInt(data.getId())))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email já está em uso.");
		if(fatherRepository.existsCpfToUpdate(data.getCpf(), Integer.parseInt(data.getId())))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CPF já está em uso.");
		if(fatherRepository.existsPhoneToUpdate(data.getPhone(), Integer.parseInt(data.getId())))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Telefone já está em uso.");
		
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
		if(!securityServices.verifyUserFahterWithSolicitation(id.toString()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'id' enviado não corresponde ao 'id' do usuário");
		
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
	public ResponseEntity<?> authenticateLogin(LoginFatherVO login) {
		logger.info("\n\nAutentificando Login");
		logger.info(login.getEmail()+" "+ login.getPassword());
		
		if(!fatherRepository.existsByEmail(login.getEmail()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário inexistente ou senha inválida");			    		
		
		
		var usernamePassword = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        /*Spring security verifica email e senha*/
        var auth = this.authenticationManager.authenticate(usernamePassword);
        /*Cria o token*/
        var token = tokenService.generateToken((UserFather) auth.getPrincipal());
		
		/*Buscar registro*/
		UserFather user = fatherRepository.findByEmail(login.getEmail());
		logger.info(user.getCodMudarSenha());
		/*Verificar confrimação de cadastro ou mudar senha*/
		if(user.getCodMudarSenha() != null) {
			
			/*Verificar se passou o código no json*/
			if(login.getCodVerificacao()==null)
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
						body("Credênciais inválidas, codVerificacao não pode ser nulo em caso de confirmação de conta ou troca de senha");
			 System.out.println("\n\nVerificar código de confirmação\n\n");	
	    	/*Verificar código de confirmação*/
	    	if(login.getCodVerificacao().equals(user.getCodMudarSenha())) {
	    		/*Mudar password, cod_mudar_senha para null e salvar*/
	    		user.setPassword(login.getPassword());
	    		user.setCodMudarSenha(null);
	    		
	    		UserFatherCadastroVO userVO = EdukidMapper.parseObject(user, UserFatherCadastroVO.class);
	    		System.out.println("\n\nDepois do mapper\n\n");	
	    		updateUserFather(userVO, false);
	    	} else
	    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credênciais inválidas, utilize o código de verificação enviádo em seu email de cadastrado");			    		
		}
		/*Login normal*/	

        UserFatherVO userFather = EdukidMapper.parseObject(user, UserFatherVO.class);
        userFather.setToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(userFather);
			
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

	/**
	 * METODO VERIFICA SE HÁ ALGUM CODIGO DE VERIFICAÇÃO CADASTRADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de set. de 2024
	 * @param solicitacaoMudancaSenha
	 * @return
	 */
	public ResponseEntity<?> isVerifyAccount(String email) {
		logger.info("Is verify account?");
		
		/*Verificar se o email passado já está cadastrado*/
		if(fatherRepository.existsByEmail(email)) {
			/*Buscar registro*/				
			UserFather user = fatherRepository.findByEmail(email);
			/*Verificar confrimação de cadastro*/
			if(user.getCodMudarSenha() != null) {
					return ResponseEntity.status(HttpStatus.OK).body("True");
			}else {
				return ResponseEntity.status(HttpStatus.OK).body("False");
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credênciais inválidas");
	}

	/**
	 * METODO O SCORE, POSIÇÃO NO RANKING E NOME DOS USER CHILD RELACIONADOS COM OPAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 13 de out. de 2024
	 * @param int1
	 * @return
	 */
	public ResponseEntity<?> getRanking(Integer id) {
		if(!securityServices.verifyUserFahterWithSolicitation(id.toString()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'fkUserPai' enviado não corresponde ao 'id' da conta.");
		
		RankingsForYearElementarySchoolVO allRankings = childService.getAllRankingWeekForUserFather(id); 
		
		return ResponseEntity.status(HttpStatus.OK).body(allRankings);
	}

	/**
	 * METODO VERIFICA ID E BUSCA O HISTÓRICO DE QUIZZES PARA USERS CHILD DO USER PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 19 de out. de 2024
	 * @param id
	 * @param month
	 * @param year 
	 * @return
	 */
	public ResponseEntity<?> getQuizzezHistory(Integer id, Integer month, Integer year) {
		if(!securityServices.verifyUserFahterWithSolicitation(id.toString()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'id' enviado não corresponde ao 'id' da conta.");
		
		List<QuizzesByDays> quizzesByDays = configurationQuizService.findQuizHistoryForUserFather(id, month, year);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(quizzesByDays);
	}

}
	


