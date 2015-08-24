<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${action eq 'cadastrar' }">
	<c:set var="url" value="/supervisor/periodo/${periodo.id }/folga"></c:set>
	<c:set var="titulo" value="Adicionar Folga"></c:set>
	<c:set var="botao" value="Salvar"></c:set>
</c:if>
<c:if test="${action eq 'editar' }">
	<c:set var="url" value="/supervisor/periodo/${folga.periodo.id}/folga/${folga.id }/editar"></c:set>
	<c:set var="titulo" value="Editar Folga"></c:set>
	<c:set var="botao" value="Editar"></c:set>
</c:if>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>${titulo }</title>
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />

<div class="container">
	<div class="row">
	<div class="panel panel-primary">
	
		<div class="panel-heading">
			<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> ${titulo}</h2>
		</div>

		<form:form id="adicionarFolgaForm" role="form" commandName="folga" servletRelativeAction="${url}" method="POST" cssClass="form-horizontal">
			<div class="panel-body">
				<form:hidden path="id"/>

				<div class="form-group">
					<div class="form-item col-sm-12">
						<fmt:formatDate var="inicio" value="${periodo.inicio}" pattern="dd/MM/yyyy" />
						<fmt:formatDate var="termino" value="${periodo.termino}" pattern="dd/MM/yyyy" />
						<label class="control-label text-view-info"><strong>Período</strong>: ${periodo.ano}.${periodo.semestre}, de ${inicio} a ${termino}</label>
					</div>
				</div>

				<div class="form-group">
					<div class="form-item col-sm-3">
						<label for="dataFolga" class="control-label">Data:</label>
						<form:input id="dataFolga" path="data" cssClass="data form-control" placeholder="dd/mm/aaaa" required="required"/>
						<div class="error-validation"><form:errors path="data"></form:errors></div>
					</div>
				</div>

				<div class="form-group">
					<div class="form-item col-sm-12">
						<label for="descricao" class="control-label">Descrição: </label>
						<form:textarea id="descricao" path="descricao" cssClass="form-control" placeholder="Descrição da folga" rows="3" required="required"/>
						<div class="error-validation"><form:errors path="descricao"></form:errors></div>
					</div>
				</div>
			</div>
			
			<div class="panel-footer" align="center">
				<div class="controls">
					<c:if test="${action eq 'cadastrar' }"><button type="submit" class="btn btn-primary">Cadastrar</button></c:if>
					<c:if test="${action eq 'editar' }"><button type="submit" class="btn btn-success">Salvar alterações</button></c:if>
				</div>
			</div>
		</form:form>
	</div>
	</div>
</div>

	<jsp:include page="../modulos/footer1.jsp" />
	
    <script type="text/javascript">
		$(document).ready(function(){
			$(".menu #periodos").addClass("active");
		});
	</script>	

</body>
</html>