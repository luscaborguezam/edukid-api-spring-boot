package br.com.edukid.api.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.edukid.api.configurations.springsecurity.security.infra.SecurityServices;
import br.com.edukid.api.entities.Configuration;
import br.com.edukid.api.entities.Conteudo;
import br.com.edukid.api.entities.Materia;
import br.com.edukid.api.entities.Pergunta;
import br.com.edukid.api.entities.Quiz;
import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.entities.UserFather;
import br.com.edukid.api.mapper.EdukidMapper;
import br.com.edukid.api.repositorys.ConfigurationRepository;
import br.com.edukid.api.repositorys.ConteudoRepository;
import br.com.edukid.api.repositorys.MateriaRepository;
import br.com.edukid.api.repositorys.PerguntaRepository;
import br.com.edukid.api.repositorys.QuizRepository;
import br.com.edukid.api.repositorys.TemaAprendizagemRepository;
import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.repositorys.UserFatherRepository;
import br.com.edukid.api.utils.Defines;
import br.com.edukid.api.utils.EmailService;
import br.com.edukid.api.utils.JsonService;
import br.com.edukid.api.vo.v1.configquiz.InfoPergunta;
import br.com.edukid.api.vo.v1.configquiz.MateriaVO;
import br.com.edukid.api.vo.v1.configquiz.MateriasETemasVO;
import br.com.edukid.api.vo.v1.configquiz.PerguntaVO;
import br.com.edukid.api.vo.v1.configquiz.TemaAprendizagemVO;
import br.com.edukid.api.vo.v1.contents.ConteudoParaEstudo;
import br.com.edukid.api.vo.v1.contents.MateriaDoConteudo;
import br.com.edukid.api.vo.v1.performance.QuizPerformanceData;
import br.com.edukid.api.vo.v1.quiz.FieldQuizVO;
import br.com.edukid.api.vo.v1.quiz.QuizByMateriaVO;
import br.com.edukid.api.vo.v1.quiz.QuizVO;

@Service
public class ConfigurationQuizService {
	
	@Autowired
	MateriaRepository materiaRepository;
	@Autowired
	TemaAprendizagemRepository temaAprendizagemRepository;
	@Autowired
	UserChildRepository childRepository;
	@Autowired
	JsonService jsonService;
	@Autowired
	PerguntaRepository perguntaRepository;
	@Autowired
	QuizRepository quizRepository;
	@Autowired
	ConfigurationRepository configurationRepository;
	@Autowired
	SecurityServices securityServices;
	@Autowired
	ConteudoRepository conteudoRepository;
	@Autowired
	UserFatherRepository fatherRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	PerformanceService performanceService;
	@Autowired 
	UserChildService childService;
	
	
	
	
	/**
	 * METODO BUSCA MATERIAS E TEMAS RELACIONADO AO ANO DO ENSINO FUNDAMENTAL DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 28 de ago. de 2024
	 * @param yearElementarySchool
	 * @return
	 */
	public ResponseEntity<?> getSubjectAndTheme(Integer yearElementarySchool) {
		MateriasETemasVO  materiasVO = new MateriasETemasVO();
		
		List<Materia> materias = materiaRepository.findDistinctMateriasByYearElementarySchool(yearElementarySchool);
		for(Materia m: materias) {
			MateriaVO materiaVO = EdukidMapper.parseObject(m, MateriaVO.class);
			materiaVO.setTemas(EdukidMapper.parseListObjects(temaAprendizagemRepository.findByYearElementarySchoolAndIdSubject(yearElementarySchool, m.getId()), TemaAprendizagemVO.class));
			materiasVO.addMateriaVO(materiaVO);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(materiasVO);
	}
	
	/**
	 * METODO SALVA AS MATERIAS E TEMAS ESCOLHIDO PELO USUARIO PAI PARA AS CONFIGURACOES DO QUIZ
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de ago. de 2024
	 * @param materiasETemasVO
	 * @return
	 */
	public ResponseEntity<?> registerConfQuiz(MateriasETemasVO materiasETemasVO){
		if(!securityServices.verifyUserFahterWithSolicitation( Integer.parseInt( materiasETemasVO.getIdUserChild()) ))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'id' enviado não corresponde a nenhum 'id' dos seus filhos.");
	
		
		if(!configurationRepository.existsById(Integer.parseInt(materiasETemasVO.getIdUserChild())))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User child not found");
		/*Buscar registro*/
		Optional<Configuration> opConfQuiz = configurationRepository.findById(Integer.parseInt(materiasETemasVO.getIdUserChild()));
		Configuration confQuiz = opConfQuiz.get();
		/*Setar o json de configuracao*/
		confQuiz.setConfiguration(jsonService.toJson(materiasETemasVO));
		configurationRepository.save(confQuiz);
		
		return ResponseEntity.status(HttpStatus.OK).body("Registrado!");
	}
	
	
	/**
	 * METODO BUSCA AS MATERIAS E TEMAS SALVOS NAS CONFIGURACOES PARA GERAR UM QUIZ PERSONALIZADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de ago. de 2024
	 * @param idUserChild
	 * @return
	 */
	public MateriasETemasVO getSubjectandThemeConfiguredFromUserChild(Integer idUserChild){
		
		/*Buscar registro*/
		Optional<Configuration> opConfQuiz = configurationRepository.findById(idUserChild);
		Configuration confQuiz = opConfQuiz.get();
		MateriasETemasVO mt = jsonService.fromJson(confQuiz.getConfiguration(), MateriasETemasVO.class);
		mt.setIdUserChild(idUserChild.toString());
		
		return mt;
	}
	
	/**
	 * METODO VERIFICA SE O USER DO TOKEM COM O ID DO USER PASSADO E CHAMA A CRIAÇÃO DO QUIZ OU UM QUIZ JÁ FEITO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 1 de out. de 2024
	 * @param idUserChild
	 * @return
	 */
	public ResponseEntity<?> getQuiz(Integer idUserChild){
		if(!securityServices.verifyUserChildWithSolicitation(idUserChild))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'idUserChild' enviado não corresponde ao seu 'id'.");
		return toGenerateQuiz(idUserChild);		
	}
	
	/**
	 * METODO GERA QUIZ ALEATORIO COM AS CONFIGURACOES CADASTRADAS, ou retorna um quiz atual em aberto
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de ago. de 2024
	 * @param idUserChild
	 * @return
	 */
	public ResponseEntity<?> toGenerateQuiz(Integer idUserChild){
		System.out.println("\nCriar ou buscar quiz em aberto");
		FieldQuizVO fielQuiz = new FieldQuizVO();
		Quiz quizEntity = new Quiz();
		QuizVO quiz = null;
		
		/*Verificar se existe um quiz criado e aberto na data atual*/
		System.out.println(quizRepository.existsQuizOpenByIdUserChild(idUserChild, LocalDate.now()));
		if(quizRepository.existsQuizOpenByIdUserChild(idUserChild, LocalDate.now())) {
			quizEntity = quizRepository.findQuizOpenByIdUserChild(idUserChild, LocalDate.now());
			fielQuiz = jsonService.fromJson(quizEntity.getQuiz(), FieldQuizVO.class);
			quiz = new QuizVO(quizEntity, fielQuiz);
			return ResponseEntity.status(HttpStatus.OK).body(quiz);
		}
		
		/*Buscar as configurações do quiz*/
		Optional<Configuration> opConfQuiz = configurationRepository.findById(idUserChild);
		Configuration confEntity = opConfQuiz.get();
		/*Transformar json de configuração em objeto*/
		MateriasETemasVO confQuiz = jsonService.fromJson(confEntity.getConfiguration(), MateriasETemasVO.class);
		
		if(confQuiz == null) 
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autorizado, para criar o quiz primeiro você deve cadastrar as configurações do quiz.");
		
		
		/*Buscar o quiz de cada matéria e temas relcionados a matéria segundo a configuração cadastrada para o usuário filho*/
		List<QuizByMateriaVO> quizMateriaVOs = getQuizForSubject(confQuiz);

		/*Definir a quantidade de questões no quiz final*/
		
	
		/*Adicionar ao quiz a quantidade de questões definida nas configurações do user child*/
		fielQuiz.setMaterias(quizMateriaVOs);
		
		/*Cadastrar quiz*/
		quizEntity = new Quiz(jsonService.toJson(fielQuiz), confEntity);
		/*Inserir com query nativa para adicionar data de criação pelo mysql*/
		quizRepository.insertQuizWithoutstartDate(
				quizEntity.getQuiz(),
				quizEntity.getIsfinalized(),
				quizEntity.getIdUserChild()
				);
		
		quizEntity = quizRepository.findQuizOpenByIdUserChild(idUserChild, LocalDate.now());
		
		/*Objeto de retorno*/
		quiz = new QuizVO(quizEntity, fielQuiz);
		
        return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
	}
	
	
	/**
	 * METODO CRIA QUIZ POR MATÉRIAS E TEMAS SALVOS NAS CONFIGURAÇÃO DO USUÁRIO 
	 * O QUIZ DE CADA MATÉRIA TEM O NÚMERO DE QUESTÕES DEFINIDAS NA CONFIGURAÇÃO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de set. de 2024
	 * @param confQuiz
	 * @return
	 */
	private List<QuizByMateriaVO> getQuizForSubject(MateriasETemasVO confQuiz) {
		List<QuizByMateriaVO> subjectsTheQuestions = new ArrayList<>();
		

		for(MateriaVO subject : confQuiz.getMaterias()){
			/*Buscar nome de cada matéria*/
			Optional<Materia> optional = materiaRepository.findById(Integer.parseInt(subject.getId()));
			Materia subjectEntity = optional.get();
			
			QuizByMateriaVO subjectTheQuestion = new QuizByMateriaVO(subjectEntity);
			List<PerguntaVO> perguntasVO = new ArrayList<>();
			
			for(TemaAprendizagemVO theme: subject.getTemas()) {	
			/*Buscar e criar um quiz com a lista de perguntas relaconadas aos temas para cada matéria listadas na configuração*/
				/*Buscar lista de perguntas relacionadas ao tema*/
				List<Pergunta> perguntas = perguntaRepository.findPerguntasByIdTema(Integer.parseInt(theme.getId()));
				/*Converter objeto*/
				perguntasVO = convertPerguntasToPerguntasVO(perguntas, perguntasVO, subject.getId());
			}
	        /*Embaralhar os elementos da lista(quiz)*/
	        Collections.shuffle(perguntasVO, new Random());
			/*Adicionar o quiz com a quantidade de perguntas definidas na configuração a matéria*/
	        Integer questionsQuantity = Integer.parseInt(subject.getQuantityQuestions());
			subjectTheQuestion.setQuiz(perguntasVO.subList(0, Math.min(questionsQuantity, perguntasVO.size())));
			/*Adicionar matéria a lista de matérias*/
			subjectsTheQuestions.add(subjectTheQuestion);
		}
		
		return subjectsTheQuestions;	
	}
	
	/**
	 * METODO TRANSFORMA OBJETO PERGUNTA DA LISTA DE OBJETOS PERGNTA PARA OBJETO PERGUNTASVO E ADICIONA A LISTA PERGUNTASVO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de set. de 2024
	 * @param perguntas LISTA DE OBJETO PERGUNTAS
	 * @param x 
	 * @return
	 */
	private List<PerguntaVO> convertPerguntasToPerguntasVO(List<Pergunta> perguntas, List<PerguntaVO> perguntasVO, String id_subject) {
		/*Transformar Lista de perguntas no objeto Quiz para retorno*/
		for (Pergunta p : perguntas) {
			/*Transformar Pergunta em PerguntaVO*/
				PerguntaVO perguntaVO = new PerguntaVO(p, id_subject);
				InfoPergunta infoPergunta = jsonService.fromJson(p.getInfoPergunta(), InfoPergunta.class);
				/*Adicionar infoPergunta a lista de perguntasVO*/
				perguntaVO.addItemInListInfoPergunta(infoPergunta);
				perguntasVO.add(perguntaVO);
		}
		
		return perguntasVO;
	}

	/**
	 * METODO REALIZA AlTERAÇÃO DO QUIZ REALIZADO DO QUIZ REALIZADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 11 de set. de 2024
	 * @param quizRealized
	 * @return
	 */
	public ResponseEntity<?> registerQuizRealized(QuizVO quizRealized) {
		if(!securityServices.verifyUserChildWithSolicitation( Integer.parseInt(quizRealized.getIdUserChild()) ))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'idUserChild' enviado não corresponde ao seu 'id'.");

		
		/*Verificar se existe um quiz criado na data atual*/
		if(!quizRepository.existsQuizOpenByIdUserChild(Integer.parseInt(quizRealized.getIdUserChild()), LocalDate.now()))
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Não há nenhum quiz criado na data de hoje ("+LocalDate.now()+")");
		
		/*Verificar se existe o quiz pelo id do quiz passdo pelo userchild*/
		if(!quizRepository.existsQuizOpenByIdWhereIdUserChild(
				Integer.parseInt(quizRealized.getIdUserChild()), 
				Integer.parseInt(quizRealized.getId()),
				LocalDate.now()))
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Não há nenhum quiz com o 'id' informado!");
		
		/*Buscar quiz na base de dados e trasformar em objeto QuizVO*/
		Optional<Quiz> opQuizEntity = quizRepository.findById(Integer.parseInt(quizRealized.getId()));
		Quiz quizEntity = opQuizEntity.get();
		
		
		FieldQuizVO fieldQuizRealized = jsonService.fromJson(quizEntity.getQuiz(), FieldQuizVO.class);
		QuizVO quizRegistred = new QuizVO(quizEntity, fieldQuizRealized);
		
		
		Map<String, String> map = validationQuizRegistredWithQuizSended(quizRegistred, quizRealized);
		if(map.get("Verification").equals("OK")) {
			/*Adicionar os dados necessários quando finalizados*/
			quizRegistred = updateDataWithQuizRealizedData(quizRegistred, quizRealized);
			/*Transformar objeto de volta em Quiz(Entity)*/
			quizEntity.updateDataWithQuizRealized(quizRegistred, jsonService.toJson(quizRealized.getQuiz()));
			/*atualizar registro*/
			quizRepository.save(quizEntity);
			
			/*Salvar pontuação*/
			childService.updateScore(Integer.parseInt(
					quizRealized.getIdUserChild()), 
					Double.parseDouble(fieldQuizRealized.getScore())
				);
			
			/*Corrigir quiz eenviar emaila o pai*/
			toCorrectQuiz(quizEntity.getId());
	
			return ResponseEntity.status(HttpStatus.OK).body(quizRegistred);
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(map.get("Verification"));
	}
	

	/**
	 * METODO COMPARA E VALIDA AS INFORMAÇÕES QUE NÃO SE DEVEM MUDAR DO QUIZ CADASTRADO COM O QUIZ ENVIADO
	 * GARANTE QUE O QUIZ ENVIADO É INTEGRO AO QUIZ CRIADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 14 de set. de 2024
	 * @param quizRealized
	 * @param quizRealized2
	 * @return
	 */
	private Map<String, String> validationQuizRegistredWithQuizSended(QuizVO quizRegistred, QuizVO quizRealized) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("Verification", "OK");
		
		String regex = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,9})?$";
        if(quizRealized.getEndDate()==null || !quizRealized.getEndDate().matches(regex)) {
			map.put("Verification", "Campo 'endDate' inválido, deve ser uma string no formato 'yyyy-MM-ddTHH:mm:ss.SSSSSSSSS' exemplo: 2024-09-11T15:30:45.01");
			return map;
        }
        
        LocalDateTime dataQuizRealized = LocalDateTime.parse(quizRealized.getEndDate());
        if (!dataQuizRealized.toLocalDate().isEqual(LocalDate.now())){
        	map.put("Verification", "'endDate' não pode ser uma data diferente da data atual. Data atual: "+LocalDate.now());
			return map;
        }
            
		if(quizRealized.getIsFinalized().equals("0")) {
			map.put("Verification", "isRealized do quiz enviado não pode ser igual a '0'(status aberto) ");
			return map;
		}
		
		if(!quizRealized.getIdUserChild().equals(quizRegistred.getIdUserChild())) {
			map.put("Verification", "id do user child do quiz enviado é diferente ao do quiz criado");
			return map;
		}
		
		FieldQuizVO quizRz = quizRealized.getQuiz();
		
			regex = "^[+-]?([0-9]*[.])?[0-9]+$";
			if(quizRz.getScore() == null|| !quizRz.getScore().matches(regex)) {
				map.put("Verification", "'score' inválido, uma string com formato float válido");
				return map;				
			}
			
			regex = "^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";
			if(quizRz.getTotalTime() == null|| !quizRz.getTotalTime().matches(regex)) {
				map.put("Verification", "'totalTime' deve ser uma string com formato de tempo, experado HH:mm:ss");
				return map;				
			}
						
			if(quizRegistred.getQuiz().getMaterias().size() != quizRealized.getQuiz().getMaterias().size()) {
				map.put("Verification", "Quantidade de materias do quiz enviado é diferente do quiz criado\n"
						+ " Quantidade esperada: "+quizRegistred.getQuiz().getMaterias().size() + "\n"
						+ " Quantidade enviada: "+quizRealized.getQuiz().getMaterias().size()
						
						);
				return map;				
			}
			
			
			for(int i=0; i < quizRegistred.getQuiz().getMaterias().size() ; i++ ) {//QuizByMateriaVO m: quizRz.getMaterias()
				QuizByMateriaVO quizByMateriaRz = quizRz.getMaterias().get(i);
				QuizByMateriaVO quizByMateriaRg = quizRegistred.getQuiz().getMaterias().get(i);

				if(!quizByMateriaRg.getId().equals(quizByMateriaRz.getId())) {
					map.put("Verification", "id da matéria(subject) do quiz enviado é diferente do quiz criado\n"
							+ " Id materia esperada: "+quizByMateriaRg.getId()+"\n"
							+ " Id materia enviada: "+quizByMateriaRz.getId());
					return map;					
				}

				
				/*Verificar cada perguntaVO*/
				for(int c=0; c < quizByMateriaRg.getQuiz().size(); c++) {
						PerguntaVO perguntaRz = quizByMateriaRz.getQuiz().get(c);
						PerguntaVO perguntaRg = quizByMateriaRg.getQuiz().get(c);
					
					if(!perguntaRg.getId().equals(perguntaRz.getId())) {
						map.put("Verification", "id da questão do quiz enviado é diferente do quiz criado\n"
								+ " Id pergunta esperada: "+perguntaRg.getId()+"\n"
								+ " Id pergunt enviada: "+perguntaRz.getId());
						return map;						
					}
					/*Verificar informaç~eos da pergunta*/
					for(int l=0; l < perguntaRg.getInfoPerguntas().size(); l++) {
						InfoPergunta infoPerguntaRz =  perguntaRz.getInfoPerguntas().get(l);
						InfoPergunta infoPerguntaRg =  perguntaRg.getInfoPerguntas().get(l);
						
						if(!infoPerguntaRg.getQuestion().equals(infoPerguntaRz.getQuestion())) {
							map.put("Verification", "'question' do quiz enviado é diferente do quiz criado.\n"
									+ " Id pergunta esperada: "+perguntaRg.getId()+"\n"
									+ " Id pergunt enviada: "+perguntaRz.getId()+"\n"
									+ " pergunta esperada: "+infoPerguntaRg.getQuestion()+"\n"
									+ " pergunta enviada: "+infoPerguntaRz.getQuestion());
							return map;							
						}
						
						if(!infoPerguntaRg.getOptions().contains(infoPerguntaRz.getSelectedAnswer())) {
							map.put("Verification", "'selectedAnswer' não corresponde a nenhuma opção da lista options do quiz criado\n"	
									+ "'selectedAnswer': '"+infoPerguntaRz.getSelectedAnswer()+"'\n"
									+ "Id pergunta esperada: "+perguntaRg.getId()+"\n"
									+ " Id pergunt enviada: "+perguntaRz.getId()+"\n"
									+ " options esperada: " +jsonService.toJson(infoPerguntaRg.getOptions())+"\n"
									+ " options enviada: "+jsonService.toJson(infoPerguntaRz.getOptions()));
							return map;							
						}
					}//for(infoPerguntas)
				}//for(Quiz)
				
			}//for(meterias)

			
			
		return map;
	}
	
	/**
	 * METODO ALTERA OS ATRIBUTOS DO QUIZ REGISTRADO COM O QUIZ REALIZADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 22 de set. de 2024
	 * @param quizRegistred
	 * @param quizRealized
	 * @return
	 */
	private QuizVO updateDataWithQuizRealizedData(QuizVO quizRegistred, QuizVO quizRealized) {
		//Adicionar endDate
		quizRegistred.setEndDate(quizRealized.getEndDate());
		//Adicionar isFinalized
		quizRegistred.setIsFinalized(quizRealized.getIsFinalized());
		
		/*Objeto FieldQuiz*/
			//Adicionar score
			quizRegistred.getQuiz().setScore(quizRealized.getQuiz().getScore());
			//Adicionar totaltime
			quizRegistred.getQuiz().setTotalTime(quizRealized.getQuiz().getTotalTime());
			
			/*List de materias (QuizByMateriaVO)*/
			for(int i=0; i < quizRegistred.getQuiz().getMaterias().size() ; i++ ) {//QuizByMateriaVO m: quizRz.getMaterias()
				QuizByMateriaVO quizByMateriaRz = quizRealized.getQuiz().getMaterias().get(i);
				QuizByMateriaVO quizByMateriaRg = quizRegistred.getQuiz().getMaterias().get(i);

				/*Lista de PerguntaVO*/
				for(int c=0; c < quizByMateriaRg.getQuiz().size(); c++) {
					
					/*Lista de InfoPergunta*/
					for(int l=0; l < quizByMateriaRg.getQuiz().get(c).getInfoPerguntas().size(); l++) {
						InfoPergunta infoPerguntaRz =  quizByMateriaRz.getQuiz().get(c).getInfoPerguntas().get(l);
						InfoPergunta infoPerguntaRg =  quizByMateriaRg.getQuiz().get(c).getInfoPerguntas().get(l);
						//Adicionar selectedAnswer
						quizRegistred.getQuiz().getMaterias().get(i).getQuiz().get(c).getInfoPerguntas().get(l).setSelectedAnswer(infoPerguntaRz.getSelectedAnswer());
						
					}//for(infoPerguntas)
				}//for(Quiz)
			}//for(meterias)
		return quizRegistred;	
	}//fim updateDataWithQuizRealizedData

	
	/**
	 * METODO FECHA UM QUIZ EM ABERTO NÃO REALIZADO ESPECÍFICO E ENVIA EMAIL AO USUARIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 5 de out. de 2024
	 * @param quiz
	 */
	public void closeQuizOpen(Integer quizId) {
		
		Optional<Quiz> opQuiz= quizRepository.findById(quizId);
		Quiz quiz = opQuiz.get();
		
		if(quiz.getIsfinalized() == Defines.QUIZ_EM_ABERTO) {
			
			Optional<UserChild> opChild = childRepository.findById(quiz.getIdUserChild());
			UserChild child = opChild.get();
			Optional<UserFather> opFather = fatherRepository.findById(child.getFkUserPai());
			UserFather father = opFather.get();
			

			/*Fechar quiz*/
			quizRepository.updateIsFinalizedbyId(quiz.getId());
			
			String to = father.getEmail();
			
			/*Buscar quantidade de perguntas no quiz*/
			FieldQuizVO fielQuiz = jsonService.fromJson(quiz.getQuiz(), FieldQuizVO.class);
			Integer qtdPerguntas = 0;
			for(QuizByMateriaVO materia: fielQuiz.getMaterias()) {
				qtdPerguntas += materia.getQuiz().size();
			}
			
			QuizPerformanceData quizPerformance = new QuizPerformanceData();
			quizPerformance.setNameUserChild(child.getFirstName());
			quizPerformance.setNickName(child.getNickname());
			quizPerformance.setTotalQuestions(qtdPerguntas);
			
			/*Enviar email de quiz não realizado*/
			try {
				emailService.sendEmailQuizNotRealized(to, quizPerformance);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("runWithIicialization, Erro ao enviar e-mail");
			}
		}//if()
	}
		
		
	/**
	 * METODO CONTABILZA AS QUESTÕES DO QUIZ FEITO ENVIADO PELO USER CHILD, E ENVIA EMAIL AO USER FATHER
	 * @Author LUCAS BORGUEZAM
	 * @Sice 5 de out. de 2024
	 * @param quiz
	 */
	public void toCorrectQuiz(Integer quizId) {
		
		/*Buscar quiz*/
		Optional<Quiz> opQuiz= quizRepository.findById(quizId);
		Quiz quiz = opQuiz.get();
		
		/*Verificar se o statu do quiz é igual a 1*/
		if(quiz.getIsfinalized() == Defines.QUIZ_FINALIZADO) {
			
			Optional<UserChild> opChild = childRepository.findById(quiz.getIdUserChild());
			UserChild child = opChild.get();
			Optional<UserFather> opFather = fatherRepository.findById(child.getFkUserPai());
			UserFather father = opFather.get();
			
			/*Fechar quiz*/
			quizRepository.updateIsFinalizedbyId(quiz.getId());
			
			/*Objeto FieldQuiz*/
			FieldQuizVO fieldQuiz = jsonService.fromJson(quiz.getQuiz(), FieldQuizVO.class);
			
			/*Envio de email*/
			
			/*Objeto com dados de performance do quiz*/
			QuizPerformanceData quizPerformance = performanceService.calculateQuizPerformmance(fieldQuiz);
			quizPerformance.setNameUserChild(child.getFirstName());
	        	/*Pegar a data atual do quiz Converter apenas a data para String*/
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			        String formattedDateCreation = quiz.getStartDate().toLocalDate().format(formatter);
			quizPerformance.setDataCreation(formattedDateCreation);
			quizPerformance.setNickName(child.getNickname());
			
			System.out.println("\n\n"+jsonService.toJson(quizPerformance));
			
			//System.out.println("\n\n"+jsonService.toJson(calculateQuizThemePerformance(fieldQuiz))); 
			
			/*Enviar email de quiz realizado realizado*/
			try {
				emailService.sendEmailQuizRealized(father.getEmail(), quizPerformance);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("runWithIicialization, Erro ao enviar e-mail");
			}
		}//if()
    		
	}

	/**
	 * METODO VERIFICA O TOKEN COM O ID ENVIADO, E BUSCA QUIZ ATUAL EM ABERTO DO ID PASSADO 
	 * E RETORNA O CONEUDO DE ESTUDO PARA ESSE QUIZ
	 * @Author LUCAS BORGUEZAM
	 * @Sice 4 de out. de 2024
	 * @param idUserChild
	 * @return
	 */
	public ResponseEntity<?> getContentToStudy(Integer idUserChild) {
		if(!securityServices.verifyUserChildWithSolicitation(idUserChild))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'idUserChild' enviado não corresponde ao seu 'id'.");
		
		if(!quizRepository.existsQuizOpenByIdUserChild(idUserChild, LocalDate.now()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não existe um quiz em aberto para buscar o conteúdo.");

		/*BUSCAR O QUIZ*/
		Quiz quizEntity = quizRepository.findQuizOpenByIdUserChild(idUserChild, LocalDate.now());
		/*BUSCAR CONTEUDO*/
		List<MateriaDoConteudo> materiasEConteudos = findContentToStudybyQuiz(quizEntity);
		
		return ResponseEntity.status(HttpStatus.OK).body(materiasEConteudos);
	}
	
	/**
	 * METODO BUSCA O CONTEUDO DE ACORDO COM A ENTITY QUIZ
	 * @Author LUCAS BORGUEZAM
	 * @Sice 4 de out. de 2024
	 * @param quizEntity -> QUIZ PARA PROCURAR O CONTEÚDO
	 * @return
	 */
	private List<MateriaDoConteudo> findContentToStudybyQuiz(Quiz quizEntity) {
		List<MateriaDoConteudo> materiasEConteudos = new ArrayList<>();
		FieldQuizVO fieldQuiz = jsonService.fromJson(quizEntity.getQuiz(), FieldQuizVO.class);
		
		for(QuizByMateriaVO materia: fieldQuiz.getMaterias()) {			
			/*Pegar informações da materia*/
			MateriaDoConteudo subject = new MateriaDoConteudo();
			subject.setId(materia.getId());
			subject.setSubject(materia.getSubject());
			
			/*Pegar os temas de cada pergunta*/
			Set<String> set = new HashSet<>();
			for(PerguntaVO pergunta: materia.getQuiz()) {
				/*CRIAR UM SET DOS IDS DO CONTEUDO UTILIZADO POR MATÉRIA*/
				set.add(pergunta.getIdConteudo());
			}
			
			for(String idConteudo: set) {
				/*Buscar conteudo*/
				Optional<Conteudo> opContents = conteudoRepository.findById(Integer.parseInt(idConteudo));
				Conteudo contents = opContents.get();
				/*converter para objetoVO e adicionar a lista de conteudos*/
				subject.getContents().add(EdukidMapper.parseObject(contents, ConteudoParaEstudo.class));
			}//for(conteudo)
			
			materiasEConteudos.add(subject);
		}//for(materia)
		
		return materiasEConteudos;
	}
	
	/**
	 * METODO VERIFICA O TOKEN COM O ID ENVIADO, BUSCA QUIZ PELO SEU ID, E RETORNA O CONTEUDO DE ESTUDO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 4 de out. de 2024
	 * @param idUserChild
	 * @return
	 */
	public ResponseEntity<?> getContentToStudyByQuizId(Integer idQuiz) {
		
		/*Buscar quiz*/
		if(!quizRepository.existsById(idQuiz))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("id do quiz não está relacionado com sua conta.");
		Optional<Quiz> opQuizEntity = quizRepository.findById(idQuiz);
		Quiz quizEntity = opQuizEntity.get();
		
		if(!securityServices.verifyUserChildWithSolicitation(quizEntity.getIdUserChild()) && !securityServices.verifyUserFahterWithSolicitation(quizEntity.getIdUserChild()))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'idUserChild' do quiz não está relacionado com sua conta.");

		/*Buscar conteudo*/
		List<MateriaDoConteudo> contents = findContentToStudybyQuiz(quizEntity);
		
		return ResponseEntity.status(HttpStatus.OK).body(contents);
	}
	
	/**
	 * METODO VERIFICA SE O USER DO TOKEM COM O ID DO USER PASSADO E BUSCA O QUIZ POR DATA
	 * @Sice 30 de out. de 2024
	 * @param idUserChild
	 * @return
	 */
	public ResponseEntity<?> findQuizByIdUserAndDate(Integer idUserChild, Integer day, Integer month, Integer year){
		/*Verificar token com o id do user child*/
		if(!securityServices.verifyUserChildWithSolicitation(idUserChild) && !securityServices.verifyUserFahterWithSolicitation(idUserChild))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("'idUserChild' enviado não está relacionado com sua conta.");
		
		List<QuizVO> listQuizVO = new ArrayList<>();
		/*Verificar se existe um quiz criado na data específica*/
		if(quizRepository.existsQuizByDateAndUser(idUserChild, day, month, year)) {
			List<Quiz> listQuizEntity = quizRepository.findQuizByDateAndUser(idUserChild, day, month, year);
			for(Quiz quizEntity: listQuizEntity) {
				FieldQuizVO fielQuiz = jsonService.fromJson(quizEntity.getQuiz(), FieldQuizVO.class);
				QuizVO quiz = new QuizVO(quizEntity, fielQuiz);
				listQuizVO.add(quiz);
			}
			return ResponseEntity.status(HttpStatus.OK).body(listQuizVO);
		} else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não existe quiz para a data "+day+"/"+month+"/"+year+".");
	}
	
	
}
