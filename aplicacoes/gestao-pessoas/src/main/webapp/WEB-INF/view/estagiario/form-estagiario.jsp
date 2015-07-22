<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Estagiário</title>
		<jsp:include page="../modulos/header-estrutura1.jsp" />
	</head>
<body>

	<jsp:include page="../modulos/header1.jsp" />




<div class="container">
	<c:if test="${action eq 'cadastrar' }">
		<c:set var="url" value="/home/meu-cadastro"></c:set>
		<c:set var="titulo" value="Meu Cadastro"></c:set>
	</c:if>
	<c:if test="${action eq 'editar' }">
		<c:set var="url" value="/estagiari/editar-perfil"></c:set>
		<c:set var="titulo" value="Meu Dados"></c:set>
	</c:if>


		<div class="panel panel-default">
			<div class="panel-body">



	<div class="row">
		<div class="col-sm-1"></div>

		<div class="col-sm-10">
			<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-user"></span></a> ${titulo}</h2>
			
			<form:form id="DadosPessoaisEstagiarioForm" role="form" commandName="estagiario" modelAttribute="estagiario" servletRelativeAction="${url}" method="POST" cssClass="form-horizontal">

				<form:hidden path="id"/>

				<h4 class="secao-form-h4">Informações do Estágio</h4>
		
				<div class="form-group">
					<div class="form-item col-sm-4">
						<label for="localEstagio" class="control-label">*Local do Estagio:</label>
						<form:select id="localEstagio" path="localEstagio" cssClass="form-control selectpicker" required="required">
							<form:options itemLabel="labelLocal" />
						</form:select>
						<div class="error-validation"><form:errors path="localEstagio"></form:errors></div>
					</div>
				</div>
		
				<h4 class="secao-form-h4">Dados Pessoais</h4>
				
				<div class="form-group">
					<div class="form-item col-sm-9">
						<label for="nomeCompleto" class="control-label">*Nome Completo:</label>
						<form:input id="nomeCompleto" path="nomeCompleto" cssClass="form-control" placeholder="Meu nome completo" required="required" />
						<div class="error-validation"><form:errors path="nomeCompleto"></form:errors></div>
					</div>
					
					<div class="form-item col-sm-3">
						<label for="dataNascimento" class="control-label">*Data de Nascimento:</label>
						<form:input id="dataNascimento" type="text" path="dataNascimento" cssClass="form-control data" required="required" />
						<div class="error-validation">
							<form:errors path="dataNascimento"></form:errors>
							<c:if test="${not empty error_inicio}"><span>${error_inicio}</span></c:if>
						</div>
					</div>
				</div>
		
				<div class="form-group">
					<div class="form-item col-sm-12">
						<label for="nomeMae" class="control-label">*Nome da Mãe:</label>
						<form:input id="nomeMae" path="nomeMae" cssClass="form-control" placeholder="Nome Mãe" required="required" />
						<div class="error-validation"><form:errors path="nomeMae"></form:errors></div>
					</div>
				</div>
		
				<div class="form-group">
					<div class="form-item col-sm-12">
						<label for="telefone" class=" control-label">*Telefone:</label>
						<form:input id="telefone" path="telefone" cssClass="form-control telefone" placeholder="Telefone" required="required" />
						<div class="error-validation">
							<form:errors path="telefone"></form:errors>
						</div>
					</div>
				</div>
		
				<h4 class="secao-form-h4">Informações do Curso</h4>
		
				<div class="form-group">
					<div class="form-item col-sm-4">
						<label for="matricula" class="control-label">*Matricula:</label>
						<form:input id="matricula" path="matricula" cssClass="form-control" placeholder="Matricula" required="required" maxlength="7" size="7" />
						<div class="error-validation">
							<form:errors path="matricula"></form:errors>
						</div>
					</div>
		
					<div class="form-item col-sm-4">
						<label for="curso" class="control-label">*Curso:</label>
						<form:select path="curso" cssClass="form-control selectpicker" required="required">
							<form:options itemLabel="labelCurso" />
						</form:select>
						<div class="error-validation"><form:errors path="curso"></form:errors></div>
					</div>
		
					<div class="form-item col-sm-4">
						<label for="curso" class="control-label">*Semestre:</label>
						<form:input type="number" id="semestre" path="semestre" cssClass="form-control semestre" placeholder="Semestre" required="required" />			
						<div class="error-validation">
							<form:errors path="semestre"></form:errors>
						</div>
					</div>
				</div>
		
				<h4 class="secao-form-h4">Contas</h4>
				
				<div class="form-group">
					<div class="form-item col-sm-4">
						<label for="contaRedmine" class=" control-label">Conta Redmine:</label>
						<div class="input-group">
							<span class="input-group-addon">@</span>
							<form:input id="contaRedmine" path="contaRedmine" class="form-control" placeholder="Conta Redmine" aria-describedby="sizing-addon2"></form:input>
						</div>
					</div>
		
					<div class="form-item col-sm-4">
						<label for="contaGithub" class="control-label">Conta github:</label>
						<div class="input-group">
							<span class="input-group-addon">@</span>
							<form:input id="contaGithub" path="contaGithub" class="form-control" placeholder="Conta github"></form:input>
						</div>
					</div>
		
					<div class="form-item col-sm-4">
						<label for="contaHangout" class="control-label">Conta Hangout:</label>
						<div class="input-group">
							<span class="input-group-addon">@</span>
							<form:input id="contaHangout" path="contaHangout" class="form-control" placeholder="Conta Hangout"></form:input>
						</div>
		
					</div>
				</div>
				
		
				
				<h4 class="secao-form-h4">Endereço</h4>
		
				<div class="form-group">
					<div class="form-item col-sm-12">
						<label for="endereco" class="control-label">*Endereço:</label>
						<form:input id="endereco" path="endereco" cssClass="form-control" placeholder="Rua, Nº, Bairro" required="required" />
						<div class="error-validation"><form:errors path="endereco"></form:errors></div>
					</div>
				</div>
		
				<div class="form-group">
					<div class="form-item col-sm-4">
						<label for="cidade" class="control-label">*Cidade:</label>
						<form:input id="cidade" path="cidade" cssClass="form-control" placeholder="Cidade" required="required" />
						<div class="error-validation"><form:errors path="cidade"></form:errors></div>
					</div>
		
					<div class="form-item col-sm-4">
						<label for="uf" class="control-label">*UF:</label>
						<form:select id="uf" path="uf" cssClass="form-control selectpicker" required="required">
							<form:options itemLabel="estado" />
						</form:select>
						<div class="error-validation"><form:errors path="uf"></form:errors></div>
					</div>
		
					<div class="form-item col-sm-4">
						<label for="cep" class="control-label">*CEP :</label>
						<form:input id="cep" path="cep" cssClass="form-control cep" placeholder="CEP" required="required" />
						<div class="error-validation"><form:errors path="cep"></form:errors></div>
					</div>
				</div>
		
				<div class="form-group">
					<div class="form-item col-sm-12" align="center">
						<c:if test="${action eq 'cadastrar' }"><button type="submit" class="btn btn-primary">Cadastrar</button></c:if>
						<c:if test="${action eq 'editar' }"><button type="submit" class="btn btn-success">Salvar alterações</button></c:if>
					</div>
				</div>
			</form:form>
		</div>
		
		<div class="col-sm-1"></div>
		
	</div>
	
	</div>
	</div>
		
	
	
</div>


	<jsp:include page="../modulos/footer1.jsp" />

</body>
</html>
