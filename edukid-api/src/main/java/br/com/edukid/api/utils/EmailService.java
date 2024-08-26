package br.com.edukid.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * METODO FAZ ENVIO DE E-MAIl COM CÓDIGO PARA ALTERAR SENHA
     * @Author LUCAS BORGUEZAM
     * @Sice 25 de ago. de 2024
     * @param destinatario
     * @param assunto
     * @param corpoMensagem
     */
    public void sendEmailChangePasword(String destinatario, String codigoAleatorio) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject("Edukid - Mudar Senha");
        mensagem.setText("Utilize esse códdigo para fazer login: \n"
        					+codigoAleatorio);
        mensagem.setFrom("EduKid");

        javaMailSender.send(mensagem);

        System.out.println("E-mail enviado com sucesso!");
    }
    
    /**
     * METODO FAZ ENVIO DE E-MAIl COM CÓDIGO PARA CONFIRMAR CADASTRO
     * @Author LUCAS BORGUEZAM
     * @Sice 25 de ago. de 2024
     * @param destinatario
     * @param assunto
     * @param corpoMensagem
     */
    public void sendEmailConfirmRegistration(String destinatario, String codigoAleatorio) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject("Edukid - Confirmação de cadastro");
        mensagem.setText("Utilize esse códdigo para fazer login: \n"
        					+codigoAleatorio);
        mensagem.setFrom("EduKid@gmail.com");

        javaMailSender.send(mensagem);

        System.out.println("E-mail enviado com sucesso!");
    }
}