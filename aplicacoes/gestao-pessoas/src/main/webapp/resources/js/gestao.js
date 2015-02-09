$(document).ready(function() {
	$('[data-toggle="tooltip"]').tooltip();

	$("#dataFolga").datepicker({
		language: 'pt-BR',
		autoclose: true,
		format: "dd/mm/yyyy"
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

function loadEstagiarios(result) {
	$(".data").datepicker({
		language: 'pt-BR',
		autoclose: true,
		format: "dd/mm/yyyy"
	});

	$("#viewEstagiarios").html($(result).find("#viewEstagiarios"));

}
