package br.com.edukid.api.utils;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

@Service
public class JsonService {

    private final Gson gson;

    public JsonService() {
        this.gson = new Gson();
    }

    /**
     * Converte um objeto para uma string JSON.
     * @param object o objeto a ser convertido.
     * @return a string JSON correspondente.
     */
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * Converte uma string JSON para um objeto da classe especificada.
     * @param json a string JSON a ser convertida.
     * @param clazz a classe do objeto de destino.
     * @param <T> o tipo do objeto de destino.
     * @return o objeto convertido.
     * @throws JsonSyntaxException se ocorrer um erro na conversão.
     */
    public <T> T fromJson(String json, Class<T> clazz) throws JsonSyntaxException {
        return gson.fromJson(json, clazz);
    }
    
    
    /**
     * METODO CONVERTE UMA STRING JSON EM UMA LISTA DE OBJETO
     * @Author LUCAS BORGUEZAM
     * @Sice 10 de set. de 2024
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, listType);
    }
}
