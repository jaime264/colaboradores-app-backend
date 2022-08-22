package pe.confianza.colaboradores.gcontenidos.server;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import pe.confianza.colaboradores.gcontenidos.server.util.Constantes;

import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import pe.confianza.colaboradores.gcontenidos.server.negocio.VacacionesTareasProgramadasNegocio;
import pe.confianza.colaboradores.gcontenidos.server.negocio.impl.VacacionesTareasProgramadasNegocioImpl;


@SpringBootApplication
@EnableScheduling
public class GestorContenidosServerApplication {
	
	@Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(Constantes.FORMATO_FECHA_HORA);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA_HORA)));
        };
    }

	public static void main(String[] args) throws UnknownHostException {
		ApplicationContext applicationContext = SpringApplication.run(GestorContenidosServerApplication.class, args);
		iniciarVacacionesActualizacion(applicationContext);
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es", "PE"));
		return localeResolver;
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding(StandardCharsets.ISO_8859_1.name());
		return messageSource;
	}
	
	private static void iniciarVacacionesActualizacion(ApplicationContext applicationContext) {
		VacacionesTareasProgramadasNegocio vacacioneTareas = applicationContext.getBean(VacacionesTareasProgramadasNegocioImpl.class);
//		vacacioneTareas.actualizarEstadoProgramaciones();
//		vacacioneTareas.actualizarPeriodos();
//		vacacioneTareas.consolidarMetasAnuales(false);
	}

}
