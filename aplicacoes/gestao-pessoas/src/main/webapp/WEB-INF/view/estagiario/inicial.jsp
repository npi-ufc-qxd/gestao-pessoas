<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<html>
<head>
	<title>Estagiários</title>
	<jsp:include page="../modulos/header-estrutura.jsp" />
</head>
<body>

	<c:if test="${not resultado}"><!-- Tela de Cadastro para Inicio no NPI -->
		<jsp:include page="../modulos/header.jsp" />
	</c:if>
	
	<div class="container">
			<c:if test="${not empty info}">
				<div class="alert alert-info alert-dismissible margin-top" role="alert">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<c:out value="${info}"></c:out>
				</div>
			</c:if>
	
	
		<c:if test="${resultado}"><!-- Tela de Cadastro para Inicio no NPI -->
			<div class="temp">
				<jsp:include page="../estagiario/meu-cadastro-npi.jsp" />
			</div>
		</c:if>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
	<script src="<c:url value="/resources/js/bootbox.min.js" />"></script>
	<script src="<c:url value="/resources/js/flippant.min.js" />"></script>
	<script src="<c:url value="/resources/js/fuelux.min.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$(".data").datepicker({
				language: 'pt-BR',
				autoclose: true,
				format: "dd/mm/yyyy"
			});
			
		});
	</script>
</body>
</html>