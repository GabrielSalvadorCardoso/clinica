package br.com.clinica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.clinica.modelo.Medico;

public class MedicoDao implements Dao<Medico> {
	private Connection connection;
	
	public MedicoDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void adiciona(Medico medico) throws MySQLIntegrityConstraintViolationException, SQLException {
		String sql = "insert into medico (crm, nome, especialidade) values (?, ?, ?)";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setString(1, medico.getCrm());
		stmt.setString(2, medico.getNome());
		stmt.setString(3, medico.getEspecialidade());
		stmt.execute();
		stmt.close();
	}

	@Override
	public void altera(Medico medico) throws MySQLIntegrityConstraintViolationException, SQLException {
		String sql = "update medico set crm = ?, nome = ?, especialidade = ? where id_medico = ?";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setString(1, medico.getCrm());
		stmt.setString(2, medico.getNome());
		stmt.setString(3, medico.getEspecialidade());
		stmt.setLong(4, medico.getIdMedico());
		stmt.execute();
		stmt.close();		
	}
	
	@Override
	public void remove(Medico medico) throws MySQLIntegrityConstraintViolationException, SQLException {
		String sql = "delete from medico where id_medico = ?";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setLong(1, medico.getIdMedico());
		stmt.execute();
		stmt.close();
	}

	@Override
	public List<Medico> lista() {
		String sql = "select * from medico";
		try {
			List<Medico> medicos = new ArrayList<Medico>();
			
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Medico medico = new Medico();
				medico.setIdMedico(rs.getLong("id_medico"));
				medico.setCrm(rs.getString("crm"));
				medico.setNome(rs.getString("nome"));
				medico.setEspecialidade(rs.getString("especialidade"));
				
				medicos.add(medico);
			}
			
			rs.close();
			stmt.close();
			return medicos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Medico pesquisa(long id) {
		String sql = "select * from medico where id_medico = ?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setLong(1, id);
			
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			Medico medico = new Medico();
			medico.setIdMedico(rs.getLong("id_medico"));
			medico.setNome(rs.getString("nome"));
			medico.setCrm(rs.getString("crm"));
			medico.setEspecialidade(rs.getString("especialidade"));
			
			rs.close();
			stmt.close();
			return medico;			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
}
