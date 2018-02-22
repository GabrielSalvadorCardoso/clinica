package br.com.clinica.dispatcher;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		if(requestMethod.equals("GET")) {
			if(idConvenio != null)
				return this.get(request, response, Long.parseLong(idConvenio));
			else
				return this.get(request, response);
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
