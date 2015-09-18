<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${action eq 'cadastrar' }">
	<c:set var="url" value="/supervisor/turma/adicionar"></c:set>
	<c:set var="titulo" value="Nova Turma"></c:set>
</c:if>
<c:if test="${action eq 'editar' }">
	<c:set var="url" value="/supervisor/turma/${turma.id}/editar"></c:set>
	<c:set var="titulo" value="Editar Turma"></c:set>
</c:if>

<html>
	<head>
		<title>${titulo }</title>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />

<div class="container">
	<div class="row">

	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-folder-open"></span> ${titulo}</h2>
			
			<div class="pull-right">
				<a title="Voltar" class="btn btn-primary back"><span class="fa fa-arrow-circle-o-left"></span> Voltar</a>
			</div>
		</div>

		<form:form id="form-turma" role="form" commandName="turma" servletRelativeAction="${url}"  method="POST" cssClass="form-horizontal">
			<div class="panel-body">
				<form:hidden path="id"/>

				<div class="form-group">
					<div class="form-item col-sm-9">
						<label for="nomeDaTurma" class="control-label">*Nome da Turma:</label>
						<form:input id="nomeDaTurma" path="nome" cssClass="form-control" placeholder="Nome da Turma" required="required" />
						<div class="error-validation"><form:errors path="nome"></form:errors></div>
					</div>

					<div class="form-item col-sm-3">
						<label for="statusTurma" class="control-label">*Status:</label>
						<form:select id="statusTurma" path="statusTurma" cssClass="form-control selectpicker" required="required">
							<form:options itemLabel="label" />
						</form:select>
						<div class="error-validation"><form:errors path="statusTurma"></form:errors></div>
					</div>
				</div>

				<div class="form-group">
					<div class="form-item col-sm-3">
						<label for="ano" class="control-label">Ano:</label>
						<form:input id="ano" path="ano" cssClass="form-control" placeholder="Ano" required="required"/>
						<div class="error-validation"><form:errors path="ano"></form:errors></div>
					</div>
	
					<div class="form-item col-sm-3">
						<label for="semestre" class="control-label">Semestre:</label>
						<form:select id="semestre" path="semestre" cssClass="selectpicker" data-width="100%">
							<form:option value="1"></form:option>
							<form:option value="2"></form:option>
						</form:select>
						<div class="error-validation"><form:errors path="semestre"></form:errors></div>
					</div>

					<div class="form-item col-sm-3">
						<label for="inicio" class="control-label">Início: </label>
						<form:input id="inicio" path="inicio" cssClass="form-control data" placeholder="Inicio do periodo" required="required"/>
						<div class="error-validation"><form:errors path="inicio"></form:errors></div>
					</div>
	
					<div class="form-item col-sm-3">
						<label for="termino" class="control-label">Término: </label>
						<form:input id="termino" path="termino" cssClass="form-control data" placeholder="Término do periodo" required="required"/>
						<div class="error-validation"><form:errors path="termino"></form:errors></div>
					</div>
				</div>
			</div>
			
			<div class="panel-footer" align="center">
				<div class="controls">
					<c:if test="${action eq 'cadastrar' }"><button type="submit" class="btn btn-primary" title="Cadastrar"><span class="fa fa-floppy-o"></span> Cadastrar</button></c:if>
					<c:if test="${action eq 'editar' }"><button type="submit" class="btn btn-primary" title="Salvar alterações"><span class="fa fa-floppy-o"></span> Salvar alterações</button></c:if>
				</div>
			</div>
		</form:form>
	</div>
	</div>
</div>

	<jsp:include page="../modulos/footer1.jsp" />
	
    <script type="text/javascript">
		$('.menu #turmas').addClass('active');
	</script>		
	
	
</body>
</html>
