package br.com.clinica.teste;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.MedicoDao;
import br.com.clinica.modelo.Medico;

public class MedicoListaTeste {

	public static void main(String[] args) {
		
		try {
			Connection connection = new ConnectionFactory().getConnection();
			MedicoDao dao = new MedicoDao(connection);
			List<Medico> medicos = dao.lista();
			
			for (Medico medico : medicos) {
				System.out.println(medico);
			}
			
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
