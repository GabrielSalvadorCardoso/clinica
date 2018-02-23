<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Clinica | Medico Detalhes</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	<h2>Detalhes do Médico</h2>
	
	<form action="MedicoDispatcher">
		<input type="hidden" name="idMedico" id="idMedico" value="${medico.idMedico}" />
		
		<label for="crm">CRM: </label>
		<input type="text" name="crm" id="crm" value="${medico.crm}" /> <br />
		
		<label for="nome">Nome: </label>
		<input type="text" name="nome" id="nome" value="${medico.nome}" /> <br />
		
		<label for="especialidade">Especialidade: </label>
		<input type="text" name="especialidade" id="especialidade" value="${medico.especialidade}" /> <br />
		
		<input type="submit" name="_method" value="DELETE" />
		<input type="submit" name="_method" value="PUT" />
		
		<c:if test="${not empty mensagem}">
			<p>${mensagem}</p>
		</c:if>
	</form>
	
	<c:import url="rodape.jsp" />
</body>
</html>