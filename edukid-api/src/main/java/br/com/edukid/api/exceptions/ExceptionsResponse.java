package br.com.edukid.api.exceptions;

import java.io.Serializable;
import java.util.Date;

/**
 * ESSA CLASSE APRESENTARA A EXCESSAO CUSTOMIZADA PARA SER RETORNADA.
 * @author LUCAS BORGUEZAM
 * @since 09072000
 */
public class ExceptionsResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date timestamp;
	private String message;
	private String details;
	private String warnning;
	
	public ExceptionsResponse(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	public ExceptionsResponse(Date timestamp, String message, String details, String warnning) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.warnning = warnning;
	}



	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	public String getWarnning() {
		return warnning;
	}
	public void setWarnning(String warnning) {
		this.warnning = warnning;
	}
	
}//ExceptionsResponse{}
