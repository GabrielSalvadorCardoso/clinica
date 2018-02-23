<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Clinica | Lista de Convênios</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	
	<h2> Lista de Convênios </h2>
	
	<table>
		<tr>	
			<th>ID</th>
			<th>Código</th>
			<th>Nome</th>
			<th>Concedente</th>
			<th></th>
		</tr>
		
		<c:forEach var="convenio" items="${convenios}">
			<tr>
				<td>${convenio.idConvenio}</td>
				<td>${convenio.codigo}</td>
				<td>${convenio.nome}</td>
				<td>${convenio.concedente}</td>
				<td> <a href="ConvenioDispatcher?idConvenio=${convenio.idConvenio}"> Detalhes </a></td>
			</tr>
		</c:forEach>
	
	</table>
	
	<hr />	
	<h2>Adicionar novo Convênio</h2>
	
	<form action="#" method="POST">
		<label for="codigo">Código</label>
		<input type="text" name="codigo" id="codigo" /> <br />
		
		<label for="nome">Nome</label>
		<input type="text" name="nome" id="nome" /> <br />
		
		<label for="concedente">Concedente</label>
		<input type="text" name="concedente" id="concedente" /> <br />
		
		<input type="submit" value="Enviar" />
	</form>
	
	<c:import url="rodape.jsp" />
</body>
</html>