<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
	<head>
		<title>Atualizar Evento</title>
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
		

		
		<div class="panel-body">
			<c:if test="${not empty success }">
				<div class="alert alert-dismissible alert-success">
					<button type="button" class="close" data-dismiss="alert">×</button>${success }
				</div>
			</c:if>			
			<div class="col-sm-6">
				<form:form id="form-evento" role="form" commandName="evento" servletRelativeAction="/supervisor/turma/${turma.id}/evento"  method="POST" cssClass="form-horizontal">
					<form:hidden path="id"/>
						<h5>Defina o Evento</h5>
						<div class="form-group">
							<div id="inicio" class="form-item col-sm-12">
								<label class="control-label">*Início do Evento:</label>
								<form:input id="inicio" path="inicio" cssClass="form-control data" placeholder="Inicio do Evento" />


								<label class="control-label">*Final do Evento:</label>
								<form:input id="termino" path="termino" cssClass="form-control data" placeholder="Termino do periodo" />
							</div>
							<div id="descricao" class="form-item col-sm-12">
								<label class="control-label">*Descrição do evento:</label>
								<form:textarea path="descricao" cssClass="form-control" placeholder="Descrição do Evento" rows="1"/>
							</div>
							<div class="form-item col-sm-3">
								<label class="control-label">&nbsp;&nbsp;&nbsp;</label>
								<button type="submit" class="form-control btn btn-primary" title="Adicionar Horário"><span class="fa fa-plus"></span> Adicionar</button>
							</div>
						</div>
				</form:form>
			</div>
			<div class="col-sm-6">
				<h5>Eventos</h5>
				<table class="table table-striped table-hover">
				  <thead>
				    <tr>
				      <th>Evento</th>
				      <th>Período</th>
				      <th></th>
				    </tr>
				  </thead>
				  <tbody class="text-view-info">
				  <c:forEach var="evento" items="${eventos}">
				    <tr align="justify">
				      <td>${evento.descricao }</td>
				      <td>${evento.inicio } a ${evento.termino}</td>
				      <td align="right">
				      	<a href="<c:url value ="/supervisor/turma/${turma.id}/evento/${evento.id}/excluir">
				      	</c:url>" title="Excluir" class="btn btn-danger" >
				      	<span class="fa fa-remove"></span>
				      	</a>
				      </td>
				    </tr>										
				   </c:forEach>
				  </tbody>
				</table> 				
				
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
