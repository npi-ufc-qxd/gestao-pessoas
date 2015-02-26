<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Menbros do Projeto</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<h4><b>Adicionar Membros ao Projeto ${projeto.nome}</b></h4>
		<form id="filtroFrequenciaDaTurma" class="form-inline filtro">
			<input id="idProjeto" type="hidden" value="${projeto.id}" >
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
				<select id="turmaFiltro" name="turma" class="selectpicker filtroTurma estagiariosProjeto" data-width="auto"></select>
			</div>
		</form>
	</div>
	
	<div class="container">
		<div class="tab-pane active" id="viewEstagiarios">

<%-- 			<c:if test="${empty estagiarios}"><div class="alert alert-warning" role="alert">Não há estagiarios matriculados nesta turma.</div></c:if> --%>
			
			<c:if test="${not empty estagiarios}">
				<h1 align="left" style="border-bottom: 1px solid #333;">Estagiários</h1>
				
				<form:form id="vincularParcicipanteForm" role="form" modelAttribute="projeto" servletRelativeAction="/coordenador/vincular-membros-projeto" method="POST" cssClass="">
					<form:hidden path="id"/>
					<form:hidden path="nome"/>
					<form:hidden path="descricao"/>
				
					<table id="professores" class="table table-striped">
						<thead>
							<tr>
								<th>Selecione</th>
								<th>Matricula</th>
								<th>Nome</th>
								<th></th>
				           </tr>
				       </thead>
	
				       <tbody id="estagiariosProjeto" class="panel">
								<c:forEach var="estagiario" items="${estagiarios}" varStatus="cont">
								<tr class="form-group">
										<td>
											<form:checkbox id="membros" path="membros[${cont.index}].id" value="${estagiario.id}" checked="${estagiario.projeto.id eq projeto.id ? 'checked' : ''}"/>
										</td>
									
										<td>${estagiario.matricula}</td>
										<td>${estagiario.nomeCompleto}</td>
										<td align="right"></td>
									</tr>
								</c:forEach>
				       </tbody>
					</table><br>
					<div class="form-group" align="center">
						<input name="vincular" type="submit" class="btn btn-primary" value="Vincular" />
					</div>
		       </form:form>
			</c:if>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>