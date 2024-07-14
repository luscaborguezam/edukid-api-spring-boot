package br.com.edukid.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ESSA CLASSE APRESENTARA A EXCESSAO CUSTOMIZADA PARA SER RETORNADA QUANDO NAO ENCONTRAR NA BASE DE DADOS.
 * @author LUCAS BORGUEZAM
 * @since 09072000
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	/**
	 * METODO CONTRUTOR
	 * @param ex TEXTO DETALHAR A EXCECAO
	 */
	public ResourceNotFoundException(String ex) {
		//Retorna uma menssagem de erro
		super(ex);
	}
	

}
