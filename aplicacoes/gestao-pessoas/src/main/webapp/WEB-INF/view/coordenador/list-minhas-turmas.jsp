<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Turmas</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="tab-pane active" id="meus-projetos">
			<c:if test="${empty turmas}">
				<div class="alert alert-warning" role="alert">Não há Turmas cadastrados.</div>
			</c:if>
			
			<c:if test="${not empty turmas}">
				<h1 align="left" style="border-bottom: 1px solid #333;">Minhas Turmas</h1>

				<table id="professores" class="table table-striped">
					<thead>
						<tr class="">
							<th>Periodo</th>
							<th>Turma</th>
							<th>N° de Estagiarios</th>
							<th></th>
			           </tr>
			       </thead>

			       <tbody class="panel">
							<c:forEach var="turma" items="${turmas}">
								<tr class="linha">
									<td>${turma.periodo.ano}.${turma.periodo.semestre}</td>
									<td>${turma.codigo}</td>
									<td><span class="badge ${fn:length(turma.estagiarios) gt 0 ? 'badge-success' : 'badge-danger' }">${fn:length(turma.estagiarios)}</span></td>
									<td align="right">
										<a href="<c:url value="/turma/${turma.id}/detalhes" />" class="btn btn-info"><span class="glyphicon glyphicon-eye-open"></span></a>
										<a href="<c:url value="/turma/${turma.id}/reposicao" />" class="btn btn-primary"><span class="glyphicon glyphicon-calendar"></span></a>
										<a href="<c:url value="/turma/${turma.id}/editar" />" class="btn btn-success"><span class="glyphicon glyphicon-pencil"></span></a>
										<a href="<c:url value="/turma/${turma.id}/excluir" />" class="btn btn-danger"> <span class="glyphicon glyphicon-trash"></span></a>
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