package br.com.edukid.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edukid.api.services.UserFatherService;
import br.com.edukid.api.vo.v1.UserChildVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user-child")
public class UserChildController {
	
	@Autowired
	UserFatherService fatherService;

	/**
	 * METODO FAZ REGISTRO DO USUÁRIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PostMapping(path="register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerUserChild(@RequestBody @Valid UserChildVO dataAccount){
		return fatherService.registerUserChild(dataAccount);	
	}
	
	/**
	 * METODO ALTERA UM REGISTRO JÁ CADASTRADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PutMapping(path="account",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserChild(@RequestBody @Valid UserChildVO dataAccount){
		return fatherService.updateUserChild(dataAccount);
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
	public ResponseEntity<?> desactivteUserChild(@RequestBody @Valid @NotBlank Long id) throws Exception {
		return fatherService.desactivteUserChild(id);
	}
}
