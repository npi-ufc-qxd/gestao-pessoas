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
<title>Cadastro de Estagiário</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<div class="novo-projeto" align="left">
			<div class="form" align="center">
				<h2>Novo Estagiário</h2>
				<form:form id="adicionarProjetoForm" role="form"
					commandName="estagiario" modelAttribute="estagiario"
					servletRelativeAction="/estagiario/cadastrar" method="POST"
					cssClass="form-horizontal">
					<label for="nomeCompleto" class="col-sm-2 control-label">Nome
						Completo:</label>
					<div class="col-sm-6">
						<form:input id="nomeCompleto" path="nomeCompleto"
							cssClass="form-control" placeholder="Nome do projeto" />
					</div>
					<label for="dataNascimento" class="col-sm-2 control-label">Data
						nascimento:</label>
					<div class="col-sm-2">
						<form:input id="dataNascimento" type="text" path="dataNascimento"
							cssClass="form-control data" placeholder="Data Nascimento" />
						<div class="error-validation">
							<form:errors path="dataNascimento"></form:errors>
						</div>
						<c:if test="${not empty error_inicio}">
							<div class="error-validation">
								<span>${error_inicio}</span>
							</div>
						</c:if>
					</div>
					<label for="nomeMae" class="col-sm-2 control-label">Nome
						Mãe:</label>

					<div class="col-sm-10">
						<form:input id="nomeMae" path="nomeMae" cssClass="form-control"
							placeholder="Nome Mãe" />
					</div>


					<label for="endereco" class="col-sm-2 control-label">Endereço
						:</label>
					<div class="col-sm-6">
						<form:input id="endereco" path="endereco" cssClass="form-control"
							placeholder="Endereço" />
					</div>

					<label for="cep" class="col-sm-2 control-label">CEP :</label>
					<div class="col-sm-2">
						<form:input id="cep" path="cep" cssClass="form-control"
							placeholder="CEP" />
					</div>

					<label for="cidade" class="col-sm-2 control-label">Cidade :</label>
					<div class="col-sm-6">
						<form:input id="cidade" path="cidade" cssClass="form-control"
							placeholder="Cidade" />
					</div>

					<label for="uf" class="col-sm-2 control-label">UF :</label>
					<div class="col-sm-2">
						<form:input id="uf" path="uf" cssClass="form-control"
							placeholder="UF" />
					</div>

					<label for="telefone" class="col-sm-2 control-label">Telefone
						:</label>
					<div class="col-sm-4">
						<form:input id="telefone" path="telefone" cssClass="form-control"
							placeholder="Telefone" />
					</div>

					<label for="curso" class="col-sm-2 control-label">Curso :</label>
					<div class="col-sm-4">
						<form:input id="curso" path="curso" cssClass="form-control"
							placeholder="Curso" />
					</div>

					<label for="semestre" class="col-sm-2 control-label">Semestre
						:</label>
					<div class="col-sm-4">
						<form:input id="semestre" path="semestre" cssClass="form-control"
							placeholder="Semestre" />
					</div>

					<label for="matricula" class="col-sm-2 control-label">Matricula
						:</label>
					<div class="col-sm-4">
						<form:input id="matricula" path="matricula"
							cssClass="form-control" placeholder="Matricula" />
					</div>
					
					<div class="controls">
						<input name="submit" type="submit" class="btn btn-primary"
							value="Cadastrar" /> <a
							href="<c:url value="/estagiario/index"></c:url>"
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