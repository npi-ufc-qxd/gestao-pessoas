<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="inicioEstagiarioNPI" class="flippant-back flippant-modal-dark flipper flipped" style="position: fixed; min-height: 136px; z-index: 9999;">
	
	<div class="container fuelux" style="color: #333;">
		<h2><a class="header-anchor" href="#"><span class="glyphicon glyphicon-link"></span></a>Meu cadastro no NPI</h2>
			<div class="section">
				<div class="wizard" data-initialize="wizard" id="cadastroNPIWizard">
					<ul class="steps" >
						<li data-step="1" class="active"><span class="badge badge-info">1</span>Dados Pessoais<span class="chevron"></span></li>
						<li data-step="2"><span class="badge">2</span>Contas<span class="chevron"></span></li>
						<li data-step="3"><span class="badge">3</span>Turma<span class="chevron"></span></li>
					</ul>

					<div class="actions">
						<button class="btn btn-default btn-prev" disabled="disabled"><span class="glyphicon glyphicon-arrow-left"></span>Anterior</button>
						<button class="btn btn-default btn-next" data-last="Finalizar">Próximo<span class="glyphicon glyphicon-arrow-right"></span></button>
					</div>						
					
				<form:form id="meuCadastroNPIEstagiario" role="form" commandName="estagiario" modelAttribute="estagiario" servletRelativeAction="/estagiario/meu-cadastro-npi" method="POST" cssClass="form-horizontal">
					<div class="step-content">
							<div class="step-pane alert active" data-step="1" id="dadosPessoais"><!-- INICIO PASSO 1 -->
								<div class="form-group">
									<div class="form-item">
										<label for="nomeCompleto" class="col-sm-2 control-label">Nome Completo:</label>
										<div class="col-sm-5">
											<form:input id="nomeCompleto" path="nomeCompleto" cssClass="form-control" placeholder="Nome do projeto" />
											<div class="error-validation"><form:errors path="nomeCompleto"></form:errors></div>
										</div>
									</div>
					
									<div class="form-item">
										<label for="dataNascimento" class="col-sm-2 control-label">Data nascimento:</label>
										<div class="col-sm-3">
											<form:input id="dataNascimento" type="text" path="dataNascimento" cssClass="form-control data" placeholder="Data Nascimento" style="z-index: 9999" />
											<div class="error-validation"><form:errors path="dataNascimento"></form:errors></div>
											<c:if test="${not empty error_inicio}">
												<div class="error-validation"><span>${error_inicio}</span></div>
											</c:if>
										</div>
									</div>
								</div>

								<div class="form-group">
									<div class="form-item">
										<label for="nomeMae" class="col-sm-2 control-label">Nome da Mãe:</label>
										<div class="col-sm-10">
											<form:input id="nomeMae" path="nomeMae" cssClass="form-control" placeholder="Nome Mãe" />
										</div>
									</div>
								</div>
					
								<div class="form-group">
									<div class="form-item">
										<label for="semestre" class="col-sm-2 control-label">Semestre :</label>
										<div class="col-sm-2">
											<form:input id="semestre" type="text" path="semestre" cssClass="form-control data" placeholder="semestre" size="40"/>
										</div>
									</div>
					
									<div class="form-item">
										<label for="curso" class="col-sm-1 control-label">Curso :</label>
										<div class="col-sm-3">
											<form:select path="curso" cssClass="selectpicker" data-width="auto">
												<form:options itemLabel="labelCurso"/>
											</form:select>
										</div>
									</div>
					
									<div class="form-item">
										<label for="matricula" class="col-sm-1 control-label">Matricula:</label>
										<div class="col-sm-3">
											<form:input id="matricula" path="matricula" cssClass="form-control" placeholder="Matricula" />
										</div>
									</div>
								</div>
					
								<div class="form-group">			
									<div class="form-item">
										<label for="endereco" class="col-sm-2 control-label">Endereço:</label>
										<div class="col-sm-10">
											<form:input id="endereco" path="endereco" cssClass="form-control" placeholder="Endereço" />
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<div class="form-item">
										<label for="cidade" class="col-sm-2 control-label">Cidade :</label>
										<div class="col-sm-2">
											<form:input id="cidade" path="cidade" cssClass="form-control" placeholder="Cidade" />
										</div>
									</div>
					
									<div class="form-item">
										<label for="uf" class="col-sm-1 control-label">UF :</label>
										<div class="col-sm-2">
											<form:input id="uf" path="uf" cssClass="form-control" placeholder="UF" />
										</div>
									</div>
					
									<div class="form-item">
										<label for="cep" class="col-sm-1 control-label">CEP :</label>
										<div class="col-sm-4">
											<form:input id="cep" path="cep" cssClass="form-control" placeholder="CEP" />
										</div>
									</div>
								</div>
					
								<div class="form-group">
									<div class="form-item">
										<label for="telefone" class="col-sm-2 control-label">Telefone :</label>
										<div class="col-sm-4">
											<form:input id="telefone" path="telefone" cssClass="form-control" placeholder="Telefone" />
										</div>
									</div>
								</div>
							</div><!-- FINAL PASSO 1 -->
	
	
							<div class="step-pane alert" data-step="2" id="conta"><!-- INICIO PASSO 2 -->
								<div class="form-group">
									<div class="form-group">
										<div class="form-item">
											<label for="contaRedmine" class="col-sm-2 control-label">Conta Redmine:</label>
											<div class="col-sm-10">
										        <form:input id="contaRedmine" path="contaRedmine" class="form-control" placeholder="Conta Redmine" aria-describedby="sizing-addon2"></form:input>
											</div>
										</div>
									</div>
	
									<div class="form-group">
										<div class="form-item">
											<label for="contaGithub" class="col-sm-2 control-label">Conta github:</label>
											<div class="col-sm-10">
										        <form:input id="contaGithub" path="contaGithub" class="form-control" placeholder="Conta github"></form:input>
											</div>
										</div>
									</div>
	
									<div class="form-group">
										<div class="form-item">
											<label for="contaHangout" class="col-sm-2 control-label">Conta Hangout:</label>
											<div class="col-sm-10">
										        <form:input id="contaHangout" path="contaHangout" class="form-control" placeholder="Conta Hangout"></form:input>
											</div>
										</div>
									</div>
								</div>
							</div><!-- FINAL PASSO 2 -->
	
	
							<div class="step-pane alert" data-step="3" id="turma"><!-- INICIO PASSO 3 -->
								<form:radiobuttons path="turma.id" items="${turmas }" itemLabel="codigo"  itemValue="id"/>
							</div>
						</div><!-- FINAL PASSO 3 -->
				</form:form>
			</div>
		</div>
	</div>
</div>

