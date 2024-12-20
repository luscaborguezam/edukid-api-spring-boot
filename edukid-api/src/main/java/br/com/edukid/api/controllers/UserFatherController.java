package br.com.edukid.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.edukid.api.services.ConfigurationQuizService;
import br.com.edukid.api.services.UserFatherService;
import br.com.edukid.api.vo.v1.LoginFatherVO;
import br.com.edukid.api.vo.v1.SolicitarMudancaSenhaVO;
import br.com.edukid.api.vo.v1.UserFatherCadastroVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@RestController
@RequestMapping("/edukid/user-father")
public class UserFatherController {

	@Autowired
	UserFatherService fatherService;
	@Autowired
	ConfigurationQuizService configurationQuizService;
	
	/**
	 * METODO FAZ O REGISTRO DO USUARIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de jul. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PostMapping(path="/account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerUserFather(@RequestBody @Valid UserFatherCadastroVO dataAccount) {
			
		return fatherService.registerUserFather(dataAccount);
	}
	
	/**
	 * METODO ALTERA UM REGISTRO JÁ CADASTRADO DO USUARIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PutMapping(path="/account",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserFather(@RequestBody @Valid UserFatherCadastroVO dataAccount){
		
			return fatherService.updateUserFather(dataAccount, true);
	}
	
	/**
	 * METODO DESATIVA A CONTA DO USUÁRIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PatchMapping(value = "/account/{id}")
	public ResponseEntity<?> desactivteUserFather(
			@PathVariable @Valid @NotBlank @Pattern(regexp = "^-?\\d+$", message = "'id' deve ser uma string numérica de valor inteiro")
			String id
		) throws Exception {
			return fatherService.desactivteUserFather(Integer.parseInt(id));
	}
	
	/**
	 * METODO FAZ O LOGIN DO USUARIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de jul. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PostMapping(path="/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loginUserFather(@RequestBody @Valid LoginFatherVO dataAccount) {
			return fatherService.authenticateLogin(dataAccount);
	}
	
	/**
	 * METODO PARA MUDANCA DE SENHA, GERA UM CODIGO ALEATORIO SALVA E ENVIA NO EMAIL DO USUÁRIO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de jul. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PostMapping(path="/change-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changePassword(@RequestBody @Valid SolicitarMudancaSenhaVO dataAccount) {
			return fatherService.changePassword(dataAccount);
	}
	

	/**
	 * METODO VERIFICA SE O LOGIN PRECISA DE VERIFICAÇÃO DE CONTA
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de set. de 2024
	 * @param dataAccount
	 * @return
	 */
	@GetMapping(path="/is-verify-account/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> isVerifyAccount(@PathVariable @Valid @NotBlank String email) {
			return fatherService.isVerifyAccount(email);
	
	}
	
	@GetMapping(path="/ranking-week/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPositionInRankingWeek(
			@PathVariable @Valid @NotBlank @Pattern(regexp = "^-?\\d+$", message = "'id' do user pai deve ser uma string numérica de valor inteiro")
			String id
		) {
			return fatherService.getRanking(Integer.parseInt(id));
	}
	
	/**
	 * METODO BUSCA O QUIZZES CRIADOS POR PERIODO RELACIONADOS A USER CHILD
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de set. de 2024
	 * @param idUserChild
	 * @return
	 */
	@GetMapping(path="/quiz-hystory/{id}-{month}-{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getQuizzezHistory(
			@PathVariable @Valid @NotBlank @Pattern(regexp = "^-?\\d+$", message = "Key 'id' must be a string with the value of a valid integer") 
			String id,
			@PathVariable @Valid @NotBlank @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "'month' must be a string with the value numeric in range of 1:12'") 
			String month,
			@PathVariable @Valid @NotBlank @Pattern(regexp = "^\\d{4}$", message = "Key 'year' must be a string with the value numeric, exemple for year of 2024 must be '2024'") 
			String year
	) {
		return fatherService.getQuizzezHistory(Integer.parseInt(id), Integer.parseInt(month), Integer.parseInt(year));
	}
}
