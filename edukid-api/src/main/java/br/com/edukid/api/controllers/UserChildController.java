package br.com.edukid.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edukid.api.services.UserChildService;
import br.com.edukid.api.vo.v1.LoginVO;
import br.com.edukid.api.vo.v1.UserChildVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/edukid/user-child")
public class UserChildController {
	
	@Autowired
	UserChildService childService;

	/**
	 * METODO FAZ REGISTRO DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PostMapping(path="/account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerUserChild(@RequestBody @Valid UserChildVO dataAccount){
		return childService.registerUserChild(dataAccount);	
	}
	
	/**
	 * METODO ALTERA UM REGISTRO JA CADASTRADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PutMapping(path="/account",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserChild(@RequestBody @Valid UserChildVO dataAccount){
		return childService.updateUserChild(dataAccount);
	}
	
	/**
	 * METODO DELETA A CONTA DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 7 de ago. de 2024
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value = "account/{id}")
	public ResponseEntity<?> deleteUserChild(@PathVariable @Valid @NotBlank String id) throws Exception {		
			return childService.deleteUserChild(Integer.parseInt(id));
	}
	
	/**
	 * METODO FAZ O LOGIN DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de jul. de 2024
	 * @param dataAccount
	 * @return
	 */
	@PostMapping(path="login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loginUserChild(@RequestBody @Valid LoginVO dataAccount) {
			return childService.authenticateLogin(dataAccount);

	}
}
