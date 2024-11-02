package br.com.edukid.api.entities;

import java.io.Serializable;

import br.com.edukid.api.entities.ids.ConfTemaId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * CLASSE REPRESENTA A TABELA USER
 * @Author LUCAS BORGUEZAM
 * @Sice 7 de ago. de 2024
 */
@Entity
@Table(name="conf_tema")
public class ConfTema implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ConfTemaId id;
	
	public ConfTema() {
		id = new ConfTemaId();
	}
	
	public ConfTema(Integer idUserChild, Integer idSubject,Integer idTema) {
		id = new ConfTemaId(idUserChild, idSubject, idTema);
	}

	public ConfTemaId getId() {
		return id;
	}

	public void setId(ConfTemaId id) {
		this.id = id;
	}
	
	
}
