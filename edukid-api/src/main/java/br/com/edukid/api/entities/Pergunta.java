	package br.com.edukid.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="pergunta")
public class Pergunta {
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_pergunta")
	    private Integer id;

	    @Column(name = "info_pergunta", columnDefinition = "json")
	    private String infoPergunta;

	    @Column(name = "dificuldade")
	    private Integer dificuldade;

	    @JoinColumn(name = "id_tema", nullable = false)
	    private Integer idTema;
	    
	    @Column(name = "id_conteudo", nullable = false)
	    private Integer idConteudo;
	    
	    @Column(name = "bibliografia", nullable = false)
	    private String bibliografia;
	    
	    @Column(name = "img")
	    private String img;

	    
	    // Getters and Setters
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getInfoPergunta() {
			return infoPergunta;
		}

		public void setInfoPergunta(String infoPergunta) {
			this.infoPergunta = infoPergunta;
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

		public Integer getIdConteudo() {
			return idConteudo;
		}

		public void setIdConteudo(Integer idConteudo) {
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
