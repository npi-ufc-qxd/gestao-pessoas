$(document).ready(function() {
	$('#turmaF').selectpicker('hide');
	$("#viewFrequencias").hide();
	moment.locale('pt-br');
	
	loadFreqByDateAndTurma(moment(), -1);

	
	$('#periodo-dia span#before').click(function(event) {
		var date = moment($('#current-data').val()).add(-1, 'days');
		var turma = $('#turmaF').val();
		console.log('antes :' + date + " - " + turma);
		loadFreqByDateAndTurma(date, turma);
	});
	
	$('#periodo-dia span#after').click(function(event) {
		var date = moment($('#current-data').val()).add(1, 'days');
		var turma = $('#turmaF').val();
		console.log('depois :' + date + " - " + turma);
		loadFreqByDateAndTurma(date, turma);
	});
		
//	$('#anoF').change(function(event) {
//		var date = moment($('#current-data').val());
//		var ano = $("#anoF").val().trim();
//		var semestre = $('#semestreF').val();
//		console.log('ano chage :' + date + " - " + ano + " - " + semestre);
//		lFreqByData(date, ano, semestre);
//	});
//
//	$('#semestreF').change(function(event) {
//		var date = moment($('#current-data').val());
//		var ano = $("#anoF").val().trim();
//		var semestre = $('#semestreF').val();
//		console.log('semestre chage :' + date + " - " + ano + " - " + semestre);
//		lFreqByData(date, ano, semestre);
//	});

	$('.filtroFrequencia').change(function(event) {
		var date = moment($('#current-data').val());
		var ano = $("#anoF").val().trim();
		var semestre = $('#semestreF').val().trim();
		console.log('filtroFrequencia :' + date + " - " + ano + " - " + semestre);
		
		loadTurma(ano, semestre);
	});
	
	$("#turmaF").change(function(event) {
		$("#viewFrequencias").show();
	});
		
});

function loadFreqByDateAndTurma(data, turma) {
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

function loadTurma(ano, semestre) {
	$('div#periodo-dia').css('display', 'block');
	var json = {
		"ano" : ano,
		"semestre" : semestre,
	};
	$.ajax({
		url: '/gestao-pessoas/frequencia/turmas.json',
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
			$('#turmaF').selectpicker('hide');
			$("#viewFrequencias").hide();
		}
	});
}

function loadSelectTurma(result) {
	//console.log(' codigo ' + result.codigo);
	
	if(result.length < 1){
		$('#turmaF').selectpicker('hide');
		$("#viewFrequencias").hide();
	}
	
	$("#turmaF").children().remove();
	var option = $("<option>");
	option.text("Selecione uma turma");
	$("#turmaF").append(option);
	$.each(result, function(i, turma) {
		var option = $("<option>");
		console.log(turma.codigo);
		option.attr("value", turma.id).text(turma.codigo);
		$("#turmaF").append(option);
	});
	$('#turmaF').selectpicker('refresh');
	if($("#turmaF option").length > 1){
		$('#turmaF').selectpicker('show');
	}else{
		$('#turmaF').selectpicker('hide');
	}
	$('#turmaF').selectpicker('refresh');

}


function lBootgrid(result, table) {
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
		})
		.bootgrid("clear")
		.bootgrid("append", result);
}
