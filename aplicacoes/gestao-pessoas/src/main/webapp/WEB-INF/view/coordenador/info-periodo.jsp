<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />

<title>Projetos</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />
	
	<div class="container">
		<div class="tab-pane active" id="meus-periodos">

			<div align="right" style="margin-bottom: 20px;">
				<a href="<c:url value="/turma/${periodo.id}/turma" ></c:url>"><button class="btn btn-primary"><span class="fa fa-plus-square"></span> Turma</button></a>
				<a href="<c:url value="/coordenador/${periodo.id}/folga" />" class="btn btn-warning"><span class="fa fa-plus-square"></span> Folga</a>
			</div>

			<h3 align="left" style="border-bottom: 1px solid #333;">Informações do Periodo</h3>

			<c:if test="${not empty periodo}">
				<div class="form-group">
					<label class="col-sm-1">Semestre: </label><label>${periodo.ano}.${periodo.semestre}</label>
				</div>

				<fmt:formatDate var="data" value="${periodo.inicio }" pattern="dd/MM/yyyy" />
				<div class="form-group">
					<label class="col-sm-1">Incio: </label><label>${data}</label>
				</div>

				<fmt:formatDate var="data" value="${periodo.termino }" pattern="dd/MM/yyyy" />
				<div class="form-group">
					<label class="col-sm-1">Término: </label><label>${data}</label>
				</div>

				<h4 align="left" style="border-bottom: 1px solid #333;"><span style="vertical-align:top;" class="badge ${fn:length(periodo.turmas) gt 0 ? 'badge-success' : 'badge-danger' }">${fn:length(periodo.turmas)}</span> Turmas deste periodo</h4>
				
				<c:if test="${empty periodo.turmas}"><div class="alert alert-warning" role="alert">Não há turmas vinculadas a este periodo.</div></c:if>
				
				<c:if test="${not empty periodo.turmas}">
					<table id="turmas-periodo" class="table table-striped">
						<thead>
							<tr class="">
								<th>Supervisor</th>
								<th>Turma</th>
								<th>N° de Estagiarios</th>
								<th></th>
				           </tr>
				       </thead>

				       <tbody class="panel">
							<c:forEach var="turma" items="${periodo.turmas}">
								<tr class="linha">
									<td>${turma.supervisor.nome}</td>
									<td>${turma.codigo}</td>
									<td><span class="badge ${fn:length(turma.estagiarios) gt 0 ? 'badge-success' : 'badge-danger' }">${fn:length(turma.estagiarios)}</span></td>
									<td align="right">
										<a href="<c:url value="/turma/${turma.id}/detalhes" />" class="btn btn-info"><span class="glyphicon glyphicon-eye-open"></span></a>
									</td>
								</tr>
							</c:forEach>
				       </tbody>
					</table>
				</c:if><!-- Final Lista das Turmas -->

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
										<a href="<c:url value="/turma/${turma.id}/editar" />" class="btn btn-success"><span class="glyphicon glyphicon-pencil"></span></a>
										<a href="<c:url value="/turma/${turma.id}/excluir" />" class="btn btn-danger"> <span class="glyphicon glyphicon-trash"></span></a>
									</td>
								</tr>
							</c:forEach>
				       </tbody>
					</table>
				</c:if>
			</c:if>
		</div>
	</div><br><br><br>
</body>
</html>