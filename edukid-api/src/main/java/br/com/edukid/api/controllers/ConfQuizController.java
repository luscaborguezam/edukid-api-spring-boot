package br.com.edukid.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edukid.api.services.ConfigurationQuizService;
import br.com.edukid.api.vo.v1.configquiz.MateriasETemasVO;
import br.com.edukid.api.vo.v1.quiz.QuizVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/edukid/conf-quiz")
public class ConfQuizController {
	
	@Autowired
	ConfigurationQuizService configurationQuizService;
	/**
	 * METODO BUSCA UMA LISTA DE MATERIAS E TEMAS RELACIONADOS AO ANO DO ENSINO FUNDAMENTAL
	 * @Author LUCAS BORGUEZAM
	 * @Sice 28 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	@GetMapping(path="/subject-and-theme/{yearSchool}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSubjectAndTheme(@PathVariable 
			@Valid @NotBlank 
			@Pattern(regexp = "^[1-9]$", message = "'yearSchool' deve ser uma string numérica com valores entre 1 e 9")
			String yearSchool
	) {
			return configurationQuizService.getSubjectAndTheme(Integer.parseInt(yearSchool));
	}
	
	/**
	 * METODO CADASTRA MATERIA E TEMAS RELACIONADO A UM USER CHILD
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de set. de 2024
	 * @param data
	 * @return
	 */
	@PostMapping(path="/subject-and-theme", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerSubjectAndThemeRelacionedWithUserChild(@RequestBody  @Valid MateriasETemasVO data) {		
		return configurationQuizService.registerConfQuiz(data);
	}
	
	/**
	 * METODO BUSCA O QUIZ MONTADO DE ACORDO COM AS MATERIAS E TEMAS CONFIGURADOS PARA O USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de set. de 2024
	 * @param idUserChild
	 * @return
	 */
	@GetMapping(path="/quiz/{idUserChild}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> toGeneratedQuiz(@PathVariable @Valid @NotBlank 
		@Pattern(regexp = "^-?\\d+$", message = "'idUserChild' deve ser uma string numérica de valor inteiro") String idUserChild)
	{
		return configurationQuizService.getQuiz(Integer.parseInt(idUserChild));
	}
	
	/**
	 * METODO iNICIA O REGISTRO DE UM QUIZ REALIZADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 11 de set. de 2024
	 * @param quizREalized
	 * @return
	 */
	@PutMapping(path="/quiz", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerQuizRealized(@RequestBody @Valid QuizVO quizRealized) {
		return configurationQuizService.registerQuizRealized(quizRealized);
	}
	
	/**
	 * METODO BUSCA O MATERIAL DE ESTUDO DE ACORDO COM AS PERGUNTAS DO QUIZ CRIADO PARA O USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de set. de 2024
	 * @param idUserChild
	 * @return
	 */
	@GetMapping(path="/quiz/contents-to-study/{idUserChild}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findContentToStudy(@PathVariable @Valid @NotBlank 
		@Pattern(regexp = "^-?\\d+$", message = "'idUserChild' deve ser uma string numérica de valor inteiro") String idUserChild)
	{
		return configurationQuizService.getContentToStudy(Integer.parseInt(idUserChild));
	}
	
	/**
	 * METODO BUSCA O MATERIAL DE ESTUDO DE ACORDO COM AS PERGUNTAS DO QUIZ CRIADO PARA O USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de set. de 2024
	 * @param idUserChild
	 * @return
	 */
	@GetMapping(path="/quiz/contents-to-study-by-quiz-id/{idQuiz}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findContentToStudyByQuizId(@PathVariable @Valid @NotBlank 
		@Pattern(regexp = "^-?\\d+$", message = "'idQuiz' deve ser uma string numérica de valor inteiro") String idQuiz)
	{
		return configurationQuizService.getContentToStudyByQuizId(Integer.parseInt(idQuiz));
	}

}
