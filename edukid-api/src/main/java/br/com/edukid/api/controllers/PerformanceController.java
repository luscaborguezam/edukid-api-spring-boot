package br.com.edukid.api.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonSerializable;

import br.com.edukid.api.entities.Quiz;
import br.com.edukid.api.repositorys.QuizRepository;
import br.com.edukid.api.services.ConfigurationQuizService;
import br.com.edukid.api.services.PerformanceService;
import br.com.edukid.api.utils.JsonService;
import br.com.edukid.api.vo.v1.quiz.QuizVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/edukid/performance")
public class PerformanceController {
	
	@Autowired
	PerformanceService performanceService;
	@Autowired
	ConfigurationQuizService configurationQuizService;
	@Autowired 
	QuizRepository quizRepository;
	
	/**
	 * METODO CONSULTA O RESULTADO DO CALCULO DO DESEMPENHO DO USER CHILD POR PERIODO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 12 de out. de 2024
	 * @param idUSerChild -> ID DO USUÁRIO FILHO
	 * @param type -> TIPO DO PERIODO [DIA, MES, ANO]
	 * @param periodo -> QUANTIDADE DO TIPO DO PERIODO
	 * @return
	 */
	@GetMapping(path="/{idUserChild}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> calculatePerformance(
		@PathVariable @Valid @NotBlank @Pattern(regexp = "^-?\\d+$", message = "'idUSerChild' deve ser uma string numérica de valor inteiro") 
		String idUserChild,
		@RequestParam (value = "tipo", required = false)
		String type, // Parâmetro opcional
        @RequestParam(value = "periodo", required = false)
        String period
		)
	{	
		Integer id = Integer.parseInt(idUserChild);
		Map<String, String> verification = performanceService.verifyParamPeriod(id, type, period);		
		
		if(verification.get("statusCode").equals("OK")){
			/*Buscar periodo formatado em LocalDate*/
			Map<String, LocalDate> periodLocalDate = performanceService.getPeriod(id, type, period);
			/*Buscar Lista de Quizzes*/
			List<Quiz> quizzesInPeriod = quizRepository.getQuizzesByPeriod(id, periodLocalDate.get("inicial"), periodLocalDate.get("final"));
			List<QuizVO> quizzesInPeriodVO = configurationQuizService.getQuizVO(quizzesInPeriod);
			
			return performanceService.getPerformanceByPeriod(id, periodLocalDate.get("inicial"), periodLocalDate.get("final"), quizzesInPeriodVO);
		} else if(verification.get("statusCode").equals("atual")){
			/*Buscar Lista de Quizzes*/
			List<Quiz> quizzesInPeriod = quizRepository.getQuizzesByCurrentDate(id);
			List<QuizVO> quizzesInPeriodVO = configurationQuizService.getQuizVO(quizzesInPeriod);
			/*Busacar periodo atual*/
			return performanceService.getPerformanceByPeriod(id, LocalDate.now(), LocalDate.now(), quizzesInPeriodVO);
			
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(verification.get("message"));
		
	}
	
	

}
