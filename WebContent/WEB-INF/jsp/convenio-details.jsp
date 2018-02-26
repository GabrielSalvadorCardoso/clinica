<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Clinica | Convênio Detalhes</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	<h2>Detalhes do Convênio</h2>

	<form action="ConvenioDispatcher">
		<input type="hidden" name="idConvenio" value="${convenio.idConvenio}" required />
		
		<label for="codigo">Código</label>
		<input type="text" name="codigo" id="codigo" value="${convenio.codigo}" required /> <br />
		
		<label for="nome">Nome</label>
		<input type="text" name="nome" id="nome" value="${convenio.nome}" required /> <br />
		
		<label for="concedente">Concedente</label>
		<input type="text" name="concedente" id="concedente" value="${convenio.concedente}" required /> <br />
		
		<input type="submit" name="_method" value="DELETE" />
		<input type="submit" name="_method" value="PUT" />
		
		<c:if test="${not empty mensagem}">
			<p>${mensagem}</p>
		</c:if>
	</form>
	
	<p> <a href="ConvenioDispatcher">Lista de Convenios</a> </p>
	<c:import url="rodape.jsp" />
</body>
</html>