$(document).ready(function() {
	$('#turmaF').selectpicker('hide');
	$("#viewFrequencias").hide();
	moment.locale('pt-br');
	
	//loadFreqByDateAndTurma(moment(), -1);
	$('label#periodo-dia').text(moment().format("DD/MM/YYYY"));
	$('#current-data').val(moment());
	
	//$("#anoF option[value='" + localStorage.getItem('ano') + "']");
	
	if (sessionStorage.getItem('ano')) {
		sessionStorage.setItem('ano', $("#anoF").val());
		console.log("sesese: " + sessionStorage.getItem('ano'));
	}

	if (sessionStorage.getItem('semestre')) {
		sessionStorage.setItem('semestre', $("#semestreF").val());
		console.log("ififif: " + sessionStorage.getItem('semestre'));	
	}

	
	$("#anoF").val(sessionStorage.getItem('ano'));
	$("#semestreF").val(sessionStorage.getItem('semestre'));
	
	
	console.log("Session Store: " + sessionStorage.getItem('ano') + " - " + sessionStorage.getItem('semestre'));
	
	$('.selectpicker').selectpicker('refresh')	
	
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
	
	$('#turmaF').change(function(event) {
		var date = moment($('#current-data').val());
		var turma = $('#turmaF').val();
		console.log('turma turma depois :' + date + " - " + turma);
		loadFreqByDateAndTurma(date, turma);
	});
	
//	$('#anoF').change(function(event) {
//		sessionStorage.setItem('ano', $("#anoF").val());
//		console.log("Ano: " + localStorage.getItem('ano'));
//		
//	});
//
//	$('#semestreF').change(function(event) {
//		sessionStorage.setItem('semestre', $("#semestreF").val());
//		console.log("Semestre: " + localStorage.getItem('semestre'));
//	});

	$('.filtroFrequencia').change(function(event) {
		
		sessionStorage.setItem('ano', $("#anoF").val());
		sessionStorage.setItem('semestre', $("#semestreF").val());
		
		var date = moment($('#current-data').val());
		var ano = $("#anoF").val().trim();
		var semestre = $('#semestreF').val().trim();

		sessionStorage.setItem('ano', ano);
		sessionStorage.setItem('semestre', semestre);

		console.log('filtroFrequencia :' + date + " - " + ano + " - " + semestre);
		if(ano != '' && semestre != '' ){
			loadTurma(ano, semestre);
		}
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
	console.log(' result.length ' + result.length);
	
	if(result.length < 1){
		$('#turmaF').selectpicker('hide');
		$("#viewFrequencias").hide();
	}
	
	$("#turmaF").children().remove();
	var option = $("<option>");
	option.text("Turma");
	$("#turmaF").append(option);
	$.each(result, function(i, turma) {
		var option = $("<option>");
		console.log(i + " turma " + turma[1]);
		option.attr("value", turma[0]).text(turma[1]);
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
	        		console.log("cssClass: " + column.cssClass  +" acoes obs " + row.observacao )
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
