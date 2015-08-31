<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="nome" value="${sessionScope.usuario.nome}"/>
<c:set var="nomeFormatado" value="${fn:substring(nome, 0, fn:indexOf(nome, ' '))}" />

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>Frequencia Estagiario</title>
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />

<div class="container">
	<div class="row">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Frequência</h2>
		</div>

		<div class="panel-body">
			<div>
				<label>Data: ${frequenciaHoje.data}</label> 
				<label>Hora: ${frequenciaHoje.tempo}</label> 
				<label>Faltas: ${dadosConsolidados.faltas}</label> 
				<label>Dias Trabalhados: ${dadosConsolidados.diasTrabalhados}</label> 
				<label>Frequência (%): ${dadosConsolidados.porcentagemFrequencia}</label>
			</div>
			
			<c:if test="${not empty message}"><div class="alert alert-info msg"><i class="fa fa-info-circle"> </i> ${message}</div></c:if>

			<table id="table-frequencias" class="table table-striped table-hover">
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
				            <c:when test="${frequencia.statusFrequencia != ''}">
				            	<tr class="success">
									<td><strong><fmt:formatDate value="${frequencia.data}" pattern="dd/MM/yyyy" /></strong></td>
									<td><a href="#" class="observacaoFrequencia" title="Realizar observação" data-pk="${frequencia.id}">${frequencia.observacao}</a></td>
									<td><a href="#" class="statusFrequencia" title="Atualizar status" data-pk="${frequencia.id}">${frequencia.statusFrequencia}</a></td>
								</tr>
				            </c:when>
				            <c:otherwise>
					            <tr class="warning">
									<td><strong><fmt:formatDate value="${frequencia.data}" pattern="dd/MM/yyyy" /></strong></td>
									<td colspan="2">Aguardando data para lançamento da frequência.</td>
								</tr>
				            </c:otherwise>
				        </c:choose>
					</c:forEach>
		       </tbody>
			</table>
		</div>
	</div>
	</div>
</div><br><br>

	<jsp:include page="../modulos/footer1.jsp" />
		
	<script type="text/javascript">
		$(".menu #estagiarios").addClass("active");
	</script>

</body>