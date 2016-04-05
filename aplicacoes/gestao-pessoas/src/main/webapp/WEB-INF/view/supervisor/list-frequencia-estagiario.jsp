<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="nome" value="${sessionScope.usuario.nome}" />
<c:set var="nomeFormatado"
	value="${fn:substring(nome, 0, fn:indexOf(nome, ' '))}" />

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Frequencia Estagiario</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="row">

			<div class="panel panel-primary">

				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-calendar"></span> Frequência:
						${estagiario.nomeCompleto}
					</h2>

					<div class="pull-right">
						<a title="Voltar" class="btn btn-default back"><span
							class="fa fa-arrow-left"></span> Voltar</a>
					</div>
				</div>

				<div class="panel-body">
					<c:if test="${not empty sucesso}">
						<div class="alert alert-success msg">
							<i class="fa fa-info-circle"> </i> ${sucesso}
						</div>
					</c:if>

					<c:if test="${not empty error}">
						<div class="alert alert-warning msg">
							<i class="fa fa-info-circle"> </i> ${error}
						</div>
					</c:if>

					<br>

					<div class="form-group">
						<label class="col-sm-2 text-view-info"><strong>Turma:
						</strong></label><label class="col-sm-2 text-view-info">${turma.nome}</label> <label
							class="col-sm-2 text-view-info"><strong>Periodo:
						</strong></label><label class="col-sm-3 text-view-info">de <fmt:formatDate
								value="${turma.inicio}" pattern="dd/MM/yyyy" /> a <fmt:formatDate
								value="${turma.termino}" pattern="dd/MM/yyyy" /></label> <label
							class="col-sm-3"></label>
					</div>
					<br>

					<div class="form-group">
						<label class="col-sm-2 text-view-info"><strong>Dias
								Trabalhados: </strong></label><label class="col-sm-2 text-view-info">${dadosConsolidados.diasTrabalhados}</label>

						<label class="col-sm-2 text-view-info"><strong>Frequência
								(%): </strong></label><label class="col-sm-2 text-view-info">${dadosConsolidados.porcentagemFrequencia}</label>

						<label class="col-sm-2 text-view-info"><strong>Faltas:
						</strong></label><label class="col-sm-2 text-view-info">${dadosConsolidados.faltas}</label>

					</div>
					<br>
					<br>

					<div class="col-sm-12">
						<h3>Frequências Pendentes</h3>
						<c:forEach var="frequencia" items="${frequencias}">
							<fmt:formatDate var="dataFrequencia" pattern="yyyy-MM-dd"
								value="${frequencia.data}" />
							<fmt:formatDate var="today" value="${dataAtual}"
								pattern="yyyy-MM-dd" />
								<c:if test="${frequencia.statusFrequencia == null}">
							<div class="panel-body" style="padding: 0px">
								<div class="form-group">
									<form class="formFrequencia" id="formFrequencia"
										action="<c:url value="/supervisor/estagiario/${estagiario.id}/turma/${turma.id}/frequencia/pendente"></c:url>"
										method="POST" class="form-inline">
										<div class="form-group">
											<input type="hidden" name="data" class="form-control"
												value="<fmt:formatDate value="${frequencia.data}" pattern="MM/dd/yyyy" />">
										</div>
										
										<c:if
											test="${frequencia.statusFrequencia == null and dataFrequencia < today}">
											<div class="form-group col-sm-3">
												<strong>&nbsp;<span class="label label-warning">Pendente</span>&nbsp;&nbsp;<fmt:formatDate
														value="${frequencia.data}" pattern="E" />, <fmt:formatDate
														value="${frequencia.data}" pattern="dd/MM/yyyy" /></strong>
											</div>
										</c:if>
										<c:if
											test="${frequencia.statusFrequencia == null and dataFrequencia == today}">
											<div class="form-group col-sm-3">
												<strong><span class="label label-primary">Hoje</span>&nbsp;&nbsp;<fmt:formatDate
														value="${frequencia.data}" pattern="EEEE" />, <fmt:formatDate
														value="${frequencia.data}" pattern="dd/MM/yyyy" /></strong>
											</div>
										</c:if>
										<div class="form-group col-sm-3">
											<label class="sr-only">Observação</label>
											<textarea id="elasticText" class="form-control" rows="1"
												cols="20" name="observacao" placeholder="Observação"></textarea>
										</div>

										<div class="form-group col-sm-3">
											<label class="sr-only" for="statusFrequencia">Status</label> 
											<select
												class="form-control" name="statusFrequencia"
												id="statusFrequencia">
												<option value="">Selecionar Status</option>
												<c:forEach var="status" items="${statusFrequencias}">
													<option value="${status}">${status}</option>
												</c:forEach>
											</select>
										</div>
										<c:if
											test="${frequencia.statusFrequencia == null and dataFrequencia < today}">
											<button type="submit" class="btn btn-warning">
												<i class="fa fa-floppy-o"></i> Lançar
											</button>
										</c:if>
										<c:if
											test="${frequencia.statusFrequencia == null and dataFrequencia == today}">
											<button type="submit" class="btn btn-primary">
												<i class="fa fa-floppy-o"></i> Lançar
											</button>
										</c:if>
										
									</form>
								</div>
							</div>
							</c:if>
						</c:forEach>
					</div>


					<div class="col-sm-12">
						<h3>Frequências</h3>
						<table id="table-frequencias"
							class="table table-striped table-hover">
							<thead>
								<tr>
									<th>Data/Horário</th>
									<th>Observação</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody class="text-view-info">
								<fmt:formatDate var="dataAtual" pattern="yyyy-MM-dd"
									value="${dataAtual}" />
								<c:forEach var="frequencia" items="${frequencias}">
									<c:if test="${frequencia.statusFrequencia == 'PRESENTE' }">
										<tr class="success">
											<td><strong><fmt:formatDate
														value="${frequencia.data}" pattern="EEEE" />, <fmt:formatDate
														value="${frequencia.data}" pattern="dd/MM/yyyy" /> <fmt:formatDate
														type="time" timeStyle="short"
														value="${frequencia.horario}" /></strong></td>
											<td><a href="#" class="observacaoFrequencia"
												title="Realizar observação" data-pk="${frequencia.id}">${frequencia.observacao}</a></td>
											<td><a href="#" class="statusFrequencia"
												title="Atualizar status" data-pk="${frequencia.id}">${frequencia.statusFrequencia}</a></td>
										</tr>
									</c:if>

									<c:if
										test="${frequencia.statusFrequencia != null and frequencia.statusFrequencia != 'PRESENTE'}">
										<tr class="warning">
											<td><strong><fmt:formatDate
														value="${frequencia.data}" pattern="EEEE" />, <fmt:formatDate
														value="${frequencia.data}" pattern="dd/MM/yyyy" /> <fmt:formatDate
														type="time" timeStyle="short"
														value="${frequencia.horario}" /></strong></td>
											<td><a href="#" class="observacaoFrequencia"
												title="Realizar observação" data-pk="${frequencia.id}">${frequencia.observacao}</a></td>
											<td><a href="#" class="statusFrequencia"
												title="Atualizar status" data-pk="${frequencia.id}">${frequencia.statusFrequencia}</a></td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>


				</div>
			</div>
		</div>
	</div>
	<br>
	<br>

	<jsp:include page="../modulos/footer.jsp" />

	<script type="text/javascript">
		$(".menu #turmas").addClass("active");
		
	</script>

</body>