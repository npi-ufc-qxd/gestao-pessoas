<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<title>Editar Evento</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

<div class="container">
	<div class="row">

	<div class="panel panel-warning">
	
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-calendar"></span> Evento</h2>
			
			<div class="pull-right">
				<a title="Voltar" class="btn btn-info" href="<c:url value="/supervisor/turma/${turma.id}"/>"><span class="fa fa-arrow-circle-o-left"></span> Voltar</a>
			</div>
			
		</div>
		

		
		<div class="panel-body">
			<c:if test="${not empty success }">
				<div class="alert alert-dismissible alert-success">
					<button type="button" class="close" data-dismiss="alert">×</button>${success }
				</div>
			</c:if>			
			<div class="col-sm-4">
				<form:form id="form-evento" role="form" commandName="evento" servletRelativeAction="/supervisor/turma/${evento.turma.id}/evento/${evento.id}/editar"  method="POST" cssClass="form-horizontal">
						<h5>Evento</h5>
						<div class="form-group">
							<div id="inicio" class="form-item col-sm-12">
								<label class="control-label">*Início do Evento:</label>
								<form:input id="inicio" path="inicio" cssClass="form-control data" placeholder="Inicio do Evento" required="required"/>


								<label class="control-label">*Final do Evento:</label>
								<form:input id="termino" path="termino" cssClass="form-control data" placeholder="Termino do periodo" required="required"/>
							</div>
							<div id="descricao" class="form-item col-sm-12">
								<label class="control-label">*Descrição do evento:</label>
								<form:textarea path="descricao" cssClass="form-control" placeholder="Descrição do Evento" rows="1"/>
							</div>
							<div class="form-item col-sm-6">
								<label class="control-label">&nbsp;&nbsp;&nbsp;</label>
								<button type="submit" class="form-control btn btn-primary" title="Atualizar Evento"><span class="fa fa-refresh"></span> Atualizar</button>
							</div>
						</div>
				</form:form>
			</div>
		</div>


		</div>
	</div>
</div>

	<jsp:include page="../modulos/footer.jsp" />
	
    <script type="text/javascript">
		$('.menu #turmas').addClass('active');
	</script>		
	
	
</body>
</html>
