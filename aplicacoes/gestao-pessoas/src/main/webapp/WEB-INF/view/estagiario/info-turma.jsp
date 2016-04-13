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

<link href="<c:url value="/resources/css/jquery-filestyle.min.css" />"
	rel="stylesheet" />

<title>Informações da Turma</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="row">
			<c:set var="showPlano" value="true"></c:set>
			<c:set var="showRelatorio" value="true"></c:set>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-folder-open"></span> Turma <strong>${turma.nome}</strong>
					</h2>

					<div class="pull-right">
						<a title="Voltar" class="btn btn-primary back"><span
							class="fa fa-arrow-circle-o-left"></span> Voltar</a>
					</div>
				</div>

				<div class="panel-body">

					<c:if test="${empty turma}">
						<div class="alert alert-warning" role="alert">Turma
							inexistente.</div>
					</c:if>

					<c:if test="${not empty turma}">
						<div class="form-group">
							<label class="col-sm-1 text-view-info"><strong>Supervisor:
							</strong></label><label class="col-sm-11 text-view-info">${turma.supervisor.nome}</label>
						</div>

						<div class="form-group">
							<label class="col-sm-1 text-view-info"><strong>Semestre:
							</strong></label><label class="col-sm-3 text-view-info">${turma.semestre}</label>

							<label class="col-sm-1 text-view-info"><strong>Periodo:
							</strong></label><label class="col-sm-3 text-view-info">de ${turma.inicio}
								a ${turma.termino}</label> <label class="col-sm-1 text-view-info"><strong>Status:
							</strong></label><label class="col-sm-3 text-view-info">${turma.statusTurma}</label>
						</div>
					</c:if>

					<c:if test="${empty turma.horarios}">
						<div class="alert alert-warning" role="alert">Expediente não
							definido.</div>
					</c:if>

					<c:if test="${not empty turma.horarios}">
						<div class="form-group">
							<table id="horarios-turma"
								class="table table-striped table-hover">
								<thead>
									<tr>
										<th>Horário</th>
										<th>Dia da Semana</th>
										<th>Início do Expediente</th>
										<th>Término do Expediente</th>
									</tr>
								</thead>
								<tbody class="text-view-info">
									<c:forEach var="horario" items="${turma.horarios}"
										varStatus="indice">
										<tr align="justify">
											<td>${indice.count}ª</td>
											<td>${horario.dia.labelDia}</td>
											<td>${horario.inicioExpediente}</td>
											<td>${horario.finalExpediente}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
					</c:if>
				</div>
			</div>

			<div class="panel panel-success">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-file-text-o"></span> Documentação
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
									<th>Tipo</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody class="text-view-info">
								<c:forEach var="submissao" items="${submissoes}">
									<c:if
										test="${submissao.tipo.labelTipo == 'Plano de Estágio' && submissao.statusEntrega.label != 'Enviado'}">
										<c:set var="showPlano" value="false"></c:set>
									</c:if>
									<c:if
										test="${submissao.tipo.labelTipo == 'Relatório Final de Estágio' && submissao.statusEntrega.label != 'Enviado'}">
										<c:set var="showRelatorio" value="false"></c:set>
									</c:if>
									<c:if test="${submissao.turma.id == turma.id}">
										<c:choose>
											<c:when test="${submissao.statusEntrega.label == 'Aceito'}">
												<tr class="success">
													<td><a href="<c:url value="/documento/ ${submissao.id }" />">${submissao.documento.nome}</a></td>
													<td><fmt:formatDate value="${submissao.data}"
															pattern="dd/MM/yyyy" /></td>
													<td>${submissao.horario}</td>
													<td>${submissao.tipo.labelTipo}</td>
													<td>${submissao.statusEntrega.label}</td>
												</tr>
											</c:when>
											<c:when
												test="${submissao.statusEntrega.label == 'Rejeitado'}">
												<tr class="danger">
													<td><a href="<c:url value="/documento/ ${submissao.id }" />">${submissao.documento.nome}</a></td>
													<td><fmt:formatDate value="${submissao.data}"
															pattern="dd/MM/yyyy" /></td>
													<td>${submissao.horario}</td>
													<td>${submissao.tipo.labelTipo}</td>
													<td>${submissao.statusEntrega.label}</td>
												</tr>
											</c:when>
											<c:otherwise>
												<tr class="warning">
													<td><a href="<c:url value="/documento/ ${submissao.id }" />">${submissao.documento.nome}</a></td>
													<td><fmt:formatDate value="${submissao.data}"
															pattern="dd/MM/yyyy" /></td>
													<td>${submissao.horario}</td>
													<td>${submissao.tipo.labelTipo}</td>
													<td>${submissao.statusEntrega.label}</td>
												</tr>
											</c:otherwise>
										</c:choose>
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
				<!-- mudar a url de submissão para a adequada para esse documento -->
				<c:if test="${showPlano}">
					<form class="form-inline"
						action="<c:url value="/estagiario/minha-documentacao/turma/${turma.id }"></c:url>"
						method="POST" enctype="multipart/form-data">
						<div class="panel-body">
							<div class="col-sm-12">
								<div class="form-group col-sm-9">
									<a href="<c:url value="/resources/documents/UFC-Quixada-Plano_Estagio-modelo.doc"/>" class="template"><button class="btn btn-primary btn-sm modelo" type="button"><span class="fa fa-arrow-circle-down"></span> Modelo de Plano de Estágio</button></a>

									<input name="anexo" type="file" multiple="multiple"
										class="form-control filestyle jfilestyle"
										data-buttonText=" Escolher arquivo" data-size="sm"
										data-buttonName="btn-primary" id="plano"
										data-placeholder="Plano de Estágio" data-inputSize="423px"
										accept="application/pdf"> <input id="tipo"
										type="hidden" name="tipo" value=PLANO_ESTAGIO>
								</div>
								<div class="form-group col-sm-3">
									<button class="btn btn-primary btn-sm submeter" type="submit">
										<span class="fa fa-arrow-circle-up"> Submeter Plano de
											Estágio</span>
									</button>
								</div>
							</div>
						</div>
					</form>
				</c:if>
				<!-- mudar a url de submissão para a adequada para esse documento -->
				<c:if test="${showRelatorio}">
					<form id="fileForm" class="form-inline"
						action="<c:url value="/estagiario/minha-documentacao/turma/${turma.id }"></c:url>"
						method="POST" enctype="multipart/form-data">
						<div class="panel-body">
							<div class="col-sm-12">
								<div class="form-group col-sm-9">
									<a href="<c:url value="/resources/documents/UFC-Quixada-Relatorio_Final_Estagio-modelo.doc" />" class="template"><button class="btn btn-primary btn-sm modelo" type="button"><span class="fa fa-arrow-circle-down"></span> Modelo de Relatório Final de Estágio</button></a>									

									<input name="anexo" type="file" multiple="multiple"
										class="form-control filestyle jfilestyle"
										data-buttonText=" Escolher arquivo" data-size="sm"
										data-buttonName="btn-primary" id="relatorio"
										data-placeholder="Relatório Final de Estágio"
										data-inputSize="423px" accept="application/pdf"> <input
										id="tipo" type="hidden" name="tipo"
										value=RELATORIO_FINAL_ESTAGIO>

								</div>
								<div class="form-group col-sm-3">
									<button class="btn btn-primary btn-sm submeter" type="submit">
										<span class="fa fa-arrow-circle-up"> Submeter Relatório
											Final de Estágio</span>
									</button>
								</div>
							</div>
						</div>
					</form>
				</c:if>
				<br>
			</div>
		</div>
	</div>
	<jsp:include page="../modulos/footer.jsp" />

	<script src="<c:url value="/resources/js/jquery-filestyle.min.js" />"></script>

	<script type="text/javascript">
		$('#table-submissao').DataTable({
			"ordering" : false,
			"paging" : false,
			"info" : false,
			"language" : ptBR,
			"bInfo" : false,
			"bFilter" : false,
		});
	</script>
</body>
</html>