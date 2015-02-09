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
				<h1 align="center">Novo Projeto</h1>

				<form:form id="adicionarProjetoForm" role="form" modelAttribute="projeto" servletRelativeAction="/coordenador/projeto" method="POST" cssClass="form-horizontal">
					<form:hidden path="id"/>

					<div class="form-group">
						<label for="nome" class="col-sm-2 control-label">Nome:</label>
						<div class="col-sm-10">
							<form:input id="nome" path="nome" cssClass="form-control" placeholder="Nome do projeto" />

							<div class="error-validation"><form:errors path="nome"></form:errors></div>
						</div>
					</div>

					<div class="form-group">
						<label for="descricao" class="col-sm-2 control-label">Descrição:</label>
						<div class="col-sm-10">
							<form:textarea id="descricao" path="descricao" class="form-control" rows="3" placeholder="Descrição do Projeto"></form:textarea>

							<div class="error-validation"><form:errors path="descricao"></form:errors></div>
						</div>
					</div>

					<c:forEach var="membro" items="${projeto.membros}" varStatus="cont">
						<form:hidden path="membros[${cont.index}].id" value="${membro.id}" />
					</c:forEach>

					<div class="form-group">
						<input name="submit" type="submit" class="btn btn-primary" value="Cadastrar" /> 
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>