package br.com.edukid.api.utils;

import java.lang.reflect.Field;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * CLASSE ARMAZENA METODOS UTEIS PARA DIVERSAS CLASSES 
 * @Author LUCAS BORGUEZAM
 * @Sice 14 de jul. de 2024
 */
@Service
public class UtilsService {

	/**
	 * METODO VERIFICA SE HÁ UM ATRIBUTO VAZIO EM QUALQUER OBJETO
	 * @Author LUCAS BORGUEZAM
	 * @Sice 14 de jul. de 2024
	 * @param obj OBJETO A SE VERIFICAR
	 * @return OBJETOS RESPONSEENTITY CASO ENCONTRE ALGO NULO OU VAZIO, SE NÃO ENCONTRAR RETORNA NULL
	 */
	public ResponseEntity<?> hasEmptyField(Object obj) {
        if (obj == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Null object");
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value == null) { /*se qualquer atributo for nulo*/
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Field "+field.getName()+" is null");
                } else if (value instanceof String && ((String) value).isEmpty()) { /*se qualquer string for vazia*/
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Field "+field.getName()+" is null"); 
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Data not null"); // nenhum atributo vazio encontrado
    }
	
}
