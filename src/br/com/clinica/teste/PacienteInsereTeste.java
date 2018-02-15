package br.com.clinica.teste;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.PacienteDao;
import br.com.clinica.modelo.Paciente;

public class PacienteInsereTeste {

	public static void main(String[] args) {
		
		try {
			Connection connection = new ConnectionFactory().getConnection();
			
			// Paciente sem convenio
			Paciente paciente1 = new Paciente();
			paciente1.setCpf("14725836900");
			paciente1.setNome("Paciente teste 1");
			paciente1.setSexo("F");
			paciente1.setDataNascimento(Calendar.getInstance());
			
			PacienteDao dao = new PacienteDao(connection);
			dao.adiciona(paciente1);
			
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
