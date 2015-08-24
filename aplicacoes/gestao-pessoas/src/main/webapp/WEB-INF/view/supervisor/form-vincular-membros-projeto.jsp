<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>Atualizar Membros do Projeto</title>
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />
	
<div class="container">
	<div class="row">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> Atualizar Membros</h3>
		</div>

		<div class="panel-body">
	        <div class="btn-group">
	            <button type="button" data-toggle="dropdown" class="btn btn-default dropdown-toggle">Selecione a Turma&nbsp;&nbsp;<span class="caret"></span></button>
	            <ul class="dropdown-menu">
	            	<c:forEach var="turma" items="${turmas}">
		                <li><a href="<c:url value="/supervisor/projeto/${projeto.id}/vincular/turma/${turma.id}" />">${turma.periodo.ano}.${turma.periodo.semestre} - ${turma.nome}</a></li>
					</c:forEach>
	            </ul>
	        </div>	

			<c:if test="${not empty estagiarios}">
				<h1 align="left" style="border-bottom: 1px solid #333;">Estagiários</h1>
				
				<form:form id="vincularParcicipanteForm" role="form" modelAttribute="projeto" servletRelativeAction="/supervisor/projeto/${projeto.id}/vincular/turma/${turma.id}" method="POST" cssClass="">
					<form:hidden path="id"/>
					<form:hidden path="nome"/>
					<form:hidden path="descricao"/>

					<table id="membros" class="table table-striped table-hover">
						<thead>
							<tr>
								<th>Selecione</th>
								<th>Matrícula</th>
								<th></th>
				           </tr>
				       </thead>
	
				       <tbody id="estagiariosProjeto" class="panel">
								<c:forEach var="estagiario" items="${estagiarios}" varStatus="cont">
								<tr class="form-group">
										<td>
											<form:checkbox id="membro${cont.index}" path="membros[${cont.index}].id" value="${estagiario.id}" checked="${estagiario.projeto.id eq projeto.id ? 'checked' : ''}"/>
											<label for="membro${cont.index}">${estagiario.nomeCompleto}</label>
										</td>
									
										<td>${estagiario.matricula}</td>
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
	</div>
</div>

	<jsp:include page="../modulos/footer1.jsp" />

    <script type="text/javascript">
		$(document).ready(function(){
			$(".menu #projetos").addClass("active");
		});
	</script>	

</body>
</html>