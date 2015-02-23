<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Documentos</title>
</head>

<style>
</style>

<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<h1 align="left" style="border-bottom: 1px solid #333;">Documentos</h1>
		
		<div id="upload">
			<h4>Enviar documentos</h4>
			
			<form:form id="adicionarDocumentosForm" role="form" commandName="pessoa" enctype="multipart/form-data" servletRelativeAction="/coordenador/cadastro-documento" method="POST" cssClass="form-horizontal">
				<div class="form-group form-item">
					<label for="documentos" class="col-sm-2 control-label">Documentos:</label>
					<div class="col-sm-10">
						<input type="file" id="documentos" name="documentos" class="file" multiple="multiple" ></input>
					</div>
				</div>
			</form:form>
		</div>

		<div id="modelos">
			<h4>Modelos</h4>
		    <ul class="list-group">
		        <li class="list-group-item"><a href="<c:url value="#" />">Plano de Estagio</a></li>
		        <li class="list-group-item"><a href="<c:url value="#" />">Relatorio de estagio</a></li>        
		    </ul>
		    
		</div>
		
		
	</div>
	
	<jsp:include page="../modulos/footer.jsp" />
	

</body>
</html>