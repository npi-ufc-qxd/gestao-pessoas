<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Projetos</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />


	<div align="center" style="margin-bottom: 20px;">
		<a href="<c:url value="/estagiario/cadastrar" ></c:url>">
			<button class="btn btn-primary">
				Novo Estagiário <span class="glyphicon glyphicon-plus"></span>
			</button>
		</a>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>