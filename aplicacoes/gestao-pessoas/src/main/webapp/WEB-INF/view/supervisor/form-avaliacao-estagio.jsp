<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${action eq 'cadastrar' }">
	<c:set var="url" value="/supervisor/turma/${turma.id}/acompanhamento-avaliacao/estagiario/${estagiario.id}/adicionar/"></c:set>
	<c:set var="titulo" value="Nova Avaliação"></c:set>
</c:if>
<c:if test="${action eq 'editar' }">
	<c:set var="url"
		value="/supervisor/turma/${turma.id}/avaliacao/${avaliacaoEstagio.id}/estagiario/${estagiario.id}/editar"></c:set>
	<c:set var="titulo" value="Editar Avaliação"></c:set>
</c:if>

<html>
<head>
<title>${titulo }</title>
<jsp:include page="../modulos/header-estrutura.jsp" />
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="row">

			<div class="panel panel-primary">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-folder-open"></span> ${titulo}
					</h2>

					<div class="pull-right">
						<a title="Voltar" class="btn btn-primary back"><span
							class="fa fa-arrow-circle-o-left"></span> Voltar</a>
					</div>
				</div>

				<form:form id="form-avaliacao-estagio" role="form"
					commandName="avaliacaoEstagio" servletRelativeAction="${url}"
					method="POST" cssClass="form-horizontal">
					<div class="panel-body">
						<form:hidden path="id" />

						<div class="form-group">
							<div class="form-item col-sm-12">
								<label for="assiduidadeDisciplina" class="control-label">*Assiduidade
									e Disciplina:</label>
								<form:textarea id="assiduidadeDisciplina"
									path="fatorAssiduidadeDisciplina" cssClass="form-control"
									placeholder="Comentário" required="required" />
								<div class="error-validation">
									<form:errors path="fatorAssiduidadeDisciplina"></form:errors>
								</div>
							</div>
						</div>


						<div class="form-group">
							<div class="form-item col-sm-12">
								<label for="iniciativaProdutividade" class="control-label">*Iniciativa
									e Produtividade:</label>
								<form:textarea id="iniciativaProdutividade"
									path="fatorIniciativaProdutividade" cssClass="form-control"
									placeholder="Comentário" required="required" />
								<div class="error-validation">
									<form:errors path="fatorIniciativaProdutividade"></form:errors>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="form-item col-sm-12">
								<label for="responsabilidade" class="control-label">*Responsabilidade:</label>
								<form:textarea id="responsabilidade"
									path="fatorResponsabilidade" cssClass="form-control"
									placeholder="Comentário" required="required" />
								<div class="error-validation">
									<form:errors path="fatorResponsabilidade"></form:errors>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="form-item col-sm-12">
								<label for="relacionamento" class="control-label">*Relacionamento:</label>
								<form:textarea id="relacionamento" path="fatorRelacionamento"
									cssClass="form-control" placeholder="Comentário"
									required="required" />
								<div class="error-validation">
									<form:errors path="fatorRelacionamento"></form:errors>
								</div>
							</div>
						</div>
						<div class="form-item col-sm-3">
						<label for="nota" class="control-label">*Nota:</label>
						<form:input path="nota" cssClass="form-control"></form:input>
						<div class="error-validation"><form:errors path="nota"></form:errors></div>
					</div>

					</div>
					<div class="panel-footer" align="center">
						<div class="controls">
							<c:if test="${action eq 'cadastrar' }">
								<button type="submit" class="btn btn-primary" title="Cadastrar">
									<span class="fa fa-floppy-o"></span> Cadastrar
								</button>
							</c:if>
							<c:if test="${action eq 'editar' }">
								<button type="submit" class="btn btn-primary"
									title="Salvar alterações">
									<span class="fa fa-floppy-o"></span> Salvar alterações
								</button>
							</c:if>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />

</body>
</html>
