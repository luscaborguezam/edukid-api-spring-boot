package br.com.edukid.api.configurations.springsecurity.security.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.edukid.api.configurations.springsecurity.security.UsersRole;
import br.com.edukid.api.configurations.springsecurity.security.DTO.Jwt;
import br.com.edukid.api.entities.UserChild;
import br.com.edukid.api.entities.UserFather;
import br.com.edukid.api.mapper.EdukidMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * METODO CRIA UM TOKEN PARA O USER FATHER
     * @Author LUCAS BORGUEZAM
     * @Sice 22 de set. de 2024
     * @param user
     * @return
     */
    public String generateToken(UserFather user){
    	UsersRole role = UsersRole.FATHER;
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("edukid-api")//Emissor do token 
                    .withSubject(user.getEmail())//Usuário que recebe o token
                    .withExpiresAt(genExpirationDate())//Tempo de expiração
                    .withClaim("userType", role.getRole())  // Adiciona o tipo do usuário ao JWT
                    .sign(algorithm);//Faz a assinatura e a criação do token
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token to userFather", exception);
        }
    }
    
    /**
     * METODO CRIA UM TOKEN PARA O USER CHILD
     * @Author LUCAS BORGUEZAM
     * @Sice 22 de set. de 2024
     * @param user
     * @return
     */
    public String generateToken(UserChild user){
    	UsersRole role = UsersRole.CHILD;
        try{
	            Algorithm algorithm = Algorithm.HMAC256(secret);
	            String token = JWT.create()
	                    .withIssuer("edukid-api")
	                    .withSubject(user.getNickname())
	                    .withExpiresAt(genExpirationDate())
	                    .withClaim("userType", role.getRole())  // Adiciona o tipo do usuário ao JWT
	                    .sign(algorithm);
	            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token to userChild", exception);
        }
    }

    /**
     * METODO FAZ A VALIDAÇÃO DO TOKEN
     * CONSIDERA A DECRIPTOGRAFIA COM O SECRET, O EMISSOR E O TEMPO DE EXPRIRAÇÃO DO TOKEN 
     * @Author LUCAS BORGUEZAM
     * @Sice 22 de set. de 2024
     * @param token
     * @return Jwt
     */
    public Jwt validateToken(String token){
        try {
        	System.out.println(token);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("edukid-api")  // Certifique-se de usar o mesmo issuer da geração do token
                    .build()
                    .verify(token);  // Verifica e decodifica o token            
            return new Jwt(decodedJWT.getSubject(), decodedJWT.getClaim("userType").asString());
        } catch (JWTVerificationException e){
        	System.out.println("Token validation error: " + e.getMessage());
        	e.printStackTrace();
            return null;
        }
    }

    /**
     * METODO DEFINE O TEMPO DE DURAÇÃO DO TOKEN
     * @Author LUCAS BORGUEZAM
     * @Sice 22 de set. de 2024
     * @return
     */
    private Instant genExpirationDate(){
    	//'plusHours' define o tempo do token
    	//'ZoneOffset.of' Configura para zona do horário de brasilía
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}