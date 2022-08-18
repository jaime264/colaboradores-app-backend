package pe.confianza.colaboradores.gcontenidos.server.util;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

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
import pe.confianza.colaboradores.gcontenidos.server.bean.MailFile;

@Component
public class EmailUtil {
	
	private static Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);
	
	private static String EMAIL_PATTERN = "^(.+)@(\\S+)$";
	
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
		if(!Pattern.compile(EMAIL_PATTERN).matcher(mail.getReceptor()).matches())
			return false;
		try {
			 MimeMessage message = emailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message,
	                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                    StandardCharsets.UTF_8.name());

	            Context context = new Context();
	            context.setVariables(mail.getContenido());
	            String html = templateEngine.process("email/fc", context);

	            helper.setTo(mail.getReceptor().trim());
	            if(mail.getReceptorCC() != null && mail.getReceptorCC().length > 0) {
					helper.setCc(mail.getReceptorCC());
				}
	            helper.setText(html, true);
	            helper.setSubject(mail.getAsunto());
	            helper.setFrom(mail.getEmisor());
	            if(mail.getAdjuntos() != null) {
	            	for (MailFile adjunto : mail.getAdjuntos()) {
	            		helper.addAttachment(adjunto.getNombreArchivo(), adjunto.getAttachmentSource(), adjunto.getContentType());
					}
	            }
	            emailSender.send(message);
	            LOGGER.info("[END] enviarEmail");
	            return true;
		} catch (Exception e) {
			LOGGER.error("[ERROR] enviarEmail", e);
			return false;
		}
	}

}
