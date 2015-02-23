$(document).ready(function() {
	
	$('[data-toggle="tooltip"]').tooltip();
	$('[data-toggle="popover"]').popover()

	$(".ano").mask("9999");	
	$(".hora").mask("99:99");
	$(".cpf").mask("999.999.999-99");
	$(".cpfNumeros").mask("99999999999");
	$(".cep").mask("99.999-999");
	$(".matricula").mask("9999999");
	$(".telefone").mask("(99) - 9999-9999");
	$(".semestre").mask("99");

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
            error.insertAfter(element.parent().children().last());
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

	$( "#adicionarDadosPessoaisEstagiarioForm" ).validate({
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
			nomeCompleto : {
				required : "Campo obrigatório",
			},
			dataNascimento : {
				required : "Campo obrigatório",
			},
			nomeMae : {
				required : "Campo obrigatório",
			},
			semestre : {
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
//		daysOfWeekDisabled: "0,6"
	});
	
//	$('.statusFrequencia a').editable({
//	    type: 'select',
//	    url: '#',
//        value: 'ATRASADO',
//        source: [
//                 {value: 'PRESENTE', text: 'Presente' },
//                 {value: 'ATRASADO', text: 'Atrasado'},
//               	 {value: 'FALTA', text: 'Falta'} 
//        ],
//   		success: function(response, newValue) {
//   		}	
//	});
//
//	$('.observacaoFrequencia a').editable({
//	    type: 'textarea',
//	    url: '',
//	    rows: 4,
//	    placeholder: 'Observação',
//	    emptytext: 'Obs.:',
//	    emptyclass: 'label label-info',
//   		success: function(response, newValue) {
//   		}	
//	});

});

