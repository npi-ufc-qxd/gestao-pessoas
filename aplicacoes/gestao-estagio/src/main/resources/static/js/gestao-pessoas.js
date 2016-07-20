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

$(document).ready(function() {
	
	$("#semestre").keyup(function() {
		if(!$.isNumeric(this))
			$(this).text("");
	});
	
	$('#semestre').mask('0000.s', {'translation': {'s': {pattern: /[1-2]/}}});	

	jQuery.validator.addMethod("maiorQue", 
			function(dataFinal, element, params) {
				$(params).val($("#inicio").datepicker('getFormattedDate'));
				return moment(dataFinal, "DD/MM/YYYY").isAfter(moment($(params).val(), "DD/MM/YYYY"));
			},'Termino do evento deve ser posterior ao início!'
		);

		jQuery.validator.addMethod("menorQue", 
			function(dataIncial, element, params) {
				$(params).val($("#termino").datepicker('getFormattedDate'));
				return moment(dataIncial, "DD/MM/YYYY").isBefore(moment($(params).val(), "DD/MM/YYYY"));
			},'Inicio do evento deve ser anterior ao termino!'
		);
	
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
					$(botaoDesvincular).parent().remove();
					
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

$(document).ready(function(){
	
	$("#buscarEstagiariosSemVinculo").on("click", function(event){
		
		event.preventDefault();
		
		var botaoBuscar = $(event.currentTarget); 
		
	    var url =  botaoBuscar.attr("href");;
	   
	    if ($('#campoBuscaEstagiario').val() != '' && $('#idTurma').val() != '') {
	    	 var nomeEstagiario = $('#campoBuscaEstagiario').val();
	 		 var idTurma = $('#idTurma').val();
	 		 url = url + '/' + nomeEstagiario + '/' + idTurma;
	 		 $("#estSemVinculoBlock").load(url);
	    }else{
	    	 $("#estSemVinculoBlock").load(url);
	    }
	    
	});
	
});
function desvincularEstagio(estagio){
	//th:href="@{/Supervisor/Turma/Acompanhamento/{id}/Desvincular(id=${est.id})}"
 console.log(estagio);
  var r = confirm("Press a button!");
  if (r == true) {
     console.log("sim")
  } else {
      console.log("cancel");
  }
  
}
	

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
        $(element).closest('.form-group').addClass('has-erth:href="@{/Supervisor/Turma/Acompanhamento/{id}/Desvincular(id=${est.id})}"ror');
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
        	number: "Informe um numero",
        	min: "Informe um semestre valido",
           	max: "Informe um semestre valido"
		},
	}
});	

$(document).ready(function(){
	$(".data").datepicker({
	    language: 'pt-BR',
	    autoclose: true,
	    format: "dd/mm/yyyy",
	    orientation: "top auto",
	});

	$(".data-reposicao").datepicker({
	    language: 'pt-BR',
	    autoclose: true,
	    format: "mm/dd/yyyy",
	    orientation: "top auto",
	});
});	  

$(document).ready(function(){
	  $('#dataNascimento').mask('00/00/0000');
	  $('#cep').mask('00000-000');
	  $('#telefone').mask('(88) 9 0000-0000');
});	  

$("#modoAvaliacao select").change(function(){
	if($(this).val() == 'FORMULARIO') {
		$("#viaFormulario").removeClass("hidden");
		$("#viaArquivo").addClass("hidden");
	} else if($(this).val() == 'ARQUIVO') {
		$("#viaArquivo").removeClass("hidden");
		$("#viaFormulario").addClass("hidden");
	}
});

