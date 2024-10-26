package pe.edu.cibertec.micro_auth.helper;

import jakarta.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pe.edu.cibertec.micro_auth.entity.TemporalRegistration;

@Component
@NoArgsConstructor
@Slf4j
public class EmailHelper implements IEmail  {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmail(TemporalRegistration temporalRegistration) {
        try {
            log.info("EmailHelper.sendEmail.request " +
                    "Email " + temporalRegistration.getUsuario().getEmail() +
                    "asunto " + temporalRegistration.getAsunto() );
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(temporalRegistration.getUsuario().getEmail());
            helper.setSubject(temporalRegistration.getAsunto());

            // Procesar la plantilla Thymeleaf
            Context context = new Context();
            StringBuilder gretting = new StringBuilder();
            gretting.append("Hola ");
            gretting.append(temporalRegistration.getUsuario().getName());
            context.setVariable("name", gretting);
            context.setVariable("secutityCode", temporalRegistration.getId());
            String contenidoHtml = templateEngine.process("email", context);
            helper.setText(contenidoHtml, true);
            javaMailSender.send(message);

        } catch (Exception e) {
            System.out.println("hola " + e);
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}
