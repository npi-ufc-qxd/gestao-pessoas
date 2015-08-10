<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${action eq 'cadastrar' }">
	<c:set var="url" value="/supervisor/adicionar-projeto/"></c:set>
	<c:set var="titulo" value="Adicionar Projeto"></c:set>
	<c:set var="botao" value="Cadastrar"></c:set>
</c:if>
<c:if test="${action eq 'editar' }">
	<c:set var="url" value="/supervisor/editar-projeto/${projeto.id}"></c:set>
	<c:set var="titulo" value="Editar Projeto"></c:set>
	<c:set var="botao" value="Editar"></c:set>
</c:if>

<html>
<head>
	<jsp:include page="../modulos/header-estrutura1.jsp" />
	<title>${titulo}</title>
</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />



<div class="container">

	<c:if test="${not empty erro}">
		<div class="alert alert-danger alert-dismissible margin-top" role="alert">
			<button type="button" class="close" data-dismiss="alert">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<c:out value="${erro}"></c:out>
		</div>
	</c:if>

	<div class="row">
	<div class="panel panel-primary">
	
		<div class="panel-heading">
			<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> ${titulo}</h2>
		</div>

		<form:form id="adicionarProjetoForm" role="form" modelAttribute="projeto" servletRelativeAction="${url}" method="POST" cssClass="form-horizontal">
			<div class="panel-body">
				<form:hidden path="id"/>

				<div class="form-group">
					<div class="form-item col-sm-12">
						<label for="nome" class="Control-label">Nome:</label>
						<form:input id="nome" path="nome" cssClass="form-control" placeholder="Nome do projeto" required="required"/>
						<div align="left" class="error-validation left"><form:errors path="nome"></form:errors></div>
					</div>
				</div>

				<div class="form-group">
					<div class="form-item col-sm-12">
						<label for="descricao" class="control-label">Descrição:</label>
						<form:textarea id="descricao" path="descricao" class="form-control" rows="3" placeholder="Descrição do Projeto" required="required"></form:textarea>
						<div class="error-validation" align="left"><form:errors path="descricao"></form:errors></div>
					</div>
				</div>

				<c:forEach var="membro" items="${projeto.membros}" varStatus="cont">
					<form:hidden path="membros[${cont.index}].id" value="${membro.id}" />
				</c:forEach>
			</div>

			<div class="panel-footer" align="center">
				<div class="controls">
					<c:if test="${action eq 'cadastrar' }"><button type="submit" class="btn btn-primary">Cadastrar</button></c:if>
					<c:if test="${action eq 'editar' }"><button type="submit" class="btn btn-success">Salvar alterações</button></c:if>
				</div>
			</div>
		</form:form>
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