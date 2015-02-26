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
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="novo-projeto" align="left">
			<div class="form" align="center">
				<h2>Adicionar Folga</h2>
				<form:form id="adicionarFolgaForm" role="form" commandName="folga" servletRelativeAction="/coordenador/${periodo.id}/folga" method="POST" cssClass="form-horizontal">
				<form:hidden path="id"/>

					<div class="form-group">
						<label for="data" class="col-sm-2 control-label">Data:</label>
						<div class="col-sm-2" align="left">
								<form:input id="dataFolga" path="data" cssClass="data form-control" placeholder="dd/mm/aaaa" required="required"/>
								<div class="error-validation">
									<form:errors path="data"></form:errors>
								</div>
						</div>
					</div>

					<div class="form-group">
						<label for="descricao" class="col-sm-2 control-label">Descrição: </label>
							<div class="col-sm-10" align="left">
								<form:textarea id="descricao" path="descricao" cssClass="form-control" placeholder="Descrição da folga" rows="3" required="required"/>
								<div class="error-validation">
									<form:errors path="descricao"></form:errors>
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