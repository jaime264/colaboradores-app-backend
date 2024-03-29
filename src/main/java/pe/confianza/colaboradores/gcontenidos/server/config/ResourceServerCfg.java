package pe.confianza.colaboradores.gcontenidos.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@ConditionalOnProperty(prefix = "security.basic", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerCfg extends ResourceServerConfigurerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(ResourceServerCfg.class);

	@Autowired
	Environment env;
	
	RemoteTokenServices tokenServices;
	
	@Value("${spring.security.oauth2.client.provider.jwk-set-uri}")
	private String urlCheckToken;
	
	@Value("${spring.security.oauth2.client.provider.client-id}")
	private String clientId;
	
	@Value("${spring.security.oauth2.client.provider.client-secret}")
	private String clientSecret;
	
	@Bean
    public ResourceServerTokenServices tokenService() {
        tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(clientId);
        tokenServices.setClientSecret(clientSecret);
        tokenServices.setCheckTokenEndpointUrl(urlCheckToken);
        return tokenServices;
    }

	@Override
	public void configure(HttpSecurity http) throws Exception {
		logger.info("[RESOURCESERV-CONF] Configurando la seguridad del servidor de recursos '{}'",
				env.getProperty("spring.application.name"));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/messages/**").access("#oauth2.hasScope('read')")
				.antMatchers(HttpMethod.POST, "/messages/**").access("#oauth2.hasScope('write')");
		
		configureH2Console(http);
		
		logger.info("[RESOURCESERV-CONF] Configuración de seguridad del servidor de recursos finalizada");
	}
	
	/**
	 * Configuración de seguridad para la consola de administración que
	 * proporciona H2. Sólo en le caso de que la propiedad
	 * {@code spring.h2.console.enabled} sea {@code true}
	 * 
	 * @param http
	 *            objeto {@link HttpSecurity}
	 * @throws Exception
	 */
	private void configureH2Console(HttpSecurity http) throws Exception {

		if (Boolean.valueOf(env.getProperty("spring.h2.console.enabled"))) {
			logger.debug("Aplicando reglas de seguridad para H2 console");
			http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests()
					.antMatchers("/h2-console/**").permitAll();

			// TODO comprobar si realmente es necesario
			http.csrf().disable();
			http.headers().frameOptions().disable();
		}
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("portal");
	}

}
