package br.com.clinica.dao;

import java.sql.Connection;
import java.util.List;

import br.com.clinica.modelo.Consulta;

public class ConsultaDao implements Dao<Consulta> {
	private Connection connection;
	
	public ConsultaDao(Connection connection) {
		this.connection =  connection;
	}
	
	@Override
	public void adiciona(Consulta object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void altera(Consulta object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Consulta object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Consulta> lista() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Consulta pesquisa(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
