<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>Informações da Turma</title>
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />

<div class="container">
	<div class="row">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Turma</h2>
		</div>

		<div class="panel-body">
			<c:if test="${empty turma}"><div class="alert alert-warning" role="alert">Turma inexistente.</div></c:if>

			<c:if test="${not empty turma}">
				<div class="form-group">
					<label class="col-sm-1 text-view-info"><strong>Supervisor: </strong></label><label class="col-sm-11 text-view-info">${turma.supervisor.nome}</label>
				</div>

				<div class="form-group">
					<label class="col-sm-1 text-view-info"><strong>Turma: </strong></label><label class="col-sm-11 text-view-info">${turma.nome}</label>
				</div>

				<div class="form-group">
					<label class="col-sm-1 text-view-info"><strong>Semestre: </strong></label><label class="col-sm-11 text-view-info">${turma.ano}.${turma.semestre}</label>
				</div>

				<div class="form-group">
					<label class="col-sm-1 text-view-info"><strong>Início: </strong></label><label class="col-sm-11 text-view-info">${turma.inicio}</label>
				</div>

				<div class="form-group">
					<label class="col-sm-1 text-view-info"><strong>Término: </strong></label><label class="col-sm-11 text-view-info">${turma.termino}</label>
				</div>
			</c:if>
		</div>
	</div>
	
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Estagiários</h2>

			<div class="pull-right">
				<a href="<c:url value="/supervisor/turma/${turma.id}/vincular" />" title="Vincular Estagiários" class="btn btn-success"><span class="glyphicon glyphicon-link"></span>&nbsp;</a>
			</div>
		</div>

		<div class="panel-body">
			<c:if test="${empty turma.estagiarios}"><div class="alert alert-warning" role="alert">Não há estagiários vinculados a esta turma.</div></c:if>

			<c:if test="${not empty turma.estagiarios}">
				<table id="estagiarios-turma" class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Matrícula</th>
							<th>Nome</th>
							<th></th>
			           </tr>
			       </thead>
			       <tbody class="text-view-info">
						<c:forEach var="estagiario" items="${turma.estagiarios}">
							<tr>
								<td>${estagiario.matricula}</td>
								<td>${estagiario.nomeCompleto}</td>
								<td align="right">
									<a href="<c:url value="/supervisor/estagiario/${estagiario.id}/frequencia" />" title="Frequências" class="btn btn-info btn-sm"><span class="fa fa-list"></span> Frequências</a>
								</td>
							</tr>
						</c:forEach>
			       </tbody>
				</table>
			</c:if>
		</div>
		<c:if test="${not empty turma.estagiarios}">
			<div class="panel-footer" align="center">
				<div class="controls">
					<a class="btn btn-default" href="<c:url value="/supervisor/turma/${idTurma }/tce" ></c:url>" title="Termo de Compromisso"><i class="fa fa-file-pdf-o"></i> Termo de Compromisso</a>
					<a class="btn btn-primary" href="<c:url value="/supervisor/turma/${idTurma }/declaracoes" ></c:url>" title="Declaração de Estágio"><i class="fa fa-file-pdf-o"></i> Declaração de Estágio</a>
				</div>
			</div>
		</c:if>
	</div>
	
	</div>
</div>

	<jsp:include page="../modulos/footer1.jsp" />

    <script type="text/javascript">
		$(".menu #turmas").addClass("active");
	</script>	

</body>
</html>