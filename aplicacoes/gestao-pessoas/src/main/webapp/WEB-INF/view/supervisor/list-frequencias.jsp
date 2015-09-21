<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<meta charset="UTF-8"/>
	<jsp:include page="../modulos/header-estrutura.jsp" />
	<title>Frequência</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

<div class="container">
	<div class="row">

	
	<input id="idTurma" type="hidden" value="${turma.id}">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><a href="#"><span class="fa fa-calendar-check-o"></span></a> Frêquencia: <strong>${turma.nome }</strong></h2>

			<div class="pull-right">
				<a title="Voltar" class="btn btn-primary back"><span class="fa fa-arrow-circle-o-left"></span> Voltar</a>
			</div>		     
		</div>

		<c:if test="${not empty turma}">
		<div class="panel-body">
			<div class="col-sm-3">
				<div id="dataFiltroFrequencia" class="col-sm-2"></div>
			</div>
			
			<div class="col-sm-9">
				<table id="table-frequencias" class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Nome</th>
							<th>Observação</th>
							<th>Status</th>
						</tr>
			       </thead>
			       <tbody class="text-view-info">
						<c:if test="${empty frequencias}">
							<tr class="warning" align="left"><td colspan="3">Não há frequências registradas para <strong><fmt:formatDate value="${dataSelecionada}" pattern="dd/MM/yyyy" /></strong></td></tr>
						</c:if>
						<c:forEach var="frequencia" items="${frequencias}">
							<tr>
								<td>${frequencia[1]}</td>
								<td><a href="#" class="observacaoFrequencia" title="Realizar observação" data-pk="${frequencia[0]}">${frequencia[2]}</a></td>
								<td><a href="#" class="statusFrequencia" title="Atualizar status" data-pk="${frequencia[0]}">${frequencia[3]}</a></td>
							</tr>
						</c:forEach>
			       </tbody>
				</table>
			</div>
		</div>
		</c:if>
	</div>
	</div>
</div>

	<jsp:include page="../modulos/footer.jsp" />
	
	<script type="text/javascript">
		$('.menu #turmas').addClass('active');
	
		$('#dataFiltroFrequencia').datepicker({
			language: 'pt-BR',
			format: "mm/dd/yyyy",
			todayHighlight: true,
		});
		
		$("#dataFiltroFrequencia").on("changeDate", function(event) {
			var data = $("#dataFiltroFrequencia").datepicker('getFormattedDate');
			var idTurma = $("#idTurma").val();
			
			$.ajax({
				url: '/gestao-pessoas/supervisor/turma/' + idTurma + '/frequencias',
				type: "POST",
				dataType: "html",
				data: {
					"data" : data,
				},
				success: function(result) {
					loadFrequencias(result);
				},
				error: function(error) {
					alert('Error: ' + error);
				}
			});			
		});
		
		function loadFrequencias(result) {	
			$("#table-frequencias").html($(result).find("#table-frequencias"));
			ativarEditable();
		}
	</script>

</body>
</html>