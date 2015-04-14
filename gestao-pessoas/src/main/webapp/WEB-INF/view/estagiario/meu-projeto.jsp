<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Projetos</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="tab-pane active" id="meus-projetos">
			<h3 align="left" style="border-bottom: 1px solid #333;">Informações do Projeto</h3>

			<c:if test="${empty projeto}"><div class="alert alert-warning" role="alert">Voçe precisa de um projeto, converse com o seu cordenador!!!.</div></c:if>


			<c:if test="${not empty projeto}">
				<div class="form-group">
					<label>Nome: </label><label>${projeto.nome}</label>
				</div>
	
				<div class="form-group">
					<label>Descrição: </label><label>${projeto.descricao}</label>
				</div>
	
				<h4 align="left" style="border-bottom: 1px solid #333;">Participantes</h4>
	
				<c:if test="${empty projeto.membros}"><div class="alert alert-warning" role="alert">Não há Membros vinculados a este projeto.</div></c:if>
	
				<c:if test="${not empty projeto.membros}">
					<table id="membros-projeto" class="table table-striped">
						<thead>
							<tr class="">
								<th class="col-sm-5">Nome</th>
								<th></th>
				           </tr>
				       </thead>
		
				       <tbody class="panel">
							<c:forEach var="estagiario" items="${projeto.membros}">
								<tr class="linha">
									<td>${estagiario.pessoa.nome}</td>
								</tr>
							</c:forEach>
				       </tbody>
					</table>
				</c:if>
			</c:if>
		</div>
	</div>

</body>
</html>