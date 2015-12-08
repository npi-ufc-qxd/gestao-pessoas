<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${action eq 'cadastrar' }">
	<c:set var="url" value="/supervisor/turma/${turma.id}/evento/cadastrar"></c:set>
	<c:set var="titulo" value="Cadastrar Evento"></c:set>
</c:if>
<c:if test="${action eq 'editar' }">
	<c:set var="url" value="/supervisor/turma/${evento.turma.id}/evento/${idEvento}/editar"></c:set>
	<c:set var="titulo" value="Editar Evento"></c:set>
</c:if>

<html>
	<head>
		<title>${titulo }</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

<div class="container">
	<div class="row">

	<div class="panel panel-warning">
	
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-calendar"></span> ${titulo }</h2>
			
			<div class="pull-right">
				<a title="Voltar" class="btn btn-primary back"><span class="fa fa-arrow-circle-o-left"></span> Voltar</a>
			</div>
		</div>
		
		<div class="panel-body">
			<div class="col-sm-4">
				<form:form id="form-evento" role="form" commandName="evento" servletRelativeAction="${url}"  method="POST" cssClass="form-horizontal">
					<form:hidden path="id"/>
						<h5>Evento</h5>
						<div class="form-group">
							<div id="inicio" class="form-item col-sm-12">
								<label class="control-label">*Início do Evento:</label>
								<form:input id="inicio" path="inicio" cssClass="form-control data" placeholder="Inicio do Evento" required="required"/>
								<div class="error-validation"><form:errors path="inicio"></form:errors></div>

								<label class="control-label">*Final do Evento:</label>
								<form:input id="termino" path="termino" cssClass="form-control data" placeholder="Termino do periodo" required="required"/>
								<div class="error-validation"><form:errors path="termino"></form:errors></div>
							</div>
							<div id="descricao" class="form-item col-sm-12">
								<label class="control-label">*Descrição do evento:</label>
								<form:textarea path="descricao" cssClass="form-control" placeholder="Descrição do Evento" rows="1" required="required"/>
								<div class="error-validation"><form:errors path="descricao"></form:errors></div>
							</div>
						</div>
						<div class="" align="left">
							<c:if test="${action eq 'cadastrar' }">
								<button type="submit" class="btn btn-primary"title="Cadastrar"><span class="fa fa-plus"></span> Adiconar Evento</button>
							</c:if>
							<c:if test="${action eq 'editar' }">
								<button type="submit" class="btn btn-primary" title="Salvar alterações"><span class="fa fa-floppy-o"></span> Salvar alterações</button>
							</c:if>
						</div>
				</form:form>
			</div>
			<div class="col-sm-8">
				<h5>Eventos</h5>
				<table class="table table-striped table-hover">
				  <thead>
				    <tr>
				      <th>Evento</th>
				      <th>Período</th>
				      <th></th>
				    </tr>
				  </thead>
				  <tbody class="text-view-info ">
				  <c:forEach var="evento" items="${eventos}">
				    <tr align="justify">
				      <td>${evento.descricao }</td>
				      <td>${evento.inicio } a ${evento.termino}</td>
				      <td align="right">
				      	<a href="<c:url value="/supervisor/turma/${turma.id}/evento/${evento.id}/editar">
					      	</c:url>" title="Editar" class="btn btn-success">
					      	<span class="fa fa-pencil"></span>
				      	</a>
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
