package br.com.clinica.dispatcher;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.MedicoDao;
import br.com.clinica.modelo.Medico;

public class MedicoDispatcher implements Dispatcher {

	@Override
	public String dispatch(HttpServletRequest request, HttpServletResponse response) {
		String requestUri = request.getRequestURI();
		String requestMethod = request.getMethod();
		String requestMethodAlt = request.getParameter("_method");
		String idMedico = request.getParameter("idMedico");
				
		List<String> uriPieces = splitURI(requestUri);
		// uriPieces[0] = clinica
		// uriPieces[1] = MedicoDispatcher
		// uriPieces[2] = idMedico (pode ter ou não)

		if(requestMethodAlt != null) {
			
			if(requestMethodAlt.equals("PUT"))
				return put(request, response);
			else // requestMethodAlt -> DELETE
				return delete(request, response);
			
		} else if(requestMethod.equals("GET")) {
			
			if(idMedico != null)
				return get(request, response, Long.parseLong(idMedico));
			else
				return get(request, response);
			
		} else if(requestMethod.equals("POST")) {
			return post(request, response);
		}
		
		//return "";Long.parseLong(uriPieces.get(2))
		return get(request, response, 13);
		
	}	

	@Override
	public String get(HttpServletRequest request, HttpServletResponse response) {
		Connection connection = (Connection) request.getAttribute("connection");
		MedicoDao dao = new MedicoDao(connection);
		
		request.setAttribute("medicos", dao.lista());		
		return "WEB-INF/jsp/medico-list.jsp";
	}
	
	@Override
	public String get(HttpServletRequest request, HttpServletResponse response, long id) {
		Connection connection = (Connection) request.getAttribute("connection");
		MedicoDao dao = new MedicoDao(connection);
		
		request.setAttribute("medico", dao.pesquisa(id));
		return "WEB-INF/jsp/medico-details.jsp";
	}

	@Override
	public String post(HttpServletRequest request, HttpServletResponse response) {
		Connection connection = (Connection) request.getAttribute("connection");
		MedicoDao dao = new MedicoDao(connection);
		
		Medico medico = new Medico();
		medico.setCrm(request.getParameter("crm"));
		medico.setNome(request.getParameter("nome"));
		medico.setEspecialidade(request.getParameter("especialidade"));
		
		dao.adiciona(medico);		
		
		return "MedicoDispatcher";
	}
	
	@Override
	public String put(HttpServletRequest request, HttpServletResponse response) {
		Connection connection = (Connection) request.getAttribute("connection");
		MedicoDao dao = new MedicoDao(connection);
		
		Medico medico = new Medico();
		medico.setIdMedico(Long.parseLong(request.getParameter("idMedico")));
		medico.setCrm(request.getParameter("crm"));
		medico.setNome(request.getParameter("nome"));
		medico.setEspecialidade(request.getParameter("especialidade"));
		
		dao.altera(medico);
		
		request.setAttribute("medico", medico);
		request.setAttribute("mensagem", "Medico alterado com sucesso!");
		
		return "WEB-INF/jsp/medico-details.jsp";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		Connection connection = (Connection) request.getAttribute("connection");
		MedicoDao dao = new MedicoDao(connection);
		
		Medico medico = new Medico();
		medico.setIdMedico(Long.parseLong(request.getParameter("idMedico")));
		medico.setCrm(request.getParameter("crm"));
		medico.setNome(request.getParameter("nome"));
		medico.setEspecialidade(request.getParameter("especialidade"));
		
		dao.remove(medico);
		
		return get(request, response);
	}
	
	private List<String> splitURI(String requestUri) {
		List<String> uriPieces = new ArrayList<String>();
		for(String piece : requestUri.split("/")) {
			if(!piece.equals(""))
				uriPieces.add(piece);				
		}
		return uriPieces;
	}
}
