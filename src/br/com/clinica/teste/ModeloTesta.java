package br.com.clinica.teste;

import java.util.Calendar;

import br.com.clinica.modelo.*;

public class ModeloTesta {
	public static void main(String[] args) {
		Medico medico = new Medico();
		medico.setIdMedico(1);
		medico.setCrm("123456");
		medico.setNome("Alberto Gomez");
		medico.setEspecialidade("Cardiologista");
		
		Convenio convenio = new Convenio();
		convenio.setIdConvenio(1);
		convenio.setCodigo("123123");
		convenio.setConcedente("Unimed");
		convenio.setNome("Unimed Basic");
		
		Paciente paciente = new Paciente();
		paciente.setIdPaciente(1);
		paciente.setCpf("12345678900");
		paciente.setNome("Roberto Farias");
		paciente.setSexo("M");
		paciente.setDataNascimento(Calendar.getInstance());
		paciente.setConvenio(convenio);
		
		Consulta consulta = new Consulta();
		consulta.setIdConsulta(1);
		consulta.setPaciente(paciente);
		consulta.setMedico(medico);
		consulta.setData(Calendar.getInstance());
		consulta.setHorario(Calendar.getInstance().getTimeInMillis());
		
		System.out.println(consulta);
	}

}
