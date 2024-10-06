package br.com.edukid.api.utils;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.edukid.api.vo.v1.performance.QuizPerformanceData;
import br.com.edukid.api.vo.v1.performance.SubjectPerformance;
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
     * METODO REALIZA O ENVIO DE UM E-MAIL SOBRE QUIZ NÃO REALIZADO 
     * COM CONTEÚDO PERSONALIZADO EM HTML E INCLUI UM GRÁFICO DE PIZZA GERADO DINAMICAMENTE.
     * @Author LUCAS BORGUEZAM
     * @Sice 5 de out. de 2024
     * @param to -> Destinatário
     * @param titleEmail -> Assunto do email
     * @param titleHTML -> Titulo da menssagem
     * @param altImageHTML -> Descrição da imagen
     * @param titleGraphic -> Titulo do gráfico
     * @param textHTML -> Texto do email
     * @param quizPerformanceData -> Dados de performance do quiz
     * @throws MessagingException
     * @throws IOException
     */
    public void sendEmailQuizNotRealized(String to, QuizPerformanceData quizPerformanceData) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        String titleEmail = "Quiz não realizado";
		String titleHTML = "Quiz não realizado";
		String altImageHTML = "Gráfico de acertos";
		String titleGraphic = "Acertos";
		String textHTML = quizPerformanceData.getNameUserChild()+" não realizou ou completou o quiz durante o periodo de realização.";
		
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
        Map<String, Integer> category = new HashMap<>();
        category.put("Não respondidas", quizPerformanceData.getTotalQuestions());
        
        JFreeChart pieChart = createPieChart(titleGraphic, category);
        ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(chartOutputStream, pieChart, 600, 400);

        // Adicionando gráfico como anexo
        helper.addInline("chart.png", new ByteArrayDataSource(chartOutputStream.toByteArray(), "image/png"));

        // Enviando o e-mail
        javaMailSender.send(message);
    }
    
    /**
     * 
     * METODO REALIZA O ENVIO DE UM E-MAIL SOBRE QUIZ NÃO REALIZADO 
     * COM CONTEÚDO PERSONALIZADO EM HTML E INCLUI UM GRÁFICO DE PIZZA GERADO DINAMICAMENTE.
     * @Author LUCAS BORGUEZAM
     * @Sice 5 de out. de 2024
     * @param to -> Destinatário
     * @param titleEmail -> Assunto do email
     * @param titleHTML -> Titulo da menssagem
     * @param altImageHTML -> Descrição da imagen
     * @param titleGraphic -> Titulo do gráfico
     * @param textHTML -> Texto do email
     * @param quizPerformanceData -> Dados de performance do quiz
     * @throws MessagingException
     * @throws IOException
     */
    public void sendEmailQuizRealized(String to, QuizPerformanceData quizPerformanceData) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        String titleEmail = "Quiz Realizado";
        String titleHTML = "Quiz realizado";
        String altImageHTML1 = "Gráfico de acertos";
        String altImageHTML2 = "Gráfico de erros e de acertos por matéria";
        String textHTML = quizPerformanceData.getNameUserChild()+" concluiu o quiz, veja nos gráficos abaixo os dados de desempenho nesse quiz.";
        
        // Montar HTML com dois gráficos
        String html = "<html>"
                    + "<body>"
                    + "<h1 style='color:blue;'>" + titleHTML + "</h1>"
                    + "<p>" + textHTML + "</p>"
                    + "<img src='cid:erros-e-acertos.png' alt='" + altImageHTML1 + "'/>"
                    + "<img src='cid:barchart.png' alt='" + altImageHTML2 + "'/>"
                    + "</body>"
                    + "</html>";
        
        // Configurando o e-mail
        helper.setTo(to);
        helper.setSubject(titleEmail);
        helper.setText(html, true);

        /*Criar Grafico de pizza para o erros e acerto das questões do quiz*/
        Map<String, Integer> errosEAcertosQuiz = new HashMap<>();
        errosEAcertosQuiz.put("Erros", quizPerformanceData.getTotalErrors());
        errosEAcertosQuiz.put("Acertos", quizPerformanceData.getTotalHits());
        
        JFreeChart graphicErrorsEAcertos = createPieChart("Erros e acertos totais", errosEAcertosQuiz);
        ByteArrayOutputStream graphicErrorsEAcertosByte = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(graphicErrorsEAcertosByte, graphicErrorsEAcertos, 600, 400);
        /*Adicionando gráficos pizza como anexo*/
        helper.addInline("erros-e-acertos.png", new ByteArrayDataSource(graphicErrorsEAcertosByte.toByteArray(), "image/png"));
        
        /*Materias*/
        /*Criando gráfico de barras de acertos por materias*/ 
        JFreeChart barChart = createBarChart(quizPerformanceData);
        ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(chartOutputStream, barChart, 600, 400);
        helper.addInline("barchart.png", new ByteArrayDataSource(chartOutputStream.toByteArray(), "image/png"));
        
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

    /**
     * METODO VRIA UM GRÁFICO DE BARRAS VERTICAL
     * @Author LUCAS BORGUEZAM
     * @Sice 6 de out. de 2024
     * @param correctAnswers
     * @param wrongAnswers
     * @return
     */
    public JFreeChart createBarChart(QuizPerformanceData quizPerformanceData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Populando o dataset com as matérias e os respectivos acertos e erros
        for (SubjectPerformance subject : quizPerformanceData.getSubjectPerformance()) {
            dataset.addValue(subject.getTotalHits(), "Acertos", subject.getSubject()); // Série "Acertos"
            dataset.addValue(subject.getTotalErrors(), "Erros", subject.getSubject()); // Série "Erros"
        }
        
        // Criando o gráfico de barras
        JFreeChart barChart = ChartFactory.createBarChart(
                "Desempenho por Matéria",    // Título do gráfico
                "Matéria",                   // Rótulo do eixo X
                "Quantidade",                // Rótulo do eixo Y
                dataset,                     // Dados
                PlotOrientation.VERTICAL,    // Orientação do gráfico
                true,                        // Legenda
                true,                        // Tooltips
                false);                      // URLs

        // Acessando o plot do gráfico
        CategoryPlot plot = barChart.getCategoryPlot();

        // Configurando o eixo Y para usar apenas números inteiros
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickUnit(new NumberTickUnit(1)); // Define o intervalo de ticks de 1 em 1
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); // Mostra apenas inteiros no eixo Y

        // Definindo o intervalo do eixo Y
        rangeAxis.setRange(new Range(0, quizPerformanceData.getTotalQuestions())); // Exemplo: O range vai de 0 a 10
        
        
        return barChart;
    }
}