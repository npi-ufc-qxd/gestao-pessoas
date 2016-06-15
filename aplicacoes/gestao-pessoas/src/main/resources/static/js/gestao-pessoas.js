function toggleChevron(e) {
    $(e.target)
        .prev('.panel-heading')
        .find("i.fa")
        .toggleClass('fa-caret-right fa-chevron-down');
}
$('.panel-body').on('hidden.bs.collapse', toggleChevron);
$('.panel-body').on('shown.bs.collapse', toggleChevron);

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








response.done(function(e) {
    var codigoTitulo = botaoReceber.data('codigo');
    $('[data-role=' + codigoTitulo + ']').html('<span class="label label-success">' + e + '</span>');
    botaoReceber.hide();
});

response.fail(function(e) {
    console.log(e);
    alert('Erro recebendo cobrança');
});