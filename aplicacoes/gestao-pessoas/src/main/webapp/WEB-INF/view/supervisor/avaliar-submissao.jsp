<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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

					<h4>Plano</h4>
					<c:if test="${empty submissoes}">
						<div class="alert alert-warning" role="alert">Estagiário
							inexistente.</div>
					</c:if>
					<c:forEach var="submissoes" items="${submissoes}">
						<form class="form-inline" role="form">
							<div class="form-group">
								<label for="nota" class="control-label">Nota:</label> <input
									type="number" class="form-control" id="nota">
							</div>
							<div class="form-group">
								<label for="status">Status:</label> <select class="form-control"
									id="status">
									<option>ENVIADO</option>
									<option>ACEITO</option>
									<option>REJEITADO</option>
								</select>
							</div>
							<div class="form-group">
								<label class="col-sm-3 text-view-info"><strong>Tipo:
								</strong></label> <label class="col-sm-3 text-view-info">${submissoes.tipo}</label>

							</div>
							<a
								href="<c:url value="/supervisor/turma/${turma.id}/submissao/${submissao.id}/estagiario/${estagiario.id}/avaliar-submissao" ></c:url>"
								title="Editar"><button class="btn btn-primary">
									<span class="fa fa-pencil"></span> Avaliar
								</button></a>
						</form>
						<br>
					</c:forEach>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-pencil"></span> Avaliação de estágio
					</h2>
					<c:if test="${empty avaliacaoRendimento}">
						<div class="pull-right">
							<a
								href="<c:url value="/supervisor/turma/${turma.id}/acompanhamento-avaliacao/estagiario/${estagiario.id}/adicionar/" ></c:url>"
								title="Nova Avaliacao"><button class="btn btn-primary">
									<span class="fa fa-plus"></span> Avaliação
								</button></a>
						</div>
					</c:if>
					<c:if test="${not empty avaliacaoRendimento}">
						<c:forEach var="avaliacaoRendimento" items="${avaliacaoEstagio}">
							<div class="pull-right">
								<a
									href="<c:url value="/supervisor/turma/${turma.id}/avaliacao/${avaliacaoRendimento.id}/estagiario/${estagiario.id}/editar" ></c:url>"
									title="Editar"><button class="btn btn-success">
										<span class="fa fa-pencil"></span> Editar avaliação
									</button></a>
							</div>
						</c:forEach>
					</c:if>
					- <br>
				</div>
				<div class="panel-body">

					<c:if test="${not empty success }">
						<div class="alert alert-dismissible alert-success">
							<button type="button" class="close" data-dismiss="alert">×</button>${success}</div>
					</c:if>

					<c:if test="${empty avaliacaoRendimento}">
						<div class="alert alert-warning" role="alert">Não há
							Avaliação cadastrada.</div>
					</c:if>

					<c:if test="${not empty avaliacaoRendimento}">
						<c:forEach var="avaliacaoRendimento" items="${avaliacaoRendimento}">
							<div class="form-group">
								<label class="col-sm-3 text-view-info"><strong>Nota
										geral do estágio: </strong></label><label class="col-sm-3 text-view-info">${avaliacaoRendimento.nota}</label>

								<label class="col-sm-12 text-view-info"><strong>Assiduidade
										e Disciplina </strong></label><label class="col-sm-12 text-view-info">${avaliacaoRendimento.fatorAssiduidadeDisciplina}</label>
								<label class="col-sm-12 text-view-info"><strong>Iniciativa
										e Produtividade </strong></label><label class="col-sm-12 text-view-info">${avaliacaoRendimento.fatorIniciativaProdutividade}</label>
								<label class="col-sm-12 text-view-info"><strong>Responsabilidade
								</strong></label><label class="col-sm-12 text-view-info">${avaliacaoRendimento.fatorResponsabilidade}</label>
								<label class="col-sm-12 text-view-info"><strong>Relacionamento
								</strong></label><label class="col-sm-12 text-view-info">${avaliacaoRendimento.fatorRelacionamento}</label>


							</div>
						</c:forEach>
					</c:if>

				</div>
			</div>

		</div>
	</div>
	<br>
	<br>
	<jsp:include page="../modulos/footer.jsp" />

</body>
</html>