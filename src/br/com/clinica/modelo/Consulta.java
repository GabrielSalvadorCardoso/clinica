package br.com.clinica.modelo;

import java.sql.Time;
import java.util.Calendar;

public class Consulta {
	private long idConsulta;
	private Paciente paciente;
	private Medico medico;
	private Calendar data;
	private Time horario;
	
	public long getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(long idConsulta) {
		this.idConsulta = idConsulta;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public Time getHorario() {
		return horario;
	}
	public void setHorario(long horario) {
		this.horario = new Time(horario);
	}
	public void setHorario(Time horario) {
		this.horario = horario;
	}
	
	@Override
	public String toString() {
		return  "\n<Consulta object>" +
				"\nID: " + this.getIdConsulta() +
				"\nPaciente: " + this.getPaciente().toString() +
				"\nMedico: " + this.getMedico().toString() +
				"\nData: " + this.getData() +
				"\nHorario: " + this.getHorario();
	}
}
