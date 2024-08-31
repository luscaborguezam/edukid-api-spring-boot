package br.com.edukid.api.vo.v1.configquiz;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class MateriaVO {
	
	@NotBlank
	private String id;
	@NotBlank
	private String name;
	private List<TemaAprendizagemVO> temas;
	
	public void addTemaAprendizagemVO(TemaAprendizagemVO temaAprendizagemVO) {
		temas.add(temaAprendizagemVO);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TemaAprendizagemVO> getTemas() {
		return temas;
	}
	public void setTemas(List<TemaAprendizagemVO> temas) {
		this.temas = temas;
	}
	
	
}
