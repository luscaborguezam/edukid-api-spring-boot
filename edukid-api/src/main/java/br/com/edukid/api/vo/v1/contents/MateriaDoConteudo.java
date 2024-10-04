package br.com.edukid.api.vo.v1.contents;

import java.util.ArrayList;
import java.util.List;

public class MateriaDoConteudo {

	private String subject;
	private String id;
	private List<ConteudoParaEstudo> contents;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ConteudoParaEstudo> getContents() {
		return contents;
	}
	public void setContents(List<ConteudoParaEstudo> contents) {
		this.contents = contents;
	}
	
	public MateriaDoConteudo() {
		contents = new ArrayList<ConteudoParaEstudo>();
	}
	
	
}
