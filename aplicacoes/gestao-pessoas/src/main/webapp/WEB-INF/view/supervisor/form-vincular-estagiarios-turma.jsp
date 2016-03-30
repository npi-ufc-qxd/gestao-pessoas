<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Vincular estagiários a turma</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	
	<div class="container">
	<div class="row">
	<div class="panel panel-primary">
	
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-folder-open"></span> Atualizar Vínculos: ${turma.nome}</h2>
			
			<div class="pull-right">
				<a title="Voltar" class="btn btn-default back"><span class="fa fa-arrow-left"></span> Voltar</a>
			</div>
		</div>

		<form:form id="form-vincular-estagiario" role="form" modelAttribute="turma" servletRelativeAction="/supervisor/turma/${turma.id}/vincular" method="POST">
			

			<div class="panel-body">
				<table id="table-vincular-estagiarios" class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Selecione os estagiários</th>
						</tr>
					</thead>
					<tbody>
					
						<c:forEach var="estagiario" items="${outrosEstagiarios}" varStatus="contadorOutroEstagiario">
							<tr>
								<td>
								<%-- 									<c:set var="indice" value="${indice + contadorOutroEstagiario.count}" ></c:set> --%>
									<c:set var="indice" value="${contadorOutroEstagiario.count}" ></c:set>
									<input type="checkbox" id="outroEstagiario${indice}" name="estagiarios[${indice}].id" value="${estagiario.id}"/>
									<label for="outroEstagiario${indice}" class="text-view-info">${estagiario.nomeCompleto}</label>
								</td>
							</tr>
						</c:forEach>
						<c:forEach var="estagiario" items="${estagiariosDaTurma}" varStatus="contador">
							<tr>
								<td>
									<c:set var="indice" value="${contador.index}"></c:set>
									<form:checkbox id="estagiarioDaTurma${indice}" path="estagiarios[${indice}].id" value="${estagiario.id}" checked="checked"/>
									<label for="estagiarioDaTurma${contador.index}" class="text-view-info">${estagiario.nomeCompleto}</label>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<div class="panel-footer" align="center">
				<div class="controls">
					<button id="atualizar-vinculo" type="submit" class="btn btn-primary" title="Atualizar vínculos"><span class="fa fa-refresh"></span> Atualizar vínculos</button>
				</div>
			</div>

		</form:form>
    </div>
	</div>
</div>

	<jsp:include page="../modulos/footer.jsp" />
	
    <script type="text/javascript">
		$(".menu #turmas").addClass("active");
		
		

		$('#table-vincular-estagiarios').DataTable({
			 "pageLength": 10,
			"language": ptBR,
			 "order": [0, 'asc'],
			 "columnDefs": [
				{ "orderable": true, "targets": 0 },
				{ "orderDataType": "dom-checkbox","targets": 0 },
							
			],
		});
		$(document).ready( function(){
			$.fn.dataTable.ext.order['dom-checkbox'] = function  ( settings, col )
			{
			    return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
			        return $('input', td).prop('checked') ? '1' : '0';
			    } );
			}		 
			
		});
		

		$('.dataTables_length label').addClass('text-view-info');
		$('.dataTables_filter label').addClass('text-view-info');
		
		$('#form-vincular-estagiarios').submit(function( e ) {
			
			alert('olá');
			
			var postData = $(this).serializeArray();
			var formURL = $(this).attr("action");

			$.ajax({
				url : formURL,
				type: "POST",
				data : postData,
			});

			e.preventDefault();
			e.unbind();		
		});
		
		$('#form-vincular-estagiarios').submit();
	</script>	
	
</body>
</html>