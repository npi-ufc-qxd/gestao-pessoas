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
		<h4><b>Atualizar menbros do projeto ${projeto.nome}</b></h4>
		<form id="filtroFrequenciaDaTurma" class="form-inline filtro">
			<div class="form-group">
				<select id="turma" name="turma" class="selectpicker selectTurma">
					<option value="">Selecione a Turma</option>

					<c:forEach var="turma" items="${turmas}">
						<option value="${turma.id}">${turma.periodo.ano}.${turma.periodo.semestre} - ${turma.nome}</option>
					</c:forEach>
				</select>
			</div>
		</form>
	</div>

	<div class="container">
		<div class="tab-pane active" id="viewEstagiarios">
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

	<jsp:include page="../modulos/footer.jsp" />

	<script type="text/javascript">
		$(".selectTurma").change(function() {
			var idTurma = $(this).val().trim();

			if (!isNaN(idTurma)) {
				loadEstagiariosTurma(idTurma, '/gestao-pessoas/coordenador/estagiarios-turma');
			}
		});

		function loadEstagiariosTurma(idTurma, url) {
			console.log('loadEstagiariosTurma :' + turma);
			
			$.ajax({
				url: url,
				type: "POST",
				dataType: "html",
				data: {
					"turma" : idTurma,
				},
				success: function(result) {
					loadEstagiarios(result);
				},
				error: function(error) {
					alert("error");
				}
			});
		}
		
		function loadEstagiarios(result) {	
			$("#viewEstagiarios").html($(result).find("#viewEstagiarios"));
			$("#viewEstagiarios").removeClass('hidden');
		}
		
		
	</script>
	
</body>
</html>