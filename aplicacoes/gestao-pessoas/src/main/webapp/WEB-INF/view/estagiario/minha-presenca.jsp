<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<title>Minha Presença</title>
<jsp:include page="../modulos/header-estrutura.jsp" />
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container" align="center">
		<c:if test="${not empty message}">
			<div class="alert alert-info msg">
				<i class="fa fa-info-circle"> </i> ${message}
			</div>
		</c:if>
		<c:if test="${liberarPresenca}">
			<div id="boxPresenca" class="panel col-sm-4" align="center">
				<div class="panel-heading">
					<h3 class="panel-title">Minha Presença</h3>
				</div>
				<form class="form-horizontal panel-body"
					action="/gestao-pessoas/frequencia/minha-presenca" method="POST">
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
					<div class="form-group">
						<label for="cpf" class="control-label col-xs-2">CPF</label>
						<div class="col-xs-10">
							<input type="text" class="form-control cpfNumeros" id="cpf"
								name="cpf" placeholder="CPF" value="${estagiario.pessoa.cpf}">
						</div>
					</div>
					<div class="form-group">
						<label for="senha" class="control-label col-xs-2">Senha</label>
						<div class="col-xs-10">
							<input type="password" class="form-control" id="senha"
								name="senha" placeholder="Senha" autofocus="autofocus">
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<input type="submit" class="form-control btn btn-primary"
								id="estouPresenete" name="estouPresenete"
								value="Estou Presente!!!">
						</div>
					</div>
				</form>
			</div>
		</c:if>
	</div>
	<div class="container" align="center">
		<div>
			<label>Data: ${frequenciaHoje.data}</label> <label>Hora:
				${frequenciaHoje.tempo}</label> <label>Faltas:
				${dadosConsolidados.faltas}</label> <label>Dias Trabalhados:
				${dadosConsolidados.diasTrabalhados}</label> <label>Frequência (%):
				${dadosConsolidados.porcentagemFrequencia}</label>
		</div>

		<!-- Inicio Historico Frequencia -->
		<table id="frequencias" class="table table-bordered table-condensed">
			<thead>
				<tr>
					<td data-column-id="1" data-order="asc" data-type="">Data</td>
					<td data-column-id="2" data-formatter="observacao" data-type="">Observação</td>
					<td data-column-id="3" data-order="asc" data-formatter="status"
						data-type="">Status</td>
				</tr>

				<c:forEach items="${frequencia}" var="frequencia">
					<tr>
						<td data-column-id="1" data-order="asc" data-type="">${frequencia.data}</td>
						<td data-column-id="2" data-formatter="observacao" data-type="">${frequencia.observacao}</td>
						<td data-column-id="3" data-order="asc" data-formatter="status"
							data-type="">${frequencia.statusFrequencia.label}</td>
					</tr>
				</c:forEach>

			</thead>
		</table>
		<!-- Termino Historico Frequencia -->
	</div>
	<jsp:include page="../modulos/footer.jsp" />
	<script src="<c:url value="/resources/js/frequencia.js" />"></script>

</body>