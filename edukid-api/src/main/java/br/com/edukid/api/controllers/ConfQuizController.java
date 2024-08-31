package br.com.edukid.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edukid.api.services.ConfigurationQuizService;
import br.com.edukid.api.vo.v1.configquiz.MateriasETemasVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/edukid/conf-quiz")
public class ConfQuizController {
	
	@Autowired
	ConfigurationQuizService configurationQuizService;
	/**
	 * METODO BUSCA UMA LISTA DE MATERIAS E TEMAS RELACIONADOS AO ANO DO ENSINO MEDIO DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 28 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	@GetMapping(path="/subject-and-theme/{yearHighSchool}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSubjectAndTheme(@PathVariable @Valid @NotBlank 
			@Pattern(regexp = "^[1-9]$", message = "'yearHighSchool' deve ser uma string numérica com valores entre 1 e 9")
			String yearHighSchool
	) {
			return configurationQuizService.getSubjectAndTheme(Integer.parseInt(yearHighSchool));
	}
	
	@PostMapping(path="subject-and-theme", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerSubjectAndThemeRelacionedWithUserChild(@RequestBody  @Valid MateriasETemasVO data) {		
		return configurationQuizService.registerConfQuiz(data);
	}
	

}
