package br.com.edukid.api.configurations;

import java.awt.print.Printable;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.edukid.api.entities.Quiz;
import br.com.edukid.api.repositorys.QuizRepository;
import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.repositorys.UserFatherRepository;
import br.com.edukid.api.services.ConfigurationQuizService;
import br.com.edukid.api.utils.Defines;
import br.com.edukid.api.utils.EmailService;
import br.com.edukid.api.utils.JsonService;
import jakarta.annotation.PostConstruct;

@Component
public class ScheduledTasks {
	@Autowired
	QuizRepository quizRepository;
	
	@Autowired 
	ConfigurationQuizService configurationQuizService;
	
	@Autowired
	UserChildRepository childRepository;
	
	@Autowired
	UserFatherRepository fatherRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	JsonService jsonService;
	

	/**
	 * METODO ATUALIZA BASE DE DADOS FECHANDO TODOS QUIZ EM ABERTO QUE NÃO SEJA DA DATA ATUAL.
	 * É EXECUTADO COM APÓS A INICIALIZAÇÃO DO MÉTODO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 17 de set. de 2024
	 */
    @PostConstruct
    public void runWithIicialization() {
    	System.out.println("\n\n\nFechar quizes em aberto com a data menor que a atual\n");
    	
    	/*BUSCAR QUIZEZ*/
    	List<Quiz> quizzes = quizRepository.findQuizOpenByStartDateMinorCurrentDate(LocalDate.now());
    	
    	System.out.println(quizzes.size());
    	for(Quiz quiz: quizzes) {
    		configurationQuizService.closeQuizOpen(quiz.getId());
    	}
        
    }
    
	/**
	 * METODO ATUALIZA BASE DE DADOS FECHANDO TODOS QUIZ EM ABERTO.
	 * É EXECUTADO A 18h TODOS OS DIAS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 17 de set. de 2024
	 */
    @Scheduled(cron = "0 0 18 * * *")
    public void runAtMidnight() {
    	System.out.println("\nFechar quizzes em aberto");
    	List<Quiz> quizzes = quizRepository.findAllQuizzesByIsFinalized(Defines.QUIZ_EM_ABERTO);
    	for(Quiz quiz: quizzes) {
    		configurationQuizService.closeQuizOpen(quiz.getId());
    	}
        
    }
    
	/**
	 * METODO CRIA O QUIZ PARA TODOS OS USUÁRIOS FILHOS
	 * EXECUTADO AS 5H DA MANHÃ OU QUANDO O JAR É INICIADOS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 17 de set. de 2024
	 */
    @Scheduled(cron = "0 0 5 * * *")
    @PostConstruct
    public void runCreateQuiz() {
    	System.out.println("\nCRIAR QUIZ PARA TODOS USERS CHILD");
    	
    	List<Integer> childIds = childRepository.findAllIdWhereUserFatherEqualsActive();
    	for(Integer id: childIds)
    		configurationQuizService.toGenerateQuiz(id);
        
    }
    
    /**
     * METODO ZERA O SCORE SEMANAL DE TODOS OS USERS CHILD AS 00H DO DOMINGO
     * @Author LUCAS BORGUEZAM
     * @Sice 13 de out. de 2024
     */
    @Scheduled(cron = "0 0 0 * * SUN", zone = "America/Sao_Paulo")
    @PostConstruct
    public void executeTaskEverySunday() {
    	childRepository.resetScoreWeek();
        
    }

}
