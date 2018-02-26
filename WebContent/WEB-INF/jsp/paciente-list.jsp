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
			<th></th>
		</tr>
		
		<c:forEach var="paciente" items="${pacientes}">
			<tr>
				<td>${paciente.idPaciente}</td>
				<td>${paciente.cpf}</td>
				<td>${paciente.nome}</td>
				<td>${paciente.sexo}</td>			
				<td>
					<fmt:formatDate value="${paciente.dataNascimento.time}" pattern="dd/MM/yyyy" />
				</td>				
				<td>${paciente.convenio.nome}</td>
				<td><a href="PacienteDispatcher?idPaciente=${paciente.idPaciente}">Detalhes</a></td>
			<tr>
		</c:forEach>
	
	</table>
	
	<hr />	
	<h2>Adicionar novo Paciente</h2>
	
	<form action="PacienteDispatcher" method="POST">
		<label for="cpf">CPF</label>
		<input type="text" name="cpf" id="cpf" required /> <br />
		
		<label for="nome">Nome</label>
		<input type="text" name="nome" id="nome" required /> <br />
		
		Sexo		
		<input type="radio" name="sexo" id="sexoMasculino" value="M" checked/>
		<label for="sexoMasculino">Masculino</label>		
		<input type="radio" name="sexo" id="sexoFeminino" value="F" />
		<label for="sexoFeminino">Feminino</label> <br />
		
		<!-- data de nascimento -->
		<label for="dia">Data de Nascimento</label>
		<input type="number" name="dia" id="dia" placeholder="dd" min="1" max="31" required/> /		
		<input type="number" name="mes" id="mes" placeholder="mm" min="1" max="12" required /> /		
		<input type="number" name="ano" id="ano" placeholder="aaaa" min="1900" max="2018" required /> <br />
		
		<!-- convenio -->
		<select name="idConvenio">
			<option disabled selected>--- selecione um convenio ---</option>
			<c:forEach var="convenio" items="${convenios}">
				<option value="${convenio.idConvenio}">${convenio.nome}</option>
			</c:forEach>
		</select>
		<br />
		
		<input type="submit" name="Enviar" />
	</form>
	
	<c:import url="rodape.jsp" />
</body>
</html>