<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Clinica | Index</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	
	<!-- <a href="?logica=MedicoLogica">Medicos</a> <br />-->
	<a href="MedicoDispatcher">Medicos</a> <br />
	<a href="PacienteDispatcher">Pacientes</a> <br />
	<a href="ConvenioDispatcher">Convenios</a> <br />
	<a href="ConsultaDispatcher">Consultas</a> <br />
	
	<c:import url="rodape.jsp" />
</body>
</html>