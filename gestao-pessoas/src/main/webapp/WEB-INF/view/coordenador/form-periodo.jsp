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
	<title>Cadastro de Periodo</title>
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
			
				<h2>Nova Periodo</h2>
				<form:form id="adicionarPeriodoForm" role="form" modelAttribute="periodo" servletRelativeAction="/coordenador/periodo" method="POST" cssClass="form-horizontal">
					<form:hidden path="id"/>

					<div class="form-group" align="left">
						<div class="form-item">
							<label for="ano" class="col-sm-2 control-label">Ano:</label>
							<div class="col-sm-3">
								<form:input id="ano" path="ano" cssClass="form-control ano" placeholder="Ano" required="required"/>
								<div class="error-validation"><form:errors path="ano"></form:errors></div>
							</div>
						</div>

						<div class="form-item">
							<label for="semestre" class="col-sm-2 control-label">Semestre:</label>
							<div class="col-sm-3">
								<form:select id="semestre" path="semestre" cssClass="selectpicker" data-width="100%">
									<form:option value="1"></form:option>
									<form:option value="2"></form:option>
								</form:select>
								<div class="error-validation"><form:errors path="semestre"></form:errors></div>
							</div>
						</div>
					</div>

					<div class="form-group" align="left">
						<div class="form-item">
							<label for="inicio" class="col-sm-2 control-label">Inicio: </label>
							<div class="col-sm-3">
								<form:input id="inicio" path="inicio" cssClass="form-control data" placeholder="Inicio do periodo" required="required"/>
								<div class="error-validation"><form:errors path="inicio"></form:errors></div>
							</div>
						</div>
	
						<div class="form-item">
							<label for="termino" class="col-sm-2 control-label">Término: </label>
							<div class="col-sm-3">
								<form:input id="termino" path="termino" cssClass="form-control data" placeholder="Término do periodo" required="required"/>
								<div class="error-validation"><form:errors path="termino"></form:errors></div>
							</div>
						</div>
					</div>

					<div class="controls">
						<input name="submitPeriodo" type="submit" class="btn btn-primary" value="Cadastrar" /> 
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />

</body>
</html>