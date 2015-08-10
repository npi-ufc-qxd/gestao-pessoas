<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
		<title>Informações do Projeto</title>
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />

	<div class="container">
		<div class="tab-pane active" id="meus-projetos">

			<c:if test="${empty projeto}"><div class="alert alert-warning" role="alert">Não há Projetos cadastrados.</div></c:if>

			<h3 align="left" style="border-bottom: 1px solid #333;">Informações do Projeto</h3>

			<c:if test="${not empty projeto}">
				<div class="form-group">
					<label>Nome: </label><label>${projeto.nome}</label>
				</div>
	
				<div class="form-group">
					<label>Descrição: </label><label>${projeto.descricao}</label>
				</div>
	
				<h4 align="left" style="border-bottom: 1px solid #333;">Membros do Projeto</h4>
	
				<c:if test="${empty projeto.membros}"><div class="alert alert-warning" role="alert">Não há Membros vinculados a este projeto.</div></c:if>
	
				<c:if test="${not empty projeto.membros}">
					<table id="membros-projeto" class="table table-striped">
						<thead>
							<tr class="">
								<th class="col-sm-1">Matricula</th>
								<th class="col-sm-5">Nome</th>
				           </tr>
				       </thead>
		
				       <tbody class="panel">
							<c:forEach var="estagiario" items="${projeto.membros}">
								<tr class="linha">
									<td>${estagiario.matricula}</td>
									<td>${estagiario.pessoa.nome}</td>
								</tr>
							</c:forEach>
				       </tbody>
					</table>
				</c:if>
			</c:if>
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