package br.com.edukid.api.entities.field;

import java.util.ArrayList;
import java.util.List;

import br.com.edukid.api.vo.v1.configquiz.MateriaVO;

public class ConfigurationUserChild {

private List<MateriaVO> materias;
	
	public void addMateriaVO(MateriaVO materiaVO) {
		materias.add(materiaVO);
	}

	public ConfigurationUserChild() {
		materias = new ArrayList<>();
	}

	public List<MateriaVO> getMaterias() {
		return materias;
	}

	public void setMaterias(List<MateriaVO> materias) {
		this.materias = materias;
	}

	

}
