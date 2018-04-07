package br.com.clinica.dispatcher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.clinica.dao.ConvenioDao;
import br.com.clinica.dao.PacienteDao;
import br.com.clinica.data.ConversorData;
import br.com.clinica.modelo.Convenio;
import br.com.clinica.modelo.Paciente;

public class PacienteDispatcher implements Dispatcher {
	private Connection connection;
	private PacienteDao dao;
	
	@Override
	public String dispatch(HttpServletRequest request, HttpServletResponse response) {
		this.connection = (Connection) request.getAttribute("connection");
		this.dao = new PacienteDao(this.connection);
				
		String requestMethod = request.getMethod();
		String requestMethodAlt = request.getParameter("_method");
		String idPaciente = request.getParameter("idPaciente");
		
		if(requestMethodAlt != null) {
			
			if(requestMethodAlt.equals("PUT"))
				return this.put(request, response);
			else
				return this.delete(request, response);
			
		} else if(requestMethod.equals("GET")) {
			
			if(idPaciente != null)
				return this.get(request, response, Long.parseLong(idPaciente));
			else
				return this.get(request, response);
			
		} else if(requestMethod.equals("POST")) {
			return this.post(request, response);
		}
		
		return "WEB-INF/jsp/index.jsp";
	}

	@Override
	public String get(HttpServletRequest request, HttpServletResponse response) {
		List<Paciente> pacientes = this.dao.lista();
		List<Convenio> convenios = new ConvenioDao(this.connection).lista();
		
		request.setAttribute("pacientes", pacientes);
		request.setAttribute("convenios", convenios);
		return "WEB-INF/jsp/paciente-list.jsp";
	}

	@Override
	public String get(HttpServletRequest request, HttpServletResponse response, long id) {
		Paciente paciente = this.dao.pesquisa(id);
		List<Convenio> convenios = new ConvenioDao(this.connection).lista();
		
		request.setAttribute("paciente", paciente);
		request.setAttribute("convenios", convenios);				
		
		return "WEB-INF/jsp/paciente-details.jsp";
	}

	@Override
	public String post(HttpServletRequest request, HttpServletResponse response) {
		Paciente paciente = new Paciente();
		paciente.setCpf(request.getParameter("cpf"));
		paciente.setNome(request.getParameter("nome"));
		paciente.setSexo(request.getParameter("sexo"));
		
		String stringDate = request.getParameter("ano") + "/" +
							request.getParameter("mes") + "/" +
							request.getParameter("dia");
		Calendar calendarDate = ConversorData.stringToCalendar(stringDate, "yyyy/MM/dd");
		paciente.setDataNascimento(calendarDate);
		
		Convenio convenio = null;
		
		if(request.getParameter("idConvenio") != null) {		
			long idConvenio = Long.parseLong(request.getParameter("idConvenio"));
			convenio = new ConvenioDao(this.connection).pesquisa(idConvenio);
		}	
		
		paciente.setConvenio(convenio);
		
		try {
			this.dao.adiciona(paciente);
		} catch (MySQLIntegrityConstraintViolationException exception) {
			String mensagemViolacao = "Violação de chave unica";
			if(exception.getMessage().contains("un_cpf_paciente"))
				mensagemViolacao = "Já existe um paciente cadastrado com este CPF";
			
			request.setAttribute("exception", mensagemViolacao);
			return this.get(request, response);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return "PacienteDispatcher";
	}

	@Override
	public String put(HttpServletRequest request, HttpServletResponse response) {
		Paciente paciente = new Paciente();
		paciente.setIdPaciente(Long.parseLong(request.getParameter("idPaciente")));
		paciente.setCpf(request.getParameter("cpf"));
		paciente.setNome(request.getParameter("nome"));
		paciente.setSexo(request.getParameter("sexo"));
		
		String stringDate = request.getParameter("ano") + "/" +
							request.getParameter("mes") + "/" +
							request.getParameter("dia");
		Calendar calendarDate = ConversorData.stringToCalendar(stringDate, "yyyy/MM/dd");
		paciente.setDataNascimento(calendarDate);
		
		ConvenioDao convenioDao = new ConvenioDao(this.connection);
		if(request.getParameter("idConvenio") != null) {
			Convenio convenio = convenioDao.pesquisa(Long.parseLong(request.getParameter("idConvenio")));
			paciente.setConvenio(convenio);
		}
		
		try {
			this.dao.altera(paciente);
		} catch (MySQLIntegrityConstraintViolationException exception) {
			String mensagemViolacao = "Violação de chave unica";
			if(exception.getMessage().contains("un_cpf_paciente"))
				mensagemViolacao = "Já existe um paciente cadastrado com este CPF";
			
			request.setAttribute("exception", mensagemViolacao);
			return this.get(request, response, paciente.getIdPaciente());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		request.setAttribute("paciente", paciente);
		// Toda view que contenha criação ou alteração de paciente deve conter uma lista de convenios a sua disposição
		request.setAttribute("convenios", convenioDao.lista());
		request.setAttribute("mensagem", "Paciente alterado com sucesso");
		
		return "WEB-INF/jsp/paciente-details.jsp";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		// O objeto paciente não esta sendo montado completamente neste caso
		// pois o método remove() de PacienteDao necessita apenas do ID do paciente 
		// para realizar a operação de remoção do registro no banco de dados
		Paciente paciente = new Paciente();
		long idPaciente = Long.parseLong(request.getParameter("idPaciente"));
		paciente.setIdPaciente(idPaciente);
		try {
			this.dao.remove(paciente);
		} catch (MySQLIntegrityConstraintViolationException exception){
			String mensagemViolacao = "";
			if(exception.getMessage().contains("fk_consulta_paciente"))
				mensagemViolacao = "O paciente esta relacionado a alguma(as) consulta(s), portanto não pode ser removido";
			
			request.setAttribute("exception", mensagemViolacao);			
			return this.get(request, response, idPaciente);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return this.get(request, response);
	}

}