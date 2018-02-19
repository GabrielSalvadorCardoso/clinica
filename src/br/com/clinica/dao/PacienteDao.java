package br.com.clinica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import br.com.clinica.modelo.Convenio;
import br.com.clinica.modelo.Paciente;

public class PacienteDao implements Dao<Paciente> {
	
	private Connection connection;
	
	public PacienteDao(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void adiciona(Paciente paciente) {
		String sql = "insert into paciente (cpf, nome, sexo, dataNascimento, id_convenio) values (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, paciente.getCpf());
			stmt.setString(2, paciente.getNome());
			stmt.setString(3, paciente.getSexo());
			stmt.setDate(4, new Date(paciente.getDataNascimento().getTimeInMillis()));
			
			if(paciente.getConvenio() != null)
				if(paciente.getConvenio().getIdConvenio() != 0)
					stmt.setLong(5, paciente.getConvenio().getIdConvenio());
				else
					stmt.setString(5, null);
			else
				stmt.setString(5, null);
			
			stmt.execute();
			stmt.close();			
		} catch (SQLException e) {
			//throw new RuntimeException(e);
			e.printStackTrace();
		}
	}

	@Override
	public void altera(Paciente paciente) {
		String sql = "update paciente set cpf = ?, nome = ?, sexo = ?, dataNascimento = ?, id_convenio = ? where id_paciente = ?";
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, paciente.getCpf());
			stmt.setString(2, paciente.getNome());
			stmt.setString(3, paciente.getSexo());
			stmt.setDate(4, new Date(paciente.getDataNascimento().getTimeInMillis()));
			
			if(paciente.getConvenio() != null)
				stmt.setLong(5, paciente.getConvenio().getIdConvenio());
			else
				stmt.setString(5, null);
			
			stmt.setLong(6, paciente.getIdPaciente());
			
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void remove(Paciente paciente) {
		String sql = "delete from paciente where id_paciente = ?";
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setLong(1, paciente.getIdPaciente());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Paciente> lista() {
		String sql = "select * from paciente";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			List<Paciente> pacientes = new ArrayList<Paciente>();
					
			while(rs.next()) {
				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getLong("id_paciente"));
				paciente.setCpf(rs.getString("cpf"));
				paciente.setNome(rs.getString("nome"));
				paciente.setSexo(rs.getString("sexo"));
				
				Calendar calendarDate = Calendar.getInstance();
				calendarDate.setTime(rs.getDate("dataNascimento"));
				paciente.setDataNascimento(calendarDate);
								
				if(rs.getLong("id_convenio") != 0) {
					ConvenioDao convenioDao = new ConvenioDao(this.connection);
					Convenio convenio = convenioDao.pesquisa(rs.getLong("id_convenio"));
					paciente.setConvenio(convenio);
				}
				
				pacientes.add(paciente);
			}
			
			rs.close();
			stmt.close();			
			return pacientes;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
			//e.printStackTrace();
		}
	}

	@Override
	public Paciente pesquisa(long id) {
		String sql = "select * from paciente where id_paciente = ?";
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			Paciente paciente = new Paciente();
			paciente.setIdPaciente(rs.getLong("id_paciente"));
			paciente.setCpf(rs.getString("cpf"));
			paciente.setNome(rs.getString("nome"));
			paciente.setSexo(rs.getString("sexo"));
			
			Calendar calendarDate = Calendar.getInstance();
			calendarDate.setTime(rs.getDate("dataNascimento"));
			paciente.setDataNascimento(calendarDate);
							
			if(rs.getLong("id_convenio") != 0) {
				ConvenioDao convenioDao = new ConvenioDao(this.connection);
				Convenio convenio = convenioDao.pesquisa(rs.getLong("id_convenio"));
				paciente.setConvenio(convenio);
			}
			
			rs.close();
			stmt.close();			
			return paciente;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
