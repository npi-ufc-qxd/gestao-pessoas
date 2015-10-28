<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>Documentos Estágio</title>
<jsp:include page="../modulos/header-estrutura.jsp" />
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="row">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-file-text-o"></span> Documentação Estágio
					</h2>

					<div class="pull-right">
						<a title="Voltar" class="btn btn-primary back"><span
							class="fa fa-arrow-circle-o-left"></span> Voltar</a>
					</div>
				</div>


			</div>
		</div>

		<form:form id="form-documento" role="form"
			enctype="multipart/form-data" servletRelativeAction="/estagiario/turma/${idTurma}/documentacao"
			method="POST" cssClass="form-horizontal">

			<div class="form-group form-item">
				<label for="anexo" class="col-sm-2 control-label">Anexos:</label>
				<div class="col-sm-10">
					<input id="anexo" type="file" name="anexo"
						class="anexo file-loading" multiple="multiple"></input>
				</div>
			</div>

			<div class="controls">
				<input name="salvar" type="submit" class="btn btn-primary" value="Salvar" />
			</div>
		</form:form>
	</div>
	<br>
	<br>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>
