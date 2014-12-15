<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Projetos</title>
</head>
<body>
	<jsp:include page="../modulos/header-coordenador.jsp" />

	<div class="container">
		<div class="tab-pane active" id="meus-projetos">
			<div align="right" style="margin-bottom: 20px;">
				<a href="<c:url value="/coordenador/periodo" ></c:url>">
					<button class="btn btn-primary">Novo Periodo <span class="glyphicon glyphicon-plus"></span></button>
				</a>
			</div>
			<c:if test="${empty periodos}">
				<div class="alert alert-warning" role="alert">Não há Periodos cadastrados.</div>
			</c:if>
			<c:if test="${not empty periodos}">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">
						<h4>Periodos Cadastrados</h4>
					</div>
					<!-- Table -->
					<table class="table" id="table">
						<thead>
							<tr>
								<th>Ano</th>
								<th>Semestre</th>
								<th>Inicio</th>
								<th>Término</th>
								<th>Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="periodo" items="${periodos}">
								<tr class="linha">
									<td>${periodo.ano}</td>
									<td>${periodo.semestre}</td>
									<td>${periodo.inicio}</td>
									<td>${periodo.termino}</td>
									<td>
										<a href="<c:url value="/coordenador/${periodo.id}/turma" />" class="btn btn-default">Add Turma <span class="glyphicon glyphicon-user"></span></a>
										<a href="<c:url value="/coordenador/periodo/${periodo.id}/folga" />" class="btn btn-default">Add Folgas </a>
										<a href="<c:url value="/coordenador/periodo/${periodo.id}/detalhes" />" class="btn btn-default">Detalhes <span class="glyphicon glyphicon-eye-open"></span></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
	</div>

</body>
</html>