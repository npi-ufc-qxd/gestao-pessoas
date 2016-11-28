$('.data-inline').datepicker({
	language: 'pt-BR',
	format: "mm/dd/yyyy",
	todayHighlight: true,
});

$(".data-inline").on("changeDate", function(event) {
	var data = $(".data-inline").datepicker('getFormattedDate');
	$("#tabelaFrequencias").load($("#urlMapaFrequencia").attr("href") + "?data=" + data);
});

function toggleChevron(e) {
    $(e.target)
        .prev('.panel-heading')
        .find("i.fa")
        .toggleClass('fa-caret-right fa-chevron-down');
}
$('.panel-body').on('hidden.bs.collapse', toggleChevron);
$('.panel-body').on('shown.bs.collapse', toggleChevron);

$(".data").datepicker({
	language: 'pt-BR',
	autoclose: true,
	format: "dd/mm/yyyy",
	orientation: "top auto",
});

$(".orientador").select2();

$(".supervisores").select2();

$(".cursos").select2();

$(document).ready(function() {
	
	$("#semestre").keyup(function() {
		if(!$.isNumeric(this))
			$(this).text("");
	});
	
	$('#semestre').mask('0000.s', {'translation': {'s': {pattern: /[1-2]/}}});	

	jQuery.validator.addMethod("horaMaior", 
			function(horaFinal, params) {
		moment(horaFinal, "HH:mm").val();
				return moment(horaFinal, "HH:mm").isAfter(moment($(params).val(), "HH:mm"));
			},'Horário de término deve ser posterior ao horário de início!'
		);
	
	jQuery.validator.addMethod("horaMenor", 
			function(horaInicial, params) {
				moment(horaInicial, "HH:mm").val();
				return moment(horaInicial, "HH:mm").isBefore(moment($(params).val(), "HH:mm"));
			},'Horário de início deve ser anterior ao horário de término!'
		);

	jQuery.validator.addMethod("maiorQue", 
			function(dataFinal, params) {
				//$(params).val($("#inicio").datepicker('getFormattedDate'));
				return moment(dataFinal, "DD/MM/YYYY").isAfter(moment($(params).val(), "DD/MM/YYYY"));
			},'Data de término deve ser posterior a data de início!'
		);

	jQuery.validator.addMethod("menorQue", 
		function(dataIncial, element, params) {
			$(params).val($("#termino").datepicker('getFormattedDate'));
			return moment(dataIncial, "DD/MM/YYYY").isBefore(moment($(params).val(), "DD/MM/YYYY"));
		},'Data de início deve ser anterior a data de término!'
	);
	
	$("#form-reposicao").validate({
		onkeyup: false,
		onclick: false,
		onfocusout: false,
		rules: {
		horaAgendamentoEntrada:{
	    		horaMenor : "#horaAgendamentoSaida",
	    	},
		horaAgendamentoSaida:{
	    		horaMaior: "#horaAgendamentoEntrada",
	    	}
	    },
	    highlight: function(element) {
	        $(element).closest('.form-group').addClass('has-error color-error');
	    },
	    unhighlight: function(element) {
	        $(element).closest('.form-group').removeClass('has-error color-error');
	    },
	    errorElement: 'label',
	    errorClass: 'message-error',
	    errorPlacement: function(error, element) {
	        error.insertAfter(element);
	    },
	    messages:{
	    	dataReposicao : {
				required : "Informe a data",
			},
			horaAgendamentoEntrada:{
				required : "Informa a Hora"
			},
			horaAgendamentoSaida:{
				required : "Informa a Hora"
			}
		}
	});	
	
	$('#form-expediente').validate({
		onkeyup: false,
		onclick: false,
		onfocusout: false,
		rules: {
			horaInicio:{
        		horaMenor : "#horaTermino",
        	},

        	horaTermino:{
        		horaMaior: "#horaInicio",
        	}
        },
        highlight: function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block'
	});
	
	$('#form-evento').validate({
		rules: {
			inicio:{
        		menorQue : "#termino",
        	},

        	termino:{
        		maiorQue: "#inicio",
        	}
        },
        highlight: function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block'
	});
	
	$('#form-turma').validate({
		rules: {
        	nome: {
        		required: true
        	},
        	semestre: {
        		required: true
        	},
        	inicio: {
        		required: true
        	},
        	termino: {
        		required: true
        	},
        	inicio:{
				menorQue : "#termino",
        	},

        	termino:{
        		maiorQue: "#inicio",
        	}
        },
        highlight: function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element) {
            error.insertAfter(element.parent().children().last());
        },
        messages:{
        	nome:{
                required:"Campo obrigatório",
            },
        	ano:{
                required:"Campo obrigatório",
            },
        	semestre:{
                required:"Campo obrigatório",
            },
        	inicio:{
                required:"Campo obrigatório",
            },
            termino:{
                required:"Campo obrigatório",
            },
        }
    });
	
	
	$('#form-selecao').validate({
        rules: {
        	vagas: {
        		required: true
        	},
        	inicioInscricao: {
        		required: true
        	},
        	terminoInscricao: {
        		required: true
        	},
        	preRequisitos: {
        		required: true
        	},
        	status: {
        		required: true
        	},
        	inicioInscricao:{
        		menorQue : "#termino",
        	},

        	terminoInscricao:{
        		maiorQue: "#inicio",
        	}
        },
        highlight: function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element) {
            error.insertAfter(element.parent().children().last());
        },
        messages:{
        	vagas:{
                required:"Campo obrigatório",
            },
            status: {
        		required: "Campo obrigatório",
        	},
        	inicioInscricao:{
                required:"Campo obrigatório",
            },
        	terminoInscricao:{
                required:"Campo obrigatório",
            },
        	preRequisitos:{
                required:"Campo obrigatório",
            },
        }
    });

	

});

$(".gp-btn-desvincular").on("click", function(event) {
	event.preventDefault();
	
	var botaoDesvincular = $(event.currentTarget); 
	var urlDesvincular = botaoDesvincular.attr("href");
	
	swal({
		  title: "Tem certeza?",
		  text: "Todos as informações (submissões, frequências, avaliações) relacionado a(o) estágiario(a) serão deletadas.",
		  type: "warning",
		  showCancelButton: true,
		  confirmButtonClass: "btn-danger",
		  confirmButtonText: "Sim, Desvincular!",
		  cancelButtonText: "Não",
		  closeOnConfirm: false
		},
		function(){
			var response = $.ajax({
			    url: urlDesvincular,
			    type: 'GET'
			});
			
			response.done(function (deleted){
				if(deleted){
					swal("Sucesso", "Estágiario Desvinculado", "success");
					$(botaoDesvincular).parent().parent().remove();
					
				}else{
					swal("Erro", "Um erro ocorreu, contacte o adminstrador", "error")
				}
				
			});
		});
	

});

$(".gp-btn-expediente").on("click", function(event) {
	event.preventDefault();
	
	var botaoExcluirExpediente = $(event.currentTarget); 
	var urlExcluirExpediente = botaoExcluirExpediente.attr("href");
	
	swal({
		  title: "Deseja excluir expediente?",
		  text: "Todas as informações serão deletadas.",
		  type: "warning",
		  showCancelButton: true,
		  confirmButtonClass: "btn-danger",
		  confirmButtonText: "Sim, Excluir!",
		  cancelButtonText: "Não",
		  closeOnConfirm: false
		},
		function(){
			var response = $.ajax({
			    url: urlExcluirExpediente,
			    type: 'GET'
			});

			response.done(function (deleted){
				if(deleted){
					swal("Sucesso", "Expediente Excluído", "success");
					$(botaoExcluirExpediente).parent().parent().remove();
				}else{
					swal("Erro", "Não foi possível excluir expediente, contacte o adminstrador", "error");
				}
				
			});
		});
});

$(".gp-btn-evento").on("click", function(event) {
	event.preventDefault();
	
	var botaoExcluirEvento = $(event.currentTarget); 
	var urlExcluirEvento = botaoExcluirEvento.attr("href");
	
	swal({
		  title: "Deseja excluir o evento?",
		  text: "Todas as informações serão deletadas.",
		  type: "warning",
		  showCancelButton: true,
		  confirmButtonClass: "btn-danger",
		  confirmButtonText: "Sim, Excluir!",
		  cancelButtonText: "Não",
		  closeOnConfirm: false
		},
		function(){
			var response = $.ajax({
			    url: urlExcluirEvento,
			    type: 'GET'
			});

			response.done(function (deleted){
				if(deleted){
					swal("Sucesso", "Evento Excluído", "success");
					$(botaoExcluirEvento).parent().parent().remove();
				}else{
					swal("Erro", "Não foi possível excluir evento, contacte o adminstrador", "error");
				}
				
			});
		});
});

$(".gp-btn-reposicao").on("click", function(event) {
	event.preventDefault();
	
	var botaoExcluirReposicao = $(event.currentTarget); 
	var urlExcluirReposicao = botaoExcluirReposicao.attr("href");
	
	swal({
		  title: "Deseja excluir a reposição agendada?",
		  text: "Todas as informações serão deletadas.",
		  type: "warning",
		  showCancelButton: true,
		  confirmButtonClass: "btn-danger",
		  confirmButtonText: "Sim, Excluir!",
		  cancelButtonText: "Não",
		  closeOnConfirm: false
		},
		function(){
			var response = $.ajax({
			    url: urlExcluirReposicao,
			    type: 'GET'
			});

			response.done(function (deleted){
				if(deleted){
					swal("Sucesso", "Reposição Excluído", "success");
					$(botaoExcluirReposicao).parent().parent().remove();
				}else{
					swal("Erro", "Não foi possível excluir a reposição, contacte o adminstrador", "error");
				}
				
			});
		});
});


$( "#form-estagiario" ).validate({
    rules: {
	 semestre: {
		 required: true,
		 number: true,
		 min: 1,
		 max: 12,
	 }
    },
    highlight: function(element) {
        $(element).closest('.form-group').addClass('has-error');
    },
    unhighlight: function(element) {
        $(element).closest('.form-group').removeClass('has-error');
    },
    errorElement: 'span',
    errorClass: 'help-block',
    errorPlacement: function(error, element) {
        error.insertAfter(element.parent().children().last());
    },
    messages:{
		nomeCompleto : {
			required : "Campo obrigatório",
		},
		dataNascimento : {
			required : "Campo obrigatório",
		},
		nomeMae : {
			required : "Campo obrigatório",
		},
		matricula : {
			required : "Campo obrigatório",
		},
		endereco : {
			required : "Campo obrigatório",
		},
		cidade : {
			required : "Campo obrigatório",
		},
		uf : {
			required : "Campo obrigatório",
		},
		telefone : {
			required : "Campo obrigatório",
		},
		cep : {
			required : "Campo obrigatório",
		},
		email : {
			required : "Campo obrigatório",
		},
		semestre: {
			required : "Campo obrigatório",
        	number: "Selecione o seu semestre atual",
        	min: "Informe um semestre valido",
           	max: "Informe um semestre valido"
		},
	}
});
$('#form-expediente').validate({
	onkeyup: false,
	onclick: false,
	onfocusout: false,
	rules: {
		horaInicio:{
    		horaMenor : "#horaTermino",
    	},

    	horaTermino:{
    		horaMaior: "#horaInicio",
    	}
    },
    highlight: function(element) {
        $(element).closest('.form-group').addClass('has-error');
    },
    unhighlight: function(element) {
        $(element).closest('.form-group').removeClass('has-error');
    },
    errorElement: 'span',
    errorClass: 'help-block'
});

$(document).ready(function(){
	$(".data").datepicker({
	    language: 'pt-BR',
	    autoclose: true,
	    format: "dd/mm/yyyy",
	    orientation: "top auto",
	});
});	  

$(document).ready(function(){
	  $('#cep').mask('00000-000');
	  $('#telefone').mask('(00) 9 0000-0000');
});	  
