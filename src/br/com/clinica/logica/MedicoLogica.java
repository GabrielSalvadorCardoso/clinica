package br.com.clinica.logica;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.clinica.connection.ConnectionFactory;
import br.com.clinica.dao.MedicoDao;
import br.com.clinica.modelo.Medico;

public class MedicoLogica {//implements Logica {

	/*@Override
	public String executa(HttpServletRequest request, HttpServletResponse response) {	
		
		if(request.getParameter("idMedico") != null) {
			if(request.getParameter("alterar") != null)
				return medicoAlterar(request, response);
			
			//System.out.println(request.getParameter("alterar"));
			//else if(request.getAttribute("deletar") != null)
				//return medicoDeletar(request, response);
			return medicoDetalhes(request, response);
		} else {
			
			if(request.getParameter("action") != null && request.getParameter("action").equals("create"))
				return medicoDetalhes(request, response);
			else if(request.getParameter("adicionar") != null)
				return medicoCriar(request, response);
			else
				return medicoList(request, response);
		}
	}	

	private String medicoCriar(HttpServletRequest request, HttpServletResponse response) {
		return "WEB-INF/jsp/medico-list.jsp";
	}

	public String medicoList(HttpServletRequest request, HttpServletResponse response) {		
		Connection connection = new ConnectionFactory().getConnection();
		MedicoDao dao = new MedicoDao(connection);
				
		request.setAttribute("medicos", dao.lista());
		
		return "WEB-INF/jsp/medico-list.jsp";
	}
	
	private String medicoDetalhes(HttpServletRequest request, HttpServletResponse response) {
		if(request.getParameter("idMedico") != null) {		
			long idMedico = Long.parseLong(request.getParameter("idMedico"));
			
			Connection connection = new ConnectionFactory().getConnection();
			MedicoDao dao = new MedicoDao(connection);
			
			Medico medico = dao.pesquisa(idMedico);
			request.setAttribute("medico", medico);
		}
		
		// requisições para 'MedicoLogica' sem o envio da id do médico
		// vão mostrar apenas o form vazio referente ao objeto médico
		return "WEB-INF/jsp/medico-details.jsp";
	}
	
	
	private String medicoAlterar(HttpServletRequest request, HttpServletResponse response) {
		Connection connection = new ConnectionFactory().getConnection();
		MedicoDao dao = new MedicoDao(connection);
		
		Medico medico = new Medico();
		medico.setIdMedico(Long.parseLong(request.getParameter("idMedico")));
		medico.setCrm(request.getParameter("crm"));
		medico.setNome(request.getParameter("nome"));
		medico.setEspecialidade(request.getParameter("especialidade"));
		dao.altera(medico);
		
		request.setAttribute("mensagem", "Medico alterado com sucesso >>> <a href='?logica=MedicoLogica'>lista de medicos</a>");		
		request.setAttribute("medico", medico);
		
		return "WEB-INF/jsp/medico-details.jsp";
	}*/

}

