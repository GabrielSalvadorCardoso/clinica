<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Clinica | Lista de Consultas</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	<h2>Lista de Consultas</h2>
	
	<table>
		<tr>
			<th>ID</th>
			<th>Paciente</th>
			<th>Medico</th>
			<th>Data</th>
			<th>Horario</th>
			<th></th>
		</tr>
		
		<c:forEach var="consulta" items="${consultas}">
			<tr>
				<td>${consulta.idConsulta}</td>
				<td>${consulta.paciente.nome} (${consulta.paciente.cpf})</td>
				<td>${consulta.medico.nome} (${consulta.medico.especialidade})</td>
				<td>
					<fmt:formatDate value="${consulta.data.time}" pattern="dd/MM/yyyy" />
				</td>
				<td>${consulta.horario}</td>
				<td><a href="ConsultaDispatcher?idConsulta=${consulta.idConsulta}">Detalhes</a></td>
			</tr>
		</c:forEach>
	</table>
	
	<hr />
	<h2>Adicionar Consulta</h2>
	
	<form action="ConsultaDispatcher" method="POST">		
		<label for="paciente">Paciente</label>
		<select name="idPaciente" id="paciente" required>
			<option disabled selected>--- selecione um paciente ---</option>
			<c:forEach var="paciente" items="${pacientes}">
				<option value="${paciente.idPaciente}">
					${paciente.nome} (${paciente.cpf})
				</option>
			</c:forEach>
		</select>
		<br />
		
		<label for="medico">Medico</label>
		<select name="idMedico" id="medico" required>
			<option disabled selected>--- selecione um medico ---</option>
			<c:forEach var="medico" items="${medicos}">
				<option value="${medico.idMedico}">
					${medico.nome} (${medico.especialidade})
				</option>
			</c:forEach>
		</select>
		<br />
		
		<label for="dia">Data</label>
		<input type="number" name="dia" id="dia" min="1" max="31" placeholder="dd" required />/
		<input type="number" name="mes" id="mes" min="1" max="12" placeholder="mm" required />/
		<input type="number" name="ano" id="ano" min="2018" max="2100" placeholder="aaaa" required />
		<br />
		
		<label for="horario">Horario</label>
		<select name="horario" id="horario" required>
			<option disabled selected>--- selecione um horario ---</option>
			<c:forEach var="hora" begin="7" end="18">
				<option value="${hora}:00:00">${hora}:00:00</option>
				<option value="${hora}:30:00">${hora}:30:00</option>
			</c:forEach>
		</select>
		<br />
		
		<input type="submit" value="Enviar" />
		
		<c:if test="${not empty mensagem}">
			<p>${mensagem}</p>
		</c:if>
	</form>
	
	<c:import url="rodape.jsp" />
</body>
</html>