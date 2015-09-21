<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura.jsp" />
		<title>Informações do Projeto</title>
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

<div class="container">
	<div class="row">

	<div class="panel panel-primary">

		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-briefcase"></span> Projeto</h2>
			
			<div class="pull-right">
				<a title="Voltar" class="btn btn-primary back"><span class="fa fa-arrow-circle-o-left"></span> Voltar</a>
			</div>
		</div>
		
		<div class="panel-body">
			<c:if test="${empty projeto}"><div class="alert alert-warning" role="alert">Não há Projetos cadastrados.</div></c:if>

			<c:if test="${not empty projeto}">
				<div class="form-group">
					<label class="col-sm-1 text-view-info"><strong>Nome: </strong></label><label class="col-sm-11 text-view-info">${projeto.nome}</label>
				</div>
			
				<div class="form-group">
					<label class="col-sm-1 text-view-info"><strong>Descrição: </strong></label><label class="col-sm-11 text-view-info">${projeto.descricao}</label>
				</div>
			</c:if>
		</div>
	</div>
	
	<div class="panel panel-success">
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-group"></span> Membros</h2>
			

			<div class="pull-right">
				<a href="<c:url value="/supervisor/projeto/${projeto.id}/vincular" />" title="Atualizar Vínculos" class="btn btn-success"><span class="glyphicon glyphicon-link"></span> Vínculos</a>
			</div>
		</div>

		<div class="panel-body">
			<c:if test="${empty projeto.membros}"><div class="alert alert-warning" role="alert">Não há Membros vinculados a este projeto.</div></c:if>
			
				<c:if test="${not empty projeto.membros}">
					<table id="membros-projeto" class="table table-striped table-hover">
						<thead>
							<tr class="">
								<th class="col-md-1">Matrícula</th>
								<th class="col-md-5">Nome</th>
								<th class="col-md-3">Curso</th>
								<th class="col-md-3">Frequência</th>
				           </tr>
				       </thead>
				       <tbody class="text-view-info">
							<c:forEach var="estagiario" items="${projeto.membros}">
								<tr class="linha">
									<td>${estagiario.matricula}</td>
									<td>${estagiario.nomeCompleto}</td>
									<td>${estagiario.curso.labelCurso}</td>
									<td>xx%</td>
								</tr>
							</c:forEach>
				       </tbody>
					</table>
				</c:if>
		</div>
	</div>
	
	</div>
</div>

	<jsp:include page="../modulos/footer.jsp" />
	
    <script type="text/javascript">
		$("#menu-projetos").addClass("active");
	</script>	
</body>
</html>