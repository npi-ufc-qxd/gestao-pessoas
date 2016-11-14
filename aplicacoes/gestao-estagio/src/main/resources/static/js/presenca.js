$(".gp-btn-presenca-entrada").on("click", function(event) {
	event.preventDefault();
	
	var botaoPresenca = $(event.currentTarget); 
	var urlPresenca = botaoPresenca.attr("href");
	
	var response = $.ajax({
	    url: urlPresenca,
	    type: 'GET'
	});
	

    response.done(function(resultadoPresenca) {
    	if(resultadoPresenca) {
    		swal("Bom trabalho!", "Entrada realizada!", "success");
    	}
    	else {
        	swal("Opss!", "Fale com o supervisor(a).", "error");
    	}

    	botaoPresenca.hide();
    });

    response.fail(function(e) {
    	swal("Opss!", "Fale com o supervisor(a).", "error");
    });

});


$(".gp-btn-presenca-saida").on("click", function(event) {
	event.preventDefault();
	
	var botaoPresenca = $(event.currentTarget); 
	var urlPresenca = botaoPresenca.attr("href");
	
	var response = $.ajax({
	    url: urlPresenca,
	    type: 'GET'
	});
	

    response.done(function(resultadoPresenca) {
    	if(resultadoPresenca) {
    		swal("Bom descanso!", "Saida realizada!", "success");
    	}
    	else {
        	swal("Opss!", "Fale com o supervisor(a).", "error");
    	}

    	botaoPresenca.hide();
    });

    response.fail(function(e) {
    	swal("Opss!", "Fale com o supervisor(a).", "error");
    });

});
