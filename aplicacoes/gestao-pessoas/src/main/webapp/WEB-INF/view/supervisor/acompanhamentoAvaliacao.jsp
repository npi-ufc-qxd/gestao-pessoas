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
<link href="<c:url value="/resources/css/jquery-filestyle.min.css" />" rel="stylesheet" />
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
						<div class="alert alert-warning" role="alert">Nenhum
							documento submetido.</div>
					</c:if>

					<c:if test="${not empty submissoes}">
						<table id="table-submissao"
							class="table table-striped table-hover">
							<thead>
								<tr>
									<th>Nome</th>
									<th>Data</th>
									<th>Hora</th>
								</tr>
							</thead>
							<tbody class="text-view-info">
								<c:forEach var="submissao" items="${submissoes}">
									<c:if test="${submissao.turma.id == turma.id}">
										<tr class="success">
											<td><a href="<c:url value="/documento/ ${submissao.id }" />">${submissao.documento.nome}</a></td>
											<td><fmt:formatDate value="${submissao.data}"
													pattern="dd/MM/yyyy" /></td>
											<td>${submissao.horario}</td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>

				<div class="panel-body">
					<c:if test="${not empty error}">
						<div class="alert alert-dismissible alert-danger" role="alert">
							<button type="button" class="close" data-dismiss="alert">×</button>
							<p>
								<strong>Atenção!</strong> ${error }
							</p>
						</div>
					</c:if>
				</div>
				
				<div class="panel-body">
					<form class="form-inline" method="POST" enctype="multipart/form-data" action="<c:url value="/estagiario/minha-documentacao/turma/${turma.id }/estagiario/${estagiario.id }"></c:url>" >
					  <div class="form-group col-sm-12">
						  <div class="form-group">
						   <input type="text" class="form-control" id="nota" placeholder="Nota">
						  </div>
						  <div class="form-group">
						    <input type="text" class="form-control" id="comentario" placeholder="Comentario">
						  </div>
						  <div class="form-group">
						  	<input name="anexo" type="file" multiple="multiple" id="avaliacao" class="form-control">
						  </div>
						  <button type="submit" class="btn btn-default">Submeter Avaliação</button>
					  </div>
					</form>
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
					</c:if> -
					<br>
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
	<script src="<c:url value="/resources/js/jquery-filestyle.min.js" />"></script>

</body>
</html>