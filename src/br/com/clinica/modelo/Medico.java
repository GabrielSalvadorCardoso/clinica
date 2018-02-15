package br.com.clinica.modelo;

public class Medico {
	private long idMedico;
	private String crm;
	private String nome;
	private String especialidade;
	
	public long getIdMedico() {
		return idMedico;
	}
	public void setIdMedico(long idMedico) {
		this.idMedico = idMedico;
	}
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	
	@Override
	public String toString() {
		return  "\n<Medico object>" +
				"\nID: " + this.getIdMedico() +
				"\nCRM: " + this.getCrm() +
				"\nNome: " + this.getNome() +
				"\nEspecialidade: " + this.getEspecialidade();
	}
}
