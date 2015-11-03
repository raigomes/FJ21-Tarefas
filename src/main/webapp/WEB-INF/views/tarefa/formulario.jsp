<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Nova Tarefa</title>
	</head>
	<body>
		<h3>Adicionar tarefas</h3>
		
<%-- 		<c:set var="erroMensagem"> --%>
<%-- 			<fmt:message key="tarefa.erro.descricao"/> --%>
<%-- 		</c:set> --%>
		
		<form:errors path="tarefa.descricao" cssStyle="color:red"/>		
		<form action="adicionaTarefa" method="post">
			Descrição: <br/> 
			<textarea name="descricao" rows="5" cols="100"></textarea><br/>
			<input type="submit" value="Adicionar"/>
		</form>
	</body>
</html>