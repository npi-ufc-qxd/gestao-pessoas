<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura1.jsp" />

<title>Vincular Estagiarios a turma</title>
</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />
	
	<div class="container">
	<div class="row">
	<div class="panel panel-default">
	<div class="panel-body">

		<div class="col-sm-1"></div>

		<div class="col-sm-10">
			<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-link"></span></a> Atualizar Vínculos: ${turma.nome}</h2>
			<form:form id="vincularEstagiarioTurma" role="form" modelAttribute="turma" servletRelativeAction="/supervisor/vincular-estagiarios-turma/${turma.id}" method="POST">
				<form:hidden path="id"/>
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Selecione os Estagariarios</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="estagiario" items="${estagiarios}" varStatus="contador">
							<tr>
								<td>
									<form:checkbox id="estagiario${contador.index}" path="estagiarios[${contador.index}].id" value="${estagiario.id}" checked="${estagiario.turma.id eq turma.id ? 'checked' : ''}"/>
									<label for="estagiario${contador.index}" class="text-view-info">${estagiario.nomeCompleto}</label>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<div class="form-group" align="center">
					<button type="submit" class="btn btn-success">Atualizar vínculos <span class="glyphicon glyphicon-refresh"></span></button>
				</div>
				
			</form:form>
	    </div>

		<div class="col-sm-1"></div>

	</div>
	</div>
	</div>
	</div>

	<jsp:include page="../modulos/footer1.jsp" />
</body>
</html>