<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Projetos</title>
</head>

<style>
</style>

<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="tab-pane active" id="meus-projetos">
			<div align="right" style="margin-bottom: 20px;">
				<a href="<c:url value="/coordenador/projeto" ></c:url>"><button class="btn btn-primary"><span class="fa fa-plus-square"></span> Projeto</button></a>
			</div>
			
			<c:if test="${empty projetos}"><div class="alert alert-warning" role="alert">Não há Projetos cadastrados.</div></c:if>
			
			<c:if test="${not empty projetos}">
				<h1 align="left" style="border-bottom: 1px solid #333;">Projetos</h1>

				<table id="professores" class="table table-striped">
					<thead>
						<tr class="">
							<th>Nome</th>
							<th>Descrição</th>
							<th></th>
			           </tr>
			       </thead>

			       <tbody class="panel">
						<c:forEach var="projeto" items="${projetos}">
							<tr class="linha">
								<td>${projeto.nome}</td>
								<td>${projeto.descricao}</td>
								<td align="right">
									<a href="<c:url value="/coordenador/${projeto.id}/add-membros-projeto" />" class="btn btn-primary"><span class="fa fa-user-plus"></span></a>
									<a href="<c:url value="/coordenador/projeto/${projeto.id}/detalhes" />" class="btn btn-info"><span class="glyphicon glyphicon-eye-open"></span></a>
									<a href="<c:url value="/coordenador/projeto/${projeto.id}/editar" />" class="btn btn-success"><span class="glyphicon glyphicon-pencil"></span></a>
									<a href="<c:url value="/coordenador/projeto/${projeto.id}/excluir" />" class="btn btn-danger"> <span class="glyphicon glyphicon-trash"></span></a>
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