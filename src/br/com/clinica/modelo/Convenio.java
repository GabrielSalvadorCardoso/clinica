package br.com.clinica.modelo;

public class Convenio {
	private long idConvenio;
	private String codigo;
	private String nome;
	private String concedente;
	
	public long getIdConvenio() {
		return idConvenio;
	}
	public void setIdConvenio(long idConvenio) {
		this.idConvenio = idConvenio;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getConcedente() {
		return concedente;
	}
	public void setConcedente(String concedente) {
		this.concedente = concedente;
	}
	
	@Override
	public String toString() {
		return  "\n<Convenio object>" + 
				"\nID: " + this.getIdConvenio() +
				"\nCodigo: " + this.getCodigo() +
				"\nNome: " + this.getNome() +
				"\nConcedente: " + this.getConcedente();
	}
}
