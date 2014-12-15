<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Projetos</title>
</head>
<body>
	<jsp:include page="../modulos/header-coordenador.jsp" />
	
	<div class="container">
		<input id="current-periodo" type="hidden"/>
		<div>
			<select id="periodo" name="periodo" class="selectpicker">
			</select>
		</div>
		<div class="form" align="center">
			<h1>Periodos</h1>
		</div>
		<table id="periodos">
			<thead>
				<tr>
					<th data-column-id="ano">Ano</th>
					<th data-column-id="semestre">Semestre</th>
					<th data-column-id="inicio">Inicio</th>
					<th data-column-id="termino">Término</th>
					<th data-column-id="acoes">Ações</th>
				</tr>
			</thead>
		</table>
	</div>
	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>