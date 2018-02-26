package br.com.clinica.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.clinica.dispatcher.Dispatcher;
import br.com.clinica.logica.Logica;

@WebServlet("/")
public class ControllerServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestUri = request.getRequestURI();
		String requestMethod = request.getMethod();
		String requestMethodAlt = request.getParameter("_method");
		
		if(requestMethodAlt != null)
			System.out.println("Request URI: " + requestUri + " " + requestMethodAlt);
		else
			System.out.println("Request URI: " + requestUri + " " + requestMethod);
		
		// por padrão, as requisições serão derecionadas para o index.jsp
		String stringDispatcher = "WEB-INF/jsp/index.jsp";
		
		List<String> uriPieces = new ArrayList<String>();
		for(String piece : request.getRequestURI().split("/")) {
			if(!piece.equals(""))
				uriPieces.add(piece);				
		} 
		
		// uriPieces[0] = 'clinica'
		// uriPieces[1] = 'nome_dispatcher'
		String logica = "";
		if(uriPieces.size() > 1)
			logica = uriPieces.get(1);
		//System.out.println("Logica >>> " + logica);
				
		try {
			Class dispatcherClass = Class.forName("br.com.clinica.dispatcher." + logica);
			Dispatcher dispatcherObject = (Dispatcher) dispatcherClass.newInstance();
			stringDispatcher = dispatcherObject.dispatch(request, response);
		} catch (ClassNotFoundException notFound) {
			// SE NÃO ENCONTRAR A CLASSE, SIMPLESMENTE EXIBA index.jsp
			//throw new RuntimeException(notFound);
			log("ClassNotFoundException log: " + notFound.getMessage());
		} catch (IllegalAccessException illegaAccess) {
			throw new RuntimeException(illegaAccess);
		} catch (InstantiationException instatiation) {
			throw new RuntimeException(instatiation);
		}
		
		if(request.getMethod().equals("POST")) {
			if(request.getAttribute("exception") == null) {
				// se não houver nenhuma exceção, a requisição POST
				// teve sucesso, e neste caso, devemos redirecinar a requisição
				// com sendRedirect()
				response.sendRedirect(stringDispatcher);
			} else {
				// Caso a requisição POST não tenha sucesso, devemos redirecionar
				// com o dispatcher juntamente com a mensagem da exceção
				request.setAttribute("mensagem", request.getAttribute("exception"));
				request.getRequestDispatcher(stringDispatcher).forward(request, response);
			}
		} else {			
			//System.out.println("GET " + stringDispatcher);			
			request.getRequestDispatcher(stringDispatcher).forward(request, response);
		}
	}
}
