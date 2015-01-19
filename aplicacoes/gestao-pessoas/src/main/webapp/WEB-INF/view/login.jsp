<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
	<title>NPI - Gestão de Pessoas</title>
	<link href="<c:url value="/webjars/bootstrap/3.1.1/css/bootstrap.min.css" />" rel="stylesheet" />
	<link href="<c:url value="/resources/css/font-awesome.css" />" rel="stylesheet" />
	
	<link href="<c:url value="/resources/css/main-gestao.css" />" rel="stylesheet" />
</head>

<body onload='document.f.j_username.focus();'>
	<div class="container-fluid">

		<div class="row gp-topo">
			<h1 class="gp-titulo"><i class="fa fa-users fa-2x"></i>Gestão de Pessoas</h1>
		</div>
		<br>

		<div id="corpo" class="row">
			<div class="center col-md-4">
				<div class="panel panel-default transparent-div">
					
					<div class="panel-heading"><h3>Faça seu login</h3></div>
					
					<div class="panel-body">
						<c:if test="${not empty error}">
							<div class="gp-error"><i class="fa fa-times-circle"></i>${error}</div>
						</c:if>
		
						<c:if test="${not empty msg}">
							<div class="msg"> <i class="fa fa-info-circle"></i>${msg}</div>
						</c:if>
						
						<form name='f' action="<c:url value='j_spring_security_check' />" method='POST'>

                            <div class="form-group form-inline input-group">
                                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                <input type="text" class="form-control" placeholder="Usuario" name='j_username' value=''>
                            </div>

                            <div class="form-group form-inline input-group">
                                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                <input type="password" class="form-control" placeholder="Senha" name='j_username' value=''>
                            </div>
                            
                            <div class="pull-right">
								<input class="btn btn-default" name="reset" type="reset" value="Limpar" />
								<input class="btn btn-primary" name="submit" type="submit" value="Login" value="Login" /> 
                            </div>
                            
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer class="gp-footer">
		<p>Universidade Federal do Ceará - Todos os direitos reservados &copy;.</p>
    </footer>
	
	
	<script src="<c:url value="/webjars/jquery/2.1.0/jquery.js" />"></script>
	<script src="<c:url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js" />"></script>

</body>
</html>