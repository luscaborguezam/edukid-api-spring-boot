package br.com.edukid.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.edukid.api.utils.UtilsService;
import br.com.edukid.api.vo.v1.UserFatherVO;

/**
 * CLASSE CRIADA PARA FAZER O CONTROLE DE END POINTS RELACIONADOS AO LOGIN E CADASTRO DE USUÁRIO PAI.
 * @Author LUCAS BORGUEZAM
 * @Sice 14 de jul. de 2024
 */
@Service
public class RegisterAccountService {
	
	@Autowired
	UtilsService utilsService;
	
	public ResponseEntity<?> registerUserFather(UserFatherVO dataAccount) {
		// TODO Auto-generated method stub
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid data");
	}


}
	


