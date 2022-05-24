package pe.confianza.colaboradores.gcontenidos.server.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {
	
	@Value("${firebase.dabase.url}")
	private String urlFirebase;
	
	@Value("${firebase.path.certificado}")
	private String certificadoFirebase;
	
	@Primary
	@Bean
	public FirebaseApp getfirebaseApp() throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder()
				  .setCredentials(GoogleCredentials.fromStream(new FileInputStream(new File(certificadoFirebase))))
				  .setDatabaseUrl(urlFirebase)
				  .build();
		return FirebaseApp.initializeApp(options).getInstance();
	}

}
