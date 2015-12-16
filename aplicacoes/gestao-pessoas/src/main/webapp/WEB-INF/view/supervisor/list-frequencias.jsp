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
			
			<div id="container-table" class="col-sm-9">
				<table id="table-frequencias" class="table table-striped table-hover">
			       <tbody class="text-view-info">
						<c:forEach var="frequencia" items="${frequencias}">
							<tr>
								<td><fmt:formatDate type="time" pattern="HH:mm" value="${frequencia[5]}" /></td>
								<td>${frequencia[1]}</td>
								<td><a href="#" class="observacaoFrequencia" title="Realizar observação" data-pk="${frequencia[0]}">${frequencia[2]}</a></td>
								<td><a href="#" class="statusFrequencia" title="Atualizar status" data-pk="${frequencia[0]}">${frequencia[3]}</a></td>
								<td></td>
							</tr>
						</c:forEach>
						<c:forEach var="estagiario" items="${estagiarios}">
							<tr class="danger">
								<td><fmt:formatDate type="time" pattern="HH:mm" value="${dataAtual}" /></td>
								<td>${estagiario.nomeCompleto}</td>
								<form:form id="lancar-frequencia" servletRelativeAction="/supervisor/turma/${turma.id}/estagiario/${estagiario.id}/frequencia/lancar" method="POST">
								<input class="dataSelecionada" type="hidden" name="data" value="">
									<td><textarea class="form-control" rows="1" cols="10" name="observacao"></textarea></td>
									<td>
									<select class="form-control" name="statusFrequencia">
										<option value="PRESENTE">PRESENTE</option>
										<option value="FALTA">FALTA</option>
										<option value="ATRASADO">ATRASADO</option>
										<option value="FERIADO">FERIADO</option>
										<option value="ABONADO">ABONADO</option>
									</select>
									</td>
									<td><input type="submit" class="btn btn-primary" value="Lançar"/></td>
								</form:form>
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
		
// 		$(function(){
			
// 			$('#lancar-frequencia').submit(function(){
// 			$("input[type='submit']", this)
// 		    	.alert("Please Wait...");
// 			});
// 		});
		
		
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
		
		
		$("#table-frequencias").DataTable({
			 "paging": false,
			 "bInfo": false,
			 "order": [ [0, 'asc'],[1, 'asc'] ],
			 "bFilter": false,
			 "columnDefs": [
				{ "title": "Horário", "targets": 0 },
				{ "title": "Nome", "targets": 1 },
				{ "title": "obsevação", "orderable": false, "targets": 2 },
				{ "title": "Status", "orderable": false, "targets": 3 },
				{ "title": "", "orderable": false, "targets": 4 },
			],
			"destroy": true,
		});
		
		 function loadFrequencias(result) {
			
			$("#container-table #table-frequencias").remove();
 			$("#container-table").html($(result).find("#table-frequencias"));
 			$('.dataSelecionada').val($("#dataFiltroFrequencia").datepicker('getFormattedDate'));
 			ativarEditable();

			$("#table-frequencias").DataTable({
				 "paging": false,
				 "bInfo": false,
				 "order": [ [1, 'asc'], [0, 'asc'] ],
				 "bFilter": false,
				 "columnDefs": [
					{ "title": "Horário", "targets": 0 },
					{ "title": "Nome", "targets": 1 },
					{ "title": "obsevação", "orderable": false, "targets": 2 },
					{ "title": "Status", "orderable": false, "targets": 3 },
					{ "title": "", "orderable": false, "targets": 4 },
				],
				"destroy": true,
			}); 
 		}

	</script>
</body>
</html>