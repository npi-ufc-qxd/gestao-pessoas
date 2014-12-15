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
				<a href="<c:url value="/coordenador/${periodo.id}/turma" ></c:url>">
					<button class="btn btn-primary">Add Turma</button>
				</a>
			</div>
			<c:if test="${not empty periodo}">
			
			<label>Detalhes periodo..: </label>

			<c:if test="${empty periodo.turmas}">
				<div class="alert alert-warning" role="alert">Não há turmas vinculadas a este periodo.</div>
			</c:if>			

			<c:if test="${not empty periodo.turmas}">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">
						<h4>Turmas</h4>
					</div>
					<!-- Table -->
					<table class="table" id="table">
						<thead>
							<tr>
								<th>Codigo</th>
								<th>A</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="turma" items="${periodo.turmas}">
								<tr class="linha">
									<td>${turma.codigo}</td>
									<td>
										<a href="<c:url value="/coordenador/turma/${turma.id}/detalhes" />" class="btn btn-default">Detalhes <span class="glyphicon glyphicon-user"></span></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
				
			</c:if>
		</div>
	</div>

</body>
</html>