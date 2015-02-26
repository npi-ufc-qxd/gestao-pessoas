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
	<title>Reposições</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	<div class="container">
		<input id="current-data" type="hidden"/>

		<h4><b>Reposições</b></h4>
		<form id="filtroFrequenciaDaTurma" class="form-inline filtro">
			<label class="info">Selecione o periodo:</label>
			<div class="form-group">
				<input id="anoFiltro" name="ano" type="text" class="form-control filtroAno col-sm-1" placeholder="ano" size="4">
			</div>
		
			<div class="form-group">
			<select id="semestreFiltro" name="semestre" class="selectpicker filtroSemestre" data-width="auto">
				<option value="">Semestre</option>
				<option value="1">1</option>
				<option value="2">2</option>
			</select>
			</div>
			
			<div class="form-group">
				<select id="turmaFiltro" name="turma" class="selectpicker filtroTurma reposicaoTurma reposicao" data-width="auto"></select>
			</div>

			<div class="form-group status">
				<select id="statusReposicao" name="statusReposicao" class="selectpicker filtroReposicaoStatus union reposicao" data-width="auto">
					<option>Status</option>
					<option value="ATRASADO">Atrasado</option>
					<option value="FALTA">Falta</option>
				</select>
			</div>

		</form>
	</div>
	
	<div class="container">
		<div class="tab-pane active" id="viewEstagiarios">
<%-- 			<c:if test="${empty objeto}"><div class="alert alert-warning" role="alert">Não há reposições para este status nesta turma.</div></c:if> --%>
			
			<c:if test="${not empty objeto}">
				<h1 align="left" style="border-bottom: 1px solid #333;">Reposições </h1>
				
				
				<c:forEach var="estagiario" items="${objeto}" varStatus="i">
					<ol class="list-group" style="width: 48%; display: inline; float: left; margin-left: 1%;">
						<c:if test="${reposicao eq 'ATRASADO'}">
							<c:if test="${estagiario[2] mod 2 eq 0}">
								<c:set var="contReposicao" value="${estagiario[2]/2}"/>
							</c:if>						
						
							<c:if test="${estagiario[2] mod 2 eq 1}">
								<c:set var="contReposicao" value="${(estagiario[2] - 1)/2}"/>
							</c:if>
						</c:if>

						<c:if test="${reposicao eq 'FALTA'}">
							<c:set var="contReposicao" value="${estagiario[2]}"/>
						</c:if>

						<li class="list-group-item"><b>${estagiario[0]}</b> - ${estagiario[1]}</li>
						<c:forEach var="i" begin="1" end="${contReposicao}">
							<li class="list-group-item" id="reposicao${estagiario[3]}${i}">
								<form class="form-inline formReposicao" role="form">
									<div class="form-group" >
										<label><span class="badge">${i}</span></label>
										<input type="text" id="data_reposicao${estagiario[3]}${i}" class="form-control data" placeholder="Data de reposição">
									</div>
									<button type="button" class="btn btn-primary agendarreposicao" data-li="#reposicao${estagiario[3]}${i}" data-input="#data_reposicao${estagiario[3]}${i}" data-estagiario="${estagiario[3]}">Agendar Reposição</button>
								</form>
							</li>
						</c:forEach>								
					</ol>
				</c:forEach>
			</c:if>
		</div>

	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>