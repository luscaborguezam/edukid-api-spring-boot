package br.com.edukid.api.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.com.edukid.api.configurations.springsecurity.security.infra.SecurityServices;
import br.com.edukid.api.configurations.springsecurity.security.infra.TokenService;
import br.com.edukid.api.entities.Quiz;
import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.exceptions.ResourceNotFoundException;
import br.com.edukid.api.mapper.EdukidMapper;
import br.com.edukid.api.repositorys.QuizRepository;
import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.utils.JsonService;
import br.com.edukid.api.utils.StringServices;
import br.com.edukid.api.utils.UtilsService;
import br.com.edukid.api.vo.v1.LoginChildVO;
import br.com.edukid.api.vo.v1.configquiz.MateriasETemasVO;
import br.com.edukid.api.vo.v1.quiz.FieldQuizVO;
import br.com.edukid.api.vo.v1.quiz.QuizVO;
import br.com.edukid.api.vo.v1.quiz.QuizzesByDays;
import br.com.edukid.api.vo.v1.ranking.RankingVO;
import br.com.edukid.api.vo.v1.ranking.RankingsForYearElementarySchoolVO;
import br.com.edukid.api.vo.v1.user.child.UserChildCadastroVO;
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
	TokenService tokenService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    SecurityServices securityServices;
    @Autowired
    ConfigurationQuizService configurationQuizService;
    @Autowired
	QuizRepository quizRepository;
    
	/**
	 * 
	 * METODO CADASTRA DADOS DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	public ResponseEntity<?> registerUserChild(UserChildCadastroVO data) {	
		/*Faz o Hash da senha do usuario*/
		data.setPassword(hashSaltService.hash(data.getPassword()));
		
		/*Verificar se o apelido já foi cadastrado*/
		if(childRepository.existsByNickname(data.getNickname()))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Apelido já está em uso.");

		/*Converte VO para Entity e cadastra na base de dados*/
		UserChild entity = EdukidMapper.parseObject(data, UserChild.class);
		
		var vo = EdukidMapper.parseObject(childRepository.save(entity), UserChildCadastroVO.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(vo);
	}
	
	/**
	 * METODO ALTERA DADOS DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 10 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	public ResponseEntity<?> updateUserChild(UserChildCadastroVO data) {
		if(!securityServices.verifyUserFahterWithSolicitation(Integer.parseInt(data.getId())))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'id' enviado não corresponde a nenhum 'id' dos seus filhos.");
		
		/*Verificar se existe o novo nickname*/
		if(childRepository.existsNicknameToUpdate(data.getNickname(), Integer.parseInt(data.getId())))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nickname já está em uso.");
		
		Optional<UserChild> optional = childRepository.findById(Integer.parseInt(data.getId()));
		UserChild userEntity = optional.get();
		Double score, scoreWeek;
		score= userEntity.getScoreTotal();
		scoreWeek = userEntity.getScoreWeek();
		
		userEntity = EdukidMapper.parseObject(data, UserChild.class);
		userEntity.setPassword(hashSaltService.hash(data.getPassword()));
		userEntity.setScoreTotal(score);
		userEntity.setScoreWeek(scoreWeek);
		
		var entity = childRepository.save(userEntity);
		
		return ResponseEntity.status(HttpStatus.OK).body("Alterado com sucesso.");
	}
	
//	/**
//	 * METODO ALTERA SENHA DO USUARIO FILHO
//	 * @Author LUCAS BORGUEZAM
//	 * @Sice 10 de ago. de 2024
//	 * @param dataAccount
//	 * @return
//	 */
//	public ResponseEntity<?> updatePasswordUserChild(TrocarSenhaUserChild data) {
//		if(!securityServices.verifyUserFahterWithSolicitation(Integer.parseInt(data.getIdUSerChild())))
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'id' enviado não corresponde a nenhum 'id' dos seus filhos.");
//		
//		Optional<UserChild> optional = childRepository.findById(Integer.parseInt(data.getIdUSerChild()));
//		UserChild userEntity = optional.get();
//		userEntity = EdukidMapper.parseObject(data, UserChild.class);
//		userEntity.setPassword(hashSaltService.hash(data.getPassword()));
//		var entity = childRepository.save(userEntity);
//		
//		return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso.");
//	}
	
	/**
	 * METODO DELETA A CONTA DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 10 de ago. de 2024
	 * @param id
	 * @return
	 */
	public ResponseEntity<?> deleteUserChild(Integer id) {
		if(!securityServices.verifyUserFahterWithSolicitation(id))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'id' enviado não corresponde a nenhum 'id' dos seus filhos.");

		UserChild entity = childRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Esse ID não foi encontrado."));
		
		/*Deletar as relações de quiz com user child*/
		List<Quiz> quizzes = quizRepository.findByidUserChild(id);
		for(Quiz quiz : quizzes)
			quizRepository.delete(quiz);
		
		childRepository.delete(entity);
		return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso.");
	}
	
	
	/**
	 * METODO REALIZA A AUTHENTIFICAÇÃO DO LOGIN
	 * @Author LUCAS BORGUEZAM
	 * @Sice 9 de jul. de 2024
	 * @param login
	 * @return STATUS CODE HTTP + DESCRICAO
	 */
	public ResponseEntity<?> authenticateLogin(LoginChildVO login) {
		
		if(!childRepository.existsByNickname(login.getNickName()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário inexistente ou senha inválida");		
		
		var usernamePassword = new UsernamePasswordAuthenticationToken(login.getNickName(), login.getPassword());
        /*Spring security verifica email e senha*/
        var auth = this.authenticationManager.authenticate(usernamePassword);
        /*Cria o token*/
        var token = tokenService.generateToken((UserChild) auth.getPrincipal());
        				
		UserChild user = childRepository.findByNickname(login.getNickName());

		UserChildVO userChild = EdukidMapper.parseObject(user, UserChildVO.class);
		userChild.setToken(token);
		
		return ResponseEntity.status(HttpStatus.OK).body(userChild);
	}
	
	
	/**
	 * METODO BUSCA USER CHILD PELO SEU ID
	 * @Author LUCAS BORGUEZAM
	 * @Sice 10 de set. de 2024
	 * @param int1
	 * @return UserChildGetVO -> User com suas configurações
	 */
	public UserChildGetVO getUserChildById(Integer id) {
		
		if(childRepository.existsById(id)) {	
			Optional<UserChild> userOptional = childRepository.findById(id);
			UserChild user = userOptional.get();
			
			MateriasETemasVO conf = configurationQuizService.getSubjectandThemeConfiguredFromUserChild(id);
			
			UserChildGetVO userChild = EdukidMapper.parseObject(user, UserChildGetVO.class);
			if(conf != null)
				userChild.setConfiguration(conf.getMaterias());
			
			return userChild;	
		}
		return null;
	}

	/**
	 * METODO BUSCA USER CHILD PELO SEU ID
	 * @Author LUCAS BORGUEZAM
	 * @Sice 10 de set. de 2024
	 * @param int1
	 * @return ResponseEntity
	 */
	public ResponseEntity<?> getUserChild(Integer id) {
		if(!securityServices.verifyUserFahterWithSolicitation(id))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'id' enviado não corresponde a nenhum 'id' dos seus filhos.");
		
		UserChildGetVO userChild = getUserChildById(id);
		if(userChild != null) 
			return ResponseEntity.status(HttpStatus.OK).body(userChild);		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado.");
	}

	/**
	 * METODO BUSCA TODOS USERS CHILD DE UM USER FATHER
	 * @Author LUCAS BORGUEZAM
	 * @Sice 13 de out. de 2024
	 * @param idUserFather
	 * @return
	 */
	public ResponseEntity<?> getUserChildByUserFather(Integer idUserFather) {
		if(!securityServices.verifyUserFahterWithSolicitation(idUserFather.toString()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'fkUserPai' enviado não corresponde ao 'id' da conta.");
		
		if(childRepository.existsByFkUserPai(idUserFather)) {
			List<UserChild> usersChildsEntity = childRepository.findByFkUserPai(idUserFather);
			List<UserChildGetVO> usersChildsVO = new ArrayList<>();	
			for(UserChild user: usersChildsEntity)
				usersChildsVO.add(getUserChildById(user.getId()));
			return ResponseEntity.status(HttpStatus.OK).body(usersChildsVO);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado.");
	}

	/**
	 * METODO ATUALIZA PONTUAÇÃO DO USER CHILD
	 * @Author LUCAS BORGUEZAM
	 * @Sice 13 de out. de 2024
	 * @param idUserChild
	 * @param score
	 */
	public void updateScore(Integer idUserChild, Double score) {
		/*Buscar user child*/
		Optional<UserChild> opChild = childRepository.findById(idUserChild);
		UserChild child = opChild.get();
		/*Somar score total*/
		child.calculateScore(score);
		/*Salvar alteração*/
		childRepository.save(child);

	}

	
	public RankingsForYearElementarySchoolVO getAllRankingWeekForUserFather(Integer idUserFather) {
		RankingsForYearElementarySchoolVO allRankingWeek = new RankingsForYearElementarySchoolVO();
		/*Buscar os anos de ensino onde os usuários filhos do usuário pai estão*/
		Set<Integer> years = new HashSet<>();
		List<UserChild> childsOfUserFather = childRepository.findChildByFkUserPai(idUserFather);
		for(UserChild child : childsOfUserFather) {
			years.add(child.getSchoolYear());
		}
		
		
		for(Integer ano: years) {
			List<RankingVO> rankinVO = getRankinWeekForUserFather(idUserFather, ano);
			allRankingWeek.addElemento(ano.toString(), rankinVO);
		}
		return allRankingWeek;
	}
	
	/**
	 * METODO BUSCA RANKING SEMANAL POR ANO DO ENSINO MéDIO MOSTRANDO OS NOMES DOS USUÁRIOS FILHOS RELACIONADOS A ID DO USER FATHER
	 * @Author LUCAS BORGUEZAM
	 * @Sice 13 de out. de 2024
	 * @param id
	 * @return
	 */
	private List<RankingVO> getRankinWeekForUserFather(Integer idUserFather, Integer ano) {
		List<RankingVO> rankinVO = new ArrayList<>();
		
		List<UserChild> rankingEntity = childRepository.getRankingForScoreWeek(ano);
		List<UserChild> childsOfUserFather = childRepository.findChildByFkUserPai(idUserFather);
		
		Integer position = 0;
		for(UserChild userRanking : rankingEntity) {
			position++;
			
			/*Verificar se o userChild percorrido está na lista childsOfUSerFather*/
			boolean isChildOfUserFather = childsOfUserFather.stream()
		            .anyMatch(child -> child.getId().equals(userRanking.getId()));
			
			/*Se existir adicionar objeto com nome*/
			if(isChildOfUserFather) {
				RankingVO userPosition = new RankingVO(
						position, 
						(userRanking.getFirstName()+" "+userRanking.getLastName()),
						userRanking.getScoreWeek()
				);
				rankinVO.add(userPosition);
			}
			else {
				RankingVO userPosition = new RankingVO(
						position, 
						"User Edukid",
						userRanking.getScoreWeek()
				);
				rankinVO.add(userPosition);
			}
		}//Fim for
		
		return rankinVO;
	}
	
	/**
	 * METODO VERIFICA TOKEN COM O ID ENVIADO E BUSCA O RANKING PARA O USER CHILD
	 * @Author LUCAS BORGUEZAM
	 * @Sice 13 de out. de 2024
	 * @param idUserChild
	 * @return
	 */
	public ResponseEntity<?> getRankingWeekForUserChild(Integer idUserChild) {
		if(!securityServices.verifyUserChildWithSolicitation(idUserChild))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'id' enviado não corresponde ao seu 'id'.");
		
		Optional<UserChild> opChild = childRepository.findById(idUserChild);
		UserChild child = opChild.get();
		List<RankingVO> rankinVO = getRankinWeekForUserChild(idUserChild, child.getSchoolYear());
		
		return ResponseEntity.status(HttpStatus.OK).body(rankinVO);
	}
	
	/**
	 * METODO BUSCA RANKINGS SEMANAL POR ANO DO ENSINO MéDIO MOSTRANDO OS NOMES DOS USUÁRIOS FILHOS RELACIONADOS A ID DO USER FATHER
	 * @Author LUCAS BORGUEZAM
	 * @Sice 13 de out. de 2024
	 * @param id
	 * @return
	 */
	public List<RankingVO> getRankinWeekForUserChild(Integer idUserChild, Integer ano) {
		List<RankingVO> rankinVO = new ArrayList<>();
		
		List<UserChild> rankingEntity = childRepository.getRankingForScoreWeek(ano);
		Optional<UserChild> opChild = childRepository.findById(idUserChild);
		UserChild child = opChild.get();
		Integer position = 0;
		for(UserChild userRanking : rankingEntity) {
			position++;
			
			/*Verificar se o userChild percorrido está na lista childsOfUSerFather*/
			if(child.getId() == userRanking.getId()) {
				RankingVO userPosition = new RankingVO(
						position, 
						(userRanking.getFirstName()+" "+userRanking.getLastName()),
						userRanking.getScoreWeek()
				);
				rankinVO.add(userPosition);
			}
			else {
				RankingVO userPosition = new RankingVO(
						position, 
						"User Edukid",
						userRanking.getScoreWeek()
				);
				rankinVO.add(userPosition);
			}
		}//Fim for
		
		return rankinVO;
	}

	/**
	 * METODO VERIFICA ID E BUSCA O HISTÓRICO DE QUIZZES PARA USER CHILD
	 * @Author LUCAS BORGUEZAM
	 * @Sice 19 de out. de 2024
	 * @param idUserChild
	 * @param j 
	 * @param month 
	 * @return
	 */
	public ResponseEntity<?> getQuizzezHistory(Integer idUserChild, Integer month, Integer year) {
		if(!securityServices.verifyUserChildWithSolicitation(idUserChild))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'id' enviado não corresponde ao seu 'id'.");
		
		QuizzesByDays quizzesByDays = findQuizHistory(idUserChild, month, year);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(quizzesByDays);
	}

	/**
	 * METODO BUSCA HISTÓRICO DE QUIZ POR DIA DO USER CHILD ESPECÍFICO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 19 de out. de 2024
	 * @param idUserChild
	 * @return
	 */
	private QuizzesByDays findQuizHistory(Integer idUserChild, Integer month, Integer year) {
		QuizzesByDays quizzesByDays = new QuizzesByDays();
		/*Buscar quizzes do periodo*/
		List<Quiz> queizzesEntity = quizRepository.getHistoryQuizzesByPeriod(idUserChild, month, year);
		/*Transofrmar em VO*/
		for(Quiz quizEntity : queizzesEntity) {
			QuizVO quizVORegistred = configurationQuizService.getQuizVO(quizEntity);
			FieldQuizVO fieldQuizVO = quizVORegistred.getQuiz();
			QuizVO quizVO = new QuizVO(quizEntity, fieldQuizVO);
			/*Adicionar a lista de retorno*/
			quizzesByDays.addQuiz(quizVO);
		}
		
		return quizzesByDays;
	}

	/**
	 * METODO BUSCA HISTÓRICO DE QUIZ POR DIA DOS USERS CHILDS DO USER FAHTER
	 * @Author LUCAS BORGUEZAM
	 * @Sice 19 de out. de 2024
	 * @param id
	 * @return
	 */
	public List<QuizzesByDays> findQuizHistoryForUserFather(Integer idUserFather, Integer month, Integer year) {
		List<QuizzesByDays> quizzesByDays = new ArrayList<>();
		
		/*Buscar usuários child relacionados ao pai*/
		List<UserChild> childsOfUserFather = childRepository.findChildByFkUserPai(idUserFather);
		
		for(UserChild childEntity: childsOfUserFather) {
			QuizzesByDays byDays = findQuizHistory(childEntity.getId(), month, year);
			byDays.setId(childEntity.getId().toString());
			byDays.setNome(childEntity.getFirstName()+" "+childEntity.getLastName());
			
			quizzesByDays.add(byDays);
		}
		
		
		return quizzesByDays;
	}	
	
	
	

}
