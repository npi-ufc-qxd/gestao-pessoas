<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Cadastro de Contas Pessoais</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="novo-projeto" align="left">
			<div class="form" align="center">
				<h2>Contas Pessoais</h2>
				<div class="form-group">
					<form:form id="contaspessoais" commandName="estagiario"
						servletRelativeAction="${url }" enctype="multipart/form-data"
						cssClass="form-horizontal" method="POST">
						<label for="contaRedmine" class="col-sm-2 control-label">Conta
							Redmine:</label>
						<div class="col-sm-10">
							<form:input id="contaRedmine" path="contaRedmine"
								name="contaRedmine" class="form-control" rows="5"
								placeholder="Email"></form:input>
						</div>

						<label for="contaGithub" class="col-sm-2 control-label">Conta
							GitHub:</label>
						<div class="col-sm-10">
							<form:input id="contaGithub" path="contaGithub"
								name="contaGithub" class="form-control" rows="5"
								placeholder="Email"></form:input>
						</div>

						<label for="contaHangout" class="col-sm-2 control-label">Conta
							Hangout:</label>
						<div class="col-sm-10">
							<form:input id="contaHangout" path="contaHangout"
								name="contaHangout" class="form-control" rows="5"
								placeholder="Email"></form:input>
						</div>
						<div class="controls">
							<input name="submit" type="submit" class="btn btn-primary"
								value="Salvar" /> <a
								href="<c:url value="/estagiario/index"></c:url>"
								class="btn btn-default">Cancelar</a>
						</div>

					</form:form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>