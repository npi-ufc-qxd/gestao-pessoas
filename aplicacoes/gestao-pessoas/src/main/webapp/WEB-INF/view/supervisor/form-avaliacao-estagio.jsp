<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${action eq 'cadastrar' }">
	<c:set var="url" value="/supervisor/turma/${turma.id}/acompanhamento-avaliacao/estagiario/${estagiario.id}/adicionar/"></c:set>
	<c:set var="titulo" value="Nova Avaliação"></c:set>
</c:if>
<c:if test="${action eq 'editar' }">
	<c:set var="url"
		value="/supervisor/turma/${turma.id}/avaliacao/${avaliacaoRendimento.id}/estagiario/${estagiario.id}/editar"></c:set>
	<c:set var="titulo" value="Editar Avaliação"></c:set>
</c:if>

<html>
<head>
<title>${titulo }</title>
<jsp:include page="../modulos/header-estrutura.jsp" />
</head>
<body>
	<jsp:include page="../modulos/header.jsp" />

	<div class="container">
		<div class="row">

			<div class="panel panel-primary">
				<div class="panel-heading">
					<h2 class="titulo-panels">
						<span class="fa fa-folder-open"></span> ${titulo}
					</h2>

					<div class="pull-right">
						<a title="Voltar" class="btn btn-default back"><span
							class="fa fa-arrow-left"></span> Voltar</a>
					</div>
				</div>

				<form:form id="form-avaliacao-estagio" role="form"
					commandName="avaliacaoRendimento" servletRelativeAction="${url}"
					method="POST" cssClass="form-horizontal">
					<div class="panel-body">
						<form:hidden path="id" />

						<div class="form-group">
							<div class="form-item col-sm-12">
								<fieldset>
									<legend>Assuidade e Disciplina</legend>
									
									<h4>Frequência</h4>
									<div class="col-lg-12">
										<div class="radio">
										 
											<input type="radio" name="frequencia" id="frequencia" value="${frequencias[0]}" checked>${frequencias[0].item}<br>
										</div>
									
										<div class="radio">
											<input type="radio" name="frequencia" id="frequencia" value="${frequencias[1]}">${frequencias[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="frequencia" value="${frequencias[2]}">${frequencias[2].item}<br>
										</div>
									
										<div class="radio">
											<input type="radio" name="frequencia" value="${frequencias[3]}">${frequencias[3].item}<br>
										</div><br>			
									</div>
						
									<h4>Permanência</h4>
									<div class="col-lg-12">
										<div class="radio">
											<input type="radio" name="permanencia" value="${permanencias[0]}" checked>${permanencias[0].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="permanencia" value="${permanencias[1]}">${permanencias[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="permanencia" value="${permanencias[2]}">${permanencias[2].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="permanencia" value="${permanencias[3]}">${permanencias[3].item}<br>
										</div><br>
									</div>
						
									<h4>Disciplina quanto ao cumprimento das normas</h4>
									<div class="col-lg-12">
										<div class="radio">
											<input type="radio" name="disciplinas" value="${disciplinas[0]}" checked>${disciplinas[0].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="disciplinas" value="${disciplinas[1]}">${disciplinas[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="disciplinas" value="${disciplinas[2]}">${disciplinas[2].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="disciplinas" value="${disciplinas[3]}">${disciplinas[3].item}<br>
										</div><br>
									</div><br>
								
									<label for="assiduidadeDisciplina" class="control-label">*Assiduidade
									e Disciplina:</label>
								<form:textarea id="assiduidadeDisciplina"
									path="fatorAssiduidadeDisciplina" cssClass="form-control"
									placeholder="Comentário" required="required" />
								<div class="error-validation">
									<form:errors path="fatorAssiduidadeDisciplina"></form:errors>
								</div>
								</fieldset>
								
							</div><!-- div form-item col-sm-12 -->
						</div><!-- div form-group -->


						<div class="form-group">
							<div class="form-item col-sm-12">
								<fieldset>
									<legend>Iniciativa e Produtividade</legend>
									
									<h4>Iniciativa</h4>
									
									<div class="col-lg-12">
										<div class="radio">
											<input type="radio" name="iniciativa" value="${iniciativas[0]}" checked>${iniciativas[0].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="iniciativa" value="${iniciativas[1]}">${iniciativas[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="iniciativa" value="${iniciativas[2]}">${iniciativas[2].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="iniciativa" value="${iniciativas[3]}">${iniciativas[3].item}<br>
										</div><br>
									</div>
									
									<h4>Quantidade de Trabalho</h4>
									
									<div class="col-lg-12">
										<div class="radio">
											<input type="radio" name="quantidade" value="${quantidades[0]}" checked>${quantidades[0].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="quantidade" value="${quantidades[1]}">${quantidades[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="quantidade" value="${quantidades[2]}">${quantidades[2].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="quantidade" value="${quantidades[3]}">${quantidades[3].item}<br>
										</div><br>
									</div>
									
									<h4>Qualidade de Trabalho</h4>
									
									<div class="col-lg-12">
										<div class="radio">
											<input type="radio" name="qualidade" value="${qualidades[0]}" checked>${qualidades[0].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="qualidade" value="${qualidades[1]}">${qualidades[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="qualidade" value="${qualidades[2]}">${qualidades[2].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="qualidade" value="${qualidades[3]}">${qualidades[3].item}<br>
										</div><br>
									</div>
									
									<h4>Cumprimento de Prazos</h4>
									
									<div class="col-lg-12">
										<div class="radio">
											<input type="radio" name="cumprimento" value="${cumprimentos[0]}" checked>${cumprimentos[0].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="cumprimento" value="${cumprimentos[1]}">${cumprimentos[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="cumprimento" value="${cumprimentos[2]}">${cumprimentos[2].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="cumprimento" value="${cumprimentos[3]}">${cumprimentos[3].item}<br>
										</div><br>
									</div>
									
									<label for="iniciativaProdutividade" class="control-label">*Iniciativa
									e Produtividade:</label>
								<form:textarea id="iniciativaProdutividade"
									path="fatorIniciativaProdutividade" cssClass="form-control"
									placeholder="Comentário" required="required" />
								<div class="error-validation">
									<form:errors path="fatorIniciativaProdutividade"></form:errors>
								</div>
								</fieldset>
							</div><!-- div form-item col-sm-12 -->
						</div><!-- div form-group -->
						
						<div class="form-group">
							<div class="form-item col-sm-12">
								<fieldset>
									<legend>Responsabilidade</legend>
									
									<h4>Comprometimento com o trabalho</h4>
									
									<div class="col-lg-12">
										<div class="radio">
											<input type="radio" name="comprometimento" value="${comprometimentos[0]}" checked>${comprometimentos[0].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="comprometimento" value="${comprometimentos[1]}">${comprometimentos[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="comprometimento" value="${comprometimentos[2]}">${comprometimentos[2].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="comprometimento" value="${comprometimentos[3]}">${comprometimentos[3].item}<br>
										</div><br>
									</div>
									
									<h4>Cuidado com materiais e equipamentos</h4>
									
									<div class="col-lg-12">
										<div class="radio">
											<input type="radio" name="cuidado" value="${cuidados[0]}" checked>${cuidados[0].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="cuidado" value="${cuidados[1]}">${cuidados[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="cuidado" value="${cuidados[2]}">${cuidados[2].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="cuidado" value="${cuidados[3]}">${cuidados[3].item}<br>
										</div><br>
									</div>
									
									<label for="responsabilidade" class="control-label">*Responsabilidade:</label>
									<form:textarea id="responsabilidade"
										path="fatorResponsabilidade" cssClass="form-control"
										placeholder="Comentário" required="required" />
									<div class="error-validation">
										<form:errors path="fatorResponsabilidade"></form:errors>
									</div>
								</fieldset>
								
							</div><!--fim da div form-item col-sm-12-->
						</div><!--fim da div form-group-->
						
						<div class="form-group">
							<div class="form-item col-sm-12">
								
								<fieldset>
									<legend>Relacionamento</legend>
									
									<h4>Relacionamento junto a gerência e demais funcionários:</h4>
									
									<div class="col-lg-12">
										<div class="radio">
											<input type="radio" name="relacionamento" value="${relacionamentos[0]}" checked>${relacionamentos[0].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="relacionamento" value="${relacionamentos[1]}">${relacionamentos[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="relacionamento" value="${relacionamentos[2]}">${relacionamentos[2].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="relacionamento" value="${relacionamentos[3]}">${relacionamentos[3].item}<br>
										</div><br>
									</div>
									
									<h4>Trabalho em equipe:</h4>
									
									<div class="col-lg-12">
										<div class="radio">
											<input type="radio" name="trabalho" value="${trabalhos[0]}" checked>${trabalhos[0].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="trabalho" value="${trabalhos[1]}">${trabalhos[1].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="trabalho" value="${trabalhos[2]}">${trabalhos[2].item}<br>
										</div>
										
										<div class="radio">
											<input type="radio" name="trabalho" value="${trabalhos[3]}">${trabalhos[3].item}<br>
										</div><br>
									</div>
									
									<label for="relacionamento" class="control-label">*Relacionamento:</label>
									<form:textarea id="relacionamento" path="fatorRelacionamento"
										cssClass="form-control" placeholder="Comentário"
										required="required" />
									<div class="error-validation">
										<form:errors path="fatorRelacionamento"></form:errors>
									</div>
								</fieldset>
								
							</div><!--fim da div form-item col-sm-12-->
						</div><!--fim da div form-group-->
						
					<div class="form-item col-sm-3">
						<label for="nota" class="control-label">*Nota:</label>
						<form:input path="nota" cssClass="form-control"></form:input>
						<div class="error-validation"><form:errors path="nota"></form:errors></div>
					</div>
					
					<div class="form-item col-sm-12">
						<label for="comentario_final" class="control-label">*Comentário Final:</label>
							<form:textarea id="comentario_final" 
								cssClass="form-control" placeholder="Comentário Final"
								required="required" path=""/>
							<div class="error-validation">
								<!-- CORRIGIR ISSO -->
							</div>
							<br>
					</div>
					
					<div class="form-group">
						<div class="parecer-professor form-item col-sm-12">
							<fieldset>
								<legend>PARECER DO PROFESSOR ORIENTADOR</legend>
								
								<p>
									Caso o(a) Estagiário(a), no decorrer do período de seu estágio, persista com o mesmo desempenho 
									apresentado durante o período em que esteve sob sua orientação, deverá:
								</p>
								
								<div class="checkbox">
									<label>
										<input type="checkbox" name="confirmacao-estagio" value="true"><strong>Ser confirmado(a) no estágio.</strong>
									</label>
								</div>
								
								<br>
								<p>
									<strong>Assinale os principais fatores que serviram de base para o parecer emitido</strong>
								</p>
								
								<ol>
									<div class="checkbox">
										<li>											
											<label>
												<input type="checkbox" name="fator-assuidade" value="">Assuidade
											</label>
										</li>
										
										<li>
											<label>
												<input type="checkbox" name="fator-disciplina" value="">Disciplina
											</label>
										</li>
										
										<li>
											<label>
												<input type="checkbox" name="fator-iniciativa" value="">Capacidade de Iniciativa
											</label>
										</li>
										
										<li>
											<label>
												<input type="checkbox" name="fator-produtividade" value="">Produtividade
											</label>
										</li>
										
										<li>
											<label>
												<input type="checkbox" name="fator-responsabilidade" value="">Responsabilidade
											</label>
										</li>
										
										<li>
											<label>
												<input type="checkbox" name="fator-outros_motivos" value="">Outros Motivos.Especifique:
											</label><br>
											
											<form:textarea id="comentario_observacao" 
												cssClass="form-control" placeholder="Outros Motivos:"
												required="required" path=""/>
											<div class="error-validation">
												<!-- CORRIGIR ISSO -->
											</div>
										</li>
									</div>
								</ol>
								<br>
								
								<div>
									<label for="comentario_observacao" class="control-label"><p><strong>COMENTÁRIOS/OBSERVAÇÕES:</strong></p></label>
									<form:textarea id="comentario_observacao" 
										cssClass="form-control" placeholder="Comentários/Observações"
										required="required" path=""/>
									<div class="error-validation">
										<!-- CORRIGIR ISSO -->
									</div>
									<br>
								</div>
								
								<p><strong>NECESSIDADE DE TREINAMENTO:</strong></p>	
								<div class="checkbox">
									<label>
										<input type="checkbox" name="necessidade_treinamento" value="">Sim. Especifique:
									</label>
									
									<div>
										<form:textarea id="especificacao_treinamento" 
											cssClass="form-control" placeholder="Especificação de treinamento"
											required="required" path=""/>
										<div class="error-validation">
											<!-- CORRIGIR ISSO -->
										</div>
									<br>
									</div>
									
									<p><strong>CARÁTER DO TREINAMENTO:</strong></p>
									<label>
										<input type="checkbox" name="treinamento_urgente" value="">Urgente:
									</label>
									
									<label>
										<input type="checkbox" name="treinamento_importante" value="">Importante:
									</label>
								</div>	
								
							</fieldset>
						</div>
					</div>
					
					</div> <!-- fim da div panel-body-->
					
					<div class="panel-footer" align="center">
						<div class="controls">
							<c:if test="${action eq 'cadastrar' }">
								<button type="submit" class="btn btn-primary" title="Cadastrar">
									<span class="fa fa-floppy-o"></span> Cadastrar
								</button>
							</c:if>
							<c:if test="${action eq 'editar' }">
								<button type="submit" class="btn btn-primary"
									title="Salvar alterações">
									<span class="fa fa-floppy-o"></span> Salvar alterações
								</button>
							</c:if>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<jsp:include page="../modulos/footer.jsp" />

</body>
</html>
