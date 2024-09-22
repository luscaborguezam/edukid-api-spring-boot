package br.com.edukid.api.configurations.springsecurity.security.infra;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.edukid.api.configurations.springsecurity.security.UsersRole;
import br.com.edukid.api.configurations.springsecurity.security.DTO.Jwt;
import br.com.edukid.api.repositorys.UserChildRepository;
import br.com.edukid.api.repositorys.UserFatherRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserFatherRepository fatherRepository;
    @Autowired
    UserChildRepository childRepository; 

    /**
     * FILTRO PARA 
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if(token != null){
            Jwt login = tokenService.validateToken(token);
            if(login != null) {
            	System.out.println("login != null");
            	UserDetails user = null;
				if(login.getUserType().equals(UsersRole.FATHER.getRole())) 
            		user = fatherRepository.findByEmailUserDetails(login.getSubject());
            		
            	if(login.getUserType().equals(UsersRole.CHILD.getRole())) 
            		user = childRepository.findByNicknameUserDetails(login.getSubject());
            	
            	if(user!=null) {
            		var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            		SecurityContextHolder.getContext().setAuthentication(authentication);            		
            	}
            	
            }

        }
        filterChain.doFilter(request, response); //Vai para o próximo filtro
    }

    /**
     * METODO PEGA O TOKEN RETIRANDO O 'Barear ' DA STRING
     * @Author LUCAS BORGUEZAM
     * @Sice 22 de set. de 2024
     * @param request
     * @return
     */
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}