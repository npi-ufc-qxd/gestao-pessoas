<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>Informações da Turma</title>
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />

<div class="container">
	<div class="row">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Turma</h2>
		</div>

		<div class="panel-body">
			<c:if test="${empty turma}"><div class="alert alert-warning" role="alert">Turma inexistente.</div></c:if>

			<c:if test="${not empty turma}">
				<div class="form-group">
					<label class="col-sm-1">Supervisor: </label><label>${turma.supervisor.nome}</label>
				</div>

				<div class="form-group">
					<label class="col-sm-1">Turma: </label><label>${turma.nome}</label>
				</div>

				<div class="form-group">
					<label class="col-sm-1">Semestre: </label><label>${turma.periodo.ano}.${turma.periodo.semestre}</label>
				</div>
			</c:if>
		</div>
	</div>
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Estagiários</h2>

			<div class="pull-right">
				<a href="<c:url value="/supervisor/turma/${turma.id}/vincular" />" title="Vincular Estagiários" class="btn btn-success"><span class="glyphicon glyphicon-link"></span>&nbsp;</a>
			</div>
		</div>

		<div class="panel-body">
			<c:if test="${empty turma.estagiarios}"><div class="alert alert-warning" role="alert">Não há estagiários vinculados a esta turma.</div></c:if>

			<c:if test="${not empty turma.estagiarios}">
				<table id="estagiarios-turma" class="table table-striped">
					<thead>
						<tr class="">
							<th class="col-sm-1">Matrícula</th>
							<th class="col-sm-5">Nome</th>
			           </tr>
			       </thead>
	
			       <tbody class="panel">
						<c:forEach var="estagiario" items="${turma.estagiarios}">
							<tr class="linha">
								<td>${estagiario.matricula}</td>
								<td>${estagiario.nomeCompleto}</td>
							</tr>
						</c:forEach>
			       </tbody>
				</table>
			</c:if>
		</div>
	</div>
	
	</div>
</div>

	<jsp:include page="../modulos/footer1.jsp" />

    <script type="text/javascript">
		$(document).ready(function(){
			$(".menu #turmas").addClass("active");
		});
	</script>	

</body>
</html>