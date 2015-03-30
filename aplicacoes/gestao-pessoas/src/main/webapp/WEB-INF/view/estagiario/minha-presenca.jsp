<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<html>
<head>
	<title>Minha Presença oioi</title>
	<jsp:include page="../modulos/header-estrutura.jsp" />
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	
	<div class="container">
		<c:if test="${not empty msg}">
			<div class="alert alert-info msg"> <i class="fa fa-info-circle"> </i> ${msg}</div>
		</c:if>

		<c:if test="${liberarPresenca}">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Minha Presença</h3>
				</div>
				<form class="form-horizontal" action="/gestao-pessoas/estagiario/minha-presenca" method="POST">
					<div class="panel-body">
						<c:if test="${not empty error}">
							<div class="login-error"><i class="fa fa-times-circle-o"></i> ${error}</div>
						</c:if>
	
				        <div class="form-group">
				            <label for="cpf" class="control-label col-xs-2">CPF</label>
				            <div class="col-xs-10">
				                <input type="text" class="form-control cpfNumeros" id="cpf" name="cpf" placeholder="CPF">
				            </div>
				        </div>
				        <div class="form-group">
				            <label for="senha" class="control-label col-xs-2">Senha</label>
				            <div class="col-xs-10">
				                <input type="password" class="form-control" id="senha" name="senha" placeholder="Senha">
				            </div>
				        </div>
					</div> 
					<div class="panel-footer clearfix">
						<input type="submit" class="btn btn-success" id="estouPresenete" name="estouPresenete" value="Estou Presenete!!!">
					</div>
				</form>
			</div>
		</c:if>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>