<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="nome" value="${sessionScope.usuario.nome}"/>
<c:set var="nomeFormatado" value="${fn:substring(nome, 0, fn:indexOf(nome, ' '))}" />

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura.jsp" />
		<title>Minha Frequência</title>
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

<div class="container">
	<div class="row">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-calendar-check-o"></span> Frêquencia: </h2>
			
			<div class="pull-right">
				<a title="Voltar" class="btn btn-primary back"><span class="fa fa-arrow-circle-o-left"></span> Voltar</a>
			</div>
		</div>

		<c:if test="${empty turmas}">
			<div class="panel-body">
				<div class="alert alert-dismissible alert-warning">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Atençao! ${nomeFormatado}</strong>
					<span>Aguarde, você sera vinculada a uma turma, desde já sinta-se parte deste grupo, NPI.</span>
				</div>
			</div>
		</c:if>
	
		<c:if test="${possuiTurma}">
			<div class="panel-body">
		
			<c:if test="${not frequenciaNaoRealizada}">
				<div class="alert alert-dismissible alert-success">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<h5><strong>Parabéns! ${nomeFormatado},</strong> você esta presente no NPI. Tenha um Ótimo dia de trabalho <i class="fa fa-smile-o fa-2x"></i></h5>
				</div>
			</c:if>
	
			<c:if test="${liberarPresenca}">
			<div class="panel panel-info">
				<div class="panel-heading">
					<form class="form-inline form-minha-presenca" action="<c:url value="/estagiario/minha-frequencia/turma/${turma.id }"></c:url>" method="POST" align="center">
						<c:if test="${not empty error}">
							<div class="form-group" align="center">
								<label class="control-label col-xs-2"></label>
								<div class="col-xs-10"><div class="form-control label label-danger"><i class="fa fa-times-circle-o"></i> ${error}</div></div>
										
							</div>
						</c:if>
					</form>
				  </div>
				</div>
			</c:if>
	
 			<div class="form-group">
 				<label class="col-sm-2 text-view-info"><strong>Dias Trabalhados: </strong></label><label class="col-sm-10 text-view-info">${dadosConsolidados.diasTrabalhados}</label>
		
 				<label class="col-sm-2 text-view-info"><strong>Frequência (%): </strong></label><label class="col-sm-10 text-view-info">${dadosConsolidados.porcentagemFrequencia}</label>
		
 				<label class="col-sm-2 text-view-info"><strong>Faltas: </strong></label><label class="col-sm-10 text-view-info">${dadosConsolidados.faltas}</label>
 			</div>
	
			<c:if test="${not empty message}"><div class="alert alert-info msg"><i class="fa fa-info-circle"> </i> ${message}</div></c:if>
	
			<table id="table-minha-frequencia" class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Data</th>
						<th>Observação</th>
						<th>Status</th>
					</tr>
		       </thead>
		       <tbody class="text-view-info">
					<c:forEach var="frequencia" items="${frequencias}">
				        <c:choose>
				            <c:when test="${frequencia.statusFrequencia == 'PRESENTE'}">
				            	<tr class="success">
				            		
									<td data-order="<fmt:formatDate value="${frequencia.data}" pattern="yyyy-MM-dd" />" ><fmt:formatDate value="${frequencia.data}" pattern="E" />, <fmt:formatDate value="${frequencia.data}" pattern="dd/MM/yyyy" /></td>
									<td>
										<c:if test="${empty frequencia.observacao}">Não há observações</c:if>
										<c:if test="${not empty frequencia.observacao}">${frequencia.observacao}</c:if>
									</td>
									<td>${frequencia.statusFrequencia}</td>
								</tr>
				            </c:when>
				            <c:otherwise>
					            <tr class="warning">
									<td data-order="<fmt:formatDate value="${frequencia.data}" pattern="yyyy-MM-dd" />" ><fmt:formatDate value="${frequencia.data}" pattern="E" timeStyle="LONG"/>, <fmt:formatDate value="${frequencia.data}" pattern="dd/MM/yyyy" /></td>
									<td>
										<c:if test="${empty frequencia.observacao}">Não há observações</c:if>
										<c:if test="${not empty frequencia.observacao}">${frequencia.observacao}</c:if>
									</td>
									<td>${frequencia.statusFrequencia}</td>
								</tr>
				            </c:otherwise>
				        </c:choose>
					</c:forEach>
		       </tbody>
			</table>
		</div>
	</c:if>
</div>
</div>
</div><br><br>

	<jsp:include page="../modulos/footer.jsp" />
	
    <script type="text/javascript">
		$("#minha-presenca").addClass("active");
		$('#table-minha-frequencia').DataTable({
			"paging": false,
			"bFilter": false, 
			"bInfo": false,
			"language": ptBR,
			"order": [0, 'asc'],
			"columnDefs": [
				{ "orderable": false, "targets": 1 },
				{ "orderable": false, "targets": 2 },
			],
		});

		$('.dataTables_length label').addClass('text-view-info');
		$('.dataTables_filter label').addClass('text-view-info');
	</script>	

</body>