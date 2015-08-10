<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${action eq 'cadastrar' }">
	<c:set var="url" value="/supervisor/periodo/${periodo.id}/adicionar-turma"></c:set>
	<c:set var="titulo" value="Nova Turma"></c:set>
</c:if>
<c:if test="${action eq 'editar' }">
	<c:set var="url" value="/supervisor/periodo/${periodo.id}/editar-turma/${turma.id}"></c:set>
	<c:set var="titulo" value="Editar Turma"></c:set>
</c:if>

<html>
	<head>
		<title>${titulo }</title>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />

<div class="container">
	<div class="row">
	<div class="panel panel-primary">
	
		<div class="panel-heading">
			<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> ${titulo}</h2>
		</div>

		<form:form id="adicionarTurmaForm" role="form" commandName="turma" servletRelativeAction="/supervisor/periodo/${periodo.id}/adicionar-turma"  method="POST" cssClass="form-horizontal">
			<div class="panel-body">
				<form:hidden path="id"/>				
				
				<div class="form-group">
					<div class="form-item col-sm-12">
						<fmt:formatDate var="inicio" value="${periodo.inicio}" pattern="dd/MM/yyyy" />
						<fmt:formatDate var="termino" value="${periodo.termino}" pattern="dd/MM/yyyy" />
						<label class="control-label text-view-info"><strong>Periodo</strong>: ${periodo.ano}.${periodo.semestre}, de ${inicio} a ${termino}</label>
					</div>
				</div>

				<div class="form-group">
					<div class="form-item col-sm-4">
						<label for="statusTurma" class="control-label">*Status:</label>
						<form:select id="statusTurma" path="statusTurma" cssClass="form-control selectpicker" required="required">
							<form:options itemLabel="label" />
						</form:select>
						<div class="error-validation"><form:errors path="statusTurma"></form:errors></div>
					</div>

					<div class="form-item col-sm-8">
						<label for="nomeDaTurma" class="control-label">*Nome da Turma:</label>
						<form:input id="nomeDaTurma" path="nome" cssClass="form-control" placeholder="Nome da Turma" required="required" />
						<div class="error-validation"><form:errors path="nome"></form:errors></div>
					</div>

				</div>

				<h5>Defina o Expediente</h5>
				<div class="form-group">
					<c:if test="${not empty horarioError}">
						<div class="form-item col-sm-12 has-error">
							<span class="help-block"><c:out value="${horarioError}"></c:out></span>
						</div>
					</c:if>
				
					<div class="form-item col-sm-4">
						<label for="diaDaSemana" class="control-label">*Selecione o Dia da Semana:</label>
						<select id="diaDaSemana" class="form-control selectpicker" required="required">
							<c:forEach items="${dias}" var="diaDaSemana" varStatus="contador">
								<option value="${diaDaSemana }">${diaDaSemana.labelDia }</option>
							</c:forEach>
						</select>
					</div>

					<div id="inicioDoExpediente" class="form-item col-sm-3">
						<label class="control-label">*Inicio do Expediente:</label>
						<div class="bfh-timepicker" data-name="inicioDoExpediente" data-placeholder="Inicio do Expediente" data-time=""></div>
					</div>
					<div id="finalDoExpediente" class="form-item col-sm-3">
						<label class="control-label">*Final do Expediente:</label>
						<div class="bfh-timepicker" data-name="finalDoExpediente" data-placeholder="Final do Expediente" data-time=""></div>
					</div>
					<div class="form-item col-sm-2">
						<label class="control-label">&nbsp;&nbsp;&nbsp;</label>
						<button type="button" class="form-control btn btn-primary add-more">Adicionar</button>
					</div>
				</div>
				
				<div id="listaHorario"></div>
			</div>
			
			<div class="panel-footer" align="center">
				<div class="controls">
					<c:if test="${action eq 'cadastrar' }"><button type="submit" class="btn btn-primary">Cadastrar</button></c:if>
					<c:if test="${action eq 'editar' }"><button type="submit" class="btn btn-success">Salvar alterações</button></c:if>
				</div>
			</div>
		</form:form>
	</div>
	</div>
</div>

	<jsp:include page="../modulos/footer1.jsp" />
	
    <script type="text/javascript">
		$(document).ready(function(){

			$(".menu #periodos").addClass("active");

			var limiteHorario = 5
			var indice = 0;

			$(".add-more").click(function(e){
				if(limiteHorario > 0){
					var diaDaSemana = $('#diaDaSemana').val();
					var inicioExpediente = $('input[name=inicioDoExpediente]').val();
					var finalExpediente = $('input[name=finalDoExpediente]').val();

					if(inicioExpediente == '') {
						$("#inicioDoExpediente").addClass('has-error');
					}

					if(finalExpediente == '') {
						$("#finalDoExpediente").addClass('has-error');
					}

					if(inicioExpediente == '' || finalExpediente == '') {
						return;
					}

					var idHorario = "horario" + indice;
					
					var horario = 				
						'<div id="'+ idHorario +'" class="form-group">' +
							'<div class="form-item col-sm-4">' +
							'<input class="form-control" type="text" name="horarios['+ indice +'].dia" value="'+ diaDaSemana +'" readonly/>' +
						'</div>' +
						
						'<div class="form-item col-sm-3">' +
							'<div class="input-group">' +
								'<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>' +						
								'<input class="form-control" type="text" name="horarios['+ indice +'].inicioExpediente" value="'+ inicioExpediente +'" readonly/>' +
							'</div>' +
						'</div>' +
						
						'<div class="form-item col-sm-3">' +
							'<div class="input-group">' +
								'<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>' +						
								'<input class="form-control" type="text" name="horarios['+ indice +'].finalExpediente" value="'+ finalExpediente +'" readonly/>' +
							'</div>' +
						'</div>' +
		
						'<div class="form-item col-sm-2">' +
							'<button value="#'+ idHorario +'" type="button" class="form-control btn btn-danger remove-me">Excluir</button>' +
						'</div>' +
					'</div>';

					$('#listaHorario').append(horario);
					limiteHorario--;
					indice++;
				}
			});
			
			$("#listaHorario").on("click", ".remove-me", function(e){
				var horarioID = this.value;
				$(horarioID).remove();
				limiteHorario++;
				atualizarIndice();
				e.preventDefault();
			});

			function atualizarIndice(){
				$( "#listaHorario" ).children().each(function( index ) {
					indice = index;
					var idHorario =  '#' + $(this).attr('id');
					var dia = "horarios[" + indice + "].dia"
 					var inicioExpediente = "horarios[" + indice + "].inicioExpediente";
 					var finalExpediente = "horarios[" + indice + "].finalExpediente";
 					var value = "#horario" + indice
 					$(idHorario).find("input[name$='.dia']").attr("name", dia);
 					$(idHorario).find("input[name$='.inicioExpediente']").attr("name", inicioExpediente);
 					$(idHorario).find("input[name$='.finalExpediente']").attr("name", finalExpediente);
 					$(idHorario).find(".remove-me").attr("value", idHorario);
 					$(idHorario).attr("id", "horario"+indice);
				});
			}
			
		});
	</script>		
	
	
</body>
</html>
