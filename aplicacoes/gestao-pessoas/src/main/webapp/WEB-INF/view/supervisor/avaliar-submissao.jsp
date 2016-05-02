<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Avaliação dos Documentos</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="row">

			<div class="panel panel-primary">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-folder-open"></span> Avaliação dos Documentos
					</h2>

					<div class="pull-right">
						<a title="Voltar" class="btn btn-default back"><span
							class="fa fa-arrow-left"></span> Voltar</a>
					</div>
				</div>

				<div class="panel-body">

					<c:if test="${empty estagiario}">
						<div class="alert alert-warning" role="alert">Estagiário
							inexistente.</div>
					</c:if>

					<c:if test="${not empty estagiario}">
						<div class="form-group">
							<label class="col-sm-2 text-view-info"><strong>Nome
									do estagiário: </strong></label><label class="col-sm-3 text-view-info">${estagiario.nomeCompleto}</label>

							<label class="col-sm-1 text-view-info"><strong>Semestre:
							</strong></label><label class="col-sm-2 text-view-info">${estagiario.semestre}</label>
							<label class="col-sm-1 text-view-info"><strong>Curso:
							</strong></label><label class="col-sm-3 text-view-info">${estagiario.curso}</label>
						</div>
					</c:if>
				</div>
			</div>

			<div class="panel panel-info">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-file-pdf-o"></span> Documentos
					</h2>
				</div>
				<div class="panel-body">

					<h4><strong>${submissao.tipo.labelTipo}</strong></h4>
					<c:if test="${empty submissao}">
						<div class="alert alert-warning" role="alert">Estagiário
							inexistente.</div>
					</c:if>
					
					<form:form id="avaliar-submissao" role="form" commandName="Submissao" servletRelativeAction=""
					  method="POST" cssClass="form-horizontal">
							<div class="form-group">
								<div class="form-item col-sm-3">
									<label class="col-sm-2 text-view-info" for="nota"><strong>
									Nota:
									</strong></label> <input
									type="number" class="form-control" id="nota">
								</div>
							
								<div class="form-item col-sm-3">
									<label for="statusEntrega" class="col-sm-2 text-view-info"><strong>Status:</strong></label> 
									<form:select path="statusEntrega" cssClass="form-control selectpicker" required="required">
										<form:options itemLabel="label"/>
									</form:select>
								</div>
							
								<div class="form-item col-sm-12">
									<label for="comentario" class="col-sm-2 text-view-info"><strong>Comentário:</strong></label>
									<form:textarea id="comentario" path="comentario" cssClass="form-control"
										placeholder="Comentário" required="required" />
								</div>
							</div>
							<div class="form-group">
								<div class="form-item col-sm-4">
									<a href="<c:url value="/supervisor/turma/${turma.id}/submissao/${submissao.id}/estagiario/${estagiario.id}/salvar-submissao-estagiario" ></c:url>"title="Salvar">
									<button class="btn btn-primary pull-center"><span class="fa fa-floppy-o"></span>  Salvar </button>
									</a>
								</div>
							</div>
						</form:form>
						<br>
					
				</div>
			</div>
			</div>

		</div>
	<br>
	<br>
	<jsp:include page="../modulos/footer.jsp" />

</body>
</html>