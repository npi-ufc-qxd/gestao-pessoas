var ptBR = {
    "sEmptyTable": "Nenhum registro encontrado",
    "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
    "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
    "sInfoFiltered": "(Filtrados de _MAX_ registros)",
    "sInfoPostFix": "",
    "sInfoThousands": ".",
    "sLengthMenu": "Resultados por página _MENU_",
    "sLoadingRecords": "Carregando...",
    "sProcessing": "Processando...",
    "sZeroRecords": "Nenhum registro encontrado",
    "sSearch": "Pesquisar",
    "oPaginate": {
        "sNext": "Próximo",
        "sPrevious": "Anterior",
        "sFirst": "Primeiro",
        "sLast": "Último"
    },
    "oAria": {
        "sSortAscending": ": Ordenar colunas de forma ascendente",
        "sSortDescending": ": Ordenar colunas de forma descendente"
    }
}

$(document).ready(function() {
	
	$('a.back').click(function(){
		parent.history.back();
		return false;
	});
	
	$("#semestre").keyup(function() {
		if(!$.isNumeric(this))
			$(this).text("");
	});	
	
	ativarEditable();
	
	$(".ano").mask("9999");	
	$(".hora").mask("99:99");
	$(".cpf").mask("999.999.999-99");
	$(".cpfNumeros").mask("99999999999");
	$(".cep").mask("99.999-999");
	$(".matricula").mask("9999999");
	$(".telefone").mask("(99) 9 9999-9999");

	$('#form-projeto').validate({
        rules: {
            
        },
        highlight: function(element) {
            $(element).closest('.form-item').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-item').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element) {
        },
        messages:{
        	nome:{
                required:"Campo obrigatório",
            },
            descricao:{
                required:"Campo obrigatório",
            },
        }
    });
	
	
	//validador de datas. Para início e para término.
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

	$('#form-evento').validate({
		rules:{
			inicio:{
				menorQue: "#termino",
        	},

        	termino:{
        		maiorQue: "#inicio",
        	}
			
		},
		highlight: function(element) {
            $(element).closest('.form-item').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-item').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element) {
            error.insertAfter(element.parent().children().last());
        },
		messages:{
			inicio:{
				required: "Campo obrigatório",
			},
			termino:{
				required: "Campo obrigatório",
			},
			descricao:{
				required: "Campo obrigatório",
			}		
		}
	});
	
	$('#form-turma').validate({
        rules: {
        	inicio:{
				menorQue : "#termino",
        	},

        	termino:{
        		maiorQue: "#inicio",
        	}
        },
        highlight: function(element) {
            $(element).closest('.form-item').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-item').removeClass('has-error');
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

	$('#form-folga').validate({
        rules: {
            
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
        	data:{
                required:"Campo obrigatório",
            },
            descricao:{
                required:"Campo obrigatório",
            },
        }
    });	
	$('.formFrequencia').each(function (){
		$(this).validate({
	        rules: {
	        	statusFrequencia:{
	        		required: true
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
	        	statusFrequencia:{
	                required:"Campo obrigatório",
	            }
	        }
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
            $(element).closest('.form-item').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-item').removeClass('has-error');
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
			semestre: {
				required : "Campo obrigatório",
            	number: "Informe um numero",
            	min: "Informe um semestre valido",
               	max: "Informe um semestre valido"
			},
		}
	});	

	$('#form-horario').validate({
        rules: {
        	inicioExpediente:{
                required: true,
            },
            finalExpediente:{
                required: true,
            },
            
        },
        highlight: function(element) {
            $(element).closest('.form-item').addClass('has-error');
        },
        unhighlight: function(element) {
            $(element).closest('.form-item').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function(error, element) {
        },
        messages:{
        	inicioExpediente:{
                required:"Campo obrigatório",
            },
            finalExpediente:{
                required:"Campo obrigatório",
            },
        }
    });
	$(".data").datepicker({
		language: 'pt-BR',
		autoclose: true,
		format: "dd/mm/yyyy",
		orientation: "top auto",
	});
	
});

function ativarEditable() {
    $('.observacaoFrequencia').editable({
    	url : '/gestao-pessoas/supervisor/frequencia/realizar-observacao',
    	title : 'Observaçao',
    	type : 'textarea',
        emptytext : "faça sua observação",
        placement: 'right',
    });

    $('.statusFrequencia').editable({
    	url : '/gestao-pessoas/supervisor/frequencia/atualizar-status',
        type: 'select',
        title: 'Presença',
        placement: 'right',
        source: [
	          	{ value: 'FALTA', text: 'FALTA' },
	          	{ value: 'ATRASADO', text: 'ATRASADO'}, 
	          	{ value: 'ABONADO', text: 'ABONADO' },
	          	{ value: 'PRESENTE', text: 'PRESENTE' },
	          	{ value: 'FERIADO', text: 'FERIADO' },
        ]
    });
};
