function toggleChevron(e) {
    $(e.target)
        .prev('.panel-heading')
        .find("i.fa")
        .toggleClass('fa-caret-right fa-chevron-down');
}
$('.panel-body').on('hidden.bs.collapse', toggleChevron);
$('.panel-body').on('shown.bs.collapse', toggleChevron);

$(".gp-btn-presenca").on("click", function() {
	swal("Bom trabalho!", "Presença realizada!", "success");
});

$( "#form-estagiario" ).validate({
    rules: {
	 semestre: {
		 required: true,
		 number: true,
		 min: 1,
		 max: 12,
	 }
    },
    highlight: function(element) {
        $(element).closest('.form-group').addClass('has-error');
    },
    unhighlight: function(element) {
        $(element).closest('.form-group').removeClass('has-error');
    },
    errorElement: 'span',
    errorClass: 'help-block',
    errorPlacement: function(error, element) {
        error.insertAfter(element.parent().children().last());
    },
    messages:{
		nomeCompleto : {
			required : "Campo obrigatório",
		},
		dataNascimento : {
			required : "Campo obrigatório",
		},
		nomeMae : {
			required : "Campo obrigatório",
		},
		matricula : {
			required : "Campo obrigatório",
		},
		endereco : {
			required : "Campo obrigatório",
		},
		cidade : {
			required : "Campo obrigatório",
		},
		uf : {
			required : "Campo obrigatório",
		},
		telefone : {
			required : "Campo obrigatório",
		},
		cep : {
			required : "Campo obrigatório",
		},
		email : {
			required : "Campo obrigatório",
		},
		semestre: {
			required : "Campo obrigatório",
        	number: "Informe um numero",
        	min: "Informe um semestre valido",
           	max: "Informe um semestre valido"
		},
	}
});	

$(document).ready(function(){
	$(".data").datepicker({
	    language: 'pt-BR',
	    autoclose: true,
	    format: "dd/mm/yyyy",
	    orientation: "top auto",
	});
});	  

$(document).ready(function(){
	  $('#dataNascimento').mask('00/00/0000');
	  $('#cep').mask('00000-000');
	  $('#telefone').mask('(88) 9 0000-0000');
});	  
