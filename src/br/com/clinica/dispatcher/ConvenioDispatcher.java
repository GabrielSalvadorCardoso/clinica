package br.com.clinica.dispatcher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.clinica.dao.ConvenioDao;
import br.com.clinica.modelo.Convenio;

public class ConvenioDispatcher implements Dispatcher{
	private Connection connection;
	private ConvenioDao dao;
	
	@Override
	public String dispatch(HttpServletRequest request, HttpServletResponse response) {
		this.connection = (Connection) request.getAttribute("connection");
		this.dao = new ConvenioDao(this.connection);
		
		String requestUri = request.getRequestURI();
		String requestMethod = request.getMethod();
		String idConvenio = request.getParameter("idConvenio");
		String requestMethodAlt = request.getParameter("_method");
		
		if(requestMethodAlt != null) {
			if(requestMethodAlt.equals("PUT"))
				return this.put(request, response);
			else
				return this.delete(request, response);
		} else if(requestMethod.equals("GET")) {
			if(idConvenio != null)
				return this.get(request, response, Long.parseLong(idConvenio));
			else
				return this.get(request, response);
		} else if(requestMethod.equals("POST")) {
			return this.post(request, response);
		}
		
		return "WEB-INF/jsp/index.jsp";
	}

	@Override
	public String get(HttpServletRequest request, HttpServletResponse response) {
		List<Convenio> convenios = this.dao.lista();
		request.setAttribute("convenios", convenios);
		return "WEB-INF/jsp/convenio-list.jsp";
	}

	@Override
	public String get(HttpServletRequest request, HttpServletResponse response, long id) {
		Convenio convenio = this.dao.pesquisa(id);
		request.setAttribute("convenio", convenio);
		return "WEB-INF/jsp/convenio-details.jsp";
	}

	@Override
	public String post(HttpServletRequest request, HttpServletResponse response) {
		Convenio convenio = new Convenio();
		convenio.setCodigo(request.getParameter("codigo"));
		convenio.setNome(request.getParameter("nome"));
		convenio.setConcedente(request.getParameter("concedente"));

		try {
			this.dao.adiciona(convenio);
		} catch (MySQLIntegrityConstraintViolationException exception) {
			String mensagemViolacao = "";
			if(exception.getMessage().contains("un_codigo_convenio"))
				mensagemViolacao = "Já existe um convênio cadastrado com este codigo";
				
			request.setAttribute("exception", mensagemViolacao);
			return this.get(request, response);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return "ConvenioDispatcher";
	}

	@Override
	public String put(HttpServletRequest request, HttpServletResponse response) {
		Convenio convenio = new Convenio();
		convenio.setIdConvenio(Long.parseLong(request.getParameter("idConvenio")));
		convenio.setCodigo(request.getParameter("codigo"));
		convenio.setNome(request.getParameter("nome"));
		convenio.setConcedente(request.getParameter("concedente"));
		
		try {
			this.dao.altera(convenio);
		} catch (MySQLIntegrityConstraintViolationException exception) {
			String mensagemViolacao = "";
			if(exception.getMessage().contains("un_codigo_convenio"))
				mensagemViolacao = "Já existe um convênio cadastrado com este codigo";
				
			request.setAttribute("exception", mensagemViolacao);
			return this.get(request, response, convenio.getIdConvenio());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		request.setAttribute("convenio", convenio);
		request.setAttribute("mensagem", "Convênio alterado com sucesso!");
		
		return "WEB-INF/jsp/convenio-details.jsp";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		Convenio convenio = new Convenio();
		long idConvenio = Long.parseLong(request.getParameter("idConvenio"));
		convenio.setIdConvenio(idConvenio);
		convenio.setCodigo(request.getParameter("codigo"));
		convenio.setNome(request.getParameter("nome"));
		convenio.setConcedente(request.getParameter("concedente"));
		
		try {
			this.dao.remove(convenio);
		} catch(MySQLIntegrityConstraintViolationException exception) {
			String mensagemViolacao = "Violação de chave estrangeira";
			if(exception.getMessage().contains("fk_paciente_convenio"))
				mensagemViolacao = "Convenio esta associado a algum(ns) paciente(s), por isso não pode ser removido";
			
			request.setAttribute("exception",mensagemViolacao);
			return this.get(request, response, idConvenio);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		// redireciona para a lista de convenios
		return this.get(request, response);
	}

}
