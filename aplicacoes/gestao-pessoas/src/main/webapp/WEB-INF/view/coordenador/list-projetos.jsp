<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
	<jsp:include page="../modulos/header-estrutura.jsp" />
	<title>Projetos</title>
</head>

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
									<a href="<c:url value="/coordenador/projeto/${projeto.id}/vincular-membros-projeto" />" class="btn btn-primary"><span class="fa fa-user-plus"></span></a>
									<a href="<c:url value="/coordenador/projeto/detalhes/${projeto.id}" />" class="btn btn-info"><span class="glyphicon glyphicon-eye-open"></span></a>
									<a href="<c:url value="/coordenador/projeto/editar/${projeto.id}" />" class="btn btn-success"><span class="glyphicon glyphicon-pencil"></span></a>
									<a href="<c:url value="/coordenador/projeto/excluir/${projeto.id}" />" class="btn btn-danger"> <span class="glyphicon glyphicon-trash"></span></a>
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