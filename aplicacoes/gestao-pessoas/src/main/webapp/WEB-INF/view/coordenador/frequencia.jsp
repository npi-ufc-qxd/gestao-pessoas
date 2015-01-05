<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Frequência</title>
</head>
<body>
	<jsp:include page="../modulos/header-coordenador.jsp" />
	<div class="container">
	
		<form:form id="buscaEstagiariosForm" role="form" modelAttribute="filtro" servletRelativeAction="/coordenador/frequencia" method="POST" cssClass="form-inline">
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
				<c:if test="${not empty turmas}">
					<form:select id="turma" path="turma" class="form-control">
						<form:option value="">Selecione uma turma</form:option>
						<form:options items="${turmas}" itemValue="id" itemLabel="codigo"  />
					</form:select>
				</c:if>
			</div>
		
			<div class="btn-group">
				<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> Buscar</button>
		    </div>	

			<div class="form-horizontal">
				<c:if test="${not empty turmas}">
					<form:checkboxes items="${turmas}"  path="turma" itemValue="id" itemLabel="codigo" cssClass=""/>
				</c:if>
			</div>
		    
		</form:form>	

		<div class="tab-pane active" id="meus-projetos">
			<div align="right" style="margin-bottom: 20px;">
				<a href="<c:url value="#/coordenador/reposicoes" ></c:url>">
					<button class="btn btn-primary">Agendar Reposições <span class="glyphicon glyphicon-calendar"></span></button>
				</a>
			</div>
		<h1>I</h1>
			<c:if test="${empty frequencias}">
				<div class="alert alert-warning" role="alert">Não há Estagiários cadastrados.</div>
			</c:if>
			<c:if test="${not empty frequencias}">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">
						<h4>Frequências</h4>
					</div>
					<!-- Table -->
					<table class="table" id="table">
						<thead>
							<tr>
								<th>Data</th>
								<th>Nome</th>
								<th>Matricula</th>
								<th>Presença</th>
								<th>Observação</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="frequencia" items="${frequencias}">
								<tr class="linha">
									<fmt:formatDate value="${frequencia.data}" type="date" pattern="dd/MM/yyyy" var="dataFormatada"/>	
									<td>${dataFormatada}</td>
									<td>${frequencia.estagiario.nomeCompleto}</td>
									<td>${frequencia.estagiario.matricula}</td>

									<td class="status" data-pk="${frequencia.id}">
										${frequencia.statusFrequencia}
									</td>

									<td class="observacao" data-pk="${frequencia.id}">${frequencia.observacao}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>



<!-- 		<div align="center"> -->
<!-- 				<ul class="nav nav-tabs"> -->
<%-- 			<c:forEach var="turma" items="${turmas}" varStatus="turmaCont"> --%>
<%-- 					<li class="${turmaCont.index eq 0 ? 'active' : '' }"><a data-toggle="tab" href="#${turma.codigo}">${turma.codigo} - ${turma.supervisor.nome}</a></li> --%>
<%-- 			</c:forEach> --%>
<!-- 			</ul> -->

<!-- 		    <div class="tab-content"> -->
<%-- 				<c:forEach var="turma" items="${turmas}" varStatus="turmaCont"> --%>
<%-- 			        <div id="${turma.codigo }" class="tab-pane fade ${turmaCont.index eq 0 ? 'in active' : '' }"> --%>
<!-- 			        </div> -->
<%-- 				</c:forEach> --%>
<!-- 			</div> -->
<!-- 		</div> -->


	</div>
	<jsp:include page="../modulos/footer.jsp" />
	<script type="text/javascript">
	</script>
	
</body>
</html>