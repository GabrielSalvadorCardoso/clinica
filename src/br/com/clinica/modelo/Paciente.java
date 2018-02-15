package br.com.clinica.modelo;

import java.util.Calendar;

public class Paciente {
	private long idPaciente;
	private String cpf;
	private String nome;
	private String sexo;
	private Calendar dataNascimento;
	private Convenio convenio;
	
	public long getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(long idPaciente) {
		this.idPaciente = idPaciente;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Calendar getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Convenio getConvenio() {
		return convenio;
	}
	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}
	
	@Override
	public String toString() {
		return  "\n<Paciente object>" +
				"\nID: " + this.getIdPaciente() +
				"\nCPF: " + this.getCpf() +
				"\nNome: " + this.getNome() +
				"\nSexo: " + this.getSexo() +
				"\nData de Nascimento: " + this.getDataNascimento() +
				"\nConvenio: " + this.getConvenio().toString();
	}
}
