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
	
	@NotBlank
	private String idConteudo;
	
	@NotBlank
	private String bibliografia;
	
	@NotBlank
	private String img;
	
	

	
	
	
	
	public PerguntaVO(Pergunta p) {
		this.id = p.getId().toString();
		this.infoPerguntas = new ArrayList<>(); 
		this.dificuldade = p.getDificuldade().toString();
		this.idTema = p.getIdTema().toString();
		this.idConteudo = p.getIdConteudo().toString();
		this.bibliografia = p.getBibliografia();
		this.img = p.getImg();
		
	}
	
	public PerguntaVO(TemaAprendizagemVO theme) {
		this.idTema = theme.getId();
	}

	public PerguntaVO() {}

	public void addItemInListInfoPergunta(InfoPergunta infoPergunta) {
		this.infoPerguntas.add(infoPergunta);
	}

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
	
	public String getIdConteudo() {
		return idConteudo;
	}

	public void setIdConteudo(String idConteudo) {
		this.idConteudo = idConteudo;
	}

	public String getBibliografia() {
		return bibliografia;
	}

	public void setBibliografia(String bibliografia) {
		this.bibliografia = bibliografia;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
