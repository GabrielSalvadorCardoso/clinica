package br.com.clinica.teste;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.MedicoDao;
import br.com.clinica.modelo.Medico;

public class MedicoPesquisaTeste {

	public static void main(String[] args) {
		
		try {
			Connection connection = new ConnectionFactory().getConnection();
			MedicoDao dao = new MedicoDao(connection);
			
			Medico medico = dao.pesquisa(5);
			System.out.println(medico);
			
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
