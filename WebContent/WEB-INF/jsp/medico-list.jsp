<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Clinica | Lista de Medicos</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	
	<h2>Lista de Médicos</h2>
	
	<table>
		<tr>
			<th>ID</th>
			<th>CRM</th>
			<th>Nome</th>
			<th>Especialidade</th>
			<th></th>
			<th></th>
		</tr>
		
		<c:forEach var="medico" items="${medicos}">	
			<tr>	
				<td> ${medico.idMedico}</td>				
				<td> ${medico.crm} </td>
				<td> ${medico.nome} </td>
				<td> ${medico.especialidade} </td>
				<td> <a href="MedicoDispatcher?idMedico=${medico.idMedico}">Detalhes</a> </td>
				<!-- <td> <a href="?logica=MedicoLogica&idMedico=${medico.idMedico}">Detalhes</a> </td> -->
			</tr>
		</c:forEach>
	</table>
	
	<hr />	
	<h2>Adicionar novo médico</h2>
	
	<form action="MedicoDispatcher" method="POST">
		<label for="crm">CRM: </label>
		<input type="text" name="crm" id="crm" required /> <br />
		
		<label for="nome">Nome: </label>
		<input type="text" name="nome" id="nome" required /> <br />
		
		<label for="especialidade">Especialidade: </label>
		<input type="text" name="especialidade" id="especialidade" required /> <br />
		
		<input type="submit" name="Adicionar" />
	</form>
	
	<c:import url="rodape.jsp" />
</body>
</html>