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

	$('.estagiariosTurma').change(function(event) {
		turma = $(this).val().trim();

		if (!isNaN(turma)) {
			loadEstagiariosTurma(turma, '/gestao-pessoas/coordenador/estagiarios-turma');
		}
	});	

	$('.estagiariosProjeto').change(function(event) {
		turma = $(this).val().trim();

		if (isNaN(turma)) {
			$("#turma").selectpicker('hide');
		} else {
			loadEstagiariosTurma(turma, '/gestao-pessoas/coordenador/' + $("#idProjeto").val() + '/add-membros-projeto');
			$("#turma").selectpicker('show');
		}
	});	

	$('.frequenciasTurma').change(function(event) {
		var turma = $('#turmaFiltro').val();		

		if(!isNaN(turma)){
			var date = moment($('#current-data').val());
			loadFrequenciaTurma(date, turma);
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


	
	
	
	
//	if ($("#anoFiltro").val() != '' && $("#semestreFiltro").val() != '') {
//		sessionStorage.setItem('ano', $("#anoFiltro").val());
//		sessionStorage.setItem('semestre', $("#semestreFiltro").val());
//	}
//	if (sessionStorage.getItem('ano') && sessionStorage.getItem('semestre')) {
//		$("#anoFiltro").val(sessionStorage.getItem('ano'));
//		$("#semestreFiltro").val(sessionStorage.getItem('semestre'));
//	}

	/* FILTRO ESTAGIARIO */

	$('#turmaEstagiarios').hide();


	


	$('.reposicaoTurma').change(function(event) {
		turma = $(this).val().trim();

		if (isNaN(turma)) {
			$("#turma").selectpicker('hide');
		} else {
			$("#statusReposicao").selectpicker('show');
		}
	});
	


	
	
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
			lBootgrid(result);
			$('#turmaFiltro').find('option[value="' + turma + '"]').attr('selected', true);
			$('.selectpicker').selectpicker('refresh');
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
	        	"acoes": function(column, row) {
	        		return "Ações";
	        	}
	        }
		}).bootgrid("clear").bootgrid("append", result).on("loaded.rs.jquery.bootgrid", function (e){
			 $.fn.editable.defaults.mode = 'poupop';     
	    
		    $('.observacao').editable({
		    	url : '/gestao-pessoas/frequencia/observacao',
		    	title : 'Observaçao',
		    	type : 'textarea'
		    });
		    
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
			
			$.each( cols, function( key, value ) {
				console.log( key + ": " + value );
			});			

			$.each( rows, function( key, value ) {
				console.log( key + ": " + value );
			});			

		    console.log("CLICKED/");
		    console.log("e = " + e);
		    console.log("cols = " + cols);
		    console.log("rows.id = " + rows[0]);
		    console.log("this = " + $(this).text());
		    
		    observar(rows[0]);
		});
}

function observar(id){
	alert('sfdgasdfgsadfd')
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