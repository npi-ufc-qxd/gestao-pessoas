<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>Projetos</title>
	</head>

<body>
	<jsp:include page="../modulos/header1.jsp" />


<div class="container">
	<div class="row">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Projetos</h2>

			<div class="pull-right">
				<a href="<c:url value="/supervisor/adicionar-projeto" ></c:url>"><button class="btn btn-success"><span class="fa fa-plus-square"></span> Projeto</button></a>
			</div>
		</div>

		<div class="panel-body">
			<c:if test="${empty projetos}"><div class="alert alert-warning" role="alert">Não há Projetos cadastrados.</div></c:if>
			
			<c:if test="${not empty projetos}">
				<table id="projetos" class="table table-striped table-hover">
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
									<a href="<c:url value="/supervisor/vincular-membros-projeto/${projeto.id}" />" class="btn btn-success"><span class="glyphicon glyphicon-link"></span> Vincular Membros</a>
									<a href="<c:url value="/supervisor/informacoes-projeto/${projeto.id}" />" class="btn btn-info"><span class="glyphicon glyphicon-eye-open"></span> Informações</a>
									<a href="<c:url value="/supervisor/editar-projeto/${projeto.id}" />" class="btn btn-primary"><span class="glyphicon glyphicon-pencil"></span> Editar</a>
<%-- 									<a href="<c:url value="#/supervisor/excluir-projeto/${projeto.id}" />" class="btn btn-danger"> <span class="glyphicon glyphicon-trash"></span></a> --%>
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
			$(".menu #projetos").addClass("active");
		});
	</script>	

</body>
</html>