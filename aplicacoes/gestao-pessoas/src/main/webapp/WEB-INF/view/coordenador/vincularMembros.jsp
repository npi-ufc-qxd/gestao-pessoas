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
	<jsp:include page="../modulos/header-coordenador.jsp" />

	<div class="container">

		<form:form id="buscaEstagiariosForm" role="form" modelAttribute="filtro" servletRelativeAction="/coordenador/${projeto.id}/buscaEstagiarios" method="POST" cssClass="form-inline">
			<div class="form-group">
				<form:select id="ano" path="ano" class="form-control">
					<option value="2014">2014</option>
					<option value="2015">2015</option>
					<option value="2016">2016</option>
					<option value="2017">2017</option>
					<option value="2018">2018</option>
					<option value="2019">2019</option>
					<option value="2020">2020</option>
				</form:select>
			</div>
		
			<div class="form-group">
				<form:select id="semestre" path="semestre" class="form-control">
					<option value="1">1</option>
					<option value="2">2</option>
				</form:select>
			</div>
		
			<div class="form-group">
				<form:select id="turma" path="turma" class="form-control">
					<c:if test="${not empty turmas}">
						<form:options items="${turmas}" itemValue="id" itemLabel="codigo"  />
					</c:if>
				</form:select>
			</div>
		
			<div class="btn-group">
				<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> Buscar</button>
		    </div>	
		</form:form>	


	<c:if test="${not empty estagiarios}">
		<div class="tab-pane active" id="meus-projetos">
			<div style="clear:both"></div>
			<c:if test="${empty estagiarios}">
				<div class="alert alert-warning" role="alert">Não há estagiarios nesta turma.</div> 
			</c:if>
			<c:if test="${not empty estagiarios}">
				<form:form id="vincularParcicipanteForm" role="form" modelAttribute="projeto" servletRelativeAction="/coordenador/vincularMembros" method="POST" cssClass="form-horizontal">
					<form:hidden path="id"/>
					<form:hidden path="nome"/>
					<form:hidden path="descricao"/>					
				<div class="panel panel-default">
					<div class="panel-heading" align="center">
						<h4>Estagiarios</h4>
					</div>
					<!-- Table -->
					<table class="table" id="table">
						<thead>
							<tr>
								<th>#</th>
								<th>Nome</th>
								<th>Matricula</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="estagiario" items="${estagiarios}" varStatus="cont">
								<tr class="linha">
									<td>
										<form:checkbox path="membros[${cont.index}].id" value="${estagiario.id}" checked="${estagiario.projeto.id eq projeto.id ? 'checked' : ''}"/>
									</td>
									<td>${estagiario.pessoa.nome}</td>
									<td>${estagiario.matricula}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="controls">
					<input name="vincular" type="submit" class="btn btn-primary" value="Vincular" />
				</div>
				</form:form>
			</c:if>
		</div>
	</c:if>
	</div>
</body>
</html>