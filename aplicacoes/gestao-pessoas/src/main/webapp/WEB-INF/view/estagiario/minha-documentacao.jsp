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
<title>Minha Documentação</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="row">

			<div class="panel panel-primary">
				<c:if test="${empty turmas}">
					<div class="panel-heading">
						<h2 class="titulo-panels">
							<span class="fa fa-file-text-o"></span> Documentação
						</h2>

						<c:if test="${counter.index == 0}">
							<div class="pull-right">
								<a title="Voltar" class="btn btn-primary back"><span
									class="fa fa-arrow-circle-o-left"></span> Voltar</a>
							</div>
						</c:if>
					</div>

					<div class="panel-body">
						<div class="alert alert-dismissible alert-warning">
							<button type="button" class="close" data-dismiss="alert">×</button>
							<strong>Atençao! ${nomeFormatado}</strong> <span>Aguarde,
								você sera vinculada a uma turma, desde já sinta-se parte deste
								grupo, NPI.</span>
						</div>
					</div>
				</c:if>

				<c:if test="${not empty turmas}">

					<c:forEach var="turma" items="${turmas}" varStatus="counter">
						<div class="panel-heading">
							<h2 class="titulo-panels">
								<span class="fa fa-file-text-o"></span> Documentação:
							</h2>

							<c:if test="${counter.index == 0}">
								<div class="pull-right">
									<a title="Voltar" class="btn btn-primary back"><span
										class="fa fa-arrow-circle-o-left"></span> Voltar</a>
								</div>
							</c:if>

							<h2 class="titulo-panels">${turma.nome}-
								${turma.ano}.${turma.semestre}</h2>
						</div>

						<div class="panel-body">
							<div class="form-group">Calendário e templates</div>
						</div>

						<div class="panel-body">
							<div class="panel panel-info">
								<div class="panel-heading">
									<h2 class="titulo-panels">
										<span class="fa fa-file-text-o"></span> Plano de Estágio
									</h2>
								</div>
							</div>

							<c:if test="${not liberarSubmissao}">
								<div class="alert alert-dismissible alert-warning">
									<button type="button" class="close" data-dismiss="alert">×</button>
									<strong>Atençao! ${nomeFormatado}</strong> <span>Aguarde o prazo para submissão.</span>
								</div>
							</c:if>

							<c:if test="${liberarSubmissao}">
								<div class="panel panel-info">
									<div class="panel-heading">


										<form class="form-inline form-minha-documentacao"
											action="<c:url value="/estagiario/minha-documentacao/turma/${turma.id }"></c:url>"
											method="POST" align="center">
											<c:if test="${not empty error}">
												<div class="form-group" align="center">
													<label class="control-label col-xs-2"></label>
													<div class="col-xs-10">
														<div class="form-control label label-danger">
															<i class="fa fa-times-circle-o"></i> ${error}
														</div>
													</div>
												</div>
											</c:if>
											<div class="form-group form-item">
												<label for="anexo" class="col-sm-2 control-label">Anexos:</label>
												<div class="col-sm-10">
													<input id="anexo" type="file" name="anexo"
														class="anexo file-loading" multiple="multiple"></input>
												</div>
											</div>

											<div class="controls">
												<input name="salvar" type="submit" class="btn btn-primary"
													value="Salvar" />
											</div>
										</form>
									</div>
								</div>
							</c:if>
						</div>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
	<br>
	<br>

	<jsp:include page="../modulos/footer.jsp" />

	<script type="text/javascript">
		$("#minha-documentacao").addClass("active");
		$('#table-minha-frequencia').DataTable({
			"paging" : false,
			"bFilter" : false,
			"bInfo" : false,
			"language" : ptBR,
			"order" : [ 0, 'asc' ],
			"columnDefs" : [ {
				"orderable" : false,
				"targets" : 1
			}, {
				"orderable" : false,
				"targets" : 2
			}, ],
		});

		$('.dataTables_length label').addClass('text-view-info');
		$('.dataTables_filter label').addClass('text-view-info');
	</script>

</body>