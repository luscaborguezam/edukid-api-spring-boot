package br.com.edukid.api.vo.v1.configquiz;

import java.util.ArrayList;
import java.util.List;

import br.com.edukid.api.entities.Pergunta;
import jakarta.validation.constraints.NotBlank;

public class PerguntaVO {

	@NotBlank
	private String id;

	@NotBlank
    private List<InfoPergunta> infoPerguntas;

	@NotBlank
    private String dificuldade;

	@NotBlank
    private String idTema;


	
	public void addItemInListInfoPergunta(InfoPergunta infoPergunta) {
		this.infoPerguntas.add(infoPergunta);
	}
	
	
	
	public PerguntaVO(Pergunta p) {
		this.id = p.getId().toString();
		this.dificuldade = p.getDificuldade().toString();
		this.idTema = p.getIdTema().toString();
		this.infoPerguntas = new ArrayList<>(); 
	}
	
	public PerguntaVO(TemaAprendizagemVO theme) {
		this.idTema = theme.getId();
	}

	public PerguntaVO() {}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<InfoPergunta> getInfoPerguntas() {
		return infoPerguntas;
	}

	public void setInfoPerguntas(List<InfoPergunta> infoPergunta) {
		this.infoPerguntas = infoPergunta;
	}

	public String getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(String dificuldade) {
		this.dificuldade = dificuldade;
	}

	public String getIdTema() {
		return idTema;
	}

	public void setIdTema(String idTema) {
		this.idTema = idTema;
	}

}
