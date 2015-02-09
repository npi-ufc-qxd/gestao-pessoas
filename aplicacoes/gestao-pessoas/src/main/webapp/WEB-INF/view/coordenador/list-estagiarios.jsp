<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Estagiários</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
	
		<form:form id="buscaEstagiariosForm" role="form" modelAttribute="filtro" servletRelativeAction="/coordenador/estagiarios" method="POST" cssClass="form-inline">
			<div class="form-group">
				<form:select id="ano" path="ano" class="form-control selectFiltro selectpicker">
					<option>Ano</option>
					<option value="2014">2014</option>
					<option value="2015">2015</option>
					<option value="2016">2016</option>
					<option value="2017">2017</option>
					<option value="2018">2018</option>
					<option value="2019">2019</option>
					<option value="2020">2020</option>
				</form:select>
			</div>
		
			<div class="form-group">
				<form:select id="semestre" path="semestre" class="form-control selectFiltro selectpicker">
					<option>Semestre</option>
					<option value="1">1</option>
					<option value="2">2</option>
				</form:select>
			</div>
		
			<div class="form-group turma">
				<select id="turma" name="turma" class="selectpicker filtroFrequenciaTurma union" data-width="auto">
				</select>

<%-- 				<form:select id="turma" path="turma" class="form-control union"> --%>
<%-- 					<c:if test="${not empty turmas}"> --%>
<%-- 						<form:options items="${turmas}" itemValue="id" itemLabel="codigo"  /> --%>
<%-- 					</c:if> --%>
<%-- 				</form:select> --%>
			</div>

		</form:form>	
	
	
		<div class="tab-pane active" id="meus-projetos">
			<c:if test="${empty estagiarios}">
				<div class="alert alert-warning" role="alert">Não há Estagiários cadastrados.</div>
			</c:if>
			<c:if test="${not empty estagiarios}">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">
						<h4>Estagiários Cadastrados</h4>
					</div>
					<!-- Table -->
					<table class="table" id="table">
						<thead>
							<tr>
								<th>Nome</th>
								<th>Matricula</th>
								<th>Turma</th>
								<th>Ação</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="estagiario" items="${estagiarios}">
								<tr class="linha">
									<td>${estagiario.nomeCompleto}</td>
									<td>${estagiario.matricula}</td>
									<td>${estagiario.turma.codigo}</td>
									<td>Ação</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
	</div>

	<div align="center" style="margin-bottom: 20px;">
		<a href="<c:url value="/coordenador/jrreport" ></c:url>">
			<button class="btn btn-primary">Termo de Compromisso <span class="glyphicon glyphicon-plus"></span></button>
		</a>
		<a href="<c:url value="/coordenador/declaracaoEstagio" ></c:url>">
			<button class="btn btn-primary">Declaração de Estágio <span class="glyphicon glyphicon-plus"></span></button>
		</a>
		<a href="<c:url value="#/coordenador/emitirFrequencia" ></c:url>">
			<button class="btn btn-primary">Emitir Frequência<span class="glyphicon glyphicon-plus"></span></button>
		</a>
	</div>
	
	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>