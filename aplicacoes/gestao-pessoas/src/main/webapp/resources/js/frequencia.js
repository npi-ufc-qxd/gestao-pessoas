$(document).ready(function() {
	
	$(".filtroAno").mask("9999");

	moment.locale('pt-br');
	$('#current-data').val(moment());
	$('label#periodo-dia').text(moment().format("DD/MM/YYYY"));
	
	$('#turmaFiltro').selectpicker('hide');
	$('#statusReposicao').selectpicker('hide');
	$("#viewFrequencias").addClass('hidden');

	// CAMPOS DO FILTRO
	$(".filtroAno").keyup(function (event) {
	    var maximoDigitosAno = 4;
	    var lengthAno = $(this).val().length;

	    if ( lengthAno <= maximoDigitosAno && !isNaN($(this).val()) ) {
			filtroTurma();
	    }
	});
	
	$('.filtroSemestre').change(function(event) {
		filtroTurma();
	});

	$('.filtroTurma').change(function(event) {
		filtroTurma();
	});

	$('.frequenciasTurma').change(function(event) {
		var turma = $('#turmaFiltro').val();		

		if(!isNaN(turma)){
			var date = moment($('#current-data').val());
			loadFrequenciaTurma(date, turma);
		}
	});

	$('#turmaEstagiarios').hide();

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

	$("#turmaFiltro").change(function(event) {
		if(isNaN($('#turmaFiltro').val())){
			$("#viewFrequencias").addClass('hidden');
			$("#viewEstagiarios").addClass('hidden');
		}else{
			$("#viewFrequencias").removeClass('hidden');
			$("#viewEstagiarios").removeClass('hidden');
		}
	});
	

});

function filtroTurma() {
	var ano = $("#anoFiltro").val().trim();
	var semestre = $("#semestreFiltro").val();

	if (ano != '' && semestre != '') {
		loadTurmasByPeriodo(ano, semestre);
	} else {
		$('#turmaFiltro').selectpicker('hide');
		$('.reposicao').selectpicker('hide');
		$("#viewFrequencias").addClass('hidden');
	}
}

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
			
			$.each( result, function( key, value ) {
				console.log( key + ": " + value );
				console.log( result[0][0] );
			});
			
			
			lBootgrid(result);
			
			
			$('#turmaFiltro').find('option[value="' + turma + '"]').attr('selected', true);
			$('.selectpicker').selectpicker('refresh');
			
			loadDataTable(result);
			
		},
		error: function(error) {
			console.log('ERROR: ' + error);
		}
	});
}



/* ANALISE */


function lBootgrid(result, table) {
	$.each( result, function( key, value ) {
		console.log( key + ": " + value );
		console.log( result[0][0] );
	});
	
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
	        	"observacao": function(column, row) {
	    			$.each( column, function( key, value ) {
	    				console.log("col : " + key + ": " + value );
	    			});	        		
	    			$.each( row, function( key, value ) {
	    				console.log("row : " + key + ": " + value );
	    			});
	    			
	    			if( row[2] == null){
		    			return "<a href=\"#\" class=\"observacao\" data-pk=\"" + row[0] + "\"></a>";
	    			}else{
		    			return "<a href=\"#\" class=\"observacao\" data-pk=\"" + row[0] + "\">" + row[2] + "</a>";
	    			}
	        	},
	        	"status" : function(column, row) {
	        		return "<a href=\"#\" class=\"status\" data-pk=\"" + row[0] + "\">" + row[3] + "</a>";
				}
	        
	        }
		}).bootgrid("clear").bootgrid("append", result).on("loaded.rs.jquery.bootgrid", function (e){
			 $.fn.editable.defaults.mode = 'poupop';
	    
		    $('.observacao').editable({
		    	url : '/gestao-pessoas/frequencia/observacao',
		    	title : 'Observaçao',
		    	type : 'textarea',
		        emptytext : "faça sua observação",
		    });
		    
		    $('.status').editable({
		    	url : '/gestao-pessoas/frequencia/atualizarStatus',
		        type: 'select',
		        title: 'Presença',
		        placement: 'top',
		        value: 'ATRASADO',
		        source: [
	                 {value: 'ABONADO', text: 'Abonado' },
	                 {value: 'ATRASADO', text: 'Atrasado'},
	               	 {value: 'FALTA', text: 'Falta'} 
		        ]
		    });
		});
}





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
			
			$('#turmaFiltro').find('option[value="' + turma + '"]').attr('selected', true);
			$('.selectpicker').selectpicker('refresh');
			alert('OI ' + turma)
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
}
