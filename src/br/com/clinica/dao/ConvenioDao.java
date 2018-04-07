package br.com.clinica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.clinica.modelo.Convenio;

public class ConvenioDao implements Dao<Convenio> {
	private Connection connection;
	
	public ConvenioDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void adiciona(Convenio convenio) throws MySQLIntegrityConstraintViolationException, SQLException {
		String sql = "insert into convenio (codigo, nome, concedente) values (?, ?, ?)";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setString(1, convenio.getCodigo());
		stmt.setString(2, convenio.getNome());
		stmt.setString(3, convenio.getConcedente());
		stmt.execute();
		stmt.close();	
	}

	@Override
	public void altera(Convenio convenio) throws MySQLIntegrityConstraintViolationException, SQLException {
		String sql = "update convenio set codigo = ?, nome = ?, concedente = ? where id_convenio = ?";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setString(1, convenio.getCodigo());
		stmt.setString(2, convenio.getNome());
		stmt.setString(3, convenio.getConcedente());
		stmt.setLong(4, convenio.getIdConvenio());
		stmt.execute();
		stmt.close();
	}

	@Override
	public void remove(Convenio convenio) throws SQLException {
		String sql = "delete from convenio where id_convenio = ?";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setLong(1, convenio.getIdConvenio());
		stmt.execute();
		stmt.close();
	}

	@Override
	public List<Convenio> lista() {
		String sql = "select * from convenio";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			List<Convenio> convenios = new ArrayList<Convenio>();
			while(rs.next()) {
				Convenio convenio = new Convenio();
				convenio.setIdConvenio(rs.getLong("id_convenio"));
				convenio.setCodigo(rs.getString("codigo"));
				convenio.setNome(rs.getString("nome"));
				convenio.setConcedente(rs.getString("concedente"));
				convenios.add(convenio);
			}
						
			rs.close();
			stmt.close();
			return convenios;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}

	@Override
	public Convenio pesquisa(long id) {
		String sql = "select * from convenio where id_convenio = ?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			Convenio convenio = new Convenio();
			convenio.setIdConvenio(rs.getLong("id_convenio"));
			convenio.setCodigo(rs.getString("codigo"));
			convenio.setNome(rs.getString("nome"));
			convenio.setConcedente(rs.getString("concedente"));
			
			stmt.close();
			return convenio;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
