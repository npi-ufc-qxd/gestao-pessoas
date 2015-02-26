<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="inicioEstagiarioNPI" class="flippant-back flippant-modal-dark flipper flipped" style="position: fixed; min-height: 136px; z-index: 9999;">
	
	<div class="container fuelux" style="color: #333;">
		<h2 id="titulo-cadastro-npi"><a class="header-anchor" href="#"><span class="glyphicon glyphicon-link"></span></a>Contas</h2>
			<div class="section">
				<div class="wizard" data-initialize="wizard" id="cadastroNPIWizard">
				<form:form id="meuCadastroNPIEstagiario" role="form" commandName="estagiario" modelAttribute="estagiario" servletRelativeAction="/estagiario/meu-cadastro-npi" method="POST" cssClass="form-horizontal">
					<div class="step-content">
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

							
							
						</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

