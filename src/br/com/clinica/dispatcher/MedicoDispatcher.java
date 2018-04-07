package br.com.clinica.dispatcher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.clinica.dao.MedicoDao;
import br.com.clinica.modelo.Medico;

public class MedicoDispatcher implements Dispatcher {
	private Connection connection;
	private MedicoDao dao;

	@Override
	public String dispatch(HttpServletRequest request, HttpServletResponse response) {
		this.connection = (Connection) request.getAttribute("connection");
		this.dao = new MedicoDao(this.connection);
		
		String requestMethod = request.getMethod();
		String requestMethodAlt = request.getParameter("_method");
		String idMedico = request.getParameter("idMedico");

		// String requestUri = request.getRequestURI();
		// List<String> uriPieces = splitURI(requestUri);
		// uriPieces[0] = clinica
		// uriPieces[1] = MedicoDispatcher
		// uriPieces[2] = idMedico (pode ter ou não)

		if(requestMethodAlt != null) {
			
			if(requestMethodAlt.equals("PUT"))
				return this.put(request, response);
			else // requestMethodAlt -> DELETE
				return this.delete(request, response);
			
		} else if(requestMethod.equals("GET")) {
			
			if(idMedico != null)
				return this.get(request, response, Long.parseLong(idMedico));
			else
				return this.get(request, response);
			
		} else if(requestMethod.equals("POST")) {
			return this.post(request, response);
		}
		
		// substituir por uma página de erro
		return "WEB-INF/jsp/index.jsp";
		
	}	

	@Override
	public String get(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("medicos", this.dao.lista());		
		return "WEB-INF/jsp/medico-list.jsp";
	}
	
	@Override
	public String get(HttpServletRequest request, HttpServletResponse response, long id) {		
		request.setAttribute("medico", this.dao.pesquisa(id));
		return "WEB-INF/jsp/medico-details.jsp";
	}

	@Override
	public String post(HttpServletRequest request, HttpServletResponse response) {		
		Medico medico = new Medico();
		medico.setCrm(request.getParameter("crm"));
		medico.setNome(request.getParameter("nome"));
		medico.setEspecialidade(request.getParameter("especialidade"));
		
		try {
			this.dao.adiciona(medico);
		} catch (MySQLIntegrityConstraintViolationException exception) {
			String mensagemViolacao = "Violação de chave unica";
			if(exception.getMessage().contains("un_crm_medico"))
				mensagemViolacao = "Já existe um medico cadastrado com este CRM";
			
			request.setAttribute("exception", mensagemViolacao);
			return this.get(request, response);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
		
		return "MedicoDispatcher";
	}
	
	@Override
	public String put(HttpServletRequest request, HttpServletResponse response) {		
		Medico medico = new Medico();
		medico.setIdMedico(Long.parseLong(request.getParameter("idMedico")));
		medico.setCrm(request.getParameter("crm"));
		medico.setNome(request.getParameter("nome"));
		medico.setEspecialidade(request.getParameter("especialidade"));
		
		try {
			this.dao.altera(medico);
		} catch (MySQLIntegrityConstraintViolationException exception) {
			String mensagemViolacao = "Violação de chave unica";
			if(exception.getMessage().contains("un_crm_medico"))
				mensagemViolacao = "Já existe um medico cadastrado com este CRM";
			
			request.setAttribute("exception", mensagemViolacao);
			return this.get(request, response, medico.getIdMedico());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		request.setAttribute("medico", medico);
		request.setAttribute("mensagem", "Medico alterado com sucesso!");
		
		return "WEB-INF/jsp/medico-details.jsp";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response) {		
		Medico medico = new Medico();
		medico.setIdMedico(Long.parseLong(request.getParameter("idMedico")));
		medico.setCrm(request.getParameter("crm"));
		medico.setNome(request.getParameter("nome"));
		medico.setEspecialidade(request.getParameter("especialidade"));
		
		try {
			this.dao.remove(medico);
		} catch (MySQLIntegrityConstraintViolationException exception) {
			String mensagemViolacao = "Violação de chave estrangeira";
			if(exception.getMessage().contains("fk_consulta_medico"))
				mensagemViolacao = "O medico esta registrado em uma consulta e não pode ser removido";			
				
			request.setAttribute("exception", mensagemViolacao);
			return this.get(request, response, medico.getIdMedico());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return this.get(request, response);
	}
}
