<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<jsp:include page="../modulos/header-estrutura.jsp" />
	<title>Cadastro de Projetos</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="novo-projeto" align="left">
			<div class="form" align="center">

				<c:if test="${not empty erro}">
					<div class="alert alert-danger alert-dismissible margin-top" role="alert">
						<button type="button" class="close" data-dismiss="alert">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<c:out value="${erro}"></c:out>
					</div>
				</c:if>
			
			
				<h1 align="center">Novo Projeto</h1>

				<form:form id="adicionarProjetoForm" role="form" modelAttribute="projeto" servletRelativeAction="/coordenador/projeto" method="POST" cssClass="form-horizontal">
					<form:hidden path="id"/>

					<div class="form-group">
						<div class="form-item">
							<label for="nome" class="col-sm-2 control-label">Nome:</label>
							<div class="col-sm-10" align="left">
								<form:input id="nome" path="nome" cssClass="form-control" placeholder="Nome do projeto" required="required"/>
								<div align="left" class="error-validation left"><form:errors path="nome"></form:errors></div>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="form-item">
							<label for="descricao" class="col-sm-2 control-label">Descrição:</label>
							<div class="col-sm-10" align="left">
								<form:textarea id="descricao" path="descricao" class="form-control" rows="3" placeholder="Descrição do Projeto" required="required"></form:textarea>
								<div class="error-validation" align="left"><form:errors path="descricao"></form:errors></div>
							</div>
						</div>
					</div>

					<c:forEach var="membro" items="${projeto.membros}" varStatus="cont">
						<form:hidden path="membros[${cont.index}].id" value="${membro.id}" />
					</c:forEach>

					<div class="form-group">
						<input name="submitProjeto" type="submit" class="btn btn-primary" value="Cadastrar" /> 
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>