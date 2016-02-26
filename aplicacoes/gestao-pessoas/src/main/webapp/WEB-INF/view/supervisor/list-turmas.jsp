<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura.jsp" />

		<title>Minhas Turmas</title>
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	
<div class="container">
	<div class="row">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><a href="#"><span class="fa fa-folder-open"></span></a> Turmas</h2>

			<div class="pull-right"> 
				<a href="<c:url value="/supervisor/turma/adicionar" ></c:url>" title="Novo Turma"><button class="btn btn-primary"><span class="fa fa-plus"></span> Turma</button></a>
			</div><br>
			
		</div>

		<div class="panel-body">

		<c:if test="${not empty success }"><div class="alert alert-dismissible alert-success"><button type="button" class="close" data-dismiss="alert">×</button>${success}</div></c:if>

		<c:if test="${empty turmas}">
				<div class="alert alert-warning" role="alert">Não há Turmas cadastrados.</div>
			</c:if>
			
			<c:if test="${not empty turmas}">
				<table id="table-turmas" class="table table-striped table-hover">
					<thead>
						<tr class="">
							<th>Periodo</th>
							<th class="hidden">Ano</th>
							<th class="hidden">Semestre</th>
							<th>Nome</th>
							<th>Status</th>
							<th>Início</th>
							<th>Término</th>
							<th></th>
			           </tr>
			       </thead>
			       <tbody class="text-view-info">
							<c:forEach var="turma" items="${turmas}">
								<tr>
									<td>${turma.ano}.${turma.semestre}</td>
									<td class="hidden">${turma.ano}</td>
									<td class="hidden">${turma.semestre}</td>
									<td>${turma.nome}</td>
									
									<c:if test="${turma.statusTurma eq 'FECHADA'}">
										<td><span class="label ${turma.statusTurma eq 'ABERTA' ? 'label-success':'label-danger'}">${turma.statusTurma}</span></td>
									</c:if>
									<c:if test="${turma.statusTurma eq 'ABERTA'}">
										<td><span class="label label-info">${turma.statusTurma}</span></td>
									</c:if>

									<td><fmt:formatDate value="${turma.inicio}" pattern="dd/MM/yyyy" /></td>

									<td><fmt:formatDate value="${turma.termino}" pattern="dd/MM/yyyy" /></td>

									<td align="right">
										<a href="<c:url value="/supervisor/turma/${turma.id}" />" title="Informações" class="btn btn-info informacao"><span class="fa fa-info"></span></a>
										<a href="<c:url value="/supervisor/turma/${turma.id}/expediente" />" title="Atualizar Expediente" class="btn btn-primary"><span class="fa fa-clock-o"></span></a>
										<a href="<c:url value="/supervisor/turma/${turma.id}/editar" />" title="Editar" class="btn btn-success"><span class="fa fa-pencil"></span></a>
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

	<jsp:include page="../modulos/footer.jsp" />

	<script type="text/javascript">
		$('.menu #turmas').addClass('active');

		$('#table-turmas').DataTable({
			 "pageLength": 10,
			 "order": [[ 1, 'asc' ], [ 2, 'asc' ]],
			 "columnDefs": [
				{ "order": [[ 1, 'asc' ], [ 2, 'asc' ]],    "targets": [0, 'asc'] },
				{ "orderable": false, "targets": 2 },
				{ "orderable": false, "targets": 3 },
				{ "orderable": false, "targets": 4 },
				{ "orderable": false, "targets": 5 },
				{ "orderable": false, "targets": 6 },
				{ "orderable": false, "targets": 7 },
			],
			"language": ptBR,
		});

		$('.dataTables_length label').addClass('text-view-info');
		$('.dataTables_filter label').addClass('text-view-info');
	</script>
</body>
</html>