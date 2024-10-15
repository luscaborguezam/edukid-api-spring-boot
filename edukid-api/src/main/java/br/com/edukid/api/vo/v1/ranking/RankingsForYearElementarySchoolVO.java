package br.com.edukid.api.vo.v1.ranking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingsForYearElementarySchoolVO {

	private Map<String, List<RankingVO>> rankingsForYear;
	
	public RankingsForYearElementarySchoolVO() {
		rankingsForYear = new HashMap<>();
	}

	public void addElemento(String ano, List<RankingVO> ranking) {
		this.rankingsForYear.put(ano, ranking);
	}
	
	public Map<String, List<RankingVO>> getRankingsForYear() {
		return rankingsForYear;
	}

	public void setRankingsForYear(Map<String, List<RankingVO>> rankingsForYear) {
		this.rankingsForYear = rankingsForYear;
	}
	
	
}