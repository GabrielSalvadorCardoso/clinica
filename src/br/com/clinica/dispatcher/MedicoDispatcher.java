package br.com.clinica.dispatcher;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.clinica.dao.MedicoDao;
import br.com.clinica.modelo.Medico;

public class MedicoDispatcher implements Dispatcher {
	private Connection connection;
	private MedicoDao dao;

	@Override
	public String dispatch(HttpServletRequest request, HttpServletResponse response) {
		this.connection = (Connection) request.getAttribute("connection");
		this.dao = new MedicoDao(this.connection);
		
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
		
		this.dao.adiciona(medico);		
		
		return "MedicoDispatcher";
	}
	
	@Override
	public String put(HttpServletRequest request, HttpServletResponse response) {		
		Medico medico = new Medico();
		medico.setIdMedico(Long.parseLong(request.getParameter("idMedico")));
		medico.setCrm(request.getParameter("crm"));
		medico.setNome(request.getParameter("nome"));
		medico.setEspecialidade(request.getParameter("especialidade"));
		
		this.dao.altera(medico);
		
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
		
		this.dao.remove(medico);
		
		return this.get(request, response);
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
