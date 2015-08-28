<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>Atualizar Membros do Projeto</title>
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />
	
<div class="container">
	<div class="row">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Atualizar Membros</h3>
		</div>

		<c:if test="${empty estagiarios }">
			<div class="panel-body">
				<div class="alert alert-warning" role="alert">Não há estagiários.</div>
			</div>
		</c:if>
		
		<c:if test="${not empty estagiarios }">
			<form:form id="vincularParcicipanteForm" role="form" modelAttribute="projeto" servletRelativeAction="/supervisor/projeto/${projeto.id}/vincular" method="POST" cssClass="">
				<form:hidden path="id"/>
				<form:hidden path="nome"/>
				<form:hidden path="descricao"/>

				<div class="panel-body">
					<table id="table-membros" class="table table-striped table-hover">
						<thead>
							<tr>
								<th>Membros</th>
								<th>Matrícula</th>
								<th>Turma</th>
								<th>Status Turma</th>
				           </tr>
				       </thead>
				       <tbody>
							<c:forEach var="estagiario" items="${estagiarios}" varStatus="cont">
								<c:set var="membroDoProjeto" value="${estagiario.projeto.id eq projeto.id ? true : false}"></c:set>
								<c:if test="${membroDoProjeto}">
									<tr>
										<td>
											<form:checkbox id="membro${cont.index}" path="membros[${cont.index}].id" value="${estagiario.id}" checked="checked"/>
											<label for="membro${cont.index}">${estagiario.nomeCompleto}</label>
										</td>
										<td>${estagiario.matricula}</td>
										<td>${estagiario.turma.ano}.${estagiario.turma.semestre} - ${estagiario.turma.nome}</td>
										<td>${estagiario.turma.statusTurma}</td>
									</tr>
								</c:if>
							</c:forEach>
				       </tbody>
					</table>
				</div>
				

				<div class="panel-body">
					<table id="table-vincular-membros" class="table table-striped table-hover">
						<thead>
							<tr>
								<th>Selecionar Membros</th>
								<th>Matrícula</th>
								<th>Turma</th>
								<th>Status Turma</th>
				           </tr>
				       </thead>
				       <tbody>
							<c:forEach var="estagiario" items="${estagiarios}" varStatus="cont">
								<c:set var="membroDoProjeto" value="${estagiario.projeto.id eq projeto.id ? true : false}"></c:set>
								<c:if test="${not membroDoProjeto}">
									<tr>
										<td>
											<form:checkbox id="membro${cont.index}" path="membros[${cont.index}].id" value="${estagiario.id}"/>
											<label for="membro${cont.index}">${estagiario.nomeCompleto}</label>
										</td>
										<td>${estagiario.matricula}</td>
										<td>${estagiario.turma.ano}.${estagiario.turma.semestre} - ${estagiario.turma.nome}</td>
										<td>${estagiario.turma.statusTurma}</td>
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
		$(".menu #projetos").addClass("active");
		
		$('#table-vincular-membros').DataTable({
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