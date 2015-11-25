<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<title>Atualizar Expediente</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

<div class="container">
	<div class="row">

	<div class="panel panel-warning">
	
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-calendar"></span> Atualizar Calendário</h2>
			
			<div class="pull-right">
				<a title="Voltar" class="btn btn-info" href="<c:url value="/supervisor/turma/${turma.id}"/>"><span class="fa fa-arrow-circle-o-left"></span> Voltar</a>
			</div>
			
		</div>

		<form:form id="form-evento" role="form" commandName="evento" servletRelativeAction="/supervisor/turma/${turma.id}/evento"  method="POST" cssClass="form-horizontal">
			<div class="panel-body">
				<c:if test="${not empty success }">
					<div class="alert alert-dismissible alert-success">
						<button type="button" class="close" data-dismiss="alert">×</button>${success }
					</div>
				</c:if>

				<h5>Defina o Evento</h5>
				<div class="form-group">
					<div id="descricaoDoEvento" class="form-item col-sm-3">
						<label class="control-label">*Descrição do evento:</label>
						<textarea class="form-control" rows="3" placeholder="Descrição do Evento"></textarea>
					</div>
					<div id="inicioDoEvento" class="form-item col-sm-3">
						<label class="control-label">*Início do Evento:</label>
						<div class="bfh-datepicker" data-name="inicio" data-placeholder="Inicio do Evento"></div>
					</div>
					<div id="finalDoEvento" class="form-item col-sm-3">
						<label class="control-label">*Final do Evento:</label>
						<div class="bfh-datepicker" data-name="termino" data-placeholder="Final do Evento"></div>
					</div>
					<div class="form-item col-sm-3">
						<label class="control-label">&nbsp;&nbsp;&nbsp;</label>
						<button type="submit" class="form-control btn btn-primary" title="Adicionar Horário"><span class="fa fa-plus"></span> Adicionar</button>
					</div>
				</div>
			</div>
		</form:form>
		</div>
	</div>
</div>

	<jsp:include page="../modulos/footer.jsp" />
	
    <script type="text/javascript">
		$('.menu #turmas').addClass('active');
	</script>		
	
	
</body>
</html>
