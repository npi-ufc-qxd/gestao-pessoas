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
	<title>Inicio</title>
</head>
<body>
	<jsp:include page="../modulos/header-coordenador.jsp" />
	<div class="container">
		<input id="current-data" type="hidden"/>
		<div>
			<select id="anoF" name="ano" class="selectpicker filtroFrequencia" data-width="auto">
				<option value="2015">2015</option>
				<option value="2016">2016</option>
				<option value="2017">2017</option>
			</select>
			<select id="semestreF" name="semestre" class="selectpicker filtroFrequencia" data-width="auto">
				<option value="1">1</option>
				<option value="2">2</option>
			</select>
			<select id="turmaF" name="turma" class="selectpicker" data-width="auto">
			</select>
		</div>
		<div id="viewFrequencias">
			<div class="form" align="center">
				<h1>Frequências</h1>
			</div>
			<div id="periodo-dia" class="periodo">
				<span id="before" class="glyphicon glyphicon-chevron-left"></span>
					<label id="periodo-dia"></label>
				<span id="after" class="glyphicon glyphicon-chevron-right"></span>
			</div>
	
			<table id="frequencias">
				<thead>
					<tr>
						<th data-column-id="nome" data-order="asc">Nome</th>
						<th data-column-id="observacao">Observação</th>
						<th data-column-id="status" data-order="asc">Status</th>
						<th data-column-id="acoes" data-formatter="acoes" data-sortable="false" data-searchable="false">Ações</th>
						
					</tr>
				</thead>
			</table>
		</div>


	</div>
	<jsp:include page="../modulos/footer.jsp" />


</body>
</html>