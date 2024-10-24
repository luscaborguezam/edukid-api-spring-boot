package br.com.edukid.api.vo.v1.ranking;

public class RankingVO {

	private Integer position;
	private String name;
	private Double score;
	
	
	public RankingVO(Integer position, String name, Double score) {
		this.position = position;
		this.name = name;
		this.score = score;
	}
	
	public RankingVO() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
	
}
