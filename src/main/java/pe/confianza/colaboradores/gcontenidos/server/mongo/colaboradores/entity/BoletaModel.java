package pe.confianza.colaboradores.gcontenidos.server.mongo.colaboradores.entity;

import java.io.Serializable;

public class BoletaModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String marca;
    private String ruc;
    private String periodo;
    private int codEmp;
    private String nombreEmp;
    private String nroDoc;
    private String fecNac;
    private String nacionalidad;
    private String sexo;
    private String cargo;
    private String categoria;
    private int horas;
    private String fecIng;
    private String fecCese;
    private String nroAFP;
    private String nroSeguro;
    private String inicioVac;
    private String finVac;    
    private String perVac;
    private int fila;
    private String tipoConcIN;
    private String descIN;
    private double cantIN;
    private double montoIN;
    private String tipoConcDE;
    private String descDE;
    private double cantDE;
    private double montoDE;
    private String tipoConcPA;
    private String descPA;
    private double cantPA;
    private double montoPA;
    private String nroCtaAbono;
    
    public BoletaModel() {
    	super();
    }
    
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public int getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(int codEmp) {
		this.codEmp = codEmp;
	}
	public String getNombreEmp() {
		return nombreEmp;
	}
	public void setNombreEmp(String nombreEmp) {
		this.nombreEmp = nombreEmp;
	}
	public String getNroDoc() {
		return nroDoc;
	}
	public void setNroDoc(String nroDoc) {
		this.nroDoc = nroDoc;
	}
	public String getFecNac() {
		return fecNac;
	}
	public void setFecNac(String fecNac) {
		this.fecNac = fecNac;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getHoras() {
		return horas;
	}
	public void setHoras(int horas) {
		this.horas = horas;
	}
	public String getFecIng() {
		return fecIng;
	}
	public void setFecIng(String fecIng) {
		this.fecIng = fecIng;
	}
	public String getFecCese() {
		return fecCese;
	}
	public void setFecCese(String fecCese) {
		this.fecCese = fecCese;
	}
	public String getNroAFP() {
		return nroAFP;
	}
	public void setNroAFP(String nroAFP) {
		this.nroAFP = nroAFP;
	}
	public String getNroSeguro() {
		return nroSeguro;
	}
	public void setNroSeguro(String nroSeguro) {
		this.nroSeguro = nroSeguro;
	}
	public String getInicioVac() {
		return inicioVac;
	}
	public void setInicioVac(String inicioVac) {
		this.inicioVac = inicioVac;
	}
	public String getFinVac() {
		return finVac;
	}
	public void setFinVac(String finVac) {
		this.finVac = finVac;
	}
	public String getPerVac() {
		return perVac;
	}
	public void setPerVac(String perVac) {
		this.perVac = perVac;
	}
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public String getTipoConcIN() {
		return tipoConcIN;
	}
	public void setTipoConcIN(String tipoConcIN) {
		this.tipoConcIN = tipoConcIN;
	}
	public String getDescIN() {
		return descIN;
	}
	public void setDescIN(String descIN) {
		this.descIN = descIN;
	}
	public double getCantIN() {
		return cantIN;
	}
	public void setCantIN(double cantIN) {
		this.cantIN = cantIN;
	}
	public double getMontoIN() {
		return montoIN;
	}
	public void setMontoIN(double montoIN) {
		this.montoIN = montoIN;
	}
	public String getTipoConcDE() {
		return tipoConcDE;
	}
	public void setTipoConcDE(String tipoConcDE) {
		this.tipoConcDE = tipoConcDE;
	}
	public String getDescDE() {
		return descDE;
	}
	public void setDescDE(String descDE) {
		this.descDE = descDE;
	}
	public double getCantDE() {
		return cantDE;
	}
	public void setCantDE(double cantDE) {
		this.cantDE = cantDE;
	}
	public double getMontoDE() {
		return montoDE;
	}
	public void setMontoDE(double montoDE) {
		this.montoDE = montoDE;
	}
	public String getTipoConcPA() {
		return tipoConcPA;
	}
	public void setTipoConcPA(String tipoConcPA) {
		this.tipoConcPA = tipoConcPA;
	}
	public String getDescPA() {
		return descPA;
	}
	public void setDescPA(String descPA) {
		this.descPA = descPA;
	}
	public double getCantPA() {
		return cantPA;
	}
	public void setCantPA(double cantPA) {
		this.cantPA = cantPA;
	}
	public double getMontoPA() {
		return montoPA;
	}
	public void setMontoPA(double montoPA) {
		this.montoPA = montoPA;
	}
	public String getNroCtaAbono() {
		return nroCtaAbono;
	}
	public void setNroCtaAbono(String nroCtaAbono) {
		this.nroCtaAbono = nroCtaAbono;
	}

}
