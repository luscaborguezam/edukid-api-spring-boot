package br.com.edukid.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import br.com.edukid.api.services.HashSaltService;
import br.com.edukid.api.services.LoginService;
import br.com.edukid.api.vo.v1.LoginVO;
import jakarta.validation.Valid;

/**
 * CLASSE CRIADA PARA FAZER O CONTROLE DE END POINTS RELACIONADOS AO LOGIN E CADASTRO DE USUÁRIO PAI.
 * @author LUCAS BORGUEZAM
 * @since 9 de jul. de 2024
 */

@RequestMapping("/controller-login")
@RestController
public class LoginController {

	@Autowired
	LoginService loginService;
	@Autowired
	HashSaltService hashSaltService;
	
	
	/**
	 * METODO CRIADO PARA INICIARO PROCESSO DE LOGIN
	 * @author LUCAS BORGUEZAM
	 * @since 9 de jul. de 2024
	 * @param login
	 * @return
	 */
	@PostMapping(path = "/login-user-father", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody @Valid LoginVO login) { 
		
		
		return loginService.authenticateLogin(login);
	}
	
}
