package br.com.clinica.teste;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.ConvenioDao;
import br.com.clinica.modelo.Convenio;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConvenioDaoTest {
	private static Convenio convenioTest;
	private static Connection connection = new ConnectionFactory().getConnection();
	private static ConvenioDao dao = new ConvenioDao(connection);

	public ConvenioDaoTest() {
		if(convenioTest == null) {
			initConvenio();
		}
	}
	
	public static void initConvenio() { 
		convenioTest = new Convenio();
		convenioTest.setCodigo("114422");
		convenioTest.setNome("Convenio de Teste 1");
		convenioTest.setConcedente("Concedente de Teste 1");
	}
	
	@Test
	public void TestAAdicionaEListaConvenio() {
		dao.adiciona(convenioTest);
		
		List<Convenio> convenios = dao.lista();
		Convenio convenioSelecionado = null;
		for(Convenio conv : convenios) {
			if(conv.getCodigo().equals(convenioTest.getCodigo()))
				convenioSelecionado = conv;
		}
		
		assertEquals(convenioSelecionado.getCodigo(), convenioTest.getCodigo());
		assertEquals(convenioSelecionado.getNome(), convenioTest.getNome());
		assertEquals(convenioSelecionado.getConcedente(), convenioTest.getConcedente());
		
		convenioTest.setIdConvenio(convenioSelecionado.getIdConvenio());
	}
	
	@Test
	public void TestBPesquisaConvenio() {
		Convenio convenioDb = dao.pesquisa(convenioTest.getIdConvenio());
		assertEquals(convenioDb.getIdConvenio(), convenioTest.getIdConvenio());
		assertEquals(convenioDb.getCodigo(), convenioTest.getCodigo());
		assertEquals(convenioDb.getNome(), convenioTest.getNome());
		assertEquals(convenioDb.getConcedente(), convenioTest.getConcedente());
	}
	
	@Test
	public void TestCAlteraEPesquisaConvenio() {
		// recupera convenio do banco de dados
		Convenio convenioDb = dao.pesquisa(convenioTest.getIdConvenio());
		
		// altera convenio e devolve para o banco
		convenioDb.setNome("Convenio de Teste 1 (alterado)");
		dao.altera(convenioDb);
		
		// recupera novamento o convenio
		convenioDb = dao.pesquisa(convenioTest.getIdConvenio());
		
		// a única coisa que deve diferir de convenioTest é o nome (recém alterado)
		assertEquals(convenioDb.getCodigo(), convenioTest.getCodigo());
		assertEquals(convenioDb.getConcedente(), convenioTest.getConcedente());
		assertEquals(convenioDb.getIdConvenio(), convenioTest.getIdConvenio());
		assertNotEquals(convenioDb.getNome(), convenioTest.getNome());		
	}
	
	@Test
	public void TestDRemoveConvenio() {
		dao.remove(convenioTest);
		
		List<Convenio> convenios = dao.lista();
		boolean convenioDeletado = true;
		for (Convenio conv : convenios) {
			if(conv.getIdConvenio() == convenioTest.getIdConvenio())
				convenioDeletado = false;
		}
		assertTrue(convenioDeletado);		
	}
}