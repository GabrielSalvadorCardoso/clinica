package br.com.clinica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import br.com.clinica.modelo.Paciente;

public class PacienteDao implements Dao<Paciente> {
	
	private Connection connection;
	
	public PacienteDao(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void adiciona(Paciente paciente) {
		String sql = "";
		
		if(paciente.getConvenio() != null)
			sql = "insert into paciente (cpf, nome, sexo, dataNascimento, id_convenio) values (?, ?, ?, ?, ?)";
		else
			sql = "insert into paciente (cpf, nome, sexo, dataNascimento) values (?, ?, ?, ?)";
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, paciente.getCpf());
			stmt.setString(2, paciente.getNome());
			stmt.setString(3, paciente.getSexo());
			stmt.setDate(4, new Date(paciente.getDataNascimento().getTimeInMillis()));
			
			if(paciente.getConvenio() != null)
				stmt.setLong(5, paciente.getConvenio().getIdConvenio());
			
			stmt.execute();
			stmt.close();			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void altera(Paciente paciente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Paciente paciente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Paciente> lista() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paciente pesquisa(long id) {
		/*String sql = "select * from paciente where id_paciente = ?";
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
		} catch (SQLException e) {
			
		}*/
		return null;
		
	}

}
