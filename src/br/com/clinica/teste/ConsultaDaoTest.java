package br.com.clinica.teste;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.ConsultaDao;
import br.com.clinica.dao.MedicoDao;
import br.com.clinica.dao.PacienteDao;
import br.com.clinica.data.ConversorData;
import br.com.clinica.modelo.Consulta;
import br.com.clinica.modelo.Medico;
import br.com.clinica.modelo.Paciente;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConsultaDaoTest {	
	private static Connection connection = new ConnectionFactory().getConnection();
	private static ConsultaDao consultaDao = new ConsultaDao(connection);
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
		if(consultaTest1 == null)
			initConsultaTest1();
		if(consultaTest2 == null)
			initConsultaTest2();
		if(consultaTest3 == null)
			initConsultaTest3();
	}
	
	public static void initPacienteTest1() {
		pacienteTest1 = new Paciente();
		pacienteTest1.setCpf("77884455221");
		pacienteTest1.setNome("Paciente de teste 1");
		pacienteTest1.setSexo("M");
		pacienteTest1.setDataNascimento(Calendar.getInstance());
	}
	
	public static void initPacienteTest2() {
		pacienteTest2 = new Paciente();
		pacienteTest2.setCpf("77884455220");
		pacienteTest2.setNome("Paciente de teste 2");
		pacienteTest2.setSexo("F");
		pacienteTest2.setDataNascimento(Calendar.getInstance());
	}
	
	public static void initMedicoTest1() {		
		medicoTest1 = new Medico();
		medicoTest1.setCrm("124141");
		medicoTest1.setNome("Medico de teste 1");
		medicoTest1.setEspecialidade("Especialidade de teste 1");
	}
	
	public static void initMedicoTest2() {		
		medicoTest2 = new Medico();
		medicoTest2.setCrm("124140");
		medicoTest2.setNome("Medico de teste 2");
		medicoTest2.setEspecialidade("Especialidade de teste 2");
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
	public void TestAAdicionaEListaPacienteTest1() throws MySQLIntegrityConstraintViolationException, SQLException {
		pacienteDao.adiciona(pacienteTest1);
		
		List<Paciente> pacientes = pacienteDao.lista();
		Paciente pacienteSelecionado = null;
		for(Paciente paciente : pacientes) {
			if(paciente.getCpf().equals(pacienteTest1.getCpf()))
				pacienteSelecionado = paciente;
		}
		
		assertEquals(pacienteSelecionado.getCpf(), pacienteTest1.getCpf());
		assertEquals(pacienteSelecionado.getNome(), pacienteTest1.getNome());
		assertEquals(pacienteSelecionado.getSexo(), pacienteTest1.getSexo());
		
		String pacienteSelecNasc = ConversorData.calendarToString(pacienteSelecionado.getDataNascimento(), "yyyy/MM/dd");
		String pacienteTestNasc = ConversorData.calendarToString(pacienteTest1.getDataNascimento(), "yyyy/MM/dd");
		assertEquals(pacienteSelecNasc, pacienteTestNasc);

		pacienteTest1.setIdPaciente(pacienteSelecionado.getIdPaciente());
	}
	
	@Test
	public void TestBAdicionaEListaPacienteTest2() throws MySQLIntegrityConstraintViolationException, SQLException {
		pacienteDao.adiciona(pacienteTest2);
		
		List<Paciente> pacientes = pacienteDao.lista();
		Paciente pacienteSelec = null;
		for(Paciente paciente : pacientes)
			if(paciente.getCpf().equals(pacienteTest2.getCpf()))
				pacienteSelec = paciente;
		
		assertEquals(pacienteSelec.getCpf(), pacienteTest2.getCpf());
		assertEquals(pacienteSelec.getNome(), pacienteTest2.getNome());
		assertEquals(pacienteSelec.getSexo(), pacienteTest2.getSexo());
		
		String pacienteSelecNasc = ConversorData.calendarToString(pacienteSelec.getDataNascimento(), "yyyy/MM/dd");
		String pacienteTestNasc = ConversorData.calendarToString(pacienteTest2.getDataNascimento(), "yyyy/MM/dd");
		assertEquals(pacienteSelecNasc, pacienteTestNasc);
		
		pacienteTest2.setIdPaciente(pacienteSelec.getIdPaciente());
		
	}
	
	@Test
	public void TestCAdicionaEListaMedicoTest1() throws MySQLIntegrityConstraintViolationException, SQLException {
		medicoDao.adiciona(medicoTest1);
		
		List<Medico> medicos = medicoDao.lista();
		Medico medicoSelec = null;
		for(Medico med : medicos)
			if(med.getCrm().equals(medicoTest1.getCrm()))
				medicoSelec = med;
			
		assertEquals(medicoSelec.getCrm(), medicoTest1.getCrm());
		assertEquals(medicoSelec.getNome(), medicoTest1.getNome());
		assertEquals(medicoSelec.getEspecialidade(), medicoTest1.getEspecialidade());
		
		medicoTest1.setIdMedico(medicoSelec.getIdMedico());
	}
	
	@Test
	public void TestDAdicionaEListaMedicoTest2() throws MySQLIntegrityConstraintViolationException, SQLException {
		medicoDao.adiciona(medicoTest2);
		
		List<Medico> medicos = medicoDao.lista();
		Medico medicoSelec = null;
		for(Medico med : medicos)
			if(med.getCrm().equals(medicoTest2.getCrm()))
				medicoSelec = med;
		
		assertEquals(medicoSelec.getCrm(), medicoTest2.getCrm());
		assertEquals(medicoSelec.getNome(), medicoTest2.getNome());
		assertEquals(medicoSelec.getEspecialidade(), medicoTest2.getEspecialidade());
		
		medicoTest2.setIdMedico(medicoSelec.getIdMedico());
	}
	
	@Test
	public void TestEAdicionaEListaConsultaTest1() throws MySQLIntegrityConstraintViolationException, SQLException {		
		consultaDao.adiciona(consultaTest1);
		
		List<Consulta> consultas = consultaDao.lista();
		Consulta consultaSelec = null;
		for(Consulta consulta : consultas) {
			long pacienteId = consulta.getPaciente().getIdPaciente();
			long medicoId = consulta.getMedico().getIdMedico();
			String consultaData = ConversorData.calendarToString(consulta.getData(), "yyyy/MM/dd");
			String consultaTestData = ConversorData.calendarToString(consultaTest1.getData(), "yyyy/MM/dd");
			String consultaHorario = ConversorData.timeToString(consulta.getHorario(), "hh:mm:ss");
			String consultaTestHorario = ConversorData.timeToString(consultaTest1.getHorario(), "hh:mm:ss");
			
			// esta condição já é suficiente para determinar se o registro foi inserido no banco ou não
			if( pacienteId == consultaTest1.getPaciente().getIdPaciente() &&
				medicoId == consultaTest1.getMedico().getIdMedico() &&
				consultaData.equals(consultaTestData) &&
				consultaHorario.equals(consultaTestHorario))
			{				
					consultaSelec = consulta;				
			}		
		}
		
		assertNotNull(consultaSelec);
		consultaTest1.setIdConsulta(consultaSelec.getIdConsulta());
	}
	
	@Test
	public void TestFPesquisaConsultaTest1() {
		Consulta consultaDb = consultaDao.pesquisa(consultaTest1.getIdConsulta());
		assertEquals(consultaDb.getIdConsulta(), consultaTest1.getIdConsulta());
		assertEquals(consultaDb.getPaciente().getIdPaciente(), consultaTest1.getPaciente().getIdPaciente());
		assertEquals(consultaDb.getMedico().getIdMedico(), consultaTest1.getMedico().getIdMedico());
		String consultaDbData = ConversorData.calendarToString(consultaDb.getData(), "yyyy/MM/dd");
		String consultaTestData = ConversorData.calendarToString(consultaTest1.getData(), "yyyy/MM/dd");
		assertEquals(consultaDbData, consultaTestData);
		String consultaDbHorario = ConversorData.timeToString(consultaDb.getHorario(), "hh:mm:ss");
		String consultaTestHorario = ConversorData.timeToString(consultaTest1.getHorario(), "hh:mm:ss");
		assertEquals(consultaDbHorario, consultaTestHorario);
	}
		
	@Test(expected=MySQLIntegrityConstraintViolationException.class)
	public void TestGAdicionaconsultaTest2ComSQLException() throws MySQLIntegrityConstraintViolationException, SQLException {
		consultaDao.adiciona(consultaTest2);
	}
	
	@Test(expected=MySQLIntegrityConstraintViolationException.class)
	public void TestHAdicionaconsultaTest3ComSQLException() throws MySQLIntegrityConstraintViolationException, SQLException {
		consultaDao.adiciona(consultaTest3);
	}
	
	@Test
	public void TestIAlteraEPesquisaConsultaTest1() {
		Consulta consultaDb = consultaDao.pesquisa(consultaTest1.getIdConsulta());
		
		//System.out.println(ConversorData.calendarToString(consultaDb.getData(), "yyyy/MM/dd"));
		//System.out.println(ConversorData.calendarToString(consultaTest1.getData(), "yyyy/MM/dd"));
		
		Calendar consultaDbData = consultaDb.getData();
		consultaDbData.add(Calendar.DATE, 90);
		consultaDb.setData(consultaDbData);
		
		//System.out.println(ConversorData.calendarToString(consultaDb.getData(), "yyyy/MM/dd"));
		//System.out.println(ConversorData.calendarToString(consultaTest1.getData(), "yyyy/MM/dd"));
		//System.out.println("----------------------");
		
		try {
			consultaDao.altera(consultaDb);
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<Consulta> consultas = consultaDao.lista();
		Consulta consultaSelec = null;
		for(Consulta consulta : consultas)
			if(consulta.getIdConsulta() == consultaTest1.getIdConsulta())
				consultaSelec = consulta;
		
		assertEquals(consultaSelec.getIdConsulta(), consultaTest1.getIdConsulta());
		assertEquals(consultaSelec.getPaciente().getIdPaciente(), consultaTest1.getPaciente().getIdPaciente());
		assertEquals(consultaSelec.getMedico().getIdMedico(), consultaTest1.getMedico().getIdMedico());
		
		String dataConsultaSelec = ConversorData.calendarToString(consultaSelec.getData(), "yyyy/MM/dd");
		String dataConsultaTest = ConversorData.calendarToString(consultaTest1.getData(), "yyyy/MM/dd");
		
		//System.out.println(dataConsultaSelec);
		//System.out.println(dataConsultaTest);
		
		assertNotEquals(dataConsultaSelec, dataConsultaTest);	
		
		String horarioConsultaSelec = ConversorData.timeToString(consultaSelec.getHorario(), "hh:mm:ss");
		String horarioConsultaTest = ConversorData.timeToString(consultaTest1.getHorario(), "hh:mm:ss");
		assertEquals(horarioConsultaSelec, horarioConsultaTest);
		
	}
	
	@Test
	public void TestVRemoveConsultaTest1() {
		consultaDao.remove(consultaTest1);
		
		List<Consulta> consultas = consultaDao.lista();
		boolean consultaRemovida = true;
		for(Consulta consulta : consultas)
			if(consulta.getIdConsulta() == consultaTest1.getIdConsulta())
				consultaRemovida = false;
		
		assertTrue(consultaRemovida);
	}

	// remoção de pacientes e medicos de teste
	@Test
	public void TestWRemovePacienteTest1() throws MySQLIntegrityConstraintViolationException, SQLException {
		pacienteDao.remove(pacienteTest1);
		
		List<Paciente> pacientes = pacienteDao.lista();
		boolean pacienteRemovido = true;
		for(Paciente pac : pacientes)
			if(pac.getIdPaciente() == pacienteTest1.getIdPaciente())
				pacienteRemovido = false;
		
		assertTrue(pacienteRemovido);
	}
	
	@Test
	public void TestXRemovePacienteTest2() throws MySQLIntegrityConstraintViolationException, SQLException {
		pacienteDao.remove(pacienteTest2);
		
		List<Paciente> pacientes = pacienteDao.lista();
		boolean pacienteRemovido = true;
		for(Paciente pac : pacientes)
			if(pac.getIdPaciente() == pacienteTest2.getIdPaciente())
				pacienteRemovido = false;
		
		assertTrue(pacienteRemovido);
	}
	
	@Test
	public void TestYRemoveMedicoTest1() {
		try {
			medicoDao.remove(medicoTest1);
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<Medico> medicos = medicoDao.lista();
		boolean medicoRemovido = true;
		for(Medico med : medicos)
			if(med.getIdMedico() == medicoTest1.getIdMedico())
				medicoRemovido = false;
		
		assertTrue(medicoRemovido);
	}
	
	@Test
	public void TestZRemoveMedicoTest2() {
		try {
			medicoDao.remove(medicoTest2);
		} catch (MySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<Medico> medicos = medicoDao.lista();
		boolean medicoRemovido = true;
		for(Medico med : medicos) 
			if(med.getIdMedico() == medicoTest2.getIdMedico())
				medicoRemovido = false;
		assertTrue(medicoRemovido);
	}
}

