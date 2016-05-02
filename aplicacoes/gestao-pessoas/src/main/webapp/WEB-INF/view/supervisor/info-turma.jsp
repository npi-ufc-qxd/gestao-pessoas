<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
						<a title="Voltar" class="btn btn-default back"><span
							class="fa fa-arrow-left"></span> Voltar</a>
						<a href="<c:url value="/supervisor/turma/${turma.id}/expediente" />"
							title="Atualizar Expediente" class="btn btn-primary"><span
							class="fa fa-refresh"></span> Expediente</a>
						<a href="<c:url value="/supervisor/turma/${turma.id}/vincular" />"
							title="Atualizar Vínculos" class="btn btn-primary"><span
							class="glyphicon glyphicon-link"></span> Vinculos</a>
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
							</strong></label>
							<label class="col-sm-3 text-view-info" >
							<fmt:formatDate value="${turma.inicio}" pattern="dd/MM/yyyy"/> a <fmt:formatDate value="${turma.termino}" pattern="dd/MM/yyyy"/>
							</label> <label class="col-sm-1 text-view-info"><strong>Status:
							</strong></label><label class="col-sm-3 text-view-info">${turma.statusTurma}</label>
						</div>
					</c:if> <br>


					<c:if test="${not empty turma.horarios}">
						<div class="form-group">
							<label class="col-sm-12 text-view-info"><strong>Horários</strong></label>

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
						<span class="fa fa-group"></span> Estagiários <span class="badge bVinculos">${fn:length(turma.estagiarios)}</span>
					</h2>

					<div class="pull-right">
						<c:if test="${not empty turma.estagiarios}">
							<a class="btn btn-success"
								href="<c:url value="/supervisor/turma/${idTurma }/mapa-frequencia" ></c:url>"
								title="Mapa de Frequência"><span
								class="fa fa-calendar-check-o"></span> Mapa de Frequência</a>
							<a class="btn btn-success"
								href="<c:url value="/supervisor/turma/${idTurma }/tce" ></c:url>"
								title="Termo de Compromisso"><span class="fa fa-file-pdf-o"></span>
								Termo de Compromisso</a>
							<a class="btn btn-success"
								href="<c:url value="/supervisor/turma/${idTurma }/declaracoes" ></c:url>"
								title="Declaração de Estágio"><span class="fa fa-file-pdf-o"></span>
								Declaração de Estágio</a>
						</c:if>

					</div>
				</div>

				<div class="panel-body">
					<c:if test="${empty turma.estagiarios}">
						<div class="alert alert-warning" role="alert">Não há
							estagiários vinculados a esta turma.</div>
					</c:if>

					<c:if test="${not empty turma.estagiarios}">
						<table id="estagiarios-turma"
							class="table table-striped table-hover">
							<thead>
								<tr>
									<th class="col-md-4">Nome</th>
									<th class="col-md-1">Matrícula</th>
									<th class="col-md-3">Curso</th>
<!-- 									<th class="col-md-3">Frequência</th> -->
									<th class="col-md-1"></th>
									<th class="col-md-1"></th>
								</tr>
							</thead>
							<tbody class="text-view-info">
								<c:forEach var="estagiario" items="${turma.estagiarios}">
									<tr>
										<td><a href="<c:url value="/supervisor/estagiario/${estagiario.id}"/>">${estagiario.nomeCompleto}</a></td>
										<td>${estagiario.matricula}</td>
										<td>${estagiario.curso.labelCurso}</td>
<!-- 										<td>Frequecias%</td> -->
										<td align="right"><a
											href="<c:url value="/supervisor/turma/${idTurma }/acompanhamento-avaliacao/estagiario/${estagiario.id}" />"
											title="Acompanhamento de Avaliação"
											class="btn btn-success btn-sm"><span class="fa fa-check"></span>Acompanhamento
												de Avaliação</a></td>
										<td align="right"><a
											href="<c:url value="/supervisor/turma/${idTurma }/estagiario/${estagiario.id}/frequencia" />"
											title="Frequências" class="btn btn-success btn-sm"><span
												class="fa fa-calendar"></span> Frequências</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
	
	<!-- Início Eventos -->
	<div class="panel panel-info">
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-calendar"></span> Eventos <span class="badge bVinculos">${fn:length(turma.eventos)}</span></h2>
			
			<div class="pull-right">
				<a href="<c:url value="/supervisor/turma/${turma.id}/evento" />" title="Atualizar Evento" class="btn btn-info"><span class="fa fa-refresh"></span> Eventos</a>
			</div>
		</div>
		
		<div class="panel-body">
		<c:if test="${empty turma.eventos}"><div class="alert alert-warning" role="alert">Sem eventos cadastrados.</div></c:if>
		<c:if test="${not empty turma.eventos}">
			<div class="form-group">
					<label class="col-sm-12 text-view-info"><strong>Calendário de Eventos</strong></label>
				
					<table id="eventos-turma" class="table table-striped table-hover">
						<thead>
							<tr>
								<th>Período</th>
								<th>Descrição</th>								
				           </tr>
				       </thead>
				       <tbody class="text-view-info">
							<c:forEach var="evento" items="${turma.eventos}" varStatus="indice">
								<tr align="justify">
									<td ><fmt:formatDate value="${evento.inicio}" pattern="dd/MM/yyyy"/> a <fmt:formatDate value="${evento.termino}" pattern="dd/MM/yyyy"/></td>
									<td>${evento.descricao }</td>
								</tr>
							</c:forEach>
				       </tbody>
			       </table>
					
				</div>
			</c:if>
		</div>
	</div>
	<!-- Término Evento -->
	
	<!-- Início ANIVERSARIANTES -->
	<div class="panel panel-info">
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-birthday-cake"></span> Aniversariantes do Mês <span class="badge bVinculos">${fn:length(aniversariantes)}</span></h2>
						
		</div>
		
		<div class="panel-body">
		<c:if test="${empty aniversariantes}"><div class="alert alert-warning" role="alert">Sem Aniversariantes esse Mês</div></c:if>
		<c:if test="${not empty aniversariantes}">
			<div class="form-group">
								
					<table id="eventos-turma" class="table table-striped table-hover">
						<thead>
							<tr>
								<th>Nome</th>
								<th></th>								
				           </tr>
				       </thead>
				       <tbody class="text-view-info">
							<c:forEach var="aniversariante" items="${aniversariantes}" varStatus="indice">
								<tr align="justify">
									<td>${aniversariante.nomeCompleto }</td>
									<td ><strong><fmt:formatDate value="${aniversariante.dataNascimento}" pattern="EEEE"/> (<fmt:formatDate value="${aniversariante.dataNascimento}" pattern="dd/MM"/> )</strong></td>
									
								</tr>
							</c:forEach>
				       </tbody>
			       </table>
					
				</div>
			</c:if>
		</div>
	</div>
	<!-- Término ANIVERSARIANTES -->
	</div>
	</div>

		<br> <br>
		<jsp:include page="../modulos/footer.jsp" />

		<script type="text/javascript">
			$(".menu #turmas").addClass("active");
			
			$('#estagiarios-turma').DataTable({
				"paging" : false,
				"bInfo" : false,
				"order" : [ 0, 'asc' ],
				"bFilter" : false,
				"columnDefs" : [ 
					{"order" : [ 0, 'asc' ], "targets" : [ 0, 'asc' ]}, 
					{"orderable" : false, "targets" : 1}, 
					{"orderable" : false, "targets" : 2},
					{"orderable" : false, "targets" : 3},
					{"orderable" : false, "targets" : 4},
// 					{"orderable" : false, "targets" : 5}
				],
			});
		</script>
</body>
</html>
