<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="../modulos/header-estrutura1.jsp" />

<title>Perfil</title>
</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />

	<div class="container">

				<h3 align="left" style="border-bottom: 1px solid #333;">Informações da Turma</h3>

				<div class="form-group">
					<label class="col-sm-1">Supervisor: </label><label>${turma.supervisor.nome}</label>
				</div>

				<div class="form-group">
					<label class="col-sm-1">Turma: </label><label>${turma.nome}</label>
				</div>

				<div class="form-group">
					<label class="col-sm-1">Semestre: </label><label>${turma.periodo.ano}.${turma.periodo.semestre}</label>
				</div>
	

	</div>
</body>
</html>