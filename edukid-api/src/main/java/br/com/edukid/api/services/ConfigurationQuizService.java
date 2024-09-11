package br.com.edukid.api.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.edukid.api.entities.Materia;
import br.com.edukid.api.entities.Pergunta;
import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.entities.field.ConfigurationUserChild;
import br.com.edukid.api.mapper.EdukidMapper;
import br.com.edukid.api.repositorys.MateriaRepository;
import br.com.edukid.api.repositorys.PerguntaRepository;
import br.com.edukid.api.repositorys.TemaAprendizagemRepository;
import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.utils.JsonService;
import br.com.edukid.api.vo.v1.configquiz.InfoPergunta;
import br.com.edukid.api.vo.v1.configquiz.MateriaVO;
import br.com.edukid.api.vo.v1.configquiz.MateriasETemasVO;
import br.com.edukid.api.vo.v1.configquiz.PerguntaVO;
import br.com.edukid.api.vo.v1.configquiz.TemaAprendizagemVO;
import br.com.edukid.api.vo.v1.quiz.QuizMateriaVO;
import br.com.edukid.api.vo.v1.quiz.QuizVO;
import br.com.edukid.api.vo.v1.configquiz.MateriaVO;

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
			materiaVO.setTemas(EdukidMapper.parseListObjects(temaAprendizagemRepository.findByYearHighScoolAndIdSubject(yearHighSchool, m.getId()), TemaAprendizagemVO.class));
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
	/*Buscar as configurações do quiz*/
		Optional<UserChild> OpuserChild = childRepository.findById(idUserChild);
		UserChild userChild = OpuserChild.get();
		/*Transformar json de configuração em objeto*/
		ConfigurationUserChild confQuiz = jsonService.fromJson(userChild.getConfiguration(), ConfigurationUserChild.class);
		
		/*Buscar o quiz de cada matéria e temas relcionados a matéria segundo a configuração cadastrada para o usuário filho*/
		List<QuizMateriaVO> quizMateriaVOs = getQuizForSubject(confQuiz);
		
		/*Definir a quantidade de questões no quiz final*/
		
	
		/*Adicionar ao quiz a quantidade de questões definida nas configurações do user child*/
		QuizVO quiz = new QuizVO();
		quiz.setMaterias(quizMateriaVOs);
		//quiz.setQuiz(perguntasVO.subList(0, Math.min(userChild.getQuestionsQuantity(), copiaLista.size())) );

		
        return ResponseEntity.status(HttpStatus.OK).body(quizMateriaVOs);
	}
	
	/**
	 * METODO CRIA QUIZ POR MATÉRIAS E TEMAS SALVOS NAS CONFIGURAÇÃO DO USUÁRIO 
	 * O QUIZ DE CADA MATÉRIA TEM O NÚMERO DE QUESTÕES DEFINIDAS NA CONFIGURAÇÃO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de set. de 2024
	 * @param confQuiz
	 * @return
	 */
	private List<QuizMateriaVO> getQuizForSubject(ConfigurationUserChild confQuiz) {
		List<QuizMateriaVO> subjectsTheQuestions = new ArrayList<>();
		

		for(MateriaVO subject : confQuiz.getMaterias()){
			/*Buscar nome de cada matéria*/
			Optional<Materia> optional = materiaRepository.findById(Integer.parseInt(subject.getId()));
			Materia subjectEntity = optional.get();
			
			QuizMateriaVO subjectTheQuestion = new QuizMateriaVO(subjectEntity);
			List<PerguntaVO> perguntasVO = new ArrayList<>();
			
			for(TemaAprendizagemVO theme: subject.getTemas()) {	
			/*Buscar e criar um quiz com a lista de perguntas relaconadas aos temas para cada matéria listadas na configuração*/
				/*Buscar lista de perguntas relacionadas ao tema*/
				List<Pergunta> perguntas = perguntaRepository.findPerguntasByIdTema(Integer.parseInt(theme.getId()));
				/*Converter objeto*/
				perguntasVO = convertPerguntasToPerguntasVO(perguntas, perguntasVO);
			}
	        /*Embaralhar os elementos da lista(quiz)*/
	        Collections.shuffle(perguntasVO, new Random());
			/*Adicionar o quiz com a quantidade de perguntas definidas na configuração a matéria*/
	        Integer questionsQuantity = Integer.parseInt(subject.getQuantityQuestons());
			subjectTheQuestion.setQuiz(perguntasVO.subList(0, Math.min(questionsQuantity, perguntasVO.size())));
			/*Adicionar matéria a lista de matérias*/
			subjectsTheQuestions.add(subjectTheQuestion);
		}
		
		return subjectsTheQuestions;	
	}
	
	/**
	 * METODO TRANSFORMA OBJETO PERGUNTA DA LISTA DE OBJETOS PERGNTA PARA OBJETO PERGUNTASVO E ADICIONA A LISTA PERGUNTASVO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 8 de set. de 2024
	 * @param perguntas LISTA DE OBJETO PERGUNTAS
	 * @return
	 */
	private List<PerguntaVO> convertPerguntasToPerguntasVO(List<Pergunta> perguntas, List<PerguntaVO> perguntasVO) {
		/*Transformar Lista de perguntas no objeto Quiz para retorno*/
		for (Pergunta p : perguntas) {
			/*Transformar Pergunta em PerguntaVO*/
				PerguntaVO perguntaVO = new PerguntaVO(p);
				InfoPergunta infoPergunta = jsonService.fromJson(p.getInfoPergunta(), InfoPergunta.class);
				/*Adicionar infoPergunta a lista de perguntasVO*/
				perguntaVO.addItemInListInfoPergunta(infoPergunta);
				perguntasVO.add(perguntaVO);
		}
		
		return perguntasVO;
	}
	
}
