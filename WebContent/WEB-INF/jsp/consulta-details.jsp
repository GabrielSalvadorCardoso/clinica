<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Clinica | Detalhes da Consulta</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	<h2>Detalhes da Consulta</h2>
	
	<form action="ConsultaDispatcher">
		<input type="hidden" name="idConsulta" value="${consulta.idConsulta}" required /> <br />
		
		<!-- seleção do médico -->
		<label for="medico">Medico</label>		
		<select name="idMedico" id="medico" required>
			<!-- o medico atualmente cadastrado na consulta será o padrão -->
			<option value="${consulta.medico.idMedico}" selected="selected">
				${consulta.medico.nome} (${consulta.medico.especialidade})
			</option>
			
			<!-- o restante dos medicos do banco serão as outras opções -->
			<c:forEach var="medico" items="${medicos}">
				<option value="${medico.idMedico}">
					${medico.nome} (${medico.especialidade})
				</option>
			</c:forEach>						
		</select>
		<br />		
		
		<!-- seleção do paciente -->
		<label for="paciente">Paciente</label>
		<select name="idPaciente" id="paciente" required>
			<option value="${consulta.paciente.idPaciente}">
				${consulta.paciente.nome} (${consulta.paciente.cpf})
			</option>
			
			<c:forEach var="paciente" items="${pacientes}">
				<option value="${paciente.idPaciente}">
					${paciente.nome} (${paciente.cpf})
				</option>
			</c:forEach>
		</select>
		<br />
		
		<label for="dia">Data</label>
		<input  type="number" name="dia" id="dia" min="1" max="31" placeholder="dd" required
				value="<fmt:formatDate value='${consulta.data.time}' pattern="dd" />"			
		/>/
		<input  type="number" name="mes" id="mes" min="1" max="12" placeholder="mm" required
				value="<fmt:formatDate value='${consulta.data.time}' pattern="MM" />"			
		/>/
		<input  type="number" name="ano" id="ano" min="1900" max="2100" placeholder="aaaa" required
				value="<fmt:formatDate value='${consulta.data.time}' pattern="yyyy" />"			
		/>
		<br />
		
		<label for="horario">Horario</label>
		<select name="horario" id="horario" required>
			<option value="${consulta.horario}">${consulta.horario}</option>
			
			<c:forEach var="hora" begin="7" end="18">
				<option value="${hora}:00:00">${hora}:00:00</option>
				<option value="${hora}:30:00">${hora}:30:00</option>
			</c:forEach>
		</select>
		<br />
		
		<input type="submit" name="_method" value="DELETE" />
		<input type="submit" name="_method" value="PUT" />
		
		<c:if test="${not empty mensagem}">
			<p>${mensagem}</p>
		</c:if>
	</form>
	
	<p> <a href="ConsultaDispatcher">Lista de Consultas</a> </p>
	<c:import url="rodape.jsp" />
</body>
</html>