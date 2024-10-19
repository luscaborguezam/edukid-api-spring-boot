package br.com.edukid.api.vo.v1.configquiz;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

public class MateriasETemasVO {
	
	@Pattern(regexp = "^-?\\d+$", message = "Key 'idUserChild' must be a string with the value of a valid integer")
	private String idUserChild;
	@Valid
	private List<MateriaVO> materias;
	
	public void addMateriaVO(MateriaVO materiaVO) {
		materias.add(materiaVO);
	}

	public MateriasETemasVO() {
		materias = new ArrayList<>();
	}

	public List<MateriaVO> getMaterias() {
		return materias;
	}

	public void setMaterias(List<MateriaVO> materias) {
		this.materias = materias;
	}

	public String getIdUserChild() {
		return idUserChild;
	}

	public void setIdUserChild(String idUserChild) {
		this.idUserChild = idUserChild;
	}

	
}
