package br.com.edukid.api.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.nio.file.Files;

/**
 * NÃO VAI PRECISAR
 * CLASSE DISPONIBILIZA IMAGENS EM ENDPOINTS 
 *  
 * @Author LUCAS BORGUEZAM
 * @Sice 20 de out. de 2024
 */

@RestController
@RequestMapping("/edukid/image")
public class ImageController {
	
	@GetMapping("/bola")
    public ResponseEntity<byte[]> getImage() {
        try {
        	System.out.println("Entrou no endpoint");
            // Carrega a imagem dos recursos
            Resource imgFile = new ClassPathResource("static/images/bola.png");

            // Converte a imagem para um array de bytes
            byte[] imageBytes = Files.readAllBytes(imgFile.getFile().toPath());

            // Configura os cabeçalhos de resposta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // Ajuste para o tipo da imagem

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Retorna 404 se a imagem não for encontrada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
