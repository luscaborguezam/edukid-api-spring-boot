package br.com.edukid.api.services;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.edukid.api.entities.Materia;
import br.com.edukid.api.entities.Pergunta;
import br.com.edukid.api.entities.TemaAprendizagem;
import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.entities.field.ConfigurationUserChild;
import br.com.edukid.api.mapper.EdukidMapper;
import br.com.edukid.api.repositorys.MateriaRepository;
import br.com.edukid.api.repositorys.PerguntaRepository;
import br.com.edukid.api.repositorys.TemaAprendizagemRepository;
import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.utils.JsonService;
import br.com.edukid.api.vo.v1.configquiz.MateriaVO;
import br.com.edukid.api.vo.v1.configquiz.MateriasETemasVO;
import br.com.edukid.api.vo.v1.configquiz.QuizVO;
import br.com.edukid.api.vo.v1.configquiz.TemaAprendizagemVO;

@Service
public class ConfigurationQuizService {
	
	@Autowired
	MateriaRepository materiaRepository;
	@Autowired
	TemaAprendizagemRepository temaAprendizagemRepository;
	@Autowired
	UserChildRepository childRepository;
	@Autowired
	JsonService jsonService;
	@Autowired
	PerguntaRepository perguntaRepository;
	
	/**
	 * METODO BUSCA MATERIAS E TEMAS RELACIONADO AO ANO DO ENSINO MÉDIO DO USUARIO FILHO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 28 de ago. de 2024
	 * @param yearHighSchool
	 * @return
	 */
	public ResponseEntity<?> getSubjectAndTheme(Integer yearHighSchool) {
		MateriasETemasVO  materiasVO = new MateriasETemasVO();
		
		List<Materia> materias = materiaRepository.findDistinctMateriasByYearHighScool(yearHighSchool);
		for(Materia m: materias) {
			MateriaVO materiaVO = EdukidMapper.parseObject(m, MateriaVO.class);
			materiaVO.setTemas(EdukidMapper.parseListObjects(temaAprendizagemRepository.findByYearHighScool(yearHighSchool), TemaAprendizagemVO.class));
			materiasVO.addMateriaVO(materiaVO);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(materiasVO);
	}
	
	/**
	 * METODO SALVA AS MATERIAS E TEMAS ESCOLHIDO PELO USUARIO PAI PARA AS CONFIGURACOES DO QUIZ
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de ago. de 2024
	 * @param materiasETemasVO
	 * @return
	 */
	public ResponseEntity<?> registerConfQuiz(MateriasETemasVO materiasETemasVO){
		/*Buscar registro*/
		Optional<UserChild> OpuserChild = childRepository.findById(Integer.parseInt(materiasETemasVO.getIdUserChild()));
		UserChild userChild = OpuserChild.get();
		/*Setar o json de configuracao*/
		userChild.setConfiguration(jsonService.toJson(
					EdukidMapper.parseObject(materiasETemasVO, ConfigurationUserChild.class)
				));
		childRepository.save(userChild);
		
		return ResponseEntity.status(HttpStatus.OK).body("Registrado!");
	}
	
	
	/**
	 * METODO BUSCA AS MATERIAS E TEMAS SALVOS NAS CONFIGURACOES PARA GERAR UM QUIZ PERSONALIZADO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de ago. de 2024
	 * @param idUserChild
	 * @return
	 */
	public MateriasETemasVO getSubjectandThemeConfiguredFromUserChild(Integer idUserChild){
		/*Buscar registro*/
		Optional<UserChild> OpuserChild = childRepository.findById(idUserChild);
		UserChild userChild = OpuserChild.get();
		MateriasETemasVO mt = jsonService.fromJson(userChild.getConfiguration(), MateriasETemasVO.class);
		mt.setIdUserChild(idUserChild.toString());
		
		return mt;
	}
	
	/**
	 * METODO GERA QUIZ ALEATORIO COM AS CONFIGURACOES CADASTRADAS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 31 de ago. de 2024
	 * @param idUserChild
	 * @return
	 */
	public ResponseEntity<?> toGenerateQuiz(Integer idUserChild){
		Integer idTema = 3, quantidadePerguntas = 2;
		
		Pageable limit = PageRequest.of(0, quantidadePerguntas);
		QuizVO quiz = new QuizVO();
		//quiz.setQuiz(perguntaRepository.findRandomPerguntasByTema(idTema, limit));
		quiz.setQuiz(perguntaRepository.findPerguntasByIdTema(idTema));
        return ResponseEntity.status(HttpStatus.OK).body(quiz);	
	}
	
}
