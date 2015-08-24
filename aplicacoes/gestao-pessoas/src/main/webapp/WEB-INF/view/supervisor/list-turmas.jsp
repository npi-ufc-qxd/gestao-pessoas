<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>Minhas Turmas</title>
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />
	
<div class="container">
	<div class="row">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Turmas</h2>
		</div>

		<div class="panel-body">
		
		<c:if test="${empty turmas}">
				<div class="alert alert-warning" role="alert">Não há Turmas cadastrados.</div>
			</c:if>
			
			<c:if test="${not empty turmas}">
				<table id="minhas-turmas" class="table table-striped form-inline">
					<thead>
						<tr class="">
							<th>Periodo</th>
							<th class="hidden">Ano</th>
							<th class="hidden">Semestre</th>
							<th>Turma</th>
							<th>Status</th>
							<th>N° de Estagiarios</th>
							<th></th>
			           </tr>
			       </thead>

			       <tbody class="panel">
							<c:forEach var="turma" items="${turmas}">
								<tr>
									<td>${turma.periodo.ano}.${turma.periodo.semestre}</td>
									<td class="hidden">${turma.periodo.ano}</td>
									<td class="hidden">${turma.periodo.semestre}</td>
									<td>${turma.nome}</td>
									
									<c:if test="${turma.statusTurma eq 'FECHADA'}">
										<td><span class="label ${turma.statusTurma eq 'ABERTA' ? 'label-success':'label-danger'}">${turma.statusTurma}</span></td>
									</c:if>
									<c:if test="${turma.statusTurma eq 'ABERTA'}">
										<td><span class="label label-info">${turma.statusTurma}</span></td>
									</c:if>
									
									<td><span class="badge ${fn:length(turma.estagiarios) gt 0 ? 'badge-success' : 'badge-danger' }">${fn:length(turma.estagiarios)}</span></td>
									<td align="right">
										<a href="<c:url value="/supervisor/turma/${turma.id}" />" class="btn btn-info"><span class="glyphicon glyphicon-eye-open"></span> Informações</a>
<%-- 										<a href="<c:url value="#/turma/${turma.id}/editar" />" class="btn btn-primary"><span class="glyphicon glyphicon-pencil"></span></a> --%>
<%-- 										<a href="<c:url value="#/turma/${turma.id}/excluir" />" class="btn btn-danger"> <span class="glyphicon glyphicon-trash"></span></a> --%>
									</td>
							</tr>
						</c:forEach>
			       </tbody>
				</table>
				
				
			</c:if>
		
		</div>
	</div>
	
	</div>
		
</div>


	<jsp:include page="../modulos/footer1.jsp" />
	<script type="text/javascript">
	
	$('.menu #turmas').addClass('active');
	
	$('#minhas-turmas').DataTable({
		 "pageLength": 10,
		 "order": [[ 1, 'asc' ], [ 2, 'asc' ]],
		 "columnDefs": [
		               { "orderable": false, "targets": 0 },
		               { "order": [[ 1, 'asc' ], [ 2, 'asc' ]],    "targets": [0, 'asc'] },
		               { "orderable": false, "targets": 2 },
		               { "orderable": false, "targets": 3 },
		               { "orderable": false, "targets": 4 },
		               { "orderable": false, "targets": 5 },
		               { "orderable": false, "targets": 6 },
		],
		
		"language": {
		    "sEmptyTable": "Nenhum registro encontrado",
		    "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
		    "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
		    "sInfoFiltered": "(Filtrados de _MAX_ registros)",
		    "sInfoPostFix": "",
		    "sInfoThousands": ".",
		    "sLengthMenu": "_MENU_",
		    "sLoadingRecords": "Carregando...",
		    "sProcessing": "Processando...",
		    "sZeroRecords": "Nenhum registro encontrado",
		    "sSearch": "",
		    "oPaginate": {
		        "sNext": "Próximo",
		        "sPrevious": "Anterior",
		        "sFirst": "Primeiro",
		        "sLast": "Último"
		    },
		    "oAria": {
		        "sSortAscending": ": Ordenar colunas de forma ascendente",
		        "sSortDescending": ": Ordenar colunas de forma descendente"
		    }
		}
	});
	
	
	$('select').selectpicker({
		width: '100%',
		title: 'Resultados por página', 
	});
	$('input').attr('placeholder', 'Pesquisar...');
	$('input').addClass('form-control col-sm-2');
	$('select').addClass('form-control col-sm-4');
	
	
	</script>
</body>
</html>