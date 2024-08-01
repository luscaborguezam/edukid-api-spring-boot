package br.com.edukid.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.Mapper;

import br.com.edukid.api.entities.UserFather;
import br.com.edukid.api.mapper.DozerMapper;
import br.com.edukid.api.repositorys.UserFatherRepository;
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
	@Autowired
	UserFatherRepository fatherRepository;
	@Autowired
	HashSaltService hashSaltService;
	
	/**
	 * METODO CADASTRA DADOS DO USUARIO PAI
	 * @Author LUCAS BORGUEZAM
	 * @Sice 21 de jul. de 2024
	 * @param dataAccount
	 * @return STATUS CODE OK + OBJETO CADASRADO
	 */
	public ResponseEntity<?> registerUserFather(UserFatherVO data) {
		/*Faz o Hash da senha do usuario*/
		data.setPassword(hashSaltService.hash(data.getPassword()));
		
		
		/*Verificar se o email já foi cadastrado*/
		long exist = fatherRepository.countByEmail(data.getEmail());
		if(exist > 0)
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email já está em uso.");
			
		/*Converte VO para Entity e cadastra na base de dados*/
		var entity = DozerMapper.parseObject(data, UserFather.class);
		
			var vo = DozerMapper.parseObject(fatherRepository.save(entity), UserFatherVO.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(vo);
	}

	/**
	 * METODO RETORNA TODOS OS USER FATHERS REGISTRADOS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de jul. de 2024
	 * @return
	 */
	public ResponseEntity<?> findAllUserFather() {		
		return  ResponseEntity.status(HttpStatus.OK).body(DozerMapper.parseListObjects(fatherRepository.findAll(), UserFatherVO.class));
	}


}
	


