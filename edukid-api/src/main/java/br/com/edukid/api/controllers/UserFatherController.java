package br.com.edukid.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.edukid.api.vo.v1.UserChildVO;
import br.com.edukid.api.vo.v1.UserFatherVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user-father")
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
	@PostMapping(path="account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerUserFather(@RequestBody @Valid UserFatherVO dataAccount) {
		
		return fatherService.registerUserFather(dataAccount);
	}
	
	/**
	 * METODO ALTERA UM REGISTRO JÁ CADASTRADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PutMapping(path="account",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserFather(@RequestBody @Valid UserFatherVO dataAccount){
		
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
	public ResponseEntity<?> desactivteUserFather(@RequestBody @Valid @NotBlank Long id) throws Exception {
		return fatherService.desactivteUserFather(id);
	}
	
}
