$(document).ready(function() {

	
//	$('.status').editable({
//    	url : '/gestao-pessoas/frequencia/atualizarStatus',
//        type: 'select',
//        title: 'Presença',
//        placement: 'top',
//        value: 'ATRASADO',
//        pk: 1,
//    	mode : 'inline',
//    	sourceCache : true,
//        source: [
//                 {value: 'PRESENTE', text: 'Presente' },
//                 {value: 'ATRASADO', text: 'Atrasado'},
//               	 {value: 'FALTA', text: 'Falta'} 
//        ]
//    });	
	
	/* FILTRO PERIODO E TURMA */
	$('#turmaFiltro').selectpicker('hide');
	$('#statusReposicao').selectpicker('hide');
	$("#viewFrequencias").addClass('hidden');
	
	moment.locale('pt-br');
	$('#current-data').val(moment());
	$('label#periodo-dia').text(moment().format("DD/MM/YYYY"));
	
	if ($("#anoFiltro").val() != '' && $("#semestreFiltro").val() != '') {
		sessionStorage.setItem('ano', $("#anoFiltro").val());
		sessionStorage.setItem('semestre', $("#semestreFiltro").val());
	}
	if (sessionStorage.getItem('ano') && sessionStorage.getItem('semestre')) {
		$("#anoFiltro").val(sessionStorage.getItem('ano'));
		$("#semestreFiltro").val(sessionStorage.getItem('semestre'));
	}


	$('.selectpicker').selectpicker('refresh')	
	filtroTurma();

	$('#periodo-dia span#before').click(function(event) {
		var date = moment($('#current-data').val()).add(-1, 'days');
		var turma = $('#turmaFiltro').val();
		loadFrequenciaTurma(date, turma);
	});
	
	$('#periodo-dia span#after').click(function(event) {
		var date = moment($('#current-data').val()).add(1, 'days');
		var turma = $('#turmaFiltro').val();
		loadFrequenciaTurma(date, turma);
	});	
	
	/* FILTRO FREQUENCIA */

	$(".filtroJsonAno").keyup(function (event) {
	    var maximoDigitosAno = 4;
	    var lengthAno = $(this).val().length;

	    if ( lengthAno <= maximoDigitosAno && !isNaN($(this).val()) ) {
			filtroTurma();
	    }
	});

	$('.filtroJsonSemestre').change(function(event) {
		filtroTurma();
	});

	$("#turmaFiltro").change(function(event) {
		if(isNaN($('#turmaFiltro').val())){
			$("#viewFrequencias").addClass('hidden');
			$("#viewEstagiarios").addClass('hidden');
		}else{
			$("#viewFrequencias").removeClass('hidden');
			$("#viewEstagiarios").removeClass('hidden');
		}
	});
	
	$('.frequenciasTurma').change(function(event) {
		var turma = $('#turmaFiltro').val();		

		if(!isNaN(turma)){
			var date = moment($('#current-data').val());
			loadFrequenciaTurma(date, turma);
		}
	});

	function filtroTurma() {
		sessionStorage.setItem('ano', $("#anoFiltro").val().trim());
		sessionStorage.setItem('semestre', $("#semestreFiltro").val().trim());

		var ano = sessionStorage.getItem('ano');
		var semestre = sessionStorage.getItem('semestre');

		if (ano != '' && semestre != '') {
			loadTurmasByPeriodo(ano, semestre);
		} else {
			$('#turmaFiltro').selectpicker('hide');
			$('.reposicao').selectpicker('hide');
			$("#viewFrequencias").addClass('hidden');
		}
	}

});
	
/* FUNCTIONS FILTRO JSON PARA FREQUENCIAS */

function loadTurmasByPeriodo(ano, semestre) {
	$('div#periodo-dia').css('display', 'block');
	var json = {
		"ano" : ano,
		"semestre" : semestre,
	};
	$.ajax({
		url: '/gestao-pessoas/turma/turmas.json',
		type: "POST",
		data: JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success: function(result) {
			loadSelectTurma(result);
		},
		error: function(error) {
			console.log('ERROR TURMA: ' + error);
			$('#turmaFiltro').selectpicker('hide');
			$("#viewFrequencias").addClass('hidden');
		}
	});
}

function loadSelectTurma(result) {

	if(result.length < 1){
		$('#turmaFiltro').selectpicker('hide');
		$("#viewFrequencias").addClass('hidden');
	}
	
	$("#turmaFiltro").children().remove();
	
	var option = $("<option>");
	option.text("Selecione a Turma");
	$("#turmaFiltro").append(option);
	$.each(result, function(i, turma) {
		var option = $("<option>");
		console.log(i + " turma " + turma[1]);
		option.attr("value", turma[0]).text(turma[1]);
		$("#turmaFiltro").append(option);
	});
	
	$('#turmaFiltro').selectpicker('refresh');
	if($("#turmaFiltro option").length > 1){
		$('#turmaFiltro').selectpicker('show');
	}else{
		$('#turmaFiltro').selectpicker('hide');
	}
	$('#turmaFiltro').selectpicker('refresh');

}




function loadFrequenciaTurma(data, turma) {
	$('div#periodo-dia').css('display', 'block');
	$('label#periodo-dia').text(moment(data).format("DD/MM/YYYY"));
	$('#current-data').val(data);
	var json = {
		"data" : data,
		"turma" : turma,
	};
	$.ajax({
		url: '/gestao-pessoas/frequencia/frequencias.json',
		type: "POST",
		data: JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success: function(result) {
			lBootgrid(result);
		},
		error: function(error) {
			console.log('ERROR: ' + error);
		}
	});
}



/* ANALISE */

function showTurmas(result) {
	$("#turma").append($("<option>").text("Turma"));		
	$(".turma").html($(result).find("#turma"));
	if($("#turma").empty()){
		$("#turma").find("option").remove();
		$('#turma').hide();
	}else{
		$('#turma').show();
	}
}

function lBootgrid(result, table) {
	
	console.log("R = " + result);
	
	$("#frequencias")
		.bootgrid({
			labels: {
	            all: "Todos",
	            infos: "Mostrando {{ctx.start}} - {{ctx.end}} de {{ctx.total}}",
	            loading: "Carregando...",
	            noResults: "Nenhum resultado encontrado!",
	            refresh: "Atualizar",
	            search: "Buscar"
	        },
	        columnSelection: false,
	        caseSensitive: false,
	        formatters: {
	        	"acoes": function(column, row) {
	        		return "Ações";
	        	}
	        }
		}).bootgrid("clear").bootgrid("append", result).on("loaded.rs.jquery.bootgrid", function (e){
			 $.fn.editable.defaults.mode = 'poupop';     
	    
//		    $('.observacao').editable({
//		    	url : '/gestao-pessoas/frequencia/observacao',
//		    	title : 'Observaçao',
//		    	type : 'textarea'
//		    });
//		    
		    $('.status').editable({
		    	url : '/gestao-pessoas/frequencia/atualizarStatus',
		        type: 'select',
		        title: 'Presença',
		        placement: 'top',
		        value: 'ATRASADO',
		        pk: 1,
		    	mode : 'inline',
		    	sourceCache : true,
		        source: [
		                 {value: 'PRESENTE', text: 'Presente' },
		                 {value: 'ATRASADO', text: 'Atrasado'},
		               	 {value: 'FALTA', text: 'Falta'} 
		        ]
		    });
		}).on("click.rs.jquery.bootgrid", function (e, cols, rows) {

		    console.log("CLICKED/");
		    console.log("e = " + e);
		    console.log("cols = " + cols);
		    console.log("rows.id = " + rows.id);
		    console.log("this = " + $(this).text());
		    
		    observar(rows.id);
		});
}

function observar(id){
    console.log("observar(id) = " + id);
    $('.observacao').editable({
    	url : '/gestao-pessoas/frequencia/observacao',
    	title : 'Observaçao',
    	type : 'textarea',
    	params: function(params) {
    		params.pk = id;
    	    return params;
    	  }
    });

}