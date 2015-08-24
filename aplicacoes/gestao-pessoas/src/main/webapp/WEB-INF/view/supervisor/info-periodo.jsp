<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<jsp:include page="../modulos/header-estrutura1.jsp" />

<title>Informações do Periodo</title>
</head>
<body>
	<jsp:include page="../modulos/header1.jsp" />
	
	<div class="container">
		<div class="tab-pane active" id="meus-periodos">
			<fmt:formatDate var="inicio" value="${periodo.inicio }" pattern="dd/MM/yyyy" />
			<fmt:formatDate var="termino" value="${periodo.termino }" pattern="dd/MM/yyyy" />

			<h3 align="left" style="border-bottom: 1px solid #333;">Periodo: ${periodo.ano}.${periodo.semestre}, de ${inicio} a ${termino}</h3>

			<c:if test="${not empty periodo}">
				<div class="col-sm-6">			
				<h4 align="left" style="border-bottom: 1px solid #333;"><span style="vertical-align:top;" class="badge ${fn:length(periodo.turmas) gt 0 ? 'badge-success' : 'badge-danger' }">${fn:length(periodo.turmas)}</span> Turmas deste periodo</h4>
				
				<c:if test="${empty periodo.turmas}"><div class="alert alert-warning" role="alert">Não há turmas vinculadas a este periodo.</div></c:if>
				
				<c:if test="${not empty periodo.turmas}">
					<table id="turmas-periodo" class="table table-striped">
						<thead>
							<tr class="">
								<th>Turma</th>
								<th>Supervisor</th>
								<th>N° de Estagiarios</th>
				           </tr>
				       </thead>

				       <tbody class="panel">
							<c:forEach var="turma" items="${periodo.turmas}">
								<tr class="linha">
									<td>${turma.nome}</td>
									<td>${turma.supervisor.nome}</td>
									<td><span class="badge ${fn:length(turma.estagiarios) gt 0 ? 'badge-success' : 'badge-danger' }">${fn:length(turma.estagiarios)}</span></td>
								</tr>
							</c:forEach>
				       </tbody>
					</table>
				</c:if><!-- Final Lista das Turmas -->
				</div>
				
				<div class="col-sm-6">
				<h4 align="left" style="border-bottom: 1px solid #333;"><span style="vertical-align:top;" class="badge ${fn:length(periodo.folgas) gt 0 ? 'badge-success' : 'badge-danger' }">${fn:length(periodo.folgas)}</span> Folgas deste periodo</h4>
				
				<c:if test="${empty periodo.folgas}"><div class="alert alert-warning" role="alert">Não há folgas registradas neste periodo.</div></c:if>
				
				<c:if test="${not empty periodo.folgas}">
					<table id="turmas-periodo" class="table table-striped">
						<thead>
							<tr class="">
								<th class="col-sm-1">Data</th>
								<th>Descricao</th>
								<th></th>
				           </tr>
				       </thead>

				       <tbody class="panel">
							<c:forEach var="folga" items="${periodo.folgas}">
								<tr class="linha">
									<fmt:formatDate var="data" value="${folga.data }" pattern="dd/MM/yyyy" />
									<td>${data}</td>
									<td>${folga.descricao}</td>
									<td align="right">
										<a href="<c:url value="/supervisor/periodo/${periodo.id}/folga/${folga.id}/editar" />" class="btn btn-success"><span class="glyphicon glyphicon-pencil"></span></a>
									</td>
								</tr>
							</c:forEach>
				       </tbody>
					</table>
				</c:if>
				</div>
			</c:if>
		</div>
	</div>
	
	<jsp:include page="../modulos/footer1.jsp" />
	
    <script type="text/javascript">
		$(document).ready(function(){
			$(".menu #periodos").addClass("active");
		});
	</script>	
</body>
</html>