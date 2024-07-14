package br.com.edukid.api.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import br.com.edukid.api.exceptions.ExceptionsResponse;
import br.com.edukid.api.exceptions.ResourceNotFoundException;

/**
 * ESSA CLASSE APRESENTARA A EXCESSAO CUSTOMIZADA PARA SER RETORNADA.
 * @author LUCAS BORGUEZAM
 * @since 09072000
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler {
	
	/**
	 * ESTE METODO TRATA TODAS AS EXCECOES NAO TRATADAS
	 * @param ex
	 * @param request
	 * @return RESPONSE HTTP 500 (INTERNAL SERVER ERROR)
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionsResponse> handleAllEception(Exception ex, WebRequest request){
		ExceptionsResponse exceptionResponse = new ExceptionsResponse(
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	/**
	 *  METODO TRATA EXCECOES DO TIPO NOT_FOUND 
	 *  E RETORNA UMA RESPOSTA HTTP 404 (BAD REQUEST).
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionsResponse> handleNotFundException(Exception ex, WebRequest request){
			ExceptionsResponse exceptionResponse = new ExceptionsResponse(
					new Date(), 
					ex.getMessage(), 
					request.getDescription(false));
			return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
		}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ExceptionsResponse> handlerMethodArgumentNotValidException(Exception ex, WebRequest request){
		ExceptionsResponse exceptionResponse = new ExceptionsResponse(
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
		
	}
}
