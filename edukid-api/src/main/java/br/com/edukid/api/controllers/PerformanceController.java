package br.com.edukid.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.edukid.api.services.PerformanceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/edukid/performance")
public class PerformanceController {
	
	@Autowired
	PerformanceService performanceService;
	
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
	public ResponseEntity<?> sendEmail(
		@PathVariable @Valid @NotBlank @Pattern(regexp = "^-?\\d+$", message = "'idUSerChild' deve ser uma string numérica de valor inteiro") 
		String idUserChild,
		@RequestParam (value = "tipo", required = false)
		String type, // Parâmetro opcional
        @RequestParam(value = "periodo", required = false)
        String period
		)
	{	
		return performanceService.GetPerformanceByPeriod(Integer.parseInt(idUserChild), type, period);
		
	}
	
	

}
