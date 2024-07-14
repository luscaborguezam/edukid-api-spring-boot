package br.com.edukid.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edukid.api.services.RegisterAccountService;
import br.com.edukid.api.vo.v1.UserFatherVO;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/register-account")
public class RegisterAccountController {

	@Autowired
	RegisterAccountService register;
	
	@PostMapping(path="user-father", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> postMethodName(@RequestBody @Valid UserFatherVO dataAccount) {
		
		
		return register.registerUserFather(dataAccount);
	}
	
}
