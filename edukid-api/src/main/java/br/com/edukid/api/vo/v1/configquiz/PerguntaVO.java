package br.com.edukid.api.vo.v1.configquiz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.edukid.api.entities.Pergunta;
import br.com.edukid.api.utils.JsonService;
import jakarta.validation.constraints.NotBlank;

public class PerguntaVO {
	@Autowired
	JsonService jsonService;

	@NotBlank
	private Integer id;

	@NotBlank
    private List<InfoPergunta> infoPerguntas;

	@NotBlank
    private Integer dificuldade;

	@NotBlank
    private Integer idTema;


	
	public void addItemInListInfoPergunta(InfoPergunta infoPergunta) {
		this.infoPerguntas.add(infoPergunta);
	}
	
	
	
	public PerguntaVO(Pergunta p) {
		this.id = p.getId();
		this.dificuldade = p.getDificuldade();
		this.idTema = p.getIdTema();
		this.infoPerguntas = new ArrayList<>(); 
	}
	
	public PerguntaVO(TemaAprendizagemVO theme) {
		this.idTema = Integer.parseInt(theme.getId());
	}

	public PerguntaVO() {}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<InfoPergunta> getInfoPerguntas() {
		return infoPerguntas;
	}

	public void setInfoPerguntas(List<InfoPergunta> infoPergunta) {
		this.infoPerguntas = infoPergunta;
	}

	public Integer getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(Integer dificuldade) {
		this.dificuldade = dificuldade;
	}

	public Integer getIdTema() {
		return idTema;
	}

	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}

}
