<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>
<head>
<jsp:include page="../modulos/header-estrutura.jsp" />
<title>Acompanhamento de Avaliação</title>
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="row">

			<div class="panel panel-primary">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-folder-open"></span> Acompanhamento de
						Avaliação
					</h2>

					<div class="pull-right">
						<a title="Voltar" class="btn btn-primary back"><span
							class="fa fa-arrow-circle-o-left"></span> Voltar</a>
					</div>
				</div>

				<div class="panel-body">

					<c:if test="${empty estagiario}">
						<div class="alert alert-warning" role="alert">Estagiário
							inexistente.</div>
					</c:if>

					<c:if test="${not empty estagiario}">
						<div class="form-group">
							<label class="col-sm-2 text-view-info"><strong>Nome
									do estagiário: </strong></label><label class="col-sm-3 text-view-info">${estagiario.nomeCompleto}</label>

							<label class="col-sm-1 text-view-info"><strong>Semestre:
							</strong></label><label class="col-sm-2 text-view-info">${estagiario.semestre}</label>
							<label class="col-sm-1 text-view-info"><strong>Curso:
							</strong></label><label class="col-sm-3 text-view-info">${estagiario.curso}</label>
						</div>
					</c:if>
				</div>
			</div>

			<div class="panel panel-info">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-television"></span> Seminário
					</h2>
				</div>

				<div class="panel-body">
					<c:if test="${empty turma.horarios}">
						<div class="alert alert-warning" role="alert">Expediente não
							definido.</div>
					</c:if>

					<c:if test="${not empty turma.horarios}">
						<div class="form-group">
							<label class="col-sm-12 text-view-info"><strong>Horários</strong></label>

							<table id="horarios-turma"
								class="table table-striped table-hover">
								<thead>
									<tr>
										<th>Horário</th>
										<th>Dia da Semana</th>
										<th>Início do Expediente</th>
										<th>Término do Expediente</th>
									</tr>
								</thead>
								<tbody class="text-view-info">
									<c:forEach var="horario" items="${turma.horarios}"
										varStatus="indice">
										<tr align="justify">
											<td>${indice.count}ª</td>
											<td>${horario.dia.labelDia}</td>
											<td>${horario.inicioExpediente}</td>
											<td>${horario.finalExpediente}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
					</c:if>
				</div>
			</div>
			<div class="panel panel-info">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-pencil"></span> Avaliação de estágio
					</h2>
					<div class="pull-right">
						<a href="<c:url value="/supervisor/turma/${turma.id}/acompanhamento-avaliacao/estagiario/${estagiario.id}/adicionar/" ></c:url>"
							title="Novo Avaliacao"><button class="btn btn-primary">
								<span class="fa fa-plus"></span> Avaliação
							</button></a>
					</div>
					<br>
				</div>
				<div class="panel-body">

		<c:if test="${not empty success }"><div class="alert alert-dismissible alert-success"><button type="button" class="close" data-dismiss="alert">×</button>${success}</div></c:if>

		<c:if test="${empty avaliacaoEstagio}">
				<div class="alert alert-warning" role="alert">Não há Avaliação cadastrada.</div>
			</c:if>
			
			<c:if test="${not empty avaliacaoEstagio}">
				<table id="table-avaliacaoEstagio" class="table table-striped table-hover">
					<thead>
						<tr class="">
							<th>Nota do Estágio</th>
							<th>Assiduidade e Disciplina</th>
							<th>Iniciativa e Produtividade</th>
							<th>Responsabilidade</th>
							<th>Relacionamento</th>
							<th></th>
			           </tr>
			       </thead>
			       <tbody class="text-view-info">
							<c:forEach var="avaliacaoEstagio" items="${avaliacaoEstagio}">
								<tr>
									<td>${avaliacaoEstagio.nota}</td>
									<td>${avaliacaoEstagio.fatorAssiduidadeDisciplina}</td>
									<td>${avaliacaoEstagio.fatorIniciativaProdutividade}</td>
									<td>${avaliacaoEstagio.fatorResponsabilidade}</td>
									<td>${avaliacaoEstagio.fatorRelacionamento}</td>
									
									<td align="right">
										<a href="<c:url value="/supervisor/turma/${avaliacaoEstagio.id}" />" title="Informações" class="btn btn-info informacao"><span class="fa fa-info"></span></a>
										<a href="<c:url value="/supervisor/turma/${avaliacaoEstagio.id}/editar" />" title="Editar" class="btn btn-success"><span class="fa fa-pencil"></span></a>
									</td>
							</tr>
						</c:forEach>
			       </tbody>
				</table>
				
				
			</c:if>
		
		</div>
			</div>

		</div>
	</div>
	<br>
	<br>
	<jsp:include page="../modulos/footer.jsp" />

</body>
</html>