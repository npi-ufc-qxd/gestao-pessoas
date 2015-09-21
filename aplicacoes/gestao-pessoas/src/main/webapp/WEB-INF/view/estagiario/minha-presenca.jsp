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

		    <div class="btn-group">
				 <c:if test="${empty turma}">
				 	<button type="button" data-toggle="dropdown" class="btn btn-default dropdown-toggle col-sm-12">Selecione a Turma&nbsp;&nbsp;<span class="caret"></span></button>
				 </c:if>
				 <c:if test="${not empty turma}">
				 	<button type="button" data-toggle="dropdown" class="btn btn-default dropdown-toggle col-sm-12">${turma.ano}.${turma.semestre} - ${turma.nome}&nbsp;&nbsp;<span class="caret"></span></button>
				 </c:if>
		         
		         <ul class="dropdown-menu">
	                <li><a href="<c:url value="/estagiario/minha-frequencia" />">Selecione a Turma&nbsp;&nbsp;</a></li>
		         	<c:forEach var="turma" items="${turmas}">
		              <li><a href="<c:url value="/estagiario/minha-frequencia/turma/${turma.id}" />">${turma.ano}.${turma.semestre} - ${turma.nome}</a></li>
					</c:forEach>
		         </ul>
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
						<fieldset class="form-group">
							<label class="panel-title"><strong>Minha Presença</strong></label>
						</fieldset>
						<div class="form-group">
							<label for="cpf" class="sr-only">CPF</label>
							<input type="text" class="form-control" id="cpf" name="cpf" placeholder="CPF" value="${estagiario.pessoa.cpf}" readonly="readonly">
						</div>
						<div class="form-group">
							<label for="senha" class="sr-only">Senha</label>
							<input type="password" class="form-control" id="senha" name="senha" placeholder="Senha" autofocus="autofocus">
						</div>
						<button type="submit" class="btn btn-success">Estou Presente !!!</button>
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
				            <c:when test="${frequencia.statusFrequencia != 'AGUARDO'}">
				            	<tr class="success">
				            		
									<td data-order="<fmt:formatDate value="${frequencia.data}" pattern="yyyy-MM-dd" />" ><fmt:formatDate value="${frequencia.data}" pattern="dd/MM/yyyy" /></td>
									<td>${frequencia.observacao}</td>
									<td>${frequencia.statusFrequencia}</td>
								</tr>
				            </c:when>
				            <c:otherwise>
					            <tr class="warning">
									<td data-order="<fmt:formatDate value="${frequencia.data}" pattern="yyyy-MM-dd" />" ><strong><fmt:formatDate value="${frequencia.data}" pattern="dd/MM/yyyy" /></strong></td>
									<td colspan="2">Aguardando data para lançamento da frequência.</td>
									<td style="display: none;"></td>
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