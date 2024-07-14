package br.com.edukid.api.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

/**
 * CLASSE CRIADA PARA CONTER METODOS QUE FAZEM CONVERSÕES DE OBJETOS
 * EXEMPLO VO -> ENTITY OU ENTITY -> VALUE OBJECT 
 * @Author LUCAS BORGUEZAM
 * @Sice 14 de jul. de 2024
 */
public class DozerMapper {

	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	/**
	 * METODO CONVERTE OBJETO DE ORIGEM NO OBJETO DE DESTINO, 
	 * DE FORMA QUE O OBJETO DE DESTINO TENHA AS INFORMAÇÕES DO OBJETO DE ORIGEM.
	 * @Author LUCAS BORGUEZAM
	 * @Sice 14 de jul. de 2024
	 * @param <O>
	 * @param <D>
	 * @param origin
	 * @param destination
	 * @return
	 */
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	/**
	 * METODO CONVERTE LISTA DE OBJETOS DE ORIGEM EM UMA LISTA DE OBJETOS DE DESTINO, 
	 * DE FORMA QUE O OBJETO DE DESTINO TENHA AS INFORMAÇÕES DO OBJETO DE ORIGEM.
	 * @Author LUCAS BORGUEZAM
	 * @Sice 14 de jul. de 2024
	 * @param <O>
	 * @param <D>
	 * @param origin
	 * @param destination
	 * @return
	 */
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination){
		List<D> destinationObjects = new ArrayList<D>();
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}
	
	
}
