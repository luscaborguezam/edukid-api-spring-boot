package br.com.edukid.api.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.edukid.api.configurations.springsecurity.security.infra.SecurityServices;
import br.com.edukid.api.entities.Quiz;
import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.mapper.EdukidMapper;
import br.com.edukid.api.repositorys.QuizRepository;
import br.com.edukid.api.repositorys.TemaAprendizagemRepository;
import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.utils.Defines;
import br.com.edukid.api.utils.JsonService;
import br.com.edukid.api.vo.v1.configquiz.InfoPergunta;
import br.com.edukid.api.vo.v1.configquiz.PerguntaVO;
import br.com.edukid.api.vo.v1.performance.QuizPerformanceData;
import br.com.edukid.api.vo.v1.performance.SubjectPerformance;
import br.com.edukid.api.vo.v1.performance.ThemePerformance;
import br.com.edukid.api.vo.v1.quiz.FieldQuizVO;
import br.com.edukid.api.vo.v1.quiz.QuizByMateriaVO;
import br.com.edukid.api.vo.v1.quiz.QuizVO;

@Service
public class PerformanceService {
	
	@Autowired 
	TemaAprendizagemRepository temaAprendizagemRepository;
	
	@Autowired SecurityServices securityServices;
	
	@Autowired QuizRepository quizRepository;
	
	@Autowired JsonService jsonService;
	
	@Autowired UserChildRepository childRepository;
	
	/**
	 * METODO CALCULA A PERFORMANCE DO QUIZ
	 * @Author LUCAS BORGUEZAM
	 * @Sice 6 de out. de 2024
	 * @param fieldQuiz
	 * @return
	 */
 	public QuizPerformanceData calculateQuizPerformmance(FieldQuizVO fieldQuiz) {
		QuizPerformanceData quizPerformanceData = new QuizPerformanceData();
		
		/*List de materias (QuizByMateriaVO)*/
		for(int i=0; i < fieldQuiz.getMaterias().size() ; i++ ) {//QuizByMateriaVO m: quizRz.getMaterias()			
			QuizByMateriaVO quizByMateria = fieldQuiz.getMaterias().get(i);
			
			/*Incrementar total de perguntas do quiz*/
			quizPerformanceData.incrementTotalQuestions(quizByMateria.getQuiz().size());
			
			/*Incrementar total de perguntas por materias*/
			SubjectPerformance subjectPerformance = new SubjectPerformance();
			subjectPerformance.setTotalQuestions(quizByMateria.getQuiz().size());
				/*definir nome da matérias*/
				subjectPerformance.setSubject(quizByMateria.getSubject());
			
				
			/*iniciar a string temaAnterior e objeto temaPerformance*/
			ThemePerformance themePerformance = new ThemePerformance();
			String temaAnterior = "";
				
			/*Ordenar quiz(lista de perguntas) por tema*/
			quizByMateria.orderQuizByIdTheme();
			/*Lista de PerguntaVO*/
			for(int c=0; c < quizByMateria.getQuiz().size(); c++) {
				PerguntaVO pergunta = quizByMateria.getQuiz().get(c);
				
				/*Adicionar temaAnterior*/
				temaAnterior = c ==0 ? pergunta.getIdTema() :quizByMateria.getQuiz().get(c-1).getIdTema();
				//System.out.println(temaAnterior+" !="+pergunta.getIdTema()+" ?"+!temaAnterior.equals(pergunta.getIdTema()));
				/*Adicionar themePerformance quando muudar de thema*/
				if(!temaAnterior.equals(pergunta.getIdTema())){
					System.out.println("Adicionar tema anterior "+temaAnterior);
					/*2- adicionar a performance do tema quando for mudar de tema*/
					themePerformance.calcularTotalDeErros();
					themePerformance.setTheme(temaAprendizagemRepository.findThemeById(Integer.parseInt(temaAnterior)));
					themePerformance.setIdTheme(temaAnterior);
					subjectPerformance.addThemePerformance(themePerformance);
					
					/*Criar objeto do novo tema*/	
					themePerformance = new ThemePerformance();
				}
				/*Incrementar quantidade de questões do tema*/
				themePerformance.incrementTotalQuestions();
				
				
				
				
				/*Adicionar acerto*/
				InfoPergunta infoPergunta = pergunta.getInfoPerguntas().get(0);
				if(infoPergunta.getCorrectAnswer().equals(infoPergunta.getSelectedAnswer())) {
					/*icrementar total de acertos do quiz*/
					quizPerformanceData.incrementTotalHit();
					/*incrementar total de acertos da matéria*/
					subjectPerformance.incrementTotalHit();
					/*Cacular acertos do tema*/
					themePerformance.incrementTotalHit();
					
				}//verificacação de acerto
				
				/*3 - salvar a performance do ultimo tema*/
				if(c == (quizByMateria.getQuiz().size() - 1)) {
						themePerformance.calcularTotalDeErros();
						themePerformance.setTheme(temaAprendizagemRepository.findThemeById(Integer.parseInt(pergunta.getIdTema())));
						themePerformance.setIdTheme(pergunta.getIdTema());
						subjectPerformance.addThemePerformance(themePerformance);					
				}
				
			}//for(listPerguntas)
			
			/*Calcular o total de erros da matéria*/
			subjectPerformance.calcularTotalDeErros();
			/*Adicionar a performance da materia na performance do quiz*/
			quizPerformanceData.addSubject(subjectPerformance);
			
		}//for(meterias)	
		/*Calcular o total de erros do quiz*/
		quizPerformanceData.calcularTotalDeErros();
		
		return quizPerformanceData;
	}
	
	/**
	 * METODO CALCULA A PERFORMANCE DOS TEMAS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de out. de 2024
	 * @param fieldQuiz
	 * @return
	 */
	private List<ThemePerformance> calculateQuizThemePerformance (FieldQuizVO fieldQuiz) {
		
		List<ThemePerformance> temasPerformance = new ArrayList<>();
		
		/*List de materias (QuizByMateriaVO)*/
		for(int i=0; i < fieldQuiz.getMaterias().size() ; i++ ) {//QuizByMateriaVO m: quizRz.getMaterias()			
			QuizByMateriaVO quizByMateria = fieldQuiz.getMaterias().get(i);
		
			/*1- iniciar a string temaAnterior com o primeiro tema*/
			ThemePerformance themePerformance = new ThemePerformance();
			String temaAnterior = "";//quizByMateria.getQuiz().get(0).getIdTema();
			
			/*Ordenar quiz(lista de perguntas) por tema*/
			quizByMateria.orderQuizByIdTheme();
			/*Lista de PerguntaVO*/
			for(int c=0; c < quizByMateria.getQuiz().size(); c++) {
				PerguntaVO pergunta = quizByMateria.getQuiz().get(c);
				
				
				
				/*Adicionar temaAnterior*/
				temaAnterior = c ==0 ? pergunta.getIdTema() :quizByMateria.getQuiz().get(c-1).getIdTema();
				//System.out.println(temaAnterior+" !="+pergunta.getIdTema()+" ?"+!temaAnterior.equals(pergunta.getIdTema()));
				/*Adicionar themePerformance quando muudar de thema*/
				if(!temaAnterior.equals(pergunta.getIdTema())){
					System.out.println("Adicionar tema anterior "+temaAnterior);
					/*2- adicionar a performance do tema quando for mudar de tema*/
					themePerformance.calcularTotalDeErros();
					themePerformance.setTheme(temaAprendizagemRepository.findThemeById(Integer.parseInt(temaAnterior)));
					themePerformance.setIdTheme(temaAnterior);
					temasPerformance.add(themePerformance);
					
					/*Criar objeto do novo tema*/	
					themePerformance = new ThemePerformance();
				}
				
				
				/*Adicionar acerto*/
				InfoPergunta infoPergunta = pergunta.getInfoPerguntas().get(0);
				if(infoPergunta.getCorrectAnswer().equals(infoPergunta.getSelectedAnswer())) {
					/*Cacular acertos do tema*/
					themePerformance.incrementTotalHit();
				}//Acerto
				/*Cacular questões do tema*/
				themePerformance.incrementTotalQuestions();

				
				/*3 - salvar a performance do ultimo tema*/
				if(c == (quizByMateria.getQuiz().size() - 1)) {
						themePerformance.calcularTotalDeErros();
						themePerformance.setTheme(temaAprendizagemRepository.findThemeById(Integer.parseInt(pergunta.getIdTema())));
						themePerformance.setIdTheme(pergunta.getIdTema());
						temasPerformance.add(themePerformance);					
				}
				
			}//for(Lista de perguntas)
		
		}//for(materia)
		
		
		return temasPerformance;
	}

	/**
	 * BUSCA DADOS DA PERFORMANCE ERETORNA UM RESPONSE ENTITY
	 * @param IdUserChild
	 * @param dataInicial
	 * @param dataFInal
	 * @param quizzesInPeriods
	 * @return
	 */
	public ResponseEntity<?> getPerformanceByPeriod(Integer IdUserChild, LocalDate dataInicial, LocalDate dataFInal,  List<QuizVO> quizzesInPeriods){
		
		 QuizPerformanceData performanceTotal = calculatePerformanceByPeriod(IdUserChild, dataInicial, dataFInal, quizzesInPeriods);
	        if(performanceTotal == null)
	        	return ResponseEntity.status(HttpStatus.OK).body("Não há quizzes realizados para o periodo de "+dataInicial+" até ");
	        
			return ResponseEntity.status(HttpStatus.OK).body(performanceTotal);
	}
	
	/**
	 * METODO VERIFICA OS DADOS ENVIADOS PARA CALCULAR PERFORMANCE POR PERIODO, E RETORNA OS DADOS DA PERFORMANCE
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de out. de 2024
	 * @param fieldQuiz
	 * @return
	 */
 	public Map<String, String> verifyParamPeriod(Integer IdUserChild, String type, String period) {
		Map<String, String> returnMap = new HashMap<>();
		/*Verificar token com o id do user child*/
		if(!securityServices.verifyUserChildWithSolicitation(IdUserChild) && !securityServices.verifyUserFahterWithSolicitation(IdUserChild)) {
			returnMap.put("stausCode", "UNAUTHORIZED");
			returnMap.put("message", "'idUserChild' enviado não está relacionado com sua conta.");
			return returnMap;
		}        
	        
    	if((type == null || type.isEmpty()) && period != null) {
			returnMap.put("stausCode", "UNAUTHORIZED");
			returnMap.put("message", "Para utilizar o parâmetro 'periodo' é preciso usar o parâmetro 'tipe', exemplo '?tipo=d&periodo=7'");
			return returnMap;
        }
        
    	String regex = "^[1-9][0-9]*$";	
        if(!period.matches(regex)) {
			returnMap.put("stausCode", "UNAUTHORIZED");
			returnMap.put("message", "parâmetro 'periodo' deve ser uma string numérica maior que 0 exemplo '?tipo=d&periodo=7'");
			return returnMap;
        }
        
		/*Verificar o tipo escolhido*/
		if(!Defines.TYPES_PERIOD.contains(type)) {
			returnMap.put("stausCode", "UNAUTHORIZED");
			returnMap.put("message", "'tipo' inválido, deve estar entre essas opções ['d','m','a']");
			return returnMap;
		}
		returnMap.put("statusCode", "OK");
		return returnMap;
	}
	
	/**
	 * METODO BUSCA FORMA O PERIODO DA CONSULTA
	 * @param IdUserChild
	 * @param type
	 * @param period
	 * @param quizzesInPeriods
	 * @return
	 */
	public Map<String, LocalDate> getPeriod(Integer IdUserChild, String type, String period) {
		Map<String, LocalDate> returnMap = new HashMap<>();
		QuizPerformanceData quizPerformanceData = null;
		/*Definir o periodo*/
		LocalDate hoje = LocalDate.now();
        LocalDate dataInicial;
        /*Verificar uso dos parâmetros*/
        if (type == null && period == null)
        	dataInicial = hoje;
		
		Integer p = period == null? 0: Integer.parseInt(period);
		System.out.println("pré switch");
		switch (type.toLowerCase()) {
		case "d": // Dias
			System.out.println("case 'd'");
			dataInicial = hoje.minusDays(p);
			break;
		case "m": // Meses
			dataInicial = hoje.minusMonths(p);
			break;
		case "a": // Anos
			dataInicial = hoje.minusYears(p);
			break;
		default:
			System.out.println("Período inválido.");
			return null;
		}
        System.out.println("\n\nData inicial: "+dataInicial + " Data final: "+ hoje);
		returnMap.put("inicial", dataInicial);
		returnMap.put("final", hoje);
		return returnMap;
        
	}


	/**
	 * 
	 * METODO 
	 * @Author LUCAS BORGUEZAM
	 * @Sice 12 de out. de 2024
	 * @param IdUserChild
	 * @param periodoInicial
	 * @param periodoAtual
	 * @return
	 */
	public QuizPerformanceData calculatePerformanceByPeriod(Integer IdUserChild, LocalDate periodoInicial, LocalDate periodoFinal, List<QuizVO> quizzesInPeriod) {
		
		/*Buscar dados do user child*/
		Optional<UserChild> opChild = childRepository.findById(IdUserChild);
		UserChild child = opChild.get();
		
		/*Acrescentar dados do user child no objeto de performance total*/
		QuizPerformanceData performanceTotal = new QuizPerformanceData(); 
		performanceTotal.setNameUserChild(child.getFirstName()+" "+child.getLastName());
		performanceTotal.setNickName(child.getNickname());
		performanceTotal.setDataInicial(periodoInicial.toString());
		performanceTotal.setDataFinal(periodoFinal.toString());
		/*Buscar Quantidade de quizes por status*/
		performanceTotal.setTotalQuizzes(quizRepository.countQuizzesByIdUserChild(IdUserChild));
		performanceTotal.setTotalQuizzesInPeriod(quizRepository.countQuizzesByPeriod(IdUserChild, periodoInicial, periodoFinal));
		
		performanceTotal.setTotalQuizzesOpen(quizRepository.countQuizzesByPeriodAndIsFinaled(IdUserChild, periodoInicial, periodoFinal, Defines.QUIZ_EM_ABERTO));
		performanceTotal.setTotalQuizzesIncomplet(quizRepository.countQuizzesByPeriodAndIsFinaled(IdUserChild, periodoInicial, periodoFinal, Defines.QUIZ_FINALIZADO_E_INCOMPLETO));
		performanceTotal.setTotalQuizzesNotRealized(quizRepository.countQuizzesByPeriodAndIsFinaled(IdUserChild, periodoInicial, periodoFinal, Defines.QUIZ_NAO_REALIZADO));
		
		/*BUSCAR QUIZZES REALIZADOS DO USER CHILD POR PERIODO*/		
				
		if(quizzesInPeriod == null || quizzesInPeriod.size() == 0) {
			return null;
		} else {
			performanceTotal.setTotalQuizzesFinalized(quizzesInPeriod.size());
			
			for(QuizVO quizVORegistred: quizzesInPeriod) {
				FieldQuizVO fielQuiz = quizVORegistred.getQuiz();
								
				System.out.println("json field: "+jsonService.toJson(fielQuiz));
				QuizPerformanceData performance = calculateQuizPerformmance(fielQuiz);
					
				performanceTotal.combinar(performance);
			}
			
		}
		return performanceTotal;
	
	}
}
