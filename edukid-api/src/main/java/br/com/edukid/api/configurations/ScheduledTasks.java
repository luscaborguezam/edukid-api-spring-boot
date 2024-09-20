package br.com.edukid.api.configurations;

import org.springframework.stereotype.Component;

import br.com.edukid.api.repositorys.QuizRepository;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class ScheduledTasks {
	@Autowired
	QuizRepository quizRepository;

	/**
	 * METODO ATUALIZA BASE DE DADOS FECHANDO TODOS QUIZ EM ABERTO QUE NÃO SEJA DA DATA ATUAL.
	 * É EXECUTADO COM APÓS A INICIALIZAÇÃO DO MÉTODO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 17 de set. de 2024
	 */
    @PostConstruct
    public void runWithIicialization() {
    	System.out.println("Fechar quizzes em aberto");
        quizRepository.updateIsFinalizedWhereStartDateMinorWhithCurrent();
        
    }
    
	/**
	 * METODO ATUALIZA BASE DE DADOS FECHANDO TODOS QUIZ EM ABERTO.
	 * É EXECUTADO A 18h TODOS OS DIAS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 17 de set. de 2024
	 */
    @Scheduled(cron = "0 0 18 * * *")
    public void runAtMidnight() {
    	System.out.println("Fechar quizzes em aberto");
        quizRepository.updateIsFinalizeD();
        
    }
}
