package br.com.edukid.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edukid.api.services.UserFatherService;
import br.com.edukid.api.vo.v1.LoginVO;
import br.com.edukid.api.vo.v1.UserChildVO;
import br.com.edukid.api.vo.v1.UserFatherCadastroVO;
import br.com.edukid.api.vo.v1.UserFatherVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/edukid/user-father")
public class UserFatherController {

	@Autowired
	UserFatherService fatherService;
	
	/**
	 * METODO FAZ O REGISTRO DO USUARIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de jul. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PostMapping(path="/account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerUserFather(@RequestBody @Valid UserFatherCadastroVO dataAccount) {
		try {
			return fatherService.registerUserFather(dataAccount);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
		}
	}
	
	/**
	 * METODO ALTERA UM REGISTRO JÁ CADASTRADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PutMapping(path="/account",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserFather(@RequestBody @Valid UserFatherCadastroVO dataAccount){
			return fatherService.updateUserFather(dataAccount);
	}
	
	/**
	 * METODO DESATIVA A CONTA DO USUÁRIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value = "account/{id}")
	public ResponseEntity<?> desactivteUserFather(@PathVariable @Valid @NotBlank String id) throws Exception {
			return fatherService.desactivteUserFather(Integer.parseInt(id));
	}
	
	/**
	 * METODO FAZ O LOGIN DO USUARIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de jul. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PostMapping(path="login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loginUserFather(@RequestBody @Valid LoginVO dataAccount) {
			return fatherService.authenticateLogin(dataAccount);
	}
	
	/**
	 * METODO PARA MUDANCA DE SENHA, GERA UM CODIGO ALEATORIO SALVA E ENVIA NO EMAIL DO USUÁRIO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de jul. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PostMapping(path="change-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changePassword(@RequestBody @Valid LoginVO dataAccount) {
			return fatherService.changePassword(dataAccount);
	}
}
