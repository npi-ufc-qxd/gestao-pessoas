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
<title>Cadastro de Estagiário</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="novo-projeto" align="left">
			<div class="form" align="center">
				<h2>Novo Estagiário</h2>
				<form:form id="adicionarProjetoForm" role="form"
					commandName="estagiario"
					servletRelativeAction="/estagiario/cadastrar" method="POST"
					cssClass="form-horizontal">
					<div class="form-group">
						<label for="nomeCompleto" class="col-sm-2 control-label">Nome Completo:</label>
					</div>



					<div class="controls">
						<input name="submit" type="submit" class="btn btn-primary"
							value="Cadastrar" /> <a
							href="<c:url value="/projeto/index"></c:url>"
							class="btn btn-default">Cancelar</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<jsp:include page="../modulos/footer.jsp" />
</body>
<script>
	function TrimNome() {
		var nome = document.getElementById('nome');
		nome.value = nome.value.trim();
	}
	function TrimDescricao() {
		var descricao = document.getElementById('descricao');
		descricao.value = descricao.value.trim();
	}
</script>
<script type="text/javascript">
	$(document).ready(function($) {
	}(jQuery));
</script>
</html>