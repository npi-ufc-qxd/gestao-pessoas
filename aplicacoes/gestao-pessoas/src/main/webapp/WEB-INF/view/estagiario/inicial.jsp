<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
	<head>
		<title>Estagiários</title>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
	</head>
<body>
	<c:if test="${estagiarioCadastrado}">
		<jsp:include page="../modulos/header1.jsp" />
	</c:if>

	<jsp:include page="../modulos/header1.jsp" />
	
	<div class="container">

		<div class="alert alert-dismissible alert-success">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong>Parabéns!</strong> <c:out value="${info}"></c:out>
		</div>

		<div class="alert alert-dismissible alert-info">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<h4>Seja Bem Vindo(a)!</h4>
			<strong>Nome, </strong> agora, você pode realizar sua <a href="#" class="alert-link">presença diaria</a>, além de visualizar mais informaçãoes sobre sua frequencia. Participe, pois é muito importante para sua avaliação!!!
		</div>

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Veja o que, você, consegue fazer!</h3>
			</div>
			<div class="panel-body">
				<div class="list-group">
					<a href="#" class="list-group-item label-warning">
						<span class="list-group-item-text"><i class="fa fa-check"></i> Atualizar as informações do seu perfil.</span>
					</a>
					<a href="#" class="list-group-item label-info">
						<span class="list-group-item-text"><i class="fa fa-check"></i> Realizar sua frequencia diaria, além de visualizar mais informações da sua frequencia.</span>
					</a>
					<a href="#" class="list-group-item label-success">
						<span class="list-group-item-text"><i class="fa fa-check"></i> Visualizar as informações e os membros do projeto que esta participando.</span>
					</a>
					<a href="#" class="list-group-item label-danger">
						<span class="list-group-item-text"><i class="fa fa-check"></i> Enviar a documentação do estagio.</span>
					</a>
					<a href="#" class="list-group-item label-warning">
						<span class="list-group-item-text"><i class="fa fa-check"></i> Visualizar a sua Avaliação.</span>
					</a>
					<a href="#" class="list-group-item label-info">
						<span class="list-group-item-text"><i class="fa fa-check"></i> Emitir a sua Declaração de Estagio.</span>
					</a>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer1.jsp" />
</body>
</html>