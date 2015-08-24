<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="anoTurma" value="${estagiarios[0].turma.periodo.ano}"></c:set>
<c:set var="semestreTurma" value="${estagiarios[0].turma.periodo.semestre}"></c:set>
<c:set var="nomeTurma" value="${estagiarios[0].turma.nome}"></c:set>
<c:set var="idTurma" value="${estagiarios[0].turma.id }"></c:set>

<html>
<head>
	<meta charset="UTF-8"/>
	<jsp:include page="../modulos/header-estrutura1.jsp" />
	<title>Estagiários</title>
</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />



<div class="container">
	<div class="row">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="titulo-panels"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Estagiarios</h3>

			<div class="btn-group">
				 <c:if test="${empty nomeTurma}">
				 	<button type="button" data-toggle="dropdown" class="btn btn-default dropdown-toggle col-sm-12">Selecione a Turma&nbsp;&nbsp;<span class="caret"></span></button>
				 </c:if>
				 <c:if test="${not empty nomeTurma}">
				 	<button type="button" data-toggle="dropdown" class="btn btn-default dropdown-toggle col-sm-12">${anoTurma}.${semestreTurma} - ${nomeTurma}&nbsp;&nbsp;<span class="caret"></span></button>
				 </c:if>

		         <ul class="dropdown-menu">
	                <li><a href="<c:url value="/supervisor/estagiarios" />">Selecione a Turma&nbsp;&nbsp;</a></li>
					<c:forEach var="turma" items="${turmas}">
						<li><a href="<c:url value="/supervisor/turma/${turma.id}/estagiarios" />">${turma.periodo.ano}.${turma.periodo.semestre} - ${turma.nome}</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<c:if test="${not empty warning}">
			<div class="panel-body">
				<div class="alert alert-dismissible alert-warning">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<p>Não há estagiarios vinculados a esta turma.</p>
				</div>
			</div>
		</c:if>

		<c:if test="${not empty estagiarios}">
			<div class="panel-body">
					<table id="estagiarios" class="table table-striped table-hover">
						<caption><h5><strong>${estagiarios[0].turma.nome} - ${estagiarios[0].turma.periodo.ano}.${estagiarios[0].turma.periodo.semestre}</strong></h5></caption>
						<thead>
							<tr>
								<th>Matricula</th>
								<th>Nome</th>
								<th></th>
				           </tr>
				       </thead>
				       <tbody>
							<c:forEach var="estagiario" items="${estagiarios}">
								<tr>
									<td>${estagiario.matricula}</td>
									<td>${estagiario.nomeCompleto}</td>
									<td align="right">
										<a href="<c:url value="/supervisor/estagiario/${estagiario.id}/frequencia" />" class="btn btn-success btn-sm"><span class="fa fa-list"></span> Frequências</a>
									</td>
								</tr>
							</c:forEach>
				       </tbody>
					</table>
				</div>
				<div class="panel-footer" align="center">
					<div class="controls">
						<a class="btn btn-default" href="<c:url value="/supervisor/turma/${idTurma }/tce" ></c:url>"><i class="fa fa-file-pdf-o"></i> Termo de Compromisso</a>
						<a class="btn btn-primary" href="<c:url value="/supervisor/turma/${idTurma }/declaracoes" ></c:url>"><i class="fa fa-file-pdf-o"></i> Declaração de Estágio</a>
					</div>
				</div>
		</c:if>
	</div>
	</div>
</div>

	<jsp:include page="../modulos/footer1.jsp" />
	
    <script type="text/javascript">
		$(document).ready(function(){
			$(".menu #estagiarios").addClass("active");
		});
	</script>	
</body>
</html>