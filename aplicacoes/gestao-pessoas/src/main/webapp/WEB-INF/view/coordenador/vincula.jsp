<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<meta charset="UTF-8"/>
	<jsp:include page="../modulos/header-estrutura.jsp" />
	<title>Adicionar Menbros</title>
</head>
<body>
		<jsp:include page="../modulos/header-coordenador.jsp" />
		<c:if test="${not empty info}">
			<div class="alert alert-success alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<c:out value="${info}"></c:out>
			</div>
		</c:if>

	<div class="container">
		<input id="current-ano" type="hidden"/>
		<input id="current-semestre" type="hidden"/>
		<input id="current-turma" type="hidden"/>

		<div class="form-group">
			<div>
				<select id="ano" name="ano" class="selectpicker">
					<option value="2014">2014</option>
					<option value="2015">2015</option>
					<option value="2016">2016</option>
					<option value="2017">2017</option>
					<option value="2018">2018</option>
					<option value="2019">2019</option>
					<option value="2020">2020</option>
				</select>
	
				<select id="semestre" name="semestre" class="selectpicker">
					<option value="1">1</option>
					<option value="2">2</option>
				</select>
	
				<select id="turma" name="turma">
				</select>
				
			</div>
		</div>
		<c:if test="${not empty estagiarios}">
		<h1>p ${estagiarios[0].nomeCompleto}</h1>
		<div class="panel panel-default">
			<div class="panel-heading" align="center">
				<h3>Estagiarios</h3>
			</div>
	
			<table id="tableEstagiarios" class="table">
				<thead>
					<tr>
						<th data-column-id="nomeCompleto" data-order="asc">Nome Completo</th>
						<th data-column-id="matricula">Matricula</th>
						<th data-column-id="acoes" data-formatter="acoes" data-sortable="false" data-searchable="false">Opções</th>
					</tr>
				</thead>
			</table>
		</div>
		</c:if>

	</div>
	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>