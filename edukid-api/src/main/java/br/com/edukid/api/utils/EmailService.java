package br.com.edukid.api.utils;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;

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
    
    /**
     * 
     * METODO REALIZA O ENVIO DE UM E-MAIL COM CONTEÚDO PERSONALIZADO EM HTML E INCLUI UM GRÁFICO DE PIZZA GERADO DINAMICAMENTE.
     * @Author LUCAS BORGUEZAM
     * @Sice 5 de out. de 2024
     * @param to -> Destinatário
     * @param titleEmail -> Assunto do email
     * @param titleHTML -> Titulo da menssagem
     * @param altImageHTML -> Descrição da imagen
     * @param titleGraphic -> Titulo do gráfico
     * @param textHTML -> Texto do email
     * @param category -> Categorias a ser listadas no gráfico de pizza
     * @throws MessagingException
     * @throws IOException
     */
    public void sendEmailWithChart(String to, String titleEmail, String titleHTML, String altImageHTML, String titleGraphic, String textHTML, Map<String, Integer> category) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        /*Montar html personalizado*/
        String html = "<html>"
	                + "<body>"
	                + "<h1 style='color:blue;'>"+titleHTML+"</h1>"
	                + "<p>"+textHTML+"</p>"
	                + "<img src='cid:chart.png' alt='"+altImageHTML+"'/>"
	                + "</body>"
	                + "</html>";
        
        // Configurando o e-mail
        helper.setTo(to);
        helper.setSubject(titleEmail);
        helper.setText(html, true);

        // Criando gráfico de pizza
        JFreeChart pieChart = createPieChart(titleGraphic, category);
        ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(chartOutputStream, pieChart, 600, 400);

        // Adicionando gráfico como anexo
        helper.addInline("chart.png", new ByteArrayDataSource(chartOutputStream.toByteArray(), "image/png"));

        // Enviando o e-mail
        javaMailSender.send(message);
    }
    
    /**
     * METODO CRIA GRÁFICO DE PIZZA
     * @Author LUCAS BORGUEZAM
     * @Sice 5 de out. de 2024
     * @param title -> TITULO DO GRÁFICO
     * @param category -> MATERIAL A SER LISTADO NO GRÁFICO
     * @return
     */

    private JFreeChart createPieChart(String title, Map<String, Integer> category) {
    	/*Pegar os dados que o gráfico usará, as categorias e seus respesctivos valores*/
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String key : category.keySet()) {
            dataset.setValue(key, category.get(key));
        }
        
        JFreeChart chart = ChartFactory.createPieChart(
        		title, // Título
                dataset, // Dados
                true, // Incluir legenda
                true,
                false);

        /*Cor do titulo do gráfico*/
        chart.getTitle().setPaint(Color.BLUE);
        return chart;
    }

}