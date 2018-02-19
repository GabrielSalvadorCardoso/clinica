package br.com.clinica.teste;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Time;
import java.util.Calendar;

import org.junit.Test;

import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.ConsultaDao;
import br.com.clinica.dao.MedicoDao;
import br.com.clinica.dao.PacienteDao;
import br.com.clinica.modelo.Consulta;
import br.com.clinica.modelo.Medico;
import br.com.clinica.modelo.Paciente;

public class ConsultaDaoTest {	
	private static Connection connection = new ConnectionFactory().getConnection();
	private static ConsultaDao consultadao = new ConsultaDao(connection);
	private static PacienteDao pacienteDao = new PacienteDao(connection);
	private static MedicoDao medicoDao = new MedicoDao(connection);
	private static Consulta consultaTest1;
	private static Consulta consultaTest2; // data e horario iguais a 'consultaTest1' com mesmo medico
	private static Consulta consultaTest3; // data e horario iguais a 'consultaTest1' com mesmo paciente
	private static Paciente pacienteTest1;
	private static Paciente pacienteTest2;
	private static Medico medicoTest1;
	private static Medico medicoTest2;
	
	public ConsultaDaoTest() {
		if(pacienteTest1 == null)
			initPacienteTest1();
		if(pacienteTest2 == null)
			initPacienteTest2();
		if(medicoTest1 == null)
			initMedicoTest1();
		if(medicoTest2 == null)
			initMedicoTest2();
	}
	
	public static void initPacienteTest1() {
		pacienteTest1 = new Paciente();
		pacienteTest1.setCpf("");
		pacienteTest1.setNome("");
		pacienteTest1.setSexo("");
		pacienteTest1.setDataNascimento(Calendar.getInstance());
	}
	
	public static void initPacienteTest2() {
		pacienteTest2 = new Paciente();
		pacienteTest2.setCpf("");
		pacienteTest2.setNome("");
		pacienteTest2.setSexo("");
		pacienteTest2.setDataNascimento(Calendar.getInstance());
	}
	
	public static void initMedicoTest1() {		
		medicoTest1 = new Medico();
		medicoTest1.setCrm("");
		medicoTest1.setNome("");
		medicoTest1.setEspecialidade("");
	}
	
	public static void initMedicoTest2() {		
		medicoTest2 = new Medico();
		medicoTest2.setCrm("");
		medicoTest2.setNome("");
		medicoTest2.setEspecialidade("");
	}
	
	public static void initConsultaTest1() {
		consultaTest1 = new Consulta();
		consultaTest1.setPaciente(pacienteTest1);
		consultaTest1.setMedico(medicoTest1);
		consultaTest1.setData(Calendar.getInstance());
		consultaTest1.setHorario(Calendar.getInstance().getTimeInMillis());
	}
	
	public static void initConsultaTest2() {
		// data e horario iguais a 'consultaTest1' com mesmo medico
		consultaTest2 = new Consulta();
		consultaTest2.setPaciente(pacienteTest2);
		consultaTest2.setMedico(medicoTest1);
		consultaTest2.setData(consultaTest1.getData());
		consultaTest2.setHorario(consultaTest1.getHorario());
	}
	
	public static void initConsultaTest3() {
		// data e horario iguais a 'consultaTest1' com mesmo paciente
		consultaTest3 = new Consulta();
		consultaTest3.setPaciente(pacienteTest1);
		consultaTest3.setMedico(medicoTest2);
		consultaTest3.setData(consultaTest1.getData());
		consultaTest3.setHorario(consultaTest1.getHorario());
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
