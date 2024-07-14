package br.com.edukid.api.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import br.com.edukid.api.entities.UserFather;
import br.com.edukid.api.repositorys.UserFatherRepository;
import br.com.edukid.api.utils.UtilsService;
import br.com.edukid.api.vo.v1.LoginVO;

/**
 * CLASSE CRIADA PARA AGRUPAR OS SERVIÇOS UTILIZADOS NO ENDPOINT DE LOGIN
 * @Author LUCAS BORGUEZAM
 * @Sice 9 de jul. de 2024
 */
@Service
public class LoginService {
	
	@Autowired
	UtilsService utilsService;
	
	@Autowired
	UserFatherRepository repository;
	
	/* Registrar mensagens de log em uma aplicação Java.*/
	Logger logger = Logger.getLogger(LoginService.class.getName());
	
	/**
	 * METODO REALIZA A AUTHENTIFICAÇÃO DO LOGIN
	 * @Author LUCAS BORGUEZAM
	 * @Sice 9 de jul. de 2024
	 * @param login
	 * @return STATUS CODE HTTP + DESCRICAO
	 */
	public ResponseEntity<?> authenticateLogin(LoginVO login) {
		logger.info("Autentificando Login");
		
			if(repository.countByEmail(login.getEmail()) > 0) {
				
				UserFather user = repository.findByEmail(login.getEmail());
				if(user.getPassword().equals(login.getPassword())) {
					return ResponseEntity.ok("Authentiked");
				}
			}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}	
}
