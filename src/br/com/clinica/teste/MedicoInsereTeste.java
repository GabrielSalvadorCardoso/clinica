package br.com.clinica.teste;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.MedicoDao;
import br.com.clinica.modelo.Medico;

public class MedicoInsereTeste {

	public static void main(String[] args) {
		
		try {
			Connection connection = new ConnectionFactory().getConnection();			
			MedicoDao dao = new MedicoDao(connection);
			
			Medico medico = new Medico();
			medico.setCrm("111222");
			medico.setNome("Fabio Costa");
			medico.setEspecialidade("Dermatologia");
			dao.adiciona(medico);
			
			connection.close();
			
			System.out.println("Medico cadastrado com sucesso");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
