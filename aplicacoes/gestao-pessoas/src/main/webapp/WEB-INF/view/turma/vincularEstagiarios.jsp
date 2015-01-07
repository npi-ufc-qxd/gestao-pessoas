<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Menbros da Turma</title>
</head>
<body>
	<jsp:include page="../modulos/header-coordenador.jsp" />

	<div class="container">
		<div class="tab-pane active" id="meus-projetos">
			<div>
				<span style="margin-bottom: 20px; float:left">
					<a href="<c:url value="/coordenador/projeto" ></c:url>"><button class="btn btn-primary">Novo Projeto <span class="glyphicon glyphicon-plus"></span></button></a>
				</span>
				<span style="margin-bottom: 20px; float:right">
					<a href="<c:url value="/coordenador/projeto" ></c:url>"><button class="btn btn-primary">Novo Projeto <span class="glyphicon glyphicon-plus"></span></button></a>
				</span>
				
			</div>
			<div style="clear:both"></div>
			<c:if test="${empty membros}">
				<div class="alert alert-warning" role="alert">Não há estagiarios nesta turma.</div>
			</c:if>
			<c:if test="${not empty membros}">
				<form:form id="vincularEstagiarioForm" role="form" modelAttribute="turma" servletRelativeAction="/turma/vincularEstagiarios" method="POST" cssClass="form-horizontal">
					<form:hidden path="id"/>
				<div class="panel panel-default">
					<div class="panel-heading" align="center">
						<h4>Estagiarios da Turma</h4>
					</div>
					<!-- Table -->
					<table class="table" id="table">
						<thead>
							<tr>
								<th>#</th>
								<th>Nome</th>
								<th>Matricula</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="estagiario" items="${membros}" varStatus="cont">
								<tr class="linha">
									<td>
										<form:checkbox path="estagiarios[${cont.index}].id" value="${estagiario.id}" checked="${estagiario.turma.id eq turma.id ? 'checked' : ''}"/>
									</td>
									<td>${estagiario.pessoa.nome}</td>
									<td>${estagiario.matricula}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
						<div class="controls">
							<input name="vincular" type="submit" class="btn btn-primary" value="Vincular" />
						</div>
				</form:form>
			</c:if>
		</div>
	</div>
</body>
</html>