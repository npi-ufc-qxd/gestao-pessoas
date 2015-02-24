$(document).ready(function() {
	$('[data-toggle="tooltip"]').tooltip();

	$("#dataFolga").datepicker({
		language: 'pt-BR',
		autoclose: true,
		format: "dd/mm/yyyy"
	});	

	$(".data").datepicker({
		language: 'pt-BR',
		autoclose: true,
		format: "dd/mm/yyyy"
	});	
	
	$('body').on('focus',".data", function(){
	    $(this).datepicker({
			language: 'pt-BR',
			autoclose: true,
			format: "dd/mm/yyyy"
	    });
	});
	
	$('.statusFrequencia a').editable({
	    type: 'select',
	    url: '#',
        value: 'ATRASADO',
        source: [
                 {value: 'PRESENTE', text: 'Presente' },
                 {value: 'ATRASADO', text: 'Atrasado'},
               	 {value: 'FALTA', text: 'Falta'} 
        ],
   		success: function(response, newValue) {
   		}	
	});

	$('.observacaoFrequencia a').editable({
	    type: 'textarea',
	    url: '#',
	    rows: 4,
	    placeholder: 'Observação',
	    emptytext: 'Obs.:',
	    emptyclass: 'label label-info',
   		success: function(response, newValue) {
   		}	
	});
		
	/* FILTRO ESTAGIARIO */

	$('#turmaEstagiarios').hide();

	$('.estagiariosTurma').change(function(event) {
		turma = $(this).val().trim();

		if (isNaN(turma)) {
			$("#turma").selectpicker('hide');
		} else {
			loadEstagiariosTurma(turma, '/gestao-pessoas/coordenador/estagiarios-turma');
			$("#turma").selectpicker('show');
		}
	});	

	$('.vinculaEstagiariosProjeto').change(function(event) {
		turma = $(this).val().trim();

		if (isNaN(turma)) {
			$("#turma").selectpicker('hide');
		} else {
			loadEstagiariosTurma(turma, '/gestao-pessoas/coordenador/' + $("#idProjeto").val() + '/add-membros-projeto');
			$("#turma").selectpicker('show');
		}
	});	
	
	/* FILTRO REPOSICAO */
	$('.reposicaoTurma').change(function(event) {
		turma = $(this).val().trim();

		if (isNaN(turma)) {
			$("#turma").selectpicker('hide');
		} else {
			$("#statusReposicao").selectpicker('show');
		}
	});
	
	$('.filtroReposicaoStatus').change(function(event) {
		var status = $(this).val().trim();

		if (status === 'ATRASADO') {
			loadEstagiariosTurma(turma, '/gestao-pessoas/coordenador/reposicao-atraso');
		} else if (status === 'FALTA') {
			loadEstagiariosTurma(turma, '/gestao-pessoas/coordenador/reposicao-falta');
		}

	});	
	
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
	
	$( "#meuCadastroNPIEstagiario" ).validate({
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
			contaRedmine : {
				required : "Campo obrigatório",
			},
			contaGithub : {
				required : "Campo obrigatório",
			},
			contaHangout : {
				required : "Campo obrigatório",
			},
		}
	});			
	
	
	$(".file").fileinput({
		showUpload: false,
		overwriteInitial: false,
		initialCaption: "Selecione...",
		browseLabel: "Buscar",
		browseClass: "btn btn-default",
		removeLabel: "Excluir",
		msgSelected: "{n} arquivos selecionados",
		msgLoading: "Carregando arquivo {index} de {files} &hellip;"
	});
	
});
	
/* FUNCTIONS FILTRO PARA ESTAGIARIOS */



/* FUNCTIONS FILTRO PARA ESTAGIARIOS */
function loadEstagiariosTurma(turma, url) {
	console.log('loadEstagiariosTurma :' + turma);
	
	$.ajax({
		url: url,
		type: "POST",
		dataType: "html",
		data: {
			"turma" : turma,
		},
		success: function(result) {
			loadEstagiarios(result);
		},
		error: function(error) {
			$('#turma').hide();
		}
	});
}

function agendarReposicao(turma, estagiario, status, dataReposicao, li) {

	$.ajax({
		type: "POST",
		data: {
			"turma" : turma,
			"estagiario" : estagiario,
			"status" : status,
			"data" : dataReposicao,
		},
		url: '/gestao-pessoas/coordenador/agendar-reposicao.json',
		success: function(result) {
			alert('sucesso');
		},
		error: function(error) {
			alert(error.msg + 'erro' + error.erro);
		}
	});
}

function loadEstagiarios(result) {

	$("#viewEstagiarios").html($(result).find("#viewEstagiarios"));

	$(".data").datepicker({
		language: 'pt-BR',
		autoclose: true,
		format: "dd/mm/yyyy"
	});
	
	$('.agendarreposicao').on('click', function(event) {
		var estagiario = $(this).data('estagiario');
		var dataReposicao = $($(this).data('input')).val();
		var li = $(this).data('li');
		var status = $('#statusReposicao').val().trim();
		console.log(li);
		console.log(dataReposicao);
		console.log(status);
		console.log(turma);
		console.log(estagiario);
		console.log('click');
		
		agendarReposicao(turma, estagiario, status, dataReposicao, li);
		
	});
}
function data() {
    $('.data').datepicker({
		language: 'pt-BR',
		autoclose: true,
		format: "dd/mm/yyyy",
    });
    $('.data').on('focus');
    $( '.data' ).trigger( 'focus' );
}
