package br.com.edukid.api.vo.v1.configquiz;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class MateriaVO {
	
	@NotBlank
	private String id;
	private String name;
	@NotBlank
	@Pattern(regexp = "^-?\\d+$", message = "Key 'questionsQuantity' must be a string with the value of a valid integer")
	private String quantityQuestions;
	private List<TemaAprendizagemVO> temas;
	
	public void addTemaAprendizagemVO(TemaAprendizagemVO temaAprendizagemVO) {
		temas.add(temaAprendizagemVO);
	}
	
	public MateriaVO() {
		// TODO Auto-generated constructor stub
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
	public String getQuantityQuestions() {
		return quantityQuestions;
	}

	public void setQuantityQuestions(String quantityQuestons) {
		this.quantityQuestions = quantityQuestons;
	}

	public List<TemaAprendizagemVO> getTemas() {
		return temas;
	}
	public void setTemas(List<TemaAprendizagemVO> temas) {
		this.temas = temas;
	}
	
	
}
