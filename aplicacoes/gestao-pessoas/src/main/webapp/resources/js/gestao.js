$(document).ready(function() {
	
	$("#semestre").keyup(function() {
		if(!$.isNumeric(this))
			$(this).text("");
	});	
	
	ativarEditable();
	
	$('[data-toggle="tooltip"]').tooltip();
	$('[data-toggle="popover"]').popover()
	$(".ano").mask("9999");	
	$(".hora").mask("99:99");
	$(".cpf").mask("999.999.999-99");
	$(".cpfNumeros").mask("99999999999");
	$(".cep").mask("99.999-999");
	$(".matricula").mask("9999999");
	$(".telefone").mask("(99) - 9999-9999");

	$('#adicionarProjetoForm').validate({
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
	
	$('#adicionarPeriodoForm').validate({
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
            error.insertAfter(element.parent().children().last());
        },
        messages:{
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
	
	$('#adicionarTurmaForm').validate({
		ignore: ':hidden:not(.selectpicker)',
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
            error.insertAfter(element.parent().children().last());
        },
        messages:{
        	horaFinal:{
                required:"Campo obrigatório",
            },
            horaInicio:{
                required:"Campo obrigatório",
            },
            inicioSemana:{
                required:"Campo obrigatório",
            },
        }
    });

	$('#adicionarFolgaForm').validate({
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

	$( "#DadosPessoaisEstagiarioForm" ).validate({
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

	$( "#meusDadosForm" ).validate({
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
            error.insertAfter(element.parent().children().last());
        },
        messages:{
			endereco : {
				required : "Campo obrigatório",
			},
		}
	});	

	$( "#adicionarContasTurmaEstagiarioForm" ).validate({
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
        	
        	error.insertAfter(element.parent().children().last());
        	
        	if (element.hasClass('customError')) {
                error.appendTo($('#minha-turma'));
        	}
        	
        },
        messages:{
        	contaRedmine : {
        		required : "Campo obrigatório",
        	},
        	contaGithub : {
        		required : "Campo obrigatório",
        	},
        	contaHangout : {
        		required : "Campo obrigatório",
        	},
        	"turma.id" : {
        		required : "Campo obrigatório",
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

function ativarEditable(){
    $('.observacaoFrequencia').editable({
    	url : '/gestao-pessoas/supervisor/frequencia-realizar-observacao',
    	title : 'Observaçao',
    	type : 'textarea',
        emptytext : "faça sua observação",
        placement: 'right',
    });

    $('.statusFrequencia').editable({
    	url : '/gestao-pessoas/supervisor/frequencia-atualizar-status',
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
