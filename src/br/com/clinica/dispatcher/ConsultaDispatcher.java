package br.com.clinica.dispatcher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.clinica.dao.ConsultaDao;
import br.com.clinica.dao.MedicoDao;
import br.com.clinica.dao.PacienteDao;
import br.com.clinica.data.ConversorData;
import br.com.clinica.modelo.Consulta;
import br.com.clinica.modelo.Medico;
import br.com.clinica.modelo.Paciente;

public class ConsultaDispatcher implements Dispatcher {
	private Connection connection;
	private ConsultaDao consultaDao;
	
	@Override
	public String dispatch(HttpServletRequest request, HttpServletResponse response) {
		this.connection = (Connection) request.getAttribute("connection");
		this.consultaDao = new ConsultaDao(this.connection);
		
		String requestMethod = request.getMethod();
		String requestMethodAlt = request.getParameter("_method");
		String idConsulta = request.getParameter("idConsulta");
		
		if(requestMethodAlt != null) {
			
			if(requestMethodAlt.equals("PUT"))
				return this.put(request, response);
			else
				return this.delete(request, response);				
			
		} else if(requestMethod.equals("GET")) {
			
			if(idConsulta != null)
				return this.get(request, response, Long.parseLong(idConsulta));
			else
				return this.get(request, response);
			
		} else if(requestMethod.equals("POST"))
			return this.post(request, response);
		
		return "WEB-INF/jsp/index.jsp";
	}

	@Override
	public String get(HttpServletRequest request, HttpServletResponse response) {		
		request.setAttribute("consultas", this.consultaDao.lista());
		request.setAttribute("pacientes", new PacienteDao(this.connection).lista());
		request.setAttribute("medicos", new MedicoDao(this.connection).lista());
		return "WEB-INF/jsp/consulta-list.jsp";
	}

	@Override
	public String get(HttpServletRequest request, HttpServletResponse response, long id) {
		Consulta consulta = this.consultaDao.pesquisa(id);
		
		request.setAttribute("medicos", new MedicoDao(this.connection).lista());
		request.setAttribute("pacientes", new PacienteDao(this.connection).lista());
		request.setAttribute("consulta", consulta);
		
		return "WEB-INF/jsp/consulta-details.jsp";
	}

	@Override
	public String post(HttpServletRequest request, HttpServletResponse response) {
		Consulta consulta = new Consulta();
		
		// Como ConsultaDao somente considera o ID do paciente para inserir uma
		// Consulta no banco de dados, não precisamos do objeto Paciente inteiro
		// apenas de seu ID. O mesmo é válido para o Medico.
		// Apesar disso, ainda precisamos dos objetos inteiro para o tratamento de exceção
		//Paciente paciente = new PacienteDao(this.connection).pesquisa( Long.parseLong(request.getParameter("idPaciente")) );
		Paciente paciente = new Paciente();
		paciente.setIdPaciente( Long.parseLong(request.getParameter("idPaciente")) );
		consulta.setPaciente(paciente);
		
		//Medico medico = new MedicoDao(this.connection).pesquisa( Long.parseLong(request.getParameter("idMedico")) );
		Medico medico = new Medico();
		medico.setIdMedico( Long.parseLong(request.getParameter("idMedico")) );
		consulta.setMedico(medico);
		
		String stringDate = request.getParameter("ano") + "/" +
				request.getParameter("mes") + "/" +
				request.getParameter("dia");		
		Calendar calendarDate = ConversorData.stringToCalendar(stringDate, "yyyy/MM/dd");
		consulta.setData(calendarDate);
		
		String stringHorario = request.getParameter("horario");
		java.sql.Time timeHorario = ConversorData.stringToTime(stringHorario , "HH:mm:ss");
		consulta.setHorario(timeHorario);		
		
		try {
			this.consultaDao.adiciona(consulta);
		} catch (MySQLIntegrityConstraintViolationException exception) {
			// Se ocorrer uma violação de uma constraint,
			// uma mensagem de exceção sera anexada a requisição
			// AVISO: note que o código abaixo esta fortemente acoplado
			// ao nome da unique key definida no banco de dados
			String mensagemViolacao = "Violação de chave unica";
			if(exception.getMessage().contains("un_tbmedico_consulta"))
				mensagemViolacao = "O medico selecionado já está cadastrado em outra consulta para esta data e horario";			
			if(exception.getMessage().contains("un_tbpaciente_consulta"))
				mensagemViolacao = "O paciente selecionado já está cadastrado em outra consulta para esta data e horario";
				
			request.setAttribute("exception", mensagemViolacao);
			return this.get(request, response);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
		
		return "ConsultaDispatcher";
	}

	@Override
	public String put(HttpServletRequest request, HttpServletResponse response) {
		Consulta consulta = new Consulta();
		
		long idConsulta = Long.parseLong(request.getParameter("idConsulta"));
		consulta.setIdConsulta(idConsulta);		
		
		PacienteDao pacienteDao = new PacienteDao(this.connection);
		Paciente paciente = pacienteDao.pesquisa( Long.parseLong(request.getParameter("idPaciente")) );
		consulta.setPaciente(paciente);
		
		MedicoDao medicoDao = new MedicoDao(this.connection);
		Medico medico = medicoDao.pesquisa( Long.parseLong( request.getParameter("idMedico") ) );
		consulta.setMedico(medico);
		
		String stringDate = request.getParameter("ano") + "/" +
				request.getParameter("mes") + "/" +
				request.getParameter("dia");		
		Calendar calendarDate = ConversorData.stringToCalendar(stringDate, "yyyy/MM/dd");
		consulta.setData(calendarDate);
		
		// Para atribuirmos o horario da consulta (vindo da view como uma String)
		// precisamos converte-lo para Calendar e depois para java.sql.Time
		String stringHorario = request.getParameter("horario");
		java.sql.Time timeHorario = ConversorData.stringToTime(stringHorario , "HH:mm:ss");
		consulta.setHorario(timeHorario);
		
		// Uma alteração no recurso Consulta pode resultar em uma exceção
		try {
			this.consultaDao.altera(consulta);
		} catch (MySQLIntegrityConstraintViolationException exception) {
			String mensagemViolacao = "Violação de chave unica";
			if(exception.getMessage().contains("un_tbmedico_consulta"))
				mensagemViolacao = "O medico selecionado já está cadastrado em outra consulta para esta data e horario";			
			if(exception.getMessage().contains("un_tbpaciente_consulta"))
				mensagemViolacao = "O paciente selecionado já está cadastrado em outra consulta para esta data e horario";
				
			request.setAttribute("exception", mensagemViolacao);
			return this.get(request, response, idConsulta);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		request.setAttribute("consulta", consulta);
		request.setAttribute("mensagem", "Consulta alterada com sucesso");
		request.setAttribute("medicos", medicoDao.lista());
		request.setAttribute("pacientes", pacienteDao.lista());
		return "WEB-INF/jsp/consulta-details.jsp";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		Consulta consulta = new Consulta();
		consulta.setIdConsulta( Long.parseLong(request.getParameter("idConsulta")) );
		this.consultaDao.remove(consulta);
		return this.get(request, response);
	}

}
