package pe.confianza.colaboradores.gcontenidos.server.util;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import pe.confianza.colaboradores.gcontenidos.server.bean.Mail;

@Component
public class EmailUtil {
	
	private static Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	public void enviarEmail(String [] to, String [] cc, String subject, String body) {
		LOGGER.info("[BEGIN] enviarEmail");
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			helper.setTo(to);
			if(cc != null && cc.length > 0) {
				helper.setCc(cc);
			}
			helper.setSubject(subject);
			helper.setText(body);
			emailSender.send(message);
		} catch (Exception e) {
			LOGGER.error("[ERROR] enviarEmail", e);
		}
		LOGGER.info("[END] enviarEmail");
	}
	
	public boolean enviarEmail(Mail mail) {
		LOGGER.info("[BEGIN] enviarEmail " + mail.toString());
		try {
			 MimeMessage message = emailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message,
	                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                    StandardCharsets.UTF_8.name());

	            Context context = new Context();
	            context.setVariables(mail.getContenido());
	            String html = templateEngine.process("email/fc", context);

	            helper.setTo(mail.getReceptor());
	            helper.setText(html, true);
	            helper.setSubject(mail.getAsunto());
	            helper.setFrom(mail.getEmisor());

	            emailSender.send(message);
	            LOGGER.info("[END] enviarEmail");
	            return true;
		} catch (Exception e) {
			LOGGER.error("[ERROR] enviarEmail", e);
			return false;
		}
	}

}
