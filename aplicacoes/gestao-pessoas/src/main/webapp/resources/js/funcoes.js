

$(document).ready(function() {
	$('#turma').hide();

	$('.selectFiltro').change(function(event) {
		var ano = $("#ano").val().trim();
		var semestre = $('#semestre').val().trim();

		console.log('selectFiltro :' + ano + " - " + semestre);
		if(ano != '' && semestre != '' ){
			loadTurmasPeriodo(ano, semestre);
		}
	});
	
	$('#turma').change(function(event) {
		turma = $(this).val().trim();
		console.log("turma = " + turma);
		load(ano, semestre, turma);
	});	

});
		
function loadEstagiarios(result) {
	$("#meus-projetos").html($(result).find("#meus-projetos"));
}

function showTurmas(result) {
		$(".turma").html($(result).find("#turma"));
		console.log($("#turma").text());
		$('#turma').show();
}

function loadTurmasPeriodo(ano, semestre) {
	console.log('loadTurmasPeriodo :' + ano + " - " + semestre);

	var filtro = {
		"ano" : ano,
		"semestre" : semestre,
	};
	
	$.ajax({
		url: '/gestao-pessoas/coordenador/turmasPeriodo',
		type: "POST",
		dataType: "html",
		data: filtro,
		success: function(result) {
			showTurmas(result);
		},
		error: function(error) {
			console.log('ERROR TURMA: ' + error);
			$('#turma').hide();
		}
	});
}



//function load(ano, semestre, turma) {
//console.log(" loadload load load");
//$.get( "/gestao-pessoas/coordenador/vincul", { "ano" : ano, "semestre" : semestre, "turma" : turma } );
//}


//    $.fn.editable.defaults.mode = 'popup';     
//
////make username editable
//$('#observacao').editable({
//	url : '/gestao-pessoas/frequencia/observacao',
//	title : 'Observaçao',
//	type : 'textarea'
//});
//
////make status editable
//$('.status').editable({
//	url : '/gestao-pessoas/frequencia/atualizarStatus',
//    type: 'select',
//    title: 'Presença',
//    placement: 'top',
//    value: 'ATRASADO',
//    pk: 1,
//	mode : 'inline',
//	sourceCache : true,
//    source: [
//             {value: 'PRESENTE', text: 'Presente' },
//             {value: 'ATRASADO', text: 'Atrasado'},
//           	 {value: 'FALTA', text: 'Falta'} 
//    ]
//});
//
//

