$(document).ready(function() {
	
	/* FILTRO JSON PARA FREQUENCIAS */

	$('#turmaJson').selectpicker('hide');
	$("#viewFrequencias").hide();
	moment.locale('pt-br');
	$('#current-data').val(moment());
	$('label#periodo-dia').text(moment().format("DD/MM/YYYY"));
	
	if ( sessionStorage.getItem('ano') || sessionStorage.getItem('semestre') ) {
		sessionStorage.setItem('ano', $("#anoJson").val());
		sessionStorage.setItem('semestre', $("#semestreJson").val());

		console.log("sessionStorage ano: " + sessionStorage.getItem('ano'));
		console.log("sessionStorage semestre: " + sessionStorage.getItem('semestre'));	
	}
		
	$("#anoJson").val(sessionStorage.getItem('ano'));
	$("#semestreJson").val(sessionStorage.getItem('semestre'));
	$('.selectpicker').selectpicker('refresh')	
	
	console.log("Session Storage: " + sessionStorage.getItem('ano') + " - " + sessionStorage.getItem('semestre'));

	$('#periodo-dia span#before').click(function(event) {
		var date = moment($('#current-data').val()).add(-1, 'days');
		var turma = $('#turmaJson').val();
		console.log('antes :' + date + " - " + turma);
		loadFrequenciaTurma(date, turma);
	});
	
	$('#periodo-dia span#after').click(function(event) {
		var date = moment($('#current-data').val()).add(1, 'days');
		var turma = $('#turmaJson').val();
		console.log('depois :' + date + " - " + turma);
		loadFrequenciaTurma(date, turma);
	});	
	
	$('#turmaJson').change(function(event) {
		var turma = $('#turmaJson').val();		

		if(!isNaN(turma)){
			var date = moment($('#current-data').val());
			console.log('É numero:' + date + " - " + turma);
			loadFrequenciaTurma(date, turma);
		}
	});
	
	$('.filtroJsonFrequencia').change(function(event) {
		
		sessionStorage.setItem('ano', $("#anoJson").val());
		sessionStorage.setItem('semestre', $("#semestreJson").val());
		
		var ano = $("#anoJson").val().trim();
		var semestre = $('#semestreJson').val().trim();

		sessionStorage.setItem('ano', ano);
		sessionStorage.setItem('semestre', semestre);

		console.log('filtroJsonFrequencia :' + ano + " - " + semestre);

		if(ano != '' && semestre != '' ){
			loadTurmasPeriodoJson(ano, semestre);
		}else{
			$('#turmaJson').selectpicker('hide');
			$("#viewFrequencias").hide();
		}
	});
	
	$("#turmaJson").change(function(event) {
		if(isNaN($('#turmaJson').val())){
			$("#viewFrequencias").hide();
		}else{
			$("#viewFrequencias").show();
		}
	});


	
	
	
	
	
	
	
	
	
	/* FILTRO PARA ESTAGIARIOS */

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

		if(isNaN(turma)){
			$("#turma").selectpicker('hide');
		}else{
			loadEstagiariosTurma(turma);
			$("#turma").selectpicker('show');
		}

	
	});	
	
});
	
/* FUNCTIONS FILTRO JSON PARA FREQUENCIAS */

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

function loadTurmasPeriodoJson(ano, semestre) {
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
			$('#turmaJson').selectpicker('hide');
			$("#viewFrequencias").hide();
		}
	});
}

function loadSelectTurma(result) {
	console.log(' result.length ' + result.length);
	
	if(result.length < 1){
		$('#turmaJson').selectpicker('hide');
		$("#viewFrequencias").hide();
	}
	
	$("#turmaJson").children().remove();
	var option = $("<option>");
	option.text("Turma");
	$("#turmaJson").append(option);
	$.each(result, function(i, turma) {
		var option = $("<option>");
		console.log(i + " turma " + turma[1]);
		option.attr("value", turma[0]).text(turma[1]);
		$("#turmaJson").append(option);
	});
	$('#turmaJson').selectpicker('refresh');
	if($("#turmaJson option").length > 1){
		$('#turmaJson').selectpicker('show');
	}else{
		$('#turmaJson').selectpicker('hide');
	}
	$('#turmaJson').selectpicker('refresh');

}

/* FUNCTIONS FILTRO PARA ESTAGIARIOS */
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



























/* ANALISE */




function loadEstagiarios(result) {
	$("#meus-projetos").html($(result).find("#meus-projetos"));
}

function showTurmas(result) {
	$("#turma").append($("<option>").text("Turma"));		
	$(".turma").html($(result).find("#turma"));
	console.log('TURMA = ' + $("#turma").html());
	if($("#turma").empty()){
		$("#turma").find("option").remove();
		$('#turma').hide();
		console.log('sim' + result);
	}else{
		$('#turma').show();
		console.log('não');
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
			 $.fn.editable.defaults.mode = 'inline';     
			 
			 //console.log("row: " + row.id);
		    
		    //make username editable
//		    $('.observacao').editable({
//		    	url : '/gestao-pessoas/frequencia/observacao',
//		    	title : 'Observaçao',
//		    	type : 'textarea'
//		    });
//		    
		    //make status editable
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
    console.log("id id id id id id id = " + id);
    $('.observacao').editable({
    	url : '/gestao-pessoas/frequencia/observacao',
    	title : 'Observaçao',
    	type : 'textarea',
    	params: function(params) {  //params already contain `name`, `value` and `pk`
    		params.pk = id;
    	    return params;
    	  }
    });

}


/* ANALISE *//* ANALISE *//* ANALISE *//* ANALISE *//* ANALISE *//* ANALISE */





/* ANALISE *//* ANALISE *//* ANALISE *//* ANALISE *//* ANALISE *//* ANALISE */









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

