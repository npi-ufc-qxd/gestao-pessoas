<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>Periodos</title>
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />


<div class="container">
	<div class="row">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Periodos</h2>

			<div class="pull-right">
				<a href="<c:url value="/supervisor/adicionar-periodo" ></c:url>"><button class="btn btn-success"><span class="fa fa-plus-square"></span> Periodo</button></a>
			</div><br>

		</div>

		<div class="panel-body">
			<c:if test="${empty periodos}"><div class="alert alert-warning" role="alert">Não há Periodos cadastrados.</div></c:if>

			<c:if test="${not empty periodos}">
				<table id="periodos" class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Ano</th>
							<th>Semestre</th>
							<th>Inicio</th>
							<th>Término</th>
							<th></th>
						</tr>
			       </thead>
			       <tbody class="text-view-info">
						<c:forEach var="periodo" items="${periodos}">
							<tr>
								<td>${periodo.ano}</td>
								<td>${periodo.semestre}</td>
								<td><fmt:formatDate value="${periodo.inicio}" pattern="dd/MM/yyyy" /></td>
								<td><fmt:formatDate value="${periodo.termino}" pattern="dd/MM/yyyy" /></td>
							
								<td align="right">
									<a href="<c:url value="/supervisor/periodo/${periodo.id}/adicionar-turma" />" class="btn btn-success btn-sm"><span class="fa fa-plus-square"></span> Turma</a>
									<a href="<c:url value="/supervisor/periodo/${periodo.id}/adicionar-folga" />" class="btn btn-warning btn-sm"><span class="fa fa-plus-square"></span> Folgas</a>
									<a href="<c:url value="/supervisor/informacoes-periodo/${periodo.id}" />" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-eye-open"></span>&nbsp;</a>
									<a href="<c:url value="/supervisor/editar-periodo/${periodo.id}" />" class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-pencil"></span>&nbsp;</a>
								</td>
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
			$(".menu #periodos").addClass("active");
		});
	</script>	
</body>
</html>