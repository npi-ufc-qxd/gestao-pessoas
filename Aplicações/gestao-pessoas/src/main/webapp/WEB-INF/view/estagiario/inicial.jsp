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



	<div class="container">

		<c:if test="${not empty erro}">
			<div class="alert alert-danger alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<c:out value="${erro}"></c:out>
			</div>
		</c:if>
		<c:if test="${not empty info}">
			<div class="alert alert-success alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<c:out value="${info}"></c:out>
			</div>
		</c:if>

		<c:if test="${empty estagiario}">
			<div class="alert alert-warning" role="alert">Você ainda não
				está Cadastrado como estagiário do NPI</div>

			<div align="center" style="margin-bottom: 20px;">
				<a href="<c:url value="/estagiario/cadastrar" ></c:url>">
					<button class="btn btn-primary">
						Realizar Cadastro <span class="glyphicon glyphicon-plus"></span>
					</button>
				</a>
			</div>
		</c:if>

		<c:if test="${not empty estagiario}">
			<c:forEach var="estagiario" items="${estagiario}">
				<div align="center" style="margin-bottom: 20px;">
					<div align="center" style="margin-bottom: 20px;">
						<a id="editar"
							href="<c:url value="/estagiario/${estagiario.id}/contaspessoais" ></c:url>">
							<button class="btn btn-info">
								Cadastrar Contas Pessoais <span class="glyphicon glyphicon-pencil"></span>
							</button>
						</a>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>