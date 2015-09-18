<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<title>Atualizar Expediente</title>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />

<div class="container">
	<div class="row">
	
	<c:if test="${not empty save }">
		<div class="alert alert-dismissible alert-success">
			<button type="button" class="close" data-dismiss="alert">×</button>${save }
		</div>
	</c:if>

	<c:if test="${not empty delete }">
		<div class="alert alert-dismissible alert-danger">
			<button type="button" class="close" data-dismiss="alert">×</button>${delete }
		</div>
	</c:if>

	<div class="panel panel-info">
	
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-clock-o"></span> Atualizar Expediente</h2>
			
			<div class="pull-right">
				<a title="Voltar" class="btn btn-info back"><span class="fa fa-arrow-circle-o-left"></span> Voltar</a>
			</div>
		</div>

		<form:form id="form-turma" role="form" commandName="horario" servletRelativeAction="/supervisor/turma/${turma.id}/horario"  method="POST" cssClass="form-horizontal">
			<div class="panel-body">
				<h5>Defina o Expediente</h5>
				<div class="form-group">
					<div class="form-item col-sm-3">
						<label for="diaDaSemana" class="control-label">*Selecione o Dia da Semana:</label>
						<select id="diaDaSemana" name="dia" class="form-control selectpicker" required="required">
							<c:forEach items="${dias}" var="diaDaSemana" varStatus="contador">
								<option value="${diaDaSemana }">${diaDaSemana.labelDia }</option>
							</c:forEach>
						</select>
					</div>

					<div id="inicioDoExpediente" class="form-item col-sm-3">
						<label class="control-label">*Início do Expediente:</label>
						<div class="bfh-timepicker" data-name="inicioExpediente" data-placeholder="Inicio do Expediente" data-time=""></div>
					</div>
					<div id="finalDoExpediente" class="form-item col-sm-3">
						<label class="control-label">*Final do Expediente:</label>
						<div class="bfh-timepicker" data-name="finalExpediente" data-placeholder="Final do Expediente" data-time=""></div>
					</div>
					<div class="form-item col-sm-3">
						<label class="control-label">&nbsp;&nbsp;&nbsp;</label>
						<button type="submit" class="form-control btn btn-primary" title="Adicionar Horário"><span class="fa fa-plus"></span> Adicionar</button>
					</div>
				</div>
			</div>
		</form:form>
		
			<div class="panel-body">
				<table id="horarios-turma" class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Dia da Semana</th>
							<th>Início do Expediente</th>
							<th>Término do Expediente</th>
							<th align="right"></th>
			           </tr>
			       </thead>
			       <tbody class="text-view-info">
						<c:forEach var="horario" items="${turma.horarios}" varStatus="indice">
							<tr align="justify">
								<td>${horario.dia.labelDia}</td>
								<td>${horario.inicioExpediente}</td>
								<td>${horario.finalExpediente}</td>
								<td align="right"><a href="<c:url value="/supervisor/turma/${turma.id}/horario/${horario.id}/excluir" />" title="Excluir" class="btn btn-danger"><span class="fa fa-remove"></span></a></td>
							</tr>
						</c:forEach>
			       </tbody>
	       </table>
			</div>
		</div>
	</div>
</div>

	<jsp:include page="../modulos/footer1.jsp" />
	
    <script type="text/javascript">
		$('.menu #turmas').addClass('active');
	</script>		
	
	
</body>
</html>
