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

<title>Informações da Turma</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="row">

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
							</strong></label><label class="col-sm-3 text-view-info">${turma.ano}.${turma.semestre}</label>

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

				<div class="panel panel-primary">
					<div class="panel-heading">
						<h2 class="titulo-panels">
							<span class="fa fa-calendar"></span> Calendário
						</h2>
					</div>

					<div class="panel-body">

						<div class="form-group">
							<table id="eventos-turma" class="table table-striped table-hover">
								<thead>
									<tr>
										<th>Descrição</th>
										<th>Início</th>
										<th>Final</th>
									</tr>
								</thead>
								<tbody class="text-view-info">
									<tr align="justify">
										<td>Evento Exemplo</td>
										<td>dd/mm/dddd</td>
										<td>dd/mm/dddd</td>
									</tr>
								</tbody>
							</table>

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
								<table class="table table-striped table-hover">
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
											<c:choose>
												<c:when test="${submissao.statusEntrega.label == 'Aceito'}">
													<tr class="success">
														<td>${submissao.nome}</td>
														<td><fmt:formatDate value="${documento.data}"
																pattern="dd/MM/yyyy" /></td>
														<td>${submissao.horario}</td>
														<td>${submissao.tipo.labelTipo}</td>
														<td>${submissao.statusEntrega.label}</td>
													</tr>
												</c:when>
												<c:when
													test="${submissao.statusEntrega.label == 'Rejeitado'}">
													<tr class="danger">
														<td>${submissao.nome}</td>
														<td><fmt:formatDate value="${submissao.data}"
																pattern="dd/MM/yyyy" /></td>
														<td>${submissao.horario}</td>
														<td>${submissao.tipo.labelTipo}</td>
														<td>${submissao.statusEntrega.label}</td>
													</tr>
												</c:when>
												<c:otherwise>
													<tr class="warning">
														<td>${submissao.nome}</td>
														<td><fmt:formatDate value="${submissao.data}"
																pattern="dd/MM/yyyy" /></td>
														<td>${submissao.horario}</td>
														<td>${submissao.tipo.labelTipo}</td>
														<td>${submissao.statusEntrega.label}</td>
													</tr>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</tbody>
								</table>
							</c:if>
						</div>

						<form class="form-inline"
							action="<c:url value="/estagiario/minha-documentacao/turma/${turma.id }"></c:url>"
							method="POST" enctype="multipart/form-data">
							<div class="panel-body">
								<div class="form-group col-sm-8">
									<label for="plano">Plano de Estágio:</label> <input type="file"
										multiple="multiple" class="form-control filestyle" id="plano"
										data-buttonName="btn-primary" data-size="sm"> <input
										id="tipo" type="hidden" name="tipo"
										value=RELATORIO_FINAL_ESTAGIO>

									<button type="submit" class="btn btn-primary" value="Salvar">Submeter</button>
								</div>
								<div class="form-group col-sm-4">
									<button type="button" class="btn btn-link">Template
										Plano de Estágio</button>
								</div>

							</div>
						</form>

						<br>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<jsp:include page="../modulos/footer.jsp" />


	<script
		src="<c:url value="/webjars/bootstrap-filestyle/1.1.2/bootstrap-filestyle.js" />"></script>
	<script type="text/javascript">
		$(".menu #turmas").addClass("active");
	</script>
</body>
</html>