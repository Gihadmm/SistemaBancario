package Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Serviço responsável pelo envio de e-mails no sistema.
 * Utilizado principalmente na recuperação de senha, onde é enviada uma nova senha gerada aleatoriamente para o e-mail do usuário.
 */
public class EmailService {

    // E-mail do remetente (deve ser o mesmo usado para autenticar no SMTP)
    private static final String REMETENTE = "seuemail@gmail.com";

    // Senha do app gerada pelo Gmail (ou simulada se estiver usando console)
    private static final String SENHA = "senhaDoApp";

    /**
     * Envia um e-mail contendo a nova senha provisória ao usuário.
     *
     * @param destino   E-mail de destino (usuário que solicitou recuperação)
     * @param novaSenha Senha temporária gerada
     */
    public static void enviarNovaSenha(String destino, String novaSenha) {
        // Configurações do servidor SMTP do Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Autenticação
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(REMETENTE, SENHA);
                    }
                });

        try {
            // Criação da mensagem de e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMETENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            message.setSubject("Nova senha - Sistema Biblioteca");
            message.setText("Sua nova senha provisória é: " + novaSenha);

            // Envia o e-mail
            Transport.send(message);
            System.out.println("✔️ E-mail enviado com sucesso para: " + destino);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("❌ Erro ao enviar e-mail.");
        }
    }
}
