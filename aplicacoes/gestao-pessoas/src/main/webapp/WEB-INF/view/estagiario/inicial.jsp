<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="nome" value="${sessionScope.usuario.nome}"/>
<c:set var="nomeFormatado" value="${fn:substring(nome, 0, fn:indexOf(nome, ' '))}" />

<html>
	<head>
		<title>Inicio</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	
	<div class="container">
	<div class="row">
	<div class="panel panel-default">
	<div class="panel-body">

		<c:if test="${not empty success}">
			<div class="alert alert-dismissible alert-success">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<h4><strong>Parabéns! ${sessionScope.usuario.nome}</strong></h4>
				<c:out value="${success}"></c:out>
			</div>
		</c:if>

		<c:if test="${empty success && not possuiTurma}">
			<div class="alert alert-dismissible alert-success">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<h4><strong>Seja Bem Vindo(a)! ${sessionScope.usuario.nome}</strong></h4>
			</div>
		</c:if>

		<c:if test="${possuiTurma}">
			<div class="alert alert-dismissible alert-info">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<h4><strong>Seja Bem Vindo(a)!</strong></h4>
				<strong>${nomeFormatado},</strong> agora, você pode realizar sua <a href="#" class="alert-link">presença diaria</a>, além de visualizar mais informaçãoes sobre sua frequencia. Participe, pois é muito importante para sua avaliação!!!
			</div>
		</c:if>

		<c:if test="${not possuiTurma}">
			<div class="alert alert-dismissible alert-warning">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<p><strong>Atençao! ${nomeFormatado},</strong> aguarde, você sera vinculada a uma turma, desde já sinta-se parte deste grupo, NPI.</p> 
			</div>
		</c:if>

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Veja o que você consegue fazer!</h3>
			</div>
			<div class="panel-body">
				<div class="list-group">
					<a href="#" class="list-group-item label-success funcionalidades">
						<span class="list-group-item-text"><i class="fa fa-check"></i> Atualizar as informações do seu perfil.</span>
					</a>
					
					<c:if test="${possuiTurma}">
						<a href="#" class="list-group-item label-info funcionalidades">
							<span class="list-group-item-text"><i class="fa fa-check"></i> Realizar sua frequencia diaria, além de visualizar mais informações da sua frequencia.</span>
						</a>
						<a href="#" class="list-group-item label-warning funcionalidades">
							<span class="list-group-item-text"><i class="fa fa-check"></i> Visualizar as informações e os membros do projeto que esta participando.</span>
						</a>
						<a href="#" class="list-group-item label-success funcionalidades">
							<span class="list-group-item-text"><i class="fa fa-check"></i> Enviar a documentação do estagio.</span>
						</a>
						<a href="#" class="list-group-item label-info funcionalidades">
							<span class="list-group-item-text"><i class="fa fa-check"></i> Visualizar a sua Avaliação.</span>
						</a>
						<a href="#" class="list-group-item label-warning funcionalidades">
							<span class="list-group-item-text"><i class="fa fa-check"></i> Emitir a sua Declaração de Estagio.</span>
						</a>
					</c:if>
				</div>
			</div>
		</div>

	</div>
	</div>
	</div>
	</div>



	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>