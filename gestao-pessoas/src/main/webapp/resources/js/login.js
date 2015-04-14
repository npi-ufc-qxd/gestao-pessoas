$(document).ready(function() {
	$("#cadastrar").hide();
	$(".cpf").mask("99999999999");
	
	$('.message-field').addClass('hidden');

	$("#cadastre-se").click(function(){
		$("#login-form").hide();
		$("#cadastrar").show();
		$("#title").text("Cadastre-se");
	});

	$("#logar").click(function(){
		$("#title").text("Faça seu login");
		$("#login-form").show();
		$("#cadastrar").hide();
	});
	
	$('#form-login').validate({
        rules: {
            
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
            $("#erro").remove();
            $("#error").removeClass('hidden');
        },
        messages:{
        	j_username:{
        		required: ''
            },
            j_password:{
            	required: ''
            },
        }
    });
});
