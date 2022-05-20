package pe.confianza.colaboradores.gcontenidos.server.config.datasource.mariadb;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration
//@EnableJpaRepositories(basePackages = "pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.dao",
//entityManagerFactoryRef = "colaboradoresMariaEntityManagerFactory",
//transactionManagerRef = "colaboradoresMariaTransactionManager")
//public class ColaboradoresMariaConfig {
//	
//	@Primary
//	@Bean
//    @ConfigurationProperties("colaboradores.mariadb")
//    public DataSourceProperties colaboradoresMariaDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//	@Primary
//	@Bean
//    @ConfigurationProperties("colaboradores.mariadb.configuration")
//    public DataSource colaboradoresMariayDataSource() {
//        return colaboradoresMariaDataSourceProperties().initializeDataSourceBuilder()
//                .type(HikariDataSource.class).build();
//    }
//	
//	@Primary
//	@Bean(name = "colaboradoresMariaEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean colaboradoresMariaEntityManagerFactory(
//            EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(colaboradoresMariayDataSource())
//                .packages("pe.confianza.colaboradores.gcontenidos.server.mariadb.colaboradores.entity")
//                .build();
//    }
//
//	@Primary
//    @Bean
//    public PlatformTransactionManager colaboradoresMariaTransactionManager(
//            final @Qualifier("colaboradoresMariaEntityManagerFactory") LocalContainerEntityManagerFactoryBean colaboradoresMariaEntityManagerFactory) {
//        return new JpaTransactionManager(colaboradoresMariaEntityManagerFactory.getObject());
//    }
//
//
//}
