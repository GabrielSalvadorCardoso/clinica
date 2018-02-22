package br.com.clinica.teste;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.ConvenioDao;
import br.com.clinica.dao.PacienteDao;
import br.com.clinica.data.ConversorData;
import br.com.clinica.modelo.Convenio;
import br.com.clinica.modelo.Paciente;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PacienteDaoTest {
	private static Connection connection = new ConnectionFactory().getConnection();
	private static PacienteDao dao = new PacienteDao(connection);
	private static Paciente pacienteTest1;
	private static Paciente pacienteTest2;
	private static ConvenioDao convenioDao = new ConvenioDao(connection);
	private static Convenio convenioTest;
	
	public PacienteDaoTest() {
		if(pacienteTest1 == null) {
			initPaciente();			
		}
		if(pacienteTest2 == null) {
			initConvenio();
			initPacienteComConvenio();
		}
	}
	
	public static void initPaciente() {
		pacienteTest1 = new Paciente();
		pacienteTest1.setCpf("11223344550");
		pacienteTest1.setNome("Paciente Teste 1");
		pacienteTest1.setSexo("M");
		pacienteTest1.setDataNascimento(Calendar.getInstance());
	}
	
	public static void initPacienteComConvenio() {
		pacienteTest2 = new Paciente();
		pacienteTest2.setCpf("99119911990");
		pacienteTest2.setNome("Paciente Teste 2");
		pacienteTest2.setSexo("F");
		pacienteTest2.setDataNascimento(Calendar.getInstance());
		pacienteTest2.setConvenio(convenioTest);
	}
	
	public static void initConvenio() { 
		convenioTest = new Convenio();
		convenioTest.setCodigo("114422");
		convenioTest.setNome("Convenio de Teste 1");
		convenioTest.setConcedente("Concedente de Teste 1");
	}

	@Test
	public void TestAAdicionaEListaPaciente() {
		dao.adiciona(pacienteTest1);
		
		List<Paciente> pacientes = dao.lista();
		Paciente pacienteSelecionado = null;
		for(Paciente pac : pacientes) 
			if(pac.getCpf().equals(pacienteTest1.getCpf())) 
				pacienteSelecionado = pac;
		
		assertEquals(pacienteSelecionado.getCpf(), pacienteTest1.getCpf());
		assertEquals(pacienteSelecionado.getNome(), pacienteTest1.getNome());
		assertEquals(pacienteSelecionado.getSexo(), pacienteTest1.getSexo());

		String pacienteSelecNasc = ConversorData.calendarToString(pacienteSelecionado.getDataNascimento(), "yyyy/MM/dd");
		String pacienteTestNasc = ConversorData.calendarToString(pacienteTest1.getDataNascimento(), "yyyy/MM/dd");		
		assertEquals(pacienteSelecNasc, pacienteTestNasc);

		pacienteTest1.setIdPaciente(pacienteSelecionado.getIdPaciente());
	}
	
	@Test
	public void TestBPesquisaPaciente() {
		Paciente pacienteDb = dao.pesquisa(pacienteTest1.getIdPaciente());
		assertEquals(pacienteDb.getIdPaciente(), pacienteTest1.getIdPaciente());
		assertEquals(pacienteDb.getCpf(), pacienteTest1.getCpf());
		assertEquals(pacienteDb.getNome(), pacienteTest1.getNome());
		assertEquals(pacienteDb.getSexo(), pacienteTest1.getSexo());
		
		String pacienteDbNasc = ConversorData.calendarToString(pacienteDb.getDataNascimento(), "yyyy/MM/dd");
		String pacienteTestNasc = ConversorData.calendarToString(pacienteTest1.getDataNascimento(), "yyyy/MM/dd");
		assertEquals(pacienteDbNasc, pacienteTestNasc);
	}
	
	@Test
	public void TestCAlteraEPesquisaPaciente() {
		Paciente pacienteDb = dao.pesquisa(pacienteTest1.getIdPaciente());
		pacienteDb.setNome("Paciente Teste 1 (alterado)");
		
		dao.altera(pacienteDb);
		
		pacienteDb = dao.pesquisa(pacienteTest1.getIdPaciente());
		assertEquals(pacienteDb.getIdPaciente(), pacienteTest1.getIdPaciente());
		assertEquals(pacienteDb.getCpf(), pacienteTest1.getCpf());
		assertEquals(pacienteDb.getSexo(), pacienteTest1.getSexo());
		
		String pacienteDbNasc = ConversorData.calendarToString(pacienteDb.getDataNascimento(), "yyyy/MM/dd");
		String pacienteTestNasc = ConversorData.calendarToString(pacienteTest1.getDataNascimento(), "yyyy/MM/dd");
		
		assertEquals(pacienteDbNasc, pacienteTestNasc);
		assertNotEquals(pacienteDb.getNome(), pacienteTest1.getNome());
	}
	
	@Test
	public void TestDRemovePaciente() {
		dao.remove(pacienteTest1);
		
		List<Paciente> pacientes = dao.lista();
		boolean pacienteDeletado = true;
		for(Paciente pac : pacientes) {
			if(pac.getCpf().equals(pacienteTest1.getCpf())) {
				pacienteDeletado = false;
			}
		}
		assertTrue(pacienteDeletado);
	}
	
	// teste de paciente com convenio (pacienteTest2)
	// antes de adicionar o paciente com convenio precisamos adicionar o convenio
	@Test
	public void TestEAdicionaEListaConvenio() { 
		convenioDao.adiciona(convenioTest);
		
		Convenio convenioSelecionado = null;
		for(Convenio convenio : convenioDao.lista())
			if(convenio.getCodigo().equals(convenioTest.getCodigo()))
				convenioSelecionado = convenio;
		
		assertEquals(convenioSelecionado.getCodigo(), convenioTest.getCodigo());
		assertEquals(convenioSelecionado.getNome(), convenioTest.getNome());
		assertEquals(convenioSelecionado.getConcedente(), convenioTest.getConcedente());
		
		convenioTest.setIdConvenio(convenioSelecionado.getIdConvenio());
	}
	
	@Test
	public void TestFAdicionaEListaPacienteComConvenio() {		
		dao.adiciona(pacienteTest2);
		
		Paciente pacienteSelecionado = null;
		for(Paciente pac : dao.lista())
			if(pac.getCpf().equals(pacienteTest2.getCpf()))
				pacienteSelecionado = pac;
		
		assertEquals(pacienteSelecionado.getCpf(), pacienteTest2.getCpf());
		assertEquals(pacienteSelecionado.getNome(), pacienteTest2.getNome());
		assertEquals(pacienteSelecionado.getSexo(), pacienteTest2.getSexo());
		
		String pacienteSelecNasc = ConversorData.calendarToString(pacienteSelecionado.getDataNascimento(), "yyyy/MM/dd");
		String pacienteTestNasc = ConversorData.calendarToString(pacienteTest2.getDataNascimento(), "yyyy/MM/dd");
		assertEquals(pacienteSelecNasc, pacienteTestNasc);
				
		assertEquals(pacienteSelecionado.getConvenio().getIdConvenio(), pacienteTest2.getConvenio().getIdConvenio());
		
		pacienteTest2.setIdPaciente(pacienteSelecionado.getIdPaciente());
	}
	
	@Test
	public void TestGRemovePacienteComConvenio() {
		dao.remove(pacienteTest2);
		
		boolean pacienteDeletado = true;
		for(Paciente pac : dao.lista())
			if(pac.getIdPaciente() == pacienteTest1.getIdPaciente())
				pacienteDeletado = false;
		
		assertTrue(pacienteDeletado);
	}
	
	@Test
	public void TestHRemoveConvenio() {
		convenioDao.remove(convenioTest);
		
		boolean convenioRemovido = true;
		for(Convenio convenio : convenioDao.lista())
			if(convenio.getIdConvenio() == convenioTest.getIdConvenio())
				convenioRemovido = false;
		
		assertTrue(convenioRemovido);
	}
}
