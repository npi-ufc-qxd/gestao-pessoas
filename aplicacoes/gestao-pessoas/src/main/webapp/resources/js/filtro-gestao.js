$(document).ready(function() {
	
	$(".filtroAno").mask("9999");

	moment.locale('pt-br');
	$('#current-data').val(moment());
	$('label#periodo-dia').text(moment().format("DD/MM/YYYY"));
	
	$('#turmaFiltro').selectpicker('hide');
	$('#statusReposicao').selectpicker('hide');
	$("#viewFrequencias").addClass('hidden');

	$('#turmaEstagiarios').hide();
	//$('.selectpicker').selectpicker('refresh')
	$("#viewEstagiarios").addClass('hidden');
	//filtroTurma();
	
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

	$('#termos').click(function(event) {
		var turma = $('.estagiariosTurma').val().trim();

		if (!isNaN(turma)) {
//			var url = '/gestao-pessoas/coordenador/termos-compromisso-estagio/' + turma;

			windonw .location = 'http://localhost:8080/gestao-pessoas/coordenador/termos-compromisso-estagio/' + turma ;
			var url = 'http://www.w3schools.com/';
			$(location).attr('href',url);			
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

	$('.reposicaoTurma').change(function(event) {
		turma = $(this).val().trim();

		if (isNaN(turma)) {
			$("#statusReposicao").selectpicker('hide');
		} else {
			$("#statusReposicao").selectpicker('show');
			$("#viewEstagiarios").addClass('hidden');
		}
	});

	$('.filtroReposicaoStatus').change(function(event) {
		var status = $(this).val().trim();

		if (status === 'ATRASADO') {
			loadEstagiariosTurma(turma, '/gestao-pessoas/frequencia/reposicao-atraso');
		} else if (status === 'FALTA') {
			loadEstagiariosTurma(turma, '/gestao-pessoas/frequencia/reposicao-falta');
		} else {
			$("#viewEstagiarios").addClass('hidden');
		}

		

	});	

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

	$(".turmaFiltro").change(function(event) {
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
	var turma = $('.selectpicker').val();
	
	$('#turmaFiltro').find('option[value="' + turma + '"]').attr('selected', true);
	$('.selectpicker').selectpicker('refresh');
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
			
			//loadDataTable(result);
			
		},
		error: function(error) {
			console.log('ERROR: ' + error);
		}
	});
}



/* ANALISE */


function lBootgrid(result, table) {
	$.each( result, function( key, value ) {
		console.log('result ' + key + ": " + value );
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
	    			if( row[2] == null)
		    			return ;
	    			else
		    			return "<a href=\"#\" class=\"observacao\" data-pk=\"" + row[0] + "\">" + row[2] + "</a>";
	        	},
	        	"status" : function(column, row) {
	    			if( row[3] == null)
		        		return ;

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

			$('#relatorios').removeClass('hidden');
			$("#termos").attr('href', '/gestao-pessoas/coordenador/tce-turma/' + turma);
			$("#declaracoes").attr('href', '/gestao-pessoas/coordenador/declaracoes-turma/' + turma);
			
			$('#turmaFiltro').find('option[value="' + turma + '"]').attr('selected', true);
			$('#turmaFiltro').selectpicker('refresh');
			$(".data").datepicker({
				language: 'pt-BR',
				autoclose: true,
				format: "dd/mm/yyyy",
				orientation: "top auto",
			});
			$('.agendarreposicao').on('click', function(event) {
				var estagiario = $(this).data('estagiario');
				var dataReposicao = $($(this).data('input')).val();
				var li = $(this).data('li');
				var status = $('#statusReposicao').val().trim();
				console.log('LI = ' + li);
				console.log('dataReposicao = ' + dataReposicao);
				console.log('status = ' + status);
				console.log('turma = ' + turma);
				console.log('estagiario = ' + estagiario);
				
				agendarReposicao(turma, estagiario, status, dataReposicao, li);
				
			});			
		},
		error: function(error) {
			$('#turma').hide();
		}
	});
}

function agendarReposicao(turma, estagiario, status, dataReposicao, li) {

	var json =  {
		"idEstagiario" : estagiario,
		"status" : status,
		"dataReposicao" : dataReposicao,
	}
	
	$.ajax({
		type: "POST",
		 headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },		
		data: JSON.stringify(json),
	    dataType: 'json',
		url: '/gestao-pessoas/frequencia/agendar-reposicao.json',
		success: function(result) {
			if(result.errorMessage && result.errorMessage.length > 0) {
				$(li).find('.form-group').addClass('has-error');
				$(li).find('.error-message').text(result.errorMessage);
			} else {
				$(li).find('input').remove();
				$(li).find('.btn').remove();
				$(li).find('.error-message').remove();
				$(li).find('.sucesso-message').addClass('alert-success').text(result.message);
			}
			
		},
		error: function(error) {
		}
	});
}

function loadEstagiarios(result) {	
	$("#viewEstagiarios").html($(result).find("#viewEstagiarios"));
	$("#viewEstagiarios").removeClass('hidden');
}
