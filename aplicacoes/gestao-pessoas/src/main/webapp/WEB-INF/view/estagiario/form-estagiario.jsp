<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${action eq 'cadastrar' }">
	<c:set var="url" value="/home/meu-cadastro"></c:set>
	<c:set var="titulo" value="Confirmação dos Dados"></c:set>
</c:if>
<c:if test="${action eq 'editar' }">
	<c:set var="url" value="/estagiario/editar-perfil"></c:set>
	<c:set var="titulo" value="Meu Dados"></c:set>
</c:if>

<html>
	<head>
		<title>Meus Dados</title>
		<jsp:include page="../modulos/header-estrutura.jsp" />
	</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

<div class="container">
	<div class="row">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h2 class="titulo-panels"><span class="fa fa-folder-open"></span> ${titulo}</h2>
			
			<div class="pull-right">
				<a title="Voltar" class="btn btn-primary back"><span class="fa fa-arrow-circle-o-left"></span> Voltar</a>
			</div>
		</div>

		<form:form id="form-estagiario" role="form" commandName="estagiario" modelAttribute="estagiario" servletRelativeAction="${url}" method="POST" cssClass="form-horizontal">
			<div class="panel-body">

				<form:hidden path="id"/>

				<h4 class="secao-form-h4">Dados Pessoais</h4>
				
				<div class="form-group">
					<div class="form-item col-sm-9">
						<label for="nomeCompleto" class="control-label">*Nome Completo:</label>
						<form:input id="nomeCompleto" path="nomeCompleto" cssClass="form-control" placeholder="Meu nome completo" required="required" value="${ empty estagiario.nomeCompleto ? usuario.nome : estagiario.nomeCompleto}" />
						<div class="error-validation"><form:errors path="nomeCompleto"></form:errors></div>
					</div>
					
					<div class="form-item col-sm-3">
						<fmt:formatDate var="nascimento" value="${empty estagiario.dataNascimento? usuario.nascimento : estagiario.dataNascimento}" pattern="dd/MM/yyyy" />
						<label for="dataNascimento" class="control-label">*Data de Nascimento:</label>
						<form:input id="dataNascimento" type="text" path="dataNascimento" cssClass="form-control data" required="required" value="${nascimento }"/>
						<div class="error-validation"> <form:errors path="dataNascimento"></form:errors></div>
					</div>
				</div>
		
				<div class="form-group">
					<div class="form-item col-sm-12">
						<label for="nomeMae" class="control-label">*Nome da Mãe:</label>
						<form:input id="nomeMae" path="nomeMae" cssClass="form-control caractere" placeholder="Nome Mãe" required="required"/>
						<div class="error-validation"><form:errors path="nomeMae"></form:errors></div>
					</div>
				</div>
		
				<div class="form-group">
					<div class="form-item col-sm-12">
						<label for="telefone" class=" control-label">*Telefone:</label>
						<form:input id="telefone" path="telefone" cssClass="form-control telefone" placeholder="Telefone" required="required" value="${empty estagiario.telefone? usuario.telefone : estagiario.telefone}"/>
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
						<label for="semestre" class="control-label">*Semestre:</label>
						<form:input type="number" id="inputSemestre" path="semestre" cssClass="form-control semestre" placeholder="Semestre" required="required" max="12" min="1" />			
						<div class="error-validation">
							<form:errors path="semestre"></form:errors>
						</div>
					</div>
				</div>
		
				<h4 class="secao-form-h4">Contas</h4>
				
				<div class="form-group">
		
					<div class="form-item col-sm-4">
						<label for="usuarioGithub" class="control-label">Usuario github:</label>
						<div class="input-group">
							<span class="input-group-addon">@</span>
							<form:input id="usuarioGithub" path="usuarioGithub" class="form-control" placeholder="Usuario github"></form:input>
						</div>
					</div>
		
					<div class="form-item col-sm-4">
						<label for="email" class="control-label">E-mail:</label>
						<div class="input-group">
							<span class="input-group-addon">@</span>
							<form:input id="email" path="email" type="email" class="form-control" placeholder="E-mail" value="${empty estagiario.email? usuario.email : estagiario.email}"></form:input>
						</div>
		
					</div>
				</div>
				
		
				
				<h4 class="secao-form-h4">Endereço</h4>
		
				<div class="form-group">
					<div class="form-item col-sm-12">
						<label for="endereco" class="control-label">*Endereço:</label>
						<form:input id="endereco" path="endereco" cssClass="form-control caractere" placeholder="Rua, Nº, Bairro" required="required" />
						<div class="error-validation"><form:errors path="endereco"></form:errors></div>
					</div>
				</div>
		
				<div class="form-group">
					<div class="form-item col-sm-4">
						<label for="cidade" class="control-label">*Cidade:</label>
						<form:input id="cidade" path="cidade" cssClass="form-control caractere" placeholder="Cidade" required="required" />
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
			</div>
			
			<div class="panel-footer" align="center">
				<div class="controls">
					<c:if test="${action eq 'cadastrar' }"><button type="submit" class="btn btn-primary"><span class="fa fa-thumbs-o-up"></span> Confirmar</button></c:if>
					<c:if test="${action eq 'editar' }"><button type="submit" class="btn btn-primary"><span class="fa fa-floppy-o"></span> Salvar alterações</button></c:if>
				</div>
			</div>
		</form:form>
	</div>
	</div>
</div><br><br>

	<jsp:include page="../modulos/footer.jsp" />
</body>
</html>
