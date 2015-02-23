<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="inicioEstagiarioNPI" class="flippant-back flippant-modal-dark flipper flipped" style="position: fixed; min-height: 136px; z-index: 9999;">

	<div class="container">
		<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-link"></span></a> Meu cadastro no NPI</h2>
		<form:form id="adicionarContasTurmaEstagiarioForm" role="form" commandName="estagiario" modelAttribute="estagiario" servletRelativeAction="/estagiario/contas-turma" method="POST" cssClass="form-horizontal">
			<form:hidden path="id"/>

			<div class="form-group">
				<div class="form-group">
					<div class="form-item">
						<label for="contaRedmine" class="col-sm-2 control-label">Conta Redmine:</label>
						<div class="col-sm-10">
					        <form:input id="contaRedmine" path="contaRedmine" class="form-control" placeholder="Conta Redmine" required="required"></form:input>
						</div>
					</div>
				</div>
	
				<div class="form-group">
					<div class="form-item">
						<label for="contaGithub" class="col-sm-2 control-label">Conta github:</label>
						<div class="col-sm-10">
					        <form:input id="contaGithub" path="contaGithub" class="form-control" placeholder="Conta github" required="required"></form:input>
						</div>
					</div>
				</div>
	
				<div class="form-group">
					<div class="form-item">
						<label for="contaHangout" class="col-sm-2 control-label">Conta Hangout:</label>
						<div class="col-sm-10">
					        <form:input id="contaHangout" path="contaHangout" class="form-control" placeholder="Conta Hangout" required="required"></form:input>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="form-item">
					<label for="minha-turma" class="col-sm-2 control-label">Selecione Turma:</label>
					<div class="col-sm-10" id="minha-turma">
					<c:forEach var="turma" items="${turmasSelect}" varStatus="cont">
					    <ul class="col-sm-5 list-unstyled horarios">
					        <li>
					        	<label class="btn btn-primary">
									<form:radiobutton path="turma.id" value="${turma.id}" required="required" cssClass="customError"/> <b>${turma.periodo.ano}.${turma.periodo.semestre} - ${turma.nome}, Sup. ${turma.supervisor.nome}</b>
					        	</label>
					        </li>
					        <li>
					            <ul class="horarios-dias">
									<c:forEach var="horario" items="${turma.horarios}" varStatus="cont">
									        <li>${horario.dia.labelDia} das ${horario.inicioExpediente} as ${horario.finalExpediente}</li>
									</c:forEach>
					            </ul>
					        </li>
						</ul>			
					</c:forEach>
					<div class="ap"></div>
				</div>
				</div>
			</div>

			<div class="form-group" align="center">
				<button name="submitContasTurma" type="submit" class="btn btn-primary">Cadastrar</button>
			</div>
		</form:form>
	</div>
</div>
