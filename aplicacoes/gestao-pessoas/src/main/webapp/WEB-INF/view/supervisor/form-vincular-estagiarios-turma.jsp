<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura1.jsp" />

<title>Vincular estagiários a turma</title>
</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />
	
	<div class="container">
	<div class="row">
	<div class="panel panel-primary">
	
		<div class="panel-heading">
			<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-link"></span></a> Atualizar Vínculos: ${turma.nome}</h2>
		</div>
		
		<c:if test="${empty estagiarios }">
			<div class="panel-body">
				<div class="alert alert-warning" role="alert">Não há estagiários.</div>
			</div>
		</c:if>
		
		<c:if test="${not empty estagiarios }">
		<form:form id="vincularEstagiarioTurma" role="form" modelAttribute="turma" servletRelativeAction="/supervisor/turma/${turma.id}/vincular" method="POST">
			<div class="panel-body">
				<form:hidden path="id"/>
				<table id="table-estagiarios" class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Estagiários da turma</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="estagiario" items="${estagiarios}" varStatus="contador">
							<c:set var="estagiarioDaTurma" value="${estagiario.turma.id eq turma.id ? true : false}"></c:set>
							<c:if test="${estagiarioDaTurma }">
							<tr>
								<td>
									<form:checkbox id="estagiario${contador.index}" path="estagiarios[${contador.index}].id" value="${estagiario.id}" checked="checked"/>
									<label for="estagiario${contador.index}" class="text-view-info">${estagiario.nomeCompleto}</label>
								</td>
							</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div class="panel-body">
				<form:hidden path="id"/>
				<table id="table-vincular-estagiarios" class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Selecione os estagiários</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="estagiario" items="${estagiarios}" varStatus="contador">
							<c:set var="estagiarioDaTurma" value="${estagiario.turma.id eq turma.id ? true : false}"></c:set>
							<c:if test="${not estagiarioDaTurma }">
							<tr>
								<td>
									<form:checkbox id="estagiario${contador.index}" path="estagiarios[${contador.index}].id" value="${estagiario.id}"/>
									<label for="estagiario${contador.index}" class="text-view-info">${estagiario.nomeCompleto}</label>
								</td>
							</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<div class="panel-footer" align="center">
				<div class="controls">
					<button type="submit" class="btn btn-success">Atualizar vínculos <span class="glyphicon glyphicon-refresh"></span></button>
				</div>
			</div>

		</form:form>
		</c:if>
    </div>
	</div>
</div>

	<jsp:include page="../modulos/footer1.jsp" />
	
    <script type="text/javascript">
		$(document).ready(function(){
			$(".menu #turmas").addClass("active");
		});

		$('#table-vincular-estagiarios').DataTable({
			 "pageLength": 10,
			"language": ptBR,
			 "order": [0, 'asc'],
			 "columnDefs": [
				{ "orderable": false, "targets": 0 },
			],
		});

		$('.dataTables_length label').addClass('text-view-info');
		$('.dataTables_filter label').addClass('text-view-info');
	</script>	
	
</body>
</html>