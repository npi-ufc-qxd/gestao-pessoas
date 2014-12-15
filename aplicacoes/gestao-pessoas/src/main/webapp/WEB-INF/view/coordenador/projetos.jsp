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
				<a href="<c:url value="/coordenador/projeto" ></c:url>">
					<button class="btn btn-primary">Novo Projeto <span class="glyphicon glyphicon-plus"></span></button>
				</a>
			</div>
			<c:if test="${empty projetos}">
				<div class="alert alert-warning" role="alert">Não há Projetos cadastrados.</div>
			</c:if>
			<c:if test="${not empty projetos}">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">
						<h4>Projetos Cadastrados</h4>
					</div>
					<!-- Table -->
					<table class="table" id="table">
						<thead>
							<tr>
								<th>Nome</th>
								<th>Descrição</th>
								<th>A</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="projeto" items="${projetos}">
								<tr class="linha">
									<td>${projeto.nome}</td>
									<td>${projeto.descricao}</td>
									<td>
										<a href="<c:url value="/coordenador/${projeto.id}/vincularMembros" />" class="btn btn-default">Add <span class="glyphicon glyphicon-user"></span></a>
										<a href="<c:url value="/coordenador/projeto/${projeto.id}/detalhes" />" class="btn btn-default">Detalhes <span class="glyphicon glyphicon-eye-open"></span></a>
										<a href="<c:url value="/coordenador/projeto/${projeto.id}/editar" />" class="btn btn-default">Editar <span class="glyphicon glyphicon-pencil"></span></a>
										<a href="<c:url value="/coordenador/projeto/${projeto.id}/excluir" />" class="btn btn-default">Excluir <span class="glyphicon glyphicon-trash"></span></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
	</div>

</body>
</html>