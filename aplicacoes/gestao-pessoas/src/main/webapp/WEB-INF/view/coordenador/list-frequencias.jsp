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
	<title>Frequência</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<input id="current-data" type="hidden"/>

		<h4><b>Frequências</b></h4>
		<form id="filtroFrequenciaDaTurma" class="form-inline filtro">
			<label class="info">Selecione o periodo:</label>
			<div class="form-group">
				<input id="anoFiltro" name="ano" type="text" class="form-control filtroAno col-sm-1" placeholder="ano" size="4">
			</div>
		
			<div class="form-group">
				<select id="semestreFiltro" name="semestre" class="selectpicker filtroSemestre " data-width="auto">
				<option value="">Semestre</option>
				<option value="1">1</option>
				<option value="2">2</option>
				</select>			
			</div>
			
			<div class="form-group">
				<select id="turmaFiltro" name="turma" class="selectpicker filtroTurma frequenciasTurma" data-width="auto"></select>
			</div>
		</form>
	</div>

	<div class="container">
		<div id="viewFrequencias" class="hidden">
			<div class="form" align="center">
				<h1>Frequências</h1>

				<div id="periodo-dia" class="periodo">
					<span id="before" class="glyphicon glyphicon-chevron-left"></span>
						<label id="periodo-dia"></label>
					<span id="after" class="glyphicon glyphicon-chevron-right"></span>
				</div>
			
			</div>
	
			<table id="frequencias">
				<thead>
					<tr>
						<td data-column-id="4" data-order="asc">Nome</td>
						<td data-column-id="1" data-css-class="observacao">Observação</td>
						<td data-column-id="3" data-order="asc" data-css-class="status">Status</td>
						<td data-column-id="acoes" data-formatter="acoes" data-sortable="false" data-searchable="false">Ações</td>
					</tr>
				</thead>
			</table>
		</div>


	</div>
	<jsp:include page="../modulos/footer.jsp" />

</body>
</html>