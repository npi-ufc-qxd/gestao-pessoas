<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Cadastro de Turma</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="novo-projeto" align="left">
			<div class="form" align="center">

				<c:if test="${not empty erro}">
					<div class="alert alert-danger alert-dismissible margin-top" role="alert">
						<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<c:out value="${erro}"></c:out>
					</div>
				</c:if>

				<h2>Nova Turma</h2>
				<form:form id="adicionarTurmaForm" role="form" commandName="turma" servletRelativeAction="/turma/${periodo.id}/turma" method="POST" cssClass="form-horizontal">
					<form:hidden path="id"/>				

					<div class="form-group">
						<div class="form-item">
							<label for="nome" class="col-sm-2 control-label">Nome:</label>
							<div class="col-sm-10" align="left">
								<form:input id="nome" path="nome" cssClass="form-control" placeholder="Nome da Turma" required="required"/>
								<div align="left" class="error-validation left"><form:errors path="nome"></form:errors></div>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="form-item">
							<label for="horarios" class="col-sm-2 control-label">Horarios:</label>
							<div class="col-sm-10" align="left">
				    <table class="table table-hover">
						<c:forEach items="${turma.horarios}" var="horario" varStatus="count">
							<tr data-check="#harario${count.index}">
					            <th>
					            	<form:checkbox path="horarios[${count.index}].id" value="${dias[count.index].labelDia}" label=" ${dias[count.index].labelDia}"/>
								</th>
					            <th>
									<form:input id="nome" type="time" path="horarios[${count.index}].inicioExpediente" cssClass="form-control hora" placeholder="Inicio do Expediente" required="required"/>
								</th>
					            <th>
									<form:input id="nome" type="time" path="horarios[${count.index}].finalExpediente" cssClass="form-control hora" placeholder="Final do Expediente" required="required"/>
								</th>
							</tr>
						
						
						</c:forEach>
					</table>

							</div>
						</div>
					</div>

					<div class="controls">
						<input name="submit" type="submit" class="btn btn-primary" value="Cadastrar" /> 
					</div>

					<c:forEach var="membro" items="${turma.estagiarios}" varStatus="cont">
						<form:hidden path="estagiarios[${cont.index}].id" value="${membro.id}" />
					</c:forEach>
				</form:form>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />
	<script type="text/javascript">
		
    $('.timepicker3').timepicker({
//     	minuteStep: 1,
//         template: 'modal',
//         appendWidgetTo: 'body',
//         showMeridian: false,
//         defaultTime: false,
    	
    	minuteStep: 5,
        showInputs: false,
        showSeconds: false,
        disableFocus: true
    });	
			
	
		$('tr').on('click', function() {
		})
	</script>
</body>
</html>