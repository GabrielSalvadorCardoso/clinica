<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Clinica | Detalhes do Paciente</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	<h2>Detalhes do Paciente</h2>
	
	<form action="PacienteDispatcher">
		<input type="hidden" name="idPaciente" value="${paciente.idPaciente}" required />
		
		<label for="cpf">CPF</label>
		<input type="text" name="cpf" id="cpf" value ="${paciente.cpf}" required /> <br />
		
		<label for="nome">Nome</label>
		<input type="text" name="nome" id="nome" value ="${paciente.nome}" required /> <br />
		
		<label for="sexo">Sexo</label>
		<input type="text" name="sexo" id="sexo" value ="${paciente.sexo}" required /> <br />
		
		<!-- seleção de data de nascimento -->
		<label for="dia">Data de Nascimento</label>		
		<input 	type="number" name="dia" id="dia" min="1" max="31" placeholder="dd" required
				value ="<fmt:formatDate value='${paciente.dataNascimento.time}' pattern='dd'/>"
		/>/
		<input 	type="number" name="mes" id="mes" min="1" max="12" placeholder="mm" required
				value ="<fmt:formatDate value='${paciente.dataNascimento.time}' pattern='MM'/>"
		/>/
		<input 	type="number" name="ano" id="ano" min="1900" max="2018" placeholder="aaaa" required
				value ="<fmt:formatDate value='${paciente.dataNascimento.time}' pattern='yyyy'/>"
		/>
		<br />
		
		<!-- seleção de convenio -->
		<label for="convenio">Convenio</label>
		<select name="idConvenio" id="convenio">
			<c:if test="${not empty paciente.convenio}">
				<option value="${paciente.convenio.idConvenio}" selected="selected">${paciente.convenio.nome}</option>
			</c:if>
			<c:if test="${empty paciente.convenio}">
				<option disabled selected>--- selecione um convenio ---</option>
			</c:if>
			
			<c:forEach var="convenio" items="${convenios}">
				<option value="${convenio.idConvenio}">${convenio.nome}</option>
			</c:forEach>
		</select>
		<br />
		
		<input type="submit" name="_method" value="DELETE" />
		<input type="submit" name="_method" value="PUT" />
		
		<c:if test="${not empty mensagem}">
			<p>${mensagem}</p>
		</c:if>
	</form>
	
	<p> <a href="PacienteDispatcher">Lista de Pacientes</a> </p>
	<c:import url="rodape.jsp" />
</body>
</html>