<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${action eq 'cadastrar' }">
	<c:set var="url"
		value="/supervisor/turma/${turma.id}/acompanhamento-avaliacao/estagiario/${estagiario.id}/"></c:set>
	<c:set var="titulo" value="Nova Avaliação"></c:set>
</c:if>

<!DOCTYPE html>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Acompanhamento de Avaliação</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="row">

			<div class="panel panel-primary">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-folder-open"></span> Acompanhamento de
						Avaliação
					</h2>

					<div class="pull-right">
						<a title="Voltar" class="btn btn-primary back"><span
							class="fa fa-arrow-circle-o-left"></span> Voltar</a>
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
					<c:if test="${empty submissoes}">
						<div class="alert alert-warning" role="alert">Submissão
							inexistente.</div>
					</c:if>
					<c:if test="${not empty submissoes}">
						<c:forEach var="submissoes" items="${submissoes}">
							<div class="pull-right">
								<button class="btn btn-primary" data-toggle="modal"
									data-target="#lancarNotas">Lançar Notas</button>
							</div>
							<div class="form-group">
								<label class="col-sm-1 text-view-info"><strong>Tipo
								</strong></label> <label class="col-sm-3 text-view-info">${submissoes.tipo}</label>
								<label class="col-sm-1 text-view-info"><strong>Nota
								</strong></label> <label class="col-sm-1 text-view-info">${submissoes.nota}</label>
								<label class="col-sm-1 text-view-info"><strong>Status
								</strong></label> <label class="col-sm-2 text-view-info">${submissoes.statusEntrega}</label>
								<br> <br>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-pencil"></span> Avaliação de estágio
					</h2>
					<c:if test="${empty avaliacaoEstagio}">
						<div class="pull-right">
							<a
								href="<c:url value="/supervisor/turma/${turma.id}/acompanhamento-avaliacao/estagiario/${estagiario.id}/adicionar/" ></c:url>"
								title="Nova Avaliacao"><button class="btn btn-primary">
									<span class="fa fa-plus"></span> Avaliação
								</button></a>
						</div>
					</c:if>
					<c:if test="${not empty avaliacaoEstagio}">
						<c:forEach var="avaliacaoEstagio" items="${avaliacaoEstagio}">
							<div class="pull-right">
								<a
									href="<c:url value="/supervisor/turma/${turma.id}/avaliacao/${avaliacaoEstagio.id}/estagiario/${estagiario.id}/editar" ></c:url>"
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

					<c:if test="${empty avaliacaoEstagio}">
						<div class="alert alert-warning" role="alert">Não há
							Avaliação cadastrada.</div>
					</c:if>

					<c:if test="${not empty avaliacaoEstagio}">
						<c:forEach var="avaliacaoEstagio" items="${avaliacaoEstagio}">
							<div class="form-group">
								<label class="col-sm-3 text-view-info"><strong>Nota
										geral do estágio: </strong></label><label class="col-sm-3 text-view-info">${avaliacaoEstagio.nota}</label>

								<label class="col-sm-12 text-view-info"><strong>Assiduidade
										e Disciplina </strong></label><label class="col-sm-12 text-view-info">${avaliacaoEstagio.fatorAssiduidadeDisciplina}</label>
								<label class="col-sm-12 text-view-info"><strong>Iniciativa
										e Produtividade </strong></label><label class="col-sm-12 text-view-info">${avaliacaoEstagio.fatorIniciativaProdutividade}</label>
								<label class="col-sm-12 text-view-info"><strong>Responsabilidade
								</strong></label><label class="col-sm-12 text-view-info">${avaliacaoEstagio.fatorResponsabilidade}</label>
								<label class="col-sm-12 text-view-info"><strong>Relacionamento
								</strong></label><label class="col-sm-12 text-view-info">${avaliacaoEstagio.fatorRelacionamento}</label>
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
	<div>
		<div class="modal fade" id="lancarNotas" tabindex="-1" role="dialog"
			aria-labelledby="lancarNotasLabel" data-backdrop="false">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="lancarNotasLabel">Lançar Notas</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label class="col-sm-2 control-label" for="inputEmail3">Nota</label>
								<div class="col-sm-10">
									<input type="email" class="form-control" id="inputEmail3"
										placeholder="Email" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label" for="inputPassword3">Status</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" id="inputPassword3"
										placeholder="Password" />
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button type="button" class="btn btn-primary">Salvar</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>