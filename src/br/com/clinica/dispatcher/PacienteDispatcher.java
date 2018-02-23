package br.com.clinica.dispatcher;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.clinica.dao.PacienteDao;
import br.com.clinica.modelo.Paciente;

public class PacienteDispatcher implements Dispatcher {
	private Connection connection;
	private PacienteDao dao;
	
	@Override
	public String dispatch(HttpServletRequest request, HttpServletResponse response) {
		this.connection = (Connection) request.getAttribute("connection");
		this.dao = new PacienteDao(this.connection);
		
		String requestMethod = request.getMethod();
		String idPaciente = request.getParameter("idPaciente");
		
		if(requestMethod.equals("GET")) {
			if(idPaciente != null)
				return this.get(request, response, Long.parseLong(idPaciente));
			else
				return this.get(request, response);
		}
		
		return "WEB-INF/jsp/index.jsp";
	}

	@Override
	public String get(HttpServletRequest request, HttpServletResponse response) {
		List<Paciente> pacientes = this.dao.lista();
		request.setAttribute("pacientes", pacientes);
		return "WEB-INF/jsp/paciente-list.jsp";
	}

	@Override
	public String get(HttpServletRequest request, HttpServletResponse response, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String post(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String put(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
