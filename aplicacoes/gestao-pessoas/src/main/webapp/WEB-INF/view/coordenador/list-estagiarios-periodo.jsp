<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<meta charset="UTF-8"/>
	<jsp:include page="../modulos/header-estrutura.jsp" />
	<title>Estagiários</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<input id="current-data" type="hidden"/>

		<h4><b>Estagiarios</b></h4>
		<form id="filtroFrequenciaDaTurma" class="form-inline filtro">
			<label class="info">Selecione a turma:</label>

			<div class="form-group">
			<select id="semestreFiltro" name="semestre" class="selectpicker filtroSemestre" data-width="auto">
				<option value="">Selecione a turma</option>
				<option value="1">1</option>
				<option value="2">2</option>
			</select>
			</div>
		</form>
	</div>
	
	<div class="container">
		<div class="tab-pane active" id="viewEstagiarios">
<%-- 			<c:if test="${empty estagiarios}"><div class="alert alert-warning" role="alert">Não há Estagiários cadastrados.</div></c:if> --%>
			
			<c:if test="${not empty estagiarios}">
				<h1 align="left" style="border-bottom: 1px solid #333;">Estagiários </h1>

				<table id="professores" class="table table-striped">
					<thead>
						<tr class="">
							<th>Matricula</th>
							<th>Nome</th>
							<th></th>
			           </tr>
			       </thead>

			       <tbody class="panel">
						<c:forEach var="estagiario" items="${estagiarios}">
							<tr class="linha">
								<td>${estagiario.matricula}</td>
								<td>${estagiario.nomeCompleto}</td>
								<td align="right">
								</td>
							</tr>
						</c:forEach>
			       </tbody>
				</table>
			</c:if>
			<div id="relatorios" align="center" style="margin: 20px 0;" class="hidden">
				<a id="termos" class="btn btn-primary" href="<c:url value="/coordenador/jrreport" ></c:url>"><i class="fa fa-file-pdf-o"></i> Termo de Compromisso</a>
				<a id="declaracoes" class="btn btn-primary" href="<c:url value="/coordenador/declaracaoEstagio" ></c:url>"><i class="fa fa-file-pdf-o"></i> Declaração de Estágio</a>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>