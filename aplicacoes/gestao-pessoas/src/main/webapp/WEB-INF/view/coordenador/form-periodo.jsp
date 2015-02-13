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
				<h2>Nova Periodo</h2>
				<form:form id="adicionarTurmaForm" role="form" modelAttribute="periodo" servletRelativeAction="/coordenador/periodo" method="POST" cssClass="form-horizontal">

					<div class="form-group" align="left">
						<div class="form-item">
							<label for="inicioSemana" class="col-sm-2 control-label">Ano:</label>
							
							<div class="col-sm-3">
								<form:input id="ano" path="ano" cssClass="form-control" placeholder="Ano" />
								<div class="error-validation"><form:errors path="ano"></form:errors></div>
							</div>
						</div>
	
						<div class="form-item">
							<label for="semestre" class="col-sm-2 control-label">Semestre:</label>
							
							<div class="col-sm-3">
								<form:select id="semestre" path="semestre" cssClass="selectpicker" data-width="100%">
									<form:option value="">Selecione o Semestre</form:option>
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
								<form:input id="inicio" path="inicio" cssClass="form-control" placeholder="Inicio do periodo" />

								<div class="error-validation"><form:errors path="inicio"></form:errors></div>
							</div>
						</div>
	
						<div class="form-item">
							<label for="termino" class="col-sm-2 control-label">Término: </label>
							
							<div class="col-sm-3">
								<form:input id="termino" path="termino" cssClass="form-control" placeholder="Término do periodo" />

								<div class="error-validation"><form:errors path="termino"></form:errors></div>
							</div>
						</div>
					</div>

					<div class="controls">
						<input name="submit" type="submit" class="btn btn-primary" value="Cadastrar" /> 
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>