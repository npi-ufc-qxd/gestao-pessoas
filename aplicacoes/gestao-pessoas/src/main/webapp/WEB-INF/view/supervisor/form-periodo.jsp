<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${action eq 'cadastrar' }">
	<c:set var="url" value="/supervisor/adicionar-periodo"></c:set>
	<c:set var="titulo" value="Adicionar Periodo"></c:set>
	<c:set var="botao" value="Salvar"></c:set>
</c:if>
<c:if test="${action eq 'editar' }">
	<c:set var="url" value="/supervisor/editar-periodo/${periodo.id }"></c:set>
	<c:set var="titulo" value="Editar Periodo"></c:set>
	<c:set var="botao" value="Editar"></c:set>
</c:if>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>${titulo}</title>
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />

<c:if test="${not empty erro}">
	<div class="alert alert-danger alert-dismissible margin-top" role="alert">
		<button type="button" class="close" data-dismiss="alert">
			<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
		</button>
		<c:out value="${erro}"></c:out>
	</div>
</c:if>


<div class="container">
	<div class="row">
	<div class="panel panel-primary">
	
		<div class="panel-heading">
			<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> ${titulo}</h2>
		</div>

		<form:form id="adicionarPeriodoForm" role="form" modelAttribute="periodo" servletRelativeAction="${url }" method="POST" cssClass="form-horizontal">
			<div class="panel-body">
				<form:hidden path="id"/>

				<div class="form-group">
					<div class="form-item col-sm-6">
						<label for="ano" class="control-label">Ano:</label>
						<form:input id="ano" path="ano" cssClass="form-control" placeholder="Ano" required="required"/>
						<div class="error-validation"><form:errors path="ano"></form:errors></div>
					</div>
	
					<div class="form-item col-sm-6">
						<label for="semestre" class="control-label">Semestre:</label>
						<form:select id="semestre" path="semestre" cssClass="selectpicker" data-width="100%">
							<form:option value="1"></form:option>
							<form:option value="2"></form:option>
						</form:select>
						<div class="error-validation"><form:errors path="semestre"></form:errors></div>
					</div>
				</div>

				<div class="form-group">
					<div class="form-item col-sm-6">
						<label for="inicio" class="control-label">Inicio: </label>
						<form:input id="inicio" path="inicio" cssClass="form-control data" placeholder="Inicio do periodo" required="required"/>
						<div class="error-validation"><form:errors path="inicio"></form:errors></div>
					</div>
	
					<div class="form-item col-sm-6">
						<label for="termino" class="control-label">Término: </label>
						<form:input id="termino" path="termino" cssClass="form-control data" placeholder="Término do periodo" required="required"/>
						<div class="error-validation"><form:errors path="termino"></form:errors></div>
					</div>
				</div>
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
			$(".menu #periodos").addClass("active");
		});
	</script>	
</body>
</html>