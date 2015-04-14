<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="inicioEstagiarioNPI" class="flippant-back flippant-modal-dark flipper flipped" style="position: fixed; min-height: 136px; z-index: 9999;">

	<div class="container">
		<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-link"></span></a> Meu cadastro no NPI</h2>
		<form:form id="adicionarDadosPessoaisEstagiarioForm" role="form" commandName="estagiario" modelAttribute="estagiario" servletRelativeAction="/estagiario/meu-cadastro-npi" method="POST" cssClass="form-horizontal">
			<div class="form-group">
				<div class="form-item">
					<label for="nomeCompleto" class="col-sm-2 control-label">Nome Completo:</label>
					<div class="col-sm-10">
						<form:input id="nomeCompleto" path="nomeCompleto" cssClass="form-control" placeholder="Meu nome completo" required="required" />
						<div class="error-validation">
							<form:errors path="nomeCompleto"></form:errors>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="form-item">
					<label for="nomeMae" class="col-sm-2 control-label">Nome da Mãe:</label>
					<div class="col-sm-10">
						<form:input id="nomeMae" path="nomeMae" cssClass="form-control" placeholder="Nome Mãe" required="required" />
						<div class="error-validation">
							<form:errors path="nomeMae"></form:errors>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="form-item">
					<label for="telefone" class="col-sm-2 control-label">Telefone:</label>
					<div class="col-sm-4">
						<form:input id="telefone" path="telefone" cssClass="form-control telefone" placeholder="Telefone" required="required" />
					</div>
					<div class="error-validation">
						<form:errors path="telefone"></form:errors>
					</div>
				</div>
				
				<div class="form-item">
					<label for="dataNascimento" class="col-sm-2 control-label">Data nascimento:</label>
					<div class="col-sm-4">
						<form:input id="dataNascimento" type="text" path="dataNascimento" cssClass="form-control data" required="required"/>
						<div class="error-validation">
							<form:errors path="dataNascimento"></form:errors>
							<c:if test="${not empty error_inicio}">
								<span>${error_inicio}</span>
							</c:if>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="form-item">
					<label for="semestre" class="col-sm-2 control-label">Semestre:</label>
					<div class="col-sm-2">
						<form:input id="semestre" type="text" path="semestre" cssClass="form-control semestre" placeholder="semestre" size="40" required="required" />
						<div class="error-validation">
							<form:errors path="semestre"></form:errors>
						</div>
					</div>
				</div>

				<div class="form-item">
					<label for="curso" class="col-sm-1 control-label">Curso :</label>
					<div class="col-sm-3">
						<form:select path="curso" cssClass="selectpicker" data-width="auto" required="required">
							<form:options itemLabel="labelCurso" />
						</form:select>
						<div class="error-validation">
							<form:errors path="curso"></form:errors>
						</div>
					</div>
				</div>

				<div class="form-item">
					<label for="matricula" class="col-sm-1 control-label">Matricula:</label>
					<div class="col-sm-3">
						<form:input id="matricula" path="matricula" cssClass="form-control matricula" placeholder="Matricula" required="required" />
						<div class="error-validation">
							<form:errors path="matricula"></form:errors>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="form-item">
					<label for="endereco" class="col-sm-2 control-label">Endereço:</label>
					<div class="col-sm-10">
						<form:input id="endereco" path="endereco" cssClass="form-control" placeholder="Rua, Nº, Bairro" required="required" />
						<div class="error-validation">
							<form:errors path="endereco"></form:errors>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="form-item">
					<label for="cidade" class="col-sm-2 control-label">Cidade:</label>
					<div class="col-sm-2">
						<form:input id="cidade" path="cidade" cssClass="form-control" placeholder="Cidade" required="required" />
						<div class="error-validation">
							<form:errors path="cidade"></form:errors>
						</div>
					</div>
				</div>

				<div class="form-item">
					<label for="uf" class="col-sm-1 control-label">UF :</label>
					<div class="col-sm-2">
						<form:select id="uf" path="uf" cssClass="selectpicker" data-width="auto" required="required">
							<form:options itemLabel="estado" />
						</form:select>
						<div class="error-validation">
							<form:errors path="uf"></form:errors>
						</div>
					</div>
				</div>

				<div class="form-item">
					<label for="cep" class="col-sm-1 control-label">CEP :</label>
					<div class="col-sm-4">
						<form:input id="cep" path="cep" cssClass="form-control cep" placeholder="CEP" required="required" />
						<div class="error-validation">
							<form:errors path="cep"></form:errors>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group" align="center">
				<button type="submit" class="btn btn-primary">Cadastrar</button>
			</div>
		</form:form>
	</div>
</div>
