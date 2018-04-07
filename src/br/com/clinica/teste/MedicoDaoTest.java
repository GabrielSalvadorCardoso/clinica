package br.com.clinica.teste;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.MedicoDao;
import br.com.clinica.modelo.Medico;
import org.junit.runners.MethodSorters;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MedicoDaoTest {
	private static Connection connection = new ConnectionFactory().getConnection();
	private static MedicoDao dao = new MedicoDao(connection);
	private static Medico medicoTest;
	
	public MedicoDaoTest() {
		if(medicoTest == null)
			initMedico();
	}
	
	public static void initMedico() {
		medicoTest = new Medico();
		medicoTest.setCrm("999999");
		medicoTest.setNome("Medico Teste 1");
		medicoTest.setEspecialidade("Especialidade Teste 1");
	}

	@Test
	public void TestAAdicionaEListaMedico() throws MySQLIntegrityConstraintViolationException, SQLException {		
		dao.adiciona(medicoTest);
		List<Medico> listaMedicos = dao.lista();		
		
		Medico medicoSelecionado = null;
		
		// Percorre a lista para verificar se algum dos medicos da lista
		// possui o CRM igual ao médico recém registrado no banco
		for (Medico med : listaMedicos) {
			if(med.getCrm().equals(medicoTest.getCrm())) {
				medicoSelecionado = med;
			}
		}
		
		// verifica se o medico retornado pelo banco é o mesmo que foi inserido
		assertEquals(medicoSelecionado.getCrm(), medicoTest.getCrm());
		assertEquals(medicoSelecionado.getNome(), medicoTest.getNome());
		assertEquals(medicoSelecionado.getCrm(), medicoTest.getCrm());
		assertEquals(medicoSelecionado.getEspecialidade(), medicoTest.getEspecialidade());
		
		// medicoTest e medicoSelecionado (retornado do banco) terão o mesmo ID,
		// afinal referem-se ao medico 
		medicoTest.setIdMedico(medicoSelecionado.getIdMedico());		
		
		// O medico recém cadastrado no banco é imediatamente removido após o teste
		// (afinal, a existencia deste registro no banco deve-se apenas para fins de teste)
		// this.dao.remove(medicoSelecionado);
	}
	
	@Test
	public void TestBPesquisaMedico() {
		// Se o teste anterior passar, existirá uma instancia de medico no banco
		// esta instancia deve coincidir com medicoTest (mesmo ID, crm, nome e especialidade)
		Medico medicoDb = dao.pesquisa(medicoTest.getIdMedico());
		
		assertEquals(medicoDb.getIdMedico(), medicoTest.getIdMedico());
		assertEquals(medicoDb.getCrm(), medicoTest.getCrm());
		assertEquals(medicoDb.getNome(), medicoTest.getNome());
		assertEquals(medicoDb.getEspecialidade(), medicoTest.getEspecialidade());
	}
	
	@Test
	public void TestCAlteraEPesquisaMedico() throws MySQLIntegrityConstraintViolationException, SQLException {
		// recupera medicoTest no banco para continuar o teste
		Medico medicoDb = dao.pesquisa(medicoTest.getIdMedico());
		
		// alterando a especialidade do medico (de "Especialidade Teste 1" para "Especialidade Teste 1 (Alterado)")
		medicoDb.setEspecialidade("Especialidade Teste 1 (Alterado)");
		dao.altera(medicoDb);
		
		Medico medicoAlterado = dao.pesquisa(medicoDb.getIdMedico());				
		assertEquals(medicoAlterado.getCrm(), medicoTest.getCrm());
		assertEquals(medicoAlterado.getNome(), medicoTest.getNome());
		// medicoTest deve diferenciar de medicoAlterado apenas na especialidade (atributo recém alterado)
		assertNotEquals(medicoAlterado.getEspecialidade(), medicoTest.getEspecialidade());
	}
	
	@Test
	public void TestDRemoveMedico() throws MySQLIntegrityConstraintViolationException, SQLException {
		Medico medicoDb = dao.pesquisa(medicoTest.getIdMedico());
		dao.remove(medicoDb);
		
		List<Medico> medicos = dao.lista();
		
		boolean medicoDeletado = true;
		for(Medico med : medicos) {
			if(med.getIdMedico() == medicoTest.getIdMedico()) {
				// se houver alguma instancia com o mesmo ID de medicoTest nesta lista
				// significa que não foi deletado
				medicoDeletado = false;
			}				
		}
		
		assertTrue(medicoDeletado);
	}

}
