<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Periodos</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="tab-pane active" id="meus-projetos">
			<div align="right" style="margin-bottom: 20px;">
				<a href="<c:url value="/coordenador/periodo" ></c:url>"><button class="btn btn-primary"><span class="fa fa-plus-square"></span> Periodo</button></a>
			</div>
			
			<c:if test="${empty periodos}"><div class="alert alert-warning" role="alert">Não há Periodos cadastrados.</div></c:if>

			<c:if test="${not empty periodos}">
				<h1 align="left" style="border-bottom: 1px solid #333;">Periodos</h1>

				<table id="periodos" class="table table-striped">
					<thead>
						<tr>
							<th>Ano</th>
							<th>Semestre</th>
							<th>Inicio</th>
							<th>Término</th>
							<th></th>
						</tr>
			       </thead>

			       <tbody class="panel">
							<c:forEach var="periodo" items="${periodos}">
								<tr class="linha">
									<td>${periodo.ano}</td>
									<td>${periodo.semestre}</td>
									<td><fmt:formatDate value="${periodo.inicio}" pattern="dd/MM/yyyy" /></td>
									<td><fmt:formatDate value="${periodo.termino}" pattern="dd/MM/yyyy" /></td>
									<td align="right">
										<a href="<c:url value="/turma/${periodo.id}/turma" />" class="btn btn-primary"><span class="fa fa-plus-square"></span> Turma</a>
										<a href="<c:url value="/coordenador/${periodo.id}/folga" />" class="btn btn-warning"><span class="fa fa-plus-square"></span> Folgas</a>
										<a href="<c:url value="/coordenador/${periodo.id}/detalhes" />" class="btn btn-info"><span class="glyphicon glyphicon-eye-open"></span>&nbsp;</a>
									</td>
								</tr>
						</c:forEach>
			       </tbody>
				</table>
				
				
				
				
			</c:if>
		</div>
	</div>

</body>
</html>