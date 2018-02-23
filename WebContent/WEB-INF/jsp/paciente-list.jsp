<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Clinica | Lista de Pacientes</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	<h2>Lista de Pacientes</h2>
	
	<table>
	
		<tr>
			<th>ID</th>
			<th>CPF</th>
			<th>Nome</th>
			<th>Sexo</th>
			<th>Data de Nascimento</th>
			<th>Convenio</th>
		</tr>
		
		<c:forEach var="paciente" items="${pacientes}">
			<tr>
				<td>${paciente.idPaciente}</td>
				<td>${paciente.cpf}</td>
				<td>${paciente.nome}</td>
				<td>${paciente.sexo}</td>			
				<td>
					<fmt:formatDate value="${paciente.dataNascimento.time}" pattern="yyyy/MM/dd" />
				</td>				
				<td>${paciente.convenio.nome}</td>
			<tr>
		</c:forEach>
	
	</table>
	
	<c:import url="rodape.jsp" />
</body>
</html>