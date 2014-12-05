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
<title>Cadastro de Turma</title>
</head>
<body>
	<jsp:include page="../modulos/header-coordenador.jsp" />

	<div class="container">
		<div class="novo-projeto" align="left">
			<div class="form" align="center">
				<h2>Nova Turma</h2>
				<form:form id="adicionarTurmaForm" role="form" modelAttribute="turma" servletRelativeAction="/coordenador/turma" method="POST" cssClass="form-horizontal">

					<div class="form-group">
						<label for="inicioSemana" class="col-sm-2 control-label">Inicio:</label>
						<div class="col-sm-10" align="left">
							<form:select id="inicioSemana" path="inicioSemana" cssClass="btn btn-default dropdown-toggle">
								<form:option value="2">Segunda-Feira</form:option>
								<form:option value="3">Terça-Feira</form:option>
								<form:option value="4">Quarta-Feira</form:option>
								<form:option value="5">Quinta-Feira</form:option>
								<form:option value="6">Sexta-Feira</form:option>
							</form:select>
							<div class="error-validation">
								<form:errors path="inicioSemana"></form:errors>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for="horaInicio" class="col-sm-2 control-label">Hora Inicial:</label>
							<div class="col-sm-10" align="left">
								<form:input id="horaInicio" path="horaInicio" cssClass="form-control" placeholder="Nome do projeto" />
								<div class="error-validation">
									<form:errors path="horaInicio"></form:errors>
								</div>
							</div>
					</div>

					<div class="form-group">
						<label for="horaFinal" class="col-sm-2 control-label">Hora Final:</label>
							<div class="col-sm-10" align="left">
								<form:input id="horaFinal" path="horaFinal" cssClass="form-control" placeholder="Nome do projeto" />
								<div class="error-validation">
									<form:errors path="horaFinal"></form:errors>
								</div>
						</div>
					</div>

					<div class="controls">
						<input name="submit" type="submit" class="btn btn-primary" value="Cadastrar" /> 
						<a href="<c:url value="/projeto/index"></c:url>" class="btn btn-default">Cancelar</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>