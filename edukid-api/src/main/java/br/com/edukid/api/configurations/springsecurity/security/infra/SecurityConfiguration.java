package br.com.edukid.api.configurations.springsecurity.security.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Desabilita as configurações padrões do SPRING SECURITY, DEFININDO uma Configuração personalizada DO WEB SECURITY 
 * @Author LUCAS BORGUEZAM
 * @Sice 21 de set. de 2024
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	SecurityFilter securityFilter;

	/**
	 * CORRENTE DE FILTRO DE SEGURANÇA, FILTROS PARA VALIDAR SE O USUÁRIO ESTÁ APITO AO ACESSO A REQUISIÇÃO
	 * @throws Exception 
	 */
	@Bean
	public SecurityFilterChain SecurityFilterChain (HttpSecurity httpSecurity) throws Exception {		
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Habilitar Authentificação Stateless que é o padrão das aplicações REST(Apenas cria token para o usuário, diferente do statefull que armazena informações da sesão do usuário) 
				.authorizeHttpRequests(authorize -> authorize
							/*Sem permissões do EndPoint user-fathers*/
						.requestMatchers(HttpMethod.GET, "/edukid/image/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/edukid/user-father/account").permitAll()
						.requestMatchers(HttpMethod.POST, "/edukid/user-father/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/edukid/user-father/change-password").permitAll()
						.requestMatchers(HttpMethod.GET,  "/edukid/user-father/is-verify-account/**").permitAll()
							/*Sem permissões do EndPoint user-child*/
						.requestMatchers(HttpMethod.POST, "/edukid/user-child/login").permitAll()
						
							/*endpoints user-father*/
						.requestMatchers(HttpMethod.POST, "/edukid/user-father/**").hasRole("FATHER")
						.requestMatchers(HttpMethod.GET, "/edukid/user-father/**").hasRole("FATHER")
						.requestMatchers(HttpMethod.PUT, "/edukid/user-father/**").hasRole("FATHER")
						.requestMatchers(HttpMethod.DELETE, "/edukid/user-father/**").hasRole("FATHER")
							/*endpoints user-Child*/
						.requestMatchers(HttpMethod.POST, "/edukid/user-child/**").hasRole("FATHER")
						.requestMatchers(HttpMethod.GET, "/edukid/user-child/account/**").hasRole("FATHER")
						.requestMatchers(HttpMethod.GET, "/edukid/user-child/by-user-father/**").hasRole("FATHER")
						.requestMatchers(HttpMethod.PUT, "/edukid/user-child/**").hasRole("FATHER")
						.requestMatchers(HttpMethod.DELETE, "/edukid/user-child/**").hasRole("FATHER")
							/*endpoints conf-quiz */
						.requestMatchers(HttpMethod.POST, "/edukid/conf-quiz/subject-and-theme").hasRole("FATHER")
						
							/*Permissões do EndPoint user-child*/
						.requestMatchers(HttpMethod.GET, "/edukid/user-child/quiz-hystory/**").hasRole("CHILD")
						.requestMatchers(HttpMethod.PUT, "/edukid/conf-quiz/quiz").hasRole("CHILD")
						.requestMatchers(HttpMethod.GET, "/edukid/conf-quiz/quiz").hasRole("CHILD")
						.requestMatchers(HttpMethod.GET, "/edukid/user-child/ranking-week/**").hasRole("CHILD")

						.anyRequest().authenticated()
				)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	/**
	 * METODO PEGA A INSTANCIA DE UM AUTHENTIFICATION
	 * @Author LUCAS BORGUEZAM
	 * @Sice 21 de set. de 2024
	 * @param authenticationConfiguration
	 * @return
	 * @throws Exception
	 */
	 @Bean 
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		 return authenticationConfiguration.getAuthenticationManager();
	 }
	 
	 /**
	  * METODO FAZ A CRIPTOGRAFIA DA SENHA DO USUÁRIO
	  * @Bean
	  * @Author LUCAS BORGUEZAM
	  * @Sice 21 de set. de 2024
	  * @return
	  */
	 @Bean
	 public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}
	 
	 
}
