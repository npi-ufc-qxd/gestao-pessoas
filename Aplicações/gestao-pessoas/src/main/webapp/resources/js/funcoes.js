
$(document).ready(function() {
	
		$("#formularioCadastroComentario")
							.validate(
									{
										submitHandler : function(form) {
											var idProjeto = $('#projeto').val();
											var idPessoa = $('#pessoa').val();
											var nomePessoa = $('#pessoa_nome')
													.val();
											var cabecalho = "Comentários do Projeto";
											var textoComentario = $(
													'#textocomentarioInput')
													.val();
											var trimTextoComentario = textoComentario
													.trim();
											var data = new Date();
											var dataFormatada = ("0" + data
													.getDate()).substr(-2)
													+ "-"
													+ ("0" + (data.getMonth() + 1))
															.substr(-2)
													+ "-"
													+ data.getFullYear()
													+ " "
													+ ('0' + data.getHours())
															.slice(-2)
													+ ":"
													+ ('0' + data.getMinutes())
															.slice(-2);
											$
													.ajax({
														type : "POST",
														data : {
															idProjeto : idProjeto,
															idPessoa : idPessoa,
															texto : textoComentario
														},
														url : "/gpa-pesquisa/comentario/comentarProjeto",
														dataType : "html",
														success : function() {
															$('#comentarioList')
																	.prepend(
																			'<li id="novoComentario" class="well">'
																					+ '<div class="nome_pessoa">'
																					+ nomePessoa
																					+ '</div>'
																					+ '<div class="corpo_texto">'
																					+ textoComentario
																					+ '</div>'
																					+ '<div class="formatacao_data">'
																					+ dataFormatada
																					+ '</div>'
																					+ '</li>');
															$(
																	"#headComentarios")
																	.show();
															$('html, body')
																	.animate(
																			{
																				scrollTop : $(
																						"#novoComentario")
																						.offset().top
																			},
																			1000);
														}
													});
	
	$("#formularioCadastroComentario")[0]
													.reset();
											return false;
										},
										rules : {
											texto : {
												required : true,
											}
										},
										messages : {
											texto : {
												required : "Campo Obrigatório",
											}
										},
										highlight : function(element) {
											$(element).closest('.form-group')
													.addClass('has-error');
										},
										unhighlight : function(element) {
											$(element).closest('.form-group')
													.removeClass('has-error');
										},
										errorElement : 'span',
										errorClass : 'help-block',
										errorPlacement : function(error,
												element) {
											if (element.parent('.input-group').length) {
												error.insertAfter(element
														.parent());
											} else {
												error.insertAfter(element);
											}
										}
									});
	
	$('div.error-validation:has(span)').find('span').css('color', '#a94442');
	$('div.error-validation:has(span)').find('span').parent().parent().parent().addClass('has-error has-feedback');
	
	$("input.data").datepicker({
		format : "dd/mm/yyyy",
		todayBtn : "linked",
		autoclose : true,
		language : "pt-BR",
		todayHighlight : true
	});
	
	$('.tab a').click(function (e) {
	  e.preventDefault();
	  $(this).tab('show');
	});
	
	$('#confirm-delete').on('show.bs.modal', function(e) {
	    $(this).find('.btn-danger').attr('href', $(e.relatedTarget).data('href'));
	});
	
	$('#confirm-submit').on('show.bs.modal', function(e) {
	    $(this).find('.btn-primary').attr('href', $(e.relatedTarget).data('href'));
	});
	
	$('.delete-document').on('click', function (e) {
		var line = this;
		var id = $(this).attr('id');
		e.preventDefault();
		bootbox.dialog({
			  message: "Tem certeza de que deseja excluir esse arquivo?",
			  title: "Excluir",
			  buttons: {
			    danger: {
			      label: "Excluir",
			      className: "btn-danger",
			      callback: function() {
			    	  $.ajax({
			    		  type: "POST",
			    		  url: "/gpa-pesquisa/documento/ajax/remover/"+id
			    	  })
		    		  .success(function( result ) {
		    			  if(result.result == 'ok') {
		    				  $(line).parent().parent().remove();
		    			  } else {
		    				  bootbox.alert(result.mensagem, function() {
		    				  });
		    			  }
		    		  });
			      }
			    },
			    main: {
			      label: "Cancelar",
			      className: "btn-default",
			      callback: function() {
			      }
			    }
			  }
			});
   
	
	$('input[type=file]').bootstrapFileInput();
	
	$('.delete-file').click(function(){
		alert($(this).attr('id'));
	});
	
	
	$('#addParticipante').click(function adicionarParticipante(){
		
				var nomeParticipante = $("#participanteEscolhido").val();
				
				if(!nomeParticipante || 0 === nomeParticipante.length){
					
					 $("#participanteEscolhido").css({'border' : '#a94442 solid 1px','box-shadow' : '1px 1px rgba(0,0,0,.075)'}); 
					 $("#labelParticipante").css("color","#a94442");
				}else{
					
					var participantesSelecionados = $('input[name=participanteSelecionado]:checked');
					var selecionado = false;	
					
					for (i = 0; i < participantesSelecionados.length; i++) { 

							if(participantesSelecionados[i].value === nomeParticipante){
								alert("Você selecionou o usuario '" +nomeParticipante +"' mais de uma vez.");
								selecionado = true;
							}
							
						}
					if(selecionado == false){
						 $("#listaParticipantesCadastrados").append(' <label class="participanteSelecionado" for="participanteSelecionado" style="margin-left:20px">'+nomeParticipante+'</label>' 
							    	+'<input type="checkbox" class = "participanteSelecionado"  id = "participanteSelecionado" name="participanteSelecionado" value = "' +nomeParticipante
							    	+'" checked="checked" ">, ');
					}
					
				}
				
		});
	
});

					$('div.error-validation:has(span)').find('span').css(
							'color', '#a94442');
					$('div.error-validation:has(span)').find('span').parent()
							.parent().parent().addClass(
									'has-error has-feedback');

					$("input.data").datepicker({
						format : "dd/mm/yyyy",
						todayBtn : "linked",
						autoclose : true,
						language : "pt-BR",
						todayHighlight : true
					});

					$('.tab a').click(function(e) {
						e.preventDefault();
						$(this).tab('show');
					});

					$('#confirm-delete').on(
							'show.bs.modal',
							function(e) {
								$(this).find('.btn-danger').attr('href',
										$(e.relatedTarget).data('href'));
							});

					$('#confirm-submit').on(
							'show.bs.modal',
							function(e) {
								$(this).find('.btn-primary').attr('href',
										$(e.relatedTarget).data('href'));
							});

					$('.delete-document')
							.on(
									'click',
									function(e) {
										var line = this;
										var id = $(this).attr('id');
										e.preventDefault();
										bootbox
												.dialog({
													message : "Tem certeza de que deseja excluir esse arquivo?",
													title : "Excluir",
													buttons : {
														danger : {
															label : "Excluir",
															className : "btn-danger",
															callback : function() {
																$
																		.ajax(
																				{
																					type : "POST",
																					url : "/gpa-pesquisa/documento/ajax/remover/"
																							+ id
																				})
																		.success(
																				function(
																						result) {
																					if (result.result == 'ok') {
																						$(
																								line)
																								.parent()
																								.parent()
																								.remove();
																					} else {
																						bootbox
																								.alert(
																										result.mensagem,
																										function() {
																										});
																					}
																				});
															}
														},
														main : {
															label : "Cancelar",
															className : "btn-default",
															callback : function() {
															}
														}
													}
												});
									});

					$('input[type=file]').bootstrapFileInput();

					$('.delete-file').click(function() {
						alert($(this).attr('id'));
					});
					$('#addParticipante')
							.click(
									function adicionarParticipante() {

										var nomeParticipante = $(
												"#participanteEscolhido").val();

										if (!nomeParticipante
												|| 0 === nomeParticipante.length) {

											$("#participanteEscolhido")
													.css(
															{
																'border' : '#a94442 solid 1px',
																'box-shadow' : '1px 1px rgba(0,0,0,.075)'
															});
											$("#labelParticipante").css(
													"color", "#a94442");
										} else {

											var participantesSelecionados = $('input[name=participanteSelecionado]:checked');
											var selecionado = "nao";

											for (i = 0; i < participantesSelecionados.length; i++) {

												if (participantesSelecionados[i].value === nomeParticipante) {
													alert("Você selecionou o usuario '"
															+ nomeParticipante
															+ "' mais de uma vez.");
													selecionado = "sim";
												}

											}
											if (selecionado === "nao") {
												$(
														"#listaParticipantesCadastrados")
														.append(
																' <label class="participanteSelecionado" for="participanteSelecionado" style="margin-left:20px">'
																		+ nomeParticipante
																		+ '</label>'
																		+ '<input type="checkbox" class = "participanteSelecionado"  id = "participanteSelecionado" name="participanteSelecionado" value = "'
																		+ nomeParticipante
																		+ '" checked="checked" ">, ');
											}

										}

									});
		
function verificarSeExisteUlNaPagina() {
	var verificaSeListaEstaVazia = $("#comentarioList li").length;
	if (verificaSeListaEstaVazia == 0) {
		$("#headComentarios").hide();
	}

}
});
