package br.com.edukid.api.utils;

import org.springframework.stereotype.Service;

@Service
public class StringServices {

	/**
	 * METODO VERIFICA SE A STRING CONTÉM APENAS VALORES NUMÉRICOS INTEIROS
	 * @Author LUCAS BORGUEZAM
	 * @Sice 27 de ago. de 2024
	 * @param input
	 * @return
	 */
    public boolean isNumeric(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return input.matches("\\d+");
    }

    /**
     * METODO VERIFICA SE A STRING CONTÉM SOMENTE LETRAS E ESPAÇO
     * @Author LUCAS BORGUEZAM
     * @Sice 27 de ago. de 2024
     * @param input
     * @return
     */
    public boolean isAlphabetic(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return input.matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*");
    }
    
    /**
     * METODO VERIFICA SE A STRING CONTÉM ALGUMA LETRA
     * @Author LUCAS BORGUEZAM
     * @Sice 27 de ago. de 2024
     * @param input
     * @return
     */
    public boolean containsLetter(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return input.matches(".*[a-zA-Z].*");
    }
}
