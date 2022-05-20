package pe.confianza.colaboradores.gcontenidos.server.config.datasource.mongodb;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@Configuration
//@EnableMongoRepositories(basePackages = "pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.dao",
//        mongoTemplateRef = "colaboradoresMongoTemplate")
//@ConfigurationProperties(prefix = "colaboradores.mongodb")
//public class ColaboradoresMongoConfig extends AbstractMongoConfig {
//
//	@Primary
//    @Bean(name = "colaboradoresMongoTemplate")
//    @Override
//    public MongoTemplate getMongoTemplate() throws Exception {
//        return new MongoTemplate(mongoDbFactory());
//    }
//}
