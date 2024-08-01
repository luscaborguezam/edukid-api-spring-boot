package br.com.edukid.api.services;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * CLASSE DE SERVIÇO PARA ENCAPSULAR A LÓGICA DE HASHING E VERIFICAÇÃO (PODE SER USADO EM SENHAS) 
 * USANDO BCRYPT(uTILIZA O SALT AUTOMATICAMENTE)
 * @Author LUCAS BORGUEZAM
 * @Sice 31 de jul. de 2024
 */
@Service
public class HashSaltService {
	
	/*CONFIGURAÇÃO QUE NUNCA MUDA POR ISSO É UMA CONSTANTE*/
	private final PasswordEncoder ENCODER;
	
	
    public HashSaltService() {
    	// Cria uma instância do BCryptPasswordEncoder
        this.ENCODER = new BCryptPasswordEncoder(); 
    }

    /**
     * MÉTODO PARA HASH DE SENHA 
     * @Author LUCAS BORGUEZAM
     * @Sice 31 de jul. de 2024
     * @param password
     * @return SENHA COM HASH E SALT
     */
    public String hash(String password) {
        return ENCODER.encode(password);
    }

    /**
     * METODO PARA VERIFICAR SE A SENHA CORRESPONDE AO HASH CADASTRADO NO BANCO DE DADOS
     * @Author LUCAS BORGUEZAM
     * @Sice 31 de jul. de 2024
     * @param rawPassword SENHA DIGITADA
     * @param hashedPassword SENHA COM HASH
     * @return
     */
    public boolean verifyHash(String rawPassword, String hashedPassword) {
        return ENCODER.matches(rawPassword, hashedPassword);
    }

}
