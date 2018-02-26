package br.com.clinica.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import br.com.clinica.modelo.Consulta;
import br.com.clinica.modelo.Medico;
import br.com.clinica.modelo.Paciente;

public class ConsultaDao implements Dao<Consulta> {
	private Connection connection;
	
	public ConsultaDao(Connection connection) {
		this.connection =  connection;
	}
	
	@Override
	public void adiciona(Consulta consulta) throws MySQLIntegrityConstraintViolationException, SQLException {
		String sql = "insert into consulta (id_paciente, id_medico, data, horario) values (?, ?, ?, ?)";
		PreparedStatement stmt = this.connection.prepareStatement(sql);
		stmt.setLong(1, consulta.getPaciente().getIdPaciente());
		stmt.setLong(2, consulta.getMedico().getIdMedico());
		stmt.setDate(3, new Date(consulta.getData().getTimeInMillis()));
		stmt.setTime(4, consulta.getHorario());
		stmt.execute();
		stmt.close();
	}

	@Override
	public void altera(Consulta consulta) {
		String sql = "update consulta set id_paciente = ?, id_medico = ?, data = ?,  horario = ? where id_consulta = ?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setLong(1, consulta.getPaciente().getIdPaciente());
			stmt.setLong(2, consulta.getMedico().getIdMedico());
			stmt.setDate(3, new Date(consulta.getData().getTimeInMillis()));
			stmt.setTime(4, consulta.getHorario());
			stmt.setLong(5, consulta.getIdConsulta());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove(Consulta consulta) {
		String sql = "delete from consulta where id_consulta = ?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setLong(1, consulta.getIdConsulta());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Consulta> lista() {
		String sql = "select * from consulta";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			List<Consulta> consultas = new ArrayList<Consulta>();
			while(rs.next()){
				Consulta consulta = new Consulta();
				consulta.setIdConsulta(rs.getLong("id_consulta"));
				
				Paciente paciente = new PacienteDao(this.connection).pesquisa(rs.getLong("id_paciente"));
				consulta.setPaciente(paciente);
				
				Medico medico = new MedicoDao(this.connection).pesquisa(rs.getLong("id_medico"));
				consulta.setMedico(medico);
				
				Calendar calendarDate = Calendar.getInstance();
				calendarDate.setTime(rs.getDate("data"));
				consulta.setData(calendarDate);
				
				consulta.setHorario(rs.getTime("horario"));
				
				consultas.add(consulta);
			}
			
			//System.out.println("Numero de Consultas: " + consultas.size());
			
			rs.close();
			stmt.close();
			return consultas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Consulta pesquisa(long id) {
		String sql = "select * from consulta where id_consulta = ?";
		
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setLong(1, id);
			
			ResultSet rs = stmt.executeQuery();
			rs.next();
			
			Consulta consulta = new Consulta();
			consulta.setIdConsulta(rs.getLong("id_consulta"));
			
			Paciente paciente = new PacienteDao(this.connection).pesquisa(rs.getLong("id_paciente"));
			consulta.setPaciente(paciente);
			
			Medico medico = new MedicoDao(this.connection).pesquisa(rs.getLong("id_medico"));
			consulta.setMedico(medico);
			
			Calendar calendarDate = Calendar.getInstance();
			calendarDate.setTime(rs.getDate("data"));
			consulta.setData(calendarDate);
			
			consulta.setHorario(rs.getTime("horario"));
			
			return consulta;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}

}
