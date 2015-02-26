<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
	<title>NPI - Gestão de Pessoas</title>
	<link href="<c:url value="/resources/img/gp-icon.png" />" rel="shortcut icon">
	
	<link href="<c:url value="/webjars/bootstrap/3.3.2/css/bootstrap.min.css" />" rel="stylesheet" />
	<link href="<c:url value="/webjars/font-awesome/4.3.0/css/font-awesome.css" />" rel="stylesheet" />
	<link href="<c:url value="/resources/css/login-gestao.css" />" rel="stylesheet" />

	<script src="<c:url value="/webjars/jquery/2.1.3/jquery.js" />"></script>
	<script src="<c:url value="/webjars/bootstrap/3.3.2/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/js/login.js" />"></script>
	<script src="<c:url value="/webjars/jquery-maskedinput/1.3.1/jquery.maskedinput.min.js" />"></script>
	

</head>

<body onload='document.f.j_username.focus();'>

	<div class="login-container" align="center">
		<div class="login-header" align="center">
			<h3 class="">Gestão NPI</h3>
			<div><span class="fa fa-group fa-5x"></span></div>
		</div>
		
		<div class="login-form">
			
			<div class="login-text">
				<span id="title">Faça seu login</span>
			</div>
				
			<c:if test="${not cadastro}">
				<form id="login-form" name='f' action="<c:url value='j_spring_security_check' />" method='POST'>
	
					<c:if test="${not empty error}">
						<div class="login-error"><i class="fa fa-times-circle-o"></i> ${error}</div>
					</c:if>
		
					<c:if test="${not empty info}">
						<div class="alert alert-info msg"> <i class="fa fa-info-circle"></i> ${info}</div>
					</c:if>
				
					<div class="form-group form-inline input-group input-login">
					    <span class="input-group-addon"><i class="fa fa-user"></i></span>
					    <input class="form-control cpf" type="text" name='j_username' value='' placeholder="cpf">
					</div>
					
					<div class="form-group form-inline input-group input-login">
					    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
					    <input class="form-control" type="password" name='j_password' value='' placeholder="senha">
					</div>
					
					<div>
						<a id="cadastre-se" href="javascript:;" class="esqueci-senha pull-left">Cadastre-se</a>
						<input class="btn btn-primary pull-right" name="submit" type="submit" value="Login" value="Login" />
					</div>
				</form>
			
			</c:if>
			
			<!-- CADASTRO -->
			<div id="cadastrar" class="" align="left">
				<form:form id="cadastro-form" name="cadastro" commandName="pessoa" role="form" modelAttribute="pessoa" servletRelativeAction="/estagiario/cadastre-se" method="POST" cssClass="form-horizontal">

				    <div class="error-validation"><form:errors path="nome"></form:errors></div>
					<div class="form-group form-inline input-group input-login">
					    <span class="input-group-addon input-group-addon-gp">Nome</span>
					    <form:input id="nome" path="nome" cssClass="form-control" placeholder="Nome" />
					</div>
	
				    <div class="error-validation"><form:errors path="email"></form:errors></div>
					<div class="form-group form-inline input-group">
					    <span class="input-group-addon input-group-addon-gp">E-mail</span>
					    <form:input id="email" path="email" cssClass="form-control" placeholder="E-mail" />
					</div>
	
				    <div class="error-validation"><form:errors path="cpf"></form:errors></div>
					<div class="form-group form-inline input-group input-login">
					    <span class="input-group-addon input-group-addon-gp">CPF</span>
					    <form:input id="cpf" path="cpf" cssClass="form-control" placeholder="CPF" /><br>
					</div>
	
				    <div class="error-validation"><form:errors path="password"></form:errors></div>
					<div class="form-group form-inline input-group input-login">
					    <span class="input-group-addon input-group-addon-gp">Senha</span>
					    <form:input id="senha" path="password" type="password" cssClass="form-control" placeholder="Senha" />
					</div>
					
					<div>
						<a id="logar" href="#" class="esqueci-senha pull-left">Login</a>
						<input class="btn btn-primary pull-right" name="cadastrar" type="submit" value="Cadastrar" />
					</div>
				</form:form>
			
			</div>
			
			
			
		</div>
	</div>
	<c:if test="${cadastro}">
		<script type="text/javascript">
		$(document).ready(function() {
			$("#title").text("Cadastre-se");
			$("#cadastrar").show();
		});
			
		</script>
	</c:if>
</body>
</html>