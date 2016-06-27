$(".gp-btn-presenca").on("click", function(event) {
	event.preventDefault();
	
	var botaoPresenca = $(event.currentTarget); 
	var urlPresenca = botaoPresenca.attr("href");
	
	var response = $.ajax({
	    url: urlPresenca,
	    type: 'GET'
	});
	

    response.done(function(resultadoPresenca) {
    	if(resultadoPresenca) {
    		swal("Bom trabalho!", "Presença realizada!", "success");
    	}
    	else {
        	swal("Opss!", "Presença não permitida, fale com o supervisor(a).", "error");
    	}

    	botaoPresenca.hide();
    });

    response.fail(function(e) {
    	swal("Opss!", "Presença não permitida, fale com o supervisor(a).", "error");
    });

});
