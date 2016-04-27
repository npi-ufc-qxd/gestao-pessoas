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

							<th>Semestre</th>
							<th>Nome</th>
							<th>Status</th>
							<th></th>							
							<th></th>
			           </tr>
			       </thead>
			       <tbody class="text-view-info">
							<c:forEach var="turma" items="${turmasComPresencaHj}">
								<tr>
									<td>${turma.semestre}</td>
									<td>${turma.nome}</td>
									
									<c:if test="${turma.statusTurma eq 'FECHADA'}">
										<td><span class="label ${turma.statusTurma eq 'ABERTA' ? 'label-success':'label-danger'}">${turma.statusTurma}</span></td>
									</c:if>
									<c:if test="${turma.statusTurma eq 'ABERTA'}">
										<td><span class="label label-info">${turma.statusTurma}</span></td>
									</c:if>
										
									<td>
									</td>

									<td align="right">
										<a href="<c:url value="/estagiario/minha-frequencia/turma/${turma.id}" />" title="Minha Frequência" class="btn btn-info informacao"><span class="fa fa-calendar-check-o"></span></a>
										<a href="<c:url value="/estagiario/turma/${turma.id}" />" title="Informações" class="btn btn-info informacao"><span class="fa fa-info"></span></a>
									</td>
									
									
							</tr>
						</c:forEach>
								<c:forEach var="turma" items="${turmasSemPresencaHj}">
								<tr>
									<td>${turma.semestre}</td>
									<td>${turma.nome}</td>
									
									<c:if test="${turma.statusTurma eq 'FECHADA'}">
										<td><span class="label ${turma.statusTurma eq 'ABERTA' ? 'label-success':'label-danger'}">${turma.statusTurma}</span></td>
									</c:if>
									<c:if test="${turma.statusTurma eq 'ABERTA'}">
										<td><span class="label label-info">${turma.statusTurma}</span></td>
									</c:if>
										
									<td>
										<form class="form-inline form-minha-presenca" action="<c:url value="/estagiario/minha-frequencia/turma/${turma.id }"></c:url>" method="POST" align="center">
										<button type="submit" class="btn btn-success">Estou Presente !!!</button>
										</form>
										</td>

									<td align="right">
										<a href="<c:url value="/estagiario/minha-frequencia/turma/${turma.id}" />" title="Minha Frequência" class="btn btn-info informacao"><span class="fa fa-calendar-check-o"></span></a>
										<a href="<c:url value="/estagiario/turma/${turma.id}" />" title="Informações" class="btn btn-info informacao"><span class="fa fa-info"></span></a>
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
			 "order": [[ 0, 'asc' ]],
			 "columnDefs": [
				{ "order": [[ 0, 'asc' ]] },    
				{ "orderable": false, "targets": 1 },
				{ "orderable": false, "targets": 2 },
				{ "orderable": false, "targets": 3 },
				{ "orderable": false, "targets": 4 },
			],
			"language": ptBR,
		});

		$('.dataTables_length label').addClass('text-view-info');
		$('.dataTables_filter label').addClass('text-view-info');
	</script>
</body>
</html>