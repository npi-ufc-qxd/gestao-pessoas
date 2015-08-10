<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<html>
<head>
	<title>Meus Dados</title>
	<jsp:include page="../modulos/header-estrutura1.jsp" />
</head>
<body>

	<jsp:include page="../modulos/header1.jsp" />
	

	<div class="container">
		<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-link"></span></a> Meu Endereço</h2>
		<form:form id="meusDadosForm" role="form" commandName="estagiario" modelAttribute="estagiario" servletRelativeAction="/estagiario/meus-dados" method="POST" cssClass="form-horizontal">
			<div class="form-group">
				<div class="form-item">
					<label for="endereco" class="col-sm-2 control-label">Endereço:</label>
					<div class="col-sm-10">
						<form:input id="endereco" path="endereco" cssClass="form-control" placeholder="Rua, Nº, Bairro" required="required" />
						<div class="error-validation">
							<form:errors path="endereco"></form:errors>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group" align="center">
				<button type="submit" class="btn btn-primary">Atualizar</button>
			</div>
		</form:form>
	</div>
	<jsp:include page="../modulos/footer1.jsp" />
	
	<script type="text/javascript">
		$("#semestre").text("00");
	</script>
	
</body>
</html>