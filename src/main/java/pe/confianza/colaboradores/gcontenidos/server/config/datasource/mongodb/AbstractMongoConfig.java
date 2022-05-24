package pe.confianza.colaboradores.gcontenidos.server.config.datasource.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public abstract class AbstractMongoConfig {

	private String host;
	private String database;
	private int port;
	private String username;
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MongoDbFactory mongoDbFactory() throws Exception {
		ServerAddress serverAddress = new ServerAddress(host, port);
		List<MongoCredential> mongoCredentialList = new ArrayList<>();
		MongoCredential mongoCredential = MongoCredential.createCredential(username, database, password.toCharArray());
		mongoCredentialList.add(mongoCredential);
		return new SimpleMongoDbFactory(new MongoClient(serverAddress, mongoCredentialList), database);
	}

	/*
	 * Factory method to create the MongoTemplate
	 */
	abstract public MongoTemplate getMongoTemplate() throws Exception;

}
