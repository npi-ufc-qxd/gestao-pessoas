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
			<select id="anoJson" name="ano" class="selectpicker filtroJsonFrequencia" data-width="auto">
				<option value="">Ano</option>
				<option value="2015">2015</option>
				<option value="2016">2016</option>
				<option value="2017">2017</option>
			</select>
			<select id="semestreJson" name="semestre" class="selectpicker filtroJsonFrequencia" data-width="auto">
				<option value="">Semestre</option>
				<option value="1">1</option>
				<option value="2">2</option>
			</select>
			<select id="turmaJson" name="turma" class="selectpicker filtroFrequenciaTurma union" data-width="auto">
			</select>
		</div>
		
		<div id="viewFrequencias">
			<div class="form" align="center"> <h1>Frequências</h1> </div>
			
			<div id="periodo-dia" class="periodo">
				<span id="before" class="glyphicon glyphicon-chevron-left"></span>
					<label id="periodo-dia"></label>
				<span id="after" class="glyphicon glyphicon-chevron-right"></span>
			</div>
	
			<table id="frequencias">
				<thead>
					<tr>
						<td data-column-id="4" data-order="asc">Nome</td>
						<td data-column-id="1" data-css-class="observacao">Observação</td>
						<td data-column-id="3" data-order="asc">Status</td>
						<td data-column-id="acoes" data-formatter="acoes" data-sortable="false" data-searchable="false">Ações</td>
						
					</tr>
				</thead>
			</table>
		</div>


	</div>
	<jsp:include page="../modulos/footer.jsp" />


</body>
</html>