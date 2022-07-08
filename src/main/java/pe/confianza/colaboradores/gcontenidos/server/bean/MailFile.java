package pe.confianza.colaboradores.gcontenidos.server.bean;

import org.springframework.core.io.ByteArrayResource;

public class MailFile {

	private String nombreArchivo;
	private String contentType;
	private byte[] inFile;
	
	public MailFile() {}	
	
	public MailFile(String nombreArchivo, String contentType, byte[] inFile) {
		super();
		this.nombreArchivo = nombreArchivo;
		this.contentType = contentType;
		this.inFile = inFile;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getInFile() {
		return inFile;
	}
	public void setInFile(byte[] inFile) {
		this.inFile = inFile;
	}
	
	public ByteArrayResource getAttachmentSource() {
		return new ByteArrayResource(this.inFile);
	}
	
	
}
